package com.abhishek.zerodroid.features.deauth_detector.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.zerodroid.core.alerts.AlertCenterRepository
import com.abhishek.zerodroid.core.alerts.AlertSeverity
import com.abhishek.zerodroid.core.alerts.AlertSource
import com.abhishek.zerodroid.core.debug.DemoData
import com.abhishek.zerodroid.core.debug.DemoDataBus
import com.abhishek.zerodroid.core.debug.observeDemoRequests
import com.abhishek.zerodroid.features.deauth_detector.domain.AlertLevel
import com.abhishek.zerodroid.features.deauth_detector.domain.AttackType
import com.abhishek.zerodroid.features.deauth_detector.domain.DeauthEvent
import com.abhishek.zerodroid.features.deauth_detector.domain.DeauthState
import com.emptyset.detector.detect.Ieee80211
import com.emptyset.detector.radio.RadioBackend
import com.emptyset.detector.radio.RadioCatalog
import com.emptyset.detector.radio.RadioFactory
import com.emptyset.detector.radio.RadioKind
import com.emptyset.detector.radio.UsbDeviceFinder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeauthDetectorViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alertCenterRepository: AlertCenterRepository,
    private val demoBus: DemoDataBus
) : ViewModel() {

    private val _state = MutableStateFlow(DeauthState())
    val state: StateFlow<DeauthState> = _state.asStateFlow()

    private var monitorJob: Job? = null
    private var timerJob: Job? = null
    private var backend: RadioBackend? = null
    private val frameTimes = ArrayDeque<Long>()
    private var lastFloodAt = 0L
    private var monitoringStartTime = 0L

    fun startMonitoring() {
        if (monitorJob?.isActive == true) return
        monitoringStartTime = System.currentTimeMillis()
        _state.value = DeauthState(isMonitoring = true)

        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                val elapsed = System.currentTimeMillis() - monitoringStartTime
                _state.value = _state.value.copy(monitoringDurationMs = elapsed)
            }
        }

        monitorJob = viewModelScope.launch {
            val attached = UsbDeviceFinder.attached(context)
            val adapter = attached.firstOrNull { it.kind == RadioKind.T2U_PLUS && it.device != null }
                ?: attached.firstOrNull { it.kind == RadioKind.PAU0A && it.device != null }
            val device = adapter?.device
            if (adapter == null || device == null) {
                val onlyExcluded = attached.any { it.kind == RadioKind.PAU0B }
                fail(
                    if (onlyExcluded) {
                        "Panda PAU0B is not used. Plug in a TP-Link Archer T2U Plus or a Panda PAU0A."
                    } else {
                        "Plug in a TP-Link Archer T2U Plus or a Panda PAU0A."
                    }
                )
                return@launch
            }
            val option = RadioCatalog.option(
                if (adapter.kind == RadioKind.T2U_PLUS) RadioCatalog.T2U_PLUS else RadioCatalog.PAU0A
            )
            val radio = RadioFactory.create(context, option, device)
            if (radio == null) {
                fail("That adapter is not supported.")
                return@launch
            }
            backend = radio
            _state.value = _state.value.copy(adapterTitle = radio.info.title, isMonitoring = true, error = null)
            radio.start(object : RadioBackend.Listener {
                override fun onStatus(message: String) {
                    _state.value = _state.value.copy(statusNote = message, error = null)
                }

                override fun onChannel(channel: Int, band: com.emptyset.detector.detect.WifiBand) {
                    _state.value = _state.value.copy(listenChannel = channel)
                }

                override fun onRawFrame(frame: ByteArray, channel: Int?, rssiDbm: Int?) {
                    val parsed = Ieee80211.parse(frame, channel, rssiDbm) ?: return
                    val now = System.currentTimeMillis()
                    frameTimes.addLast(now)
                    while (frameTimes.isNotEmpty() && now - frameTimes.first() > 2_000L) frameTimes.removeFirst()
                    if (frameTimes.size >= 5 && now - lastFloodAt > 2_000L) {
                        lastFloodAt = now
                        record(
                            DeauthEvent(
                                type = AttackType.DEAUTH_FLOOD,
                                level = AlertLevel.HIGH,
                                title = "Repeated deauth or disassoc frames",
                                detail = "${frameTimes.size} frames in 2 seconds on channel ${parsed.channelHint ?: channel ?: "?"}.",
                                affectedSsid = null,
                                affectedBssid = null
                            )
                        )
                    }
                    val heard = _state.value.framesHeard + 1
                    _state.value = _state.value.copy(
                        framesHeard = heard,
                        listenChannel = parsed.channelHint ?: _state.value.listenChannel,
                        isUnderAttack = true
                    )
                    val type = if (parsed.subtype == Ieee80211.SUBTYPE_DEAUTH) {
                        AttackType.FRAME_DEAUTH
                    } else {
                        AttackType.FRAME_DISASSOC
                    }
                    record(
                        DeauthEvent(
                            type = type,
                            level = AlertLevel.HIGH,
                            title = "${parsed.kind} heard",
                            detail = "From ${parsed.addr2} to ${parsed.addr1}, ${parsed.reasonName}" +
                                (parsed.channelHint?.let { ", channel $it" } ?: "") +
                                (parsed.rssiDbm?.let { ", $it dBm" } ?: "") + ".",
                            affectedSsid = null,
                            affectedBssid = parsed.addr3
                        )
                    )
                }

                override fun onError(message: String) {
                    fail(message)
                }
            })
            if (_state.value.error == null) {
                _state.value = _state.value.copy(isMonitoring = false)
            }
        }
    }

    fun stopMonitoring() {
        monitorJob?.cancel()
        monitorJob = null
        timerJob?.cancel()
        timerJob = null
        backend?.stop()
        backend = null
        _state.value = _state.value.copy(isMonitoring = false)
    }

    fun clearEvents() {
        _state.value = _state.value.copy(
            events = emptyList(),
            framesHeard = 0,
            isUnderAttack = false
        )
    }

    private fun record(event: DeauthEvent) {
        val current = _state.value
        val events = (listOf(event) + current.events).take(40)
        _state.value = current.copy(events = events, isUnderAttack = true)
        viewModelScope.launch {
            alertCenterRepository.record(
                source = AlertSource.DEAUTH,
                severity = AlertSeverity.HIGH,
                title = event.title,
                detail = event.detail,
                timestamp = event.timestamp
            )
        }
    }

    private fun fail(message: String) {
        timerJob?.cancel()
        backend?.stop()
        backend = null
        _state.value = _state.value.copy(isMonitoring = false, error = message)
    }

    override fun onCleared() {
        stopMonitoring()
    }

    init {
        observeDemoRequests(demoBus, DemoData.Routes.DEAUTH) { loadDemoData() }
    }

    private fun loadDemoData() {
        stopMonitoring()
        _state.value = _state.value.copy(
            events = DemoData.deauthEvents,
            monitoringDurationMs = 180_000L,
            adapterTitle = "TP-Link Archer T2U Plus",
            listenChannel = 6,
            framesHeard = 4,
            isUnderAttack = true,
            error = null
        )
    }
}
