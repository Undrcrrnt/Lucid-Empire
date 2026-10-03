package com.abhishek.zerodroid.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.abhishek.zerodroid.core.hardware.HardwareChecker
import com.abhishek.zerodroid.core.ui.zd.ZdIcons

/** How the Tools screen groups tools: by the job people want done, not by radio. */
enum class ToolGroup(val label: String, val chip: String) {
    DETECT("Detect threats", "Detect threats"),
    SCAN("Scan & discover", "Scan"),
    ANALYZE("Analyze signals", "Analyze"),
    INTERACT("Read, write & transmit", "Read / write"),
    LOG("Log & map", "Log")
}

/** The hardware a tool needs, shown as the tag on its row and used to dim unsupported tools. */
enum class HardwareRequirement(val tag: String, val missingLabel: String?) {
    WIFI("WIFI", "no WiFi"),
    BLE("BLE", "no Bluetooth LE"),
    BT("BT", "no Bluetooth"),
    LAN("LAN", "no WiFi"),
    NAN("NAN", "no Wi-Fi Aware"),
    P2P("P2P", "no Wi-Fi Direct"),
    CELL("CELL", "no cellular radio"),
    GPS("GPS", "no GPS"),
    OTG("OTG", "no USB host"),
    CAM("CAM", "no camera"),
    MIC("MIC", null),
    MAG("MAG", "no magnetometer"),
    IMU("IMU", "no accelerometer"),
    UWB("UWB", "no UWB"),
    NFC("NFC", "no NFC"),
    IR("IR", "no IR blaster"),
    MULTI("MULTI", null),
    NONE("—", null);

    fun isAvailable(hw: HardwareChecker): Boolean = when (this) {
        WIFI, LAN -> hw.hasWifi()
        BLE -> hw.hasBluetoothLe()
        BT -> hw.hasBluetooth()
        NAN -> hw.hasWifiAware()
        P2P -> hw.hasWifiDirect()
        CELL -> hw.hasTelephony()
        GPS -> hw.hasGps()
        OTG -> hw.hasUsbHost()
        CAM -> hw.hasCamera()
        MAG -> hw.hasMagnetometer()
        IMU -> hw.hasAccelerometer()
        UWB -> hw.hasUwb()
        NFC -> hw.hasNfc()
        IR -> hw.hasIr()
        MIC, MULTI, NONE -> true
    }
}

data class ToolInfo(
    val screen: LucidEmpireScreen,
    val name: String,
    /** One line on the job the tool does, in plain words. */
    val job: String,
    /** Terminal path shown in the header, e.g. `/tools/wifi`. */
    val path: String,
    val group: ToolGroup,
    val requirement: HardwareRequirement,
    val icon: ImageVector
) {
    val route: String get() = screen.route
}

object ToolCatalog {

    val tools: List<ToolInfo> by lazy {
        listOf(
            // Detect threats
            ToolInfo(LucidEmpireScreen.BluetoothTracker, "Tracker Scanner", "Find AirTags, Tiles and SmartTags near you", "/tools/trackers", ToolGroup.DETECT, HardwareRequirement.BLE, ZdIcons.Tracker),
            ToolInfo(LucidEmpireScreen.HiddenCamera, "Hidden Camera", "Check a room: WiFi OUI, BLE, magnetic, ports", "/tools/hidden-camera", ToolGroup.DETECT, HardwareRequirement.MULTI, ZdIcons.Camera),
            ToolInfo(LucidEmpireScreen.RogueAp, "Rogue AP", "Spot evil twins and karma hotspots", "/tools/rogue-ap", ToolGroup.DETECT, HardwareRequirement.WIFI, ZdIcons.Wifi),
            ToolInfo(LucidEmpireScreen.DeauthDetector, "Deauth Detector", "Notice floods kicking you off WiFi", "/tools/deauth", ToolGroup.DETECT, HardwareRequirement.WIFI, ZdIcons.Warning),
            ToolInfo(LucidEmpireScreen.CellTower, "Cell Tower", "Watch for IMSI-catcher indicators", "/tools/cell", ToolGroup.DETECT, HardwareRequirement.CELL, ZdIcons.CellTower),
            ToolInfo(LucidEmpireScreen.GpsSpoofDetector, "GPS Spoof", "Cross-check GPS against cell, WiFi, sensors", "/tools/gps-spoof", ToolGroup.DETECT, HardwareRequirement.GPS, ZdIcons.Crosshair),
            ToolInfo(LucidEmpireScreen.RfBugSweeper, "RF Bug Sweeper", "BLE + ultrasonic + magnetic sweep", "/tools/rf-bug-sweeper", ToolGroup.DETECT, HardwareRequirement.MULTI, ZdIcons.Sweep),
            ToolInfo(LucidEmpireScreen.Usb, "USB Devices", "Inspect USB, flag BadUSB combos", "/tools/usb", ToolGroup.DETECT, HardwareRequirement.OTG, ZdIcons.Usb),
            ToolInfo(LucidEmpireScreen.Camera, "QR Scanner", "Check codes for phishing before opening", "/tools/qr", ToolGroup.DETECT, HardwareRequirement.CAM, ZdIcons.Qr),
            ToolInfo(LucidEmpireScreen.PrivacyScore, "Privacy Score", "16+ checks on your phone’s exposure", "/tools/privacy", ToolGroup.DETECT, HardwareRequirement.NONE, ZdIcons.ShieldCheck),

            // Scan & discover
            ToolInfo(LucidEmpireScreen.Wifi, "WiFi Analyzer", "Networks, channels and weak security", "/tools/wifi", ToolGroup.SCAN, HardwareRequirement.WIFI, ZdIcons.Wifi),
            ToolInfo(LucidEmpireScreen.Ble, "BLE Scanner", "Devices, GATT explorer, JSON dumps", "/tools/ble", ToolGroup.SCAN, HardwareRequirement.BLE, ZdIcons.Bluetooth),
            ToolInfo(LucidEmpireScreen.BluetoothClassic, "Bluetooth Classic", "Discovery, SDP services, SPP serial", "/tools/bt-classic", ToolGroup.SCAN, HardwareRequirement.BT, ZdIcons.Bluetooth),
            ToolInfo(LucidEmpireScreen.NetworkScanner, "Network Scanner", "Hosts, open ports, banners on your LAN", "/tools/network", ToolGroup.SCAN, HardwareRequirement.LAN, ZdIcons.Lan),
            ToolInfo(LucidEmpireScreen.ProximityRadar, "Proximity Radar", "Plot nearby devices by distance", "/tools/radar", ToolGroup.SCAN, HardwareRequirement.BLE, ZdIcons.Sweep),
            ToolInfo(LucidEmpireScreen.WifiAware, "Wi-Fi Aware", "Router-less NAN peer discovery", "/tools/wifi-aware", ToolGroup.SCAN, HardwareRequirement.NAN, ZdIcons.Wifi),
            ToolInfo(LucidEmpireScreen.Sdr, "SDR Radio", "Detect RTL-SDR, HackRF, AirSpy", "/tools/sdr", ToolGroup.SCAN, HardwareRequirement.OTG, ZdIcons.Radio),
            ToolInfo(LucidEmpireScreen.UsbCamera, "USB Camera", "UVC camera modes and preview", "/tools/usb-camera", ToolGroup.SCAN, HardwareRequirement.OTG, ZdIcons.Camera),

            // Analyze signals
            ToolInfo(LucidEmpireScreen.Ultrasonic, "Ultrasonic", "18–24 kHz spectrum for beacons", "/tools/ultrasonic", ToolGroup.ANALYZE, HardwareRequirement.MIC, ZdIcons.Waveform),
            ToolInfo(LucidEmpireScreen.EmfMapper, "EMF Mapper", "Magnetic field map and hotspots", "/tools/emf", ToolGroup.ANALYZE, HardwareRequirement.MAG, ZdIcons.Magnet),
            ToolInfo(LucidEmpireScreen.Sensors, "Sensor Dashboard", "Motion, compass, level, metal detector", "/tools/sensors", ToolGroup.ANALYZE, HardwareRequirement.IMU, ZdIcons.Sensors),
            ToolInfo(LucidEmpireScreen.Gps, "GPS Tracker", "Position, satellites, raw NMEA", "/tools/gps", ToolGroup.ANALYZE, HardwareRequirement.GPS, ZdIcons.Crosshair),
            ToolInfo(LucidEmpireScreen.Uwb, "UWB Radar", "FiRa ranging and AoA capability", "/tools/uwb", ToolGroup.ANALYZE, HardwareRequirement.UWB, ZdIcons.Sweep),

            // Read, write & transmit
            ToolInfo(LucidEmpireScreen.Nfc, "NFC Tools", "Read, write, dump MIFARE, emulate", "/tools/nfc", ToolGroup.INTERACT, HardwareRequirement.NFC, ZdIcons.Nfc),
            ToolInfo(LucidEmpireScreen.Ir, "IR Remote", "TV remotes, Flipper .ir import", "/tools/ir", ToolGroup.INTERACT, HardwareRequirement.IR, ZdIcons.Remote),
            ToolInfo(LucidEmpireScreen.WifiDirect, "Wi-Fi Direct", "P2P groups and file transfer", "/tools/wifi-direct", ToolGroup.INTERACT, HardwareRequirement.P2P, ZdIcons.Peers),

            // Log & map
            ToolInfo(LucidEmpireScreen.SignalLogger, "Signal Logger", "WiFi + BLE arrival/departure timeline", "/tools/signal-logger", ToolGroup.LOG, HardwareRequirement.MULTI, ZdIcons.Timeline),
            ToolInfo(LucidEmpireScreen.Wardriving, "Wardriving", "GPS + WiFi logging, WiGLE export", "/tools/wardriving", ToolGroup.LOG, HardwareRequirement.GPS, ZdIcons.Map)
        )
    }

    private val byRoute: Map<String, ToolInfo> by lazy { tools.associateBy { it.route } }

    fun forRoute(route: String?): ToolInfo? = route?.let { byRoute[it] }

    /** Pinned to Home until the user picks their own (onboarding goals set these later). */
    val defaultPinnedRoutes: List<String> = listOf(
        LucidEmpireScreen.Wifi.route,
        LucidEmpireScreen.BluetoothTracker.route,
        LucidEmpireScreen.Ble.route,
        LucidEmpireScreen.Nfc.route
    )
}

/** Filters the catalog the way the Tools screen does: by group chip, supported-only, and text. */
fun filterTools(
    tools: List<ToolInfo>,
    group: ToolGroup?,
    query: String,
    onlySupported: Boolean,
    isAvailable: (ToolInfo) -> Boolean
): List<ToolInfo> {
    val q = query.trim().lowercase()
    return tools.filter { tool ->
        (group == null || tool.group == group) &&
            (!onlySupported || isAvailable(tool)) &&
            (q.isEmpty() || "${tool.name} ${tool.job} ${tool.requirement.tag}".lowercase().contains(q))
    }
}
