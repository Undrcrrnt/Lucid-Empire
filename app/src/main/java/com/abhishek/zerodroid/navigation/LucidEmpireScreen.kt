package com.abhishek.zerodroid.navigation

/**
 * Every navigation destination. Tool metadata (job, icon, hardware, group) lives in
 * [ToolCatalog]; this only names routes and titles.
 */
sealed class LucidEmpireScreen(
    val route: String,
    val title: String
) {
    // Top-level tabs
    data object Dashboard : LucidEmpireScreen("dashboard", "Home")
    data object Tools : LucidEmpireScreen("tools", "Tools")
    data object AlertCenter : LucidEmpireScreen("alert_center", "Alerts")
    data object Sessions : LucidEmpireScreen("sessions", "Sessions")
    data object Sweep : LucidEmpireScreen("sweep", "Sweep")

    // Pushed screens
    data object Search : LucidEmpireScreen("search", "Search")
    data object Settings : LucidEmpireScreen("settings", "Settings")

    // Tools
    data object Sensors : LucidEmpireScreen("sensors", "Sensor Dashboard")
    data object Wifi : LucidEmpireScreen("wifi", "WiFi Analyzer")
    data object Ble : LucidEmpireScreen("ble", "BLE Scanner")
    data object Nfc : LucidEmpireScreen("nfc", "NFC Tools")
    data object Ir : LucidEmpireScreen("ir", "IR Remote")
    data object Uwb : LucidEmpireScreen("uwb", "UWB Radar")
    data object Usb : LucidEmpireScreen("usb", "USB Devices")
    data object Sdr : LucidEmpireScreen("sdr", "SDR Radio")
    data object Camera : LucidEmpireScreen("camera", "QR Scanner")
    data object Ultrasonic : LucidEmpireScreen("ultrasonic", "Ultrasonic")
    data object Wardriving : LucidEmpireScreen("wardriving", "Wardriving")
    data object WifiAware : LucidEmpireScreen("wifi_aware", "Wi-Fi Aware")
    data object CellTower : LucidEmpireScreen("cell_tower", "Cell Tower")
    data object UsbCamera : LucidEmpireScreen("usb_camera", "USB Camera")
    data object Gps : LucidEmpireScreen("gps", "GPS Tracker")
    data object BluetoothClassic : LucidEmpireScreen("bluetooth_classic", "Bluetooth Classic")
    data object WifiDirect : LucidEmpireScreen("wifi_direct", "Wi-Fi Direct")
    data object HiddenCamera : LucidEmpireScreen("hidden_camera", "Camera Detector")
    data object GpsSpoofDetector : LucidEmpireScreen("gps_spoof_detector", "GPS Spoof Detector")
    data object BluetoothTracker : LucidEmpireScreen("bluetooth_tracker", "Tracker Scanner")
    data object RogueAp : LucidEmpireScreen("rogue_ap", "Rogue AP Detector")
    data object NetworkScanner : LucidEmpireScreen("network_scanner", "Network Scanner")
    data object RfBugSweeper : LucidEmpireScreen("rf_bug_sweeper", "RF Bug Sweeper")
    data object ProximityRadar : LucidEmpireScreen("proximity_radar", "Proximity Radar")
    data object PrivacyScore : LucidEmpireScreen("privacy_score", "Privacy Score")
    data object DeauthDetector : LucidEmpireScreen("deauth_detector", "Deauth Detector")
    data object EmfMapper : LucidEmpireScreen("emf_mapper", "EMF Mapper")
    data object SignalLogger : LucidEmpireScreen("signal_logger", "Signal Logger")

    companion object {
        // Lazy: an eager list here can capture nulls when a subclass object initializes first.
        val all: List<LucidEmpireScreen> by lazy {
            listOf(
                Dashboard, Tools, AlertCenter, Sessions, Sweep, Search, Settings,
                Sensors, Wifi, Ble, Nfc, Ir, Uwb,
                Usb, Sdr, Camera, Ultrasonic, Wardriving, WifiAware,
                CellTower, UsbCamera, Gps, BluetoothClassic, WifiDirect, HiddenCamera,
                GpsSpoofDetector, BluetoothTracker, RogueAp, NetworkScanner, RfBugSweeper, ProximityRadar,
                PrivacyScore, DeauthDetector, EmfMapper, SignalLogger
            )
        }
    }
}

/** Route to run a sweep; a blank place is sent as a space because path segments can't be empty. */
fun sweepRunRoute(preset: com.abhishek.zerodroid.features.sweep.domain.SweepPreset, place: String): String =
    "sweep/run/${preset.name}/${place.trim().let { if (it.isEmpty()) "%20" else android.net.Uri.encode(it) }}"
