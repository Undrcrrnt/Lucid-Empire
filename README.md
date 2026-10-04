<h1 align="center">Lucid Empire</h1>

<p align="center">
  A security research and analysis toolkit for Android.
</p>

<p align="center">
  <strong>Security research and analysis toolkit.</strong><br/>
  Turn your phone into a portable RF lab, network analyzer, and security auditor — <b>29 tools in one app</b>.
</p>

<p align="center">
  <sub>Replaces Termux + a dozen single-purpose scanner apps with one native, offline, permission-scoped toolkit.</sub>
</p>

---

## ⚡ At a Glance

Lucid Empire exposes every radio, sensor, and port your phone has — WiFi, Bluetooth, BLE, NFC, IR, UWB, USB, GPS, cellular, magnetometer, barometer, microphone — through **29 specialized tools** wrapped in a terminal-hacker UI.

|  |  |
|---|---|
| 🧰 **29 tools** | Across 5 categories: Wireless · RF & Signals · Sensors · Network · Security |
| 📡 **Every radio** | WiFi, BLE, Classic BT, NFC, IR, UWB, SDR, cellular, GPS |
| 🔋 **Zero idle battery** | No auto-start scanning, everything auto-stops, all services lazy-loaded |
| 🔒 **Privacy-first** | Runs 100% on-device · no account · permissions requested only when needed |
| 📱 **Android 8.0+** | Min SDK 26, target SDK 36 · works best on Android 12+ |
| 🆓 **Open source** | GPL-2.0 · Kotlin · Jetpack Compose · Material 3 |

> Built for penetration testers, security researchers, RF engineers — and anyone curious about the invisible wireless world around them.

---

## 📸 Screenshots

Screenshots will be added here.

---

## 🎯 The Problem

Your phone sits in a sea of invisible signals. Right now, within ~30 meters of you:

- 📷 **Hidden cameras** may be streaming over WiFi or BLE — invisible to you.
- 🏷️ **AirTags / SmartTags** could be tracking your location without your knowledge.
- 🎭 **Rogue WiFi hotspots** (evil twins) mimic real networks to steal credentials.
- 📶 **IMSI catchers** (Stingrays) force your phone to 2G to intercept calls & texts.
- 🔊 **Ultrasonic beacons** (18–24 kHz) track you across devices through your mic.
- 🚫 **Deauth attacks** kick you off WiFi — disguised as "bad signal".
- 🔌 **BadUSB devices** pretend to be keyboards to type malicious commands.

No single app detects all of these. Security pros carry a bag of separate gadgets; regular users have nothing. **Lucid Empire puts them all in one place.**

---

## 🧰 Features

Every tool solves a specific, real problem. Full step-by-step docs live in the **[📖 Complete Tool Guide →](docs/TOOLS.md)**.

<details open>
<summary><b>📡 Wireless</b></summary>

| Tool | What it does |
|------|--------------|
| **WiFi Analyzer** | Scans networks, finds channel congestion, flags weak security (OPEN/WEP/WPA) |
| **BLE Scanner** | Discovers BLE devices with full GATT explorer, distance estimates, JSON dumps |
| **NFC Tools** | Reads NDEF, dumps MIFARE Classic sectors, emulates tags via HCE |
| **Bluetooth Classic** | Discovery, SDP service listing, SPP serial connections |
| **Wi-Fi Aware** | NAN device-to-device discovery without a router |
| **Wi-Fi Direct** | Peer discovery, group formation, direct P2P file transfer |
</details>

<details>
<summary><b>📻 RF & Signals</b></summary>

| Tool | What it does |
|------|--------------|
| **IR Remote** | Pre-built Samsung/LG/Sony remotes, custom protocols, Flipper Zero `.ir` import |
| **UWB Radar** | FiRa compliance & capability check (ranging, AoA, ToF) |
| **SDR Radio** | Detects RTL-SDR, HackRF, AirSpy dongles via USB OTG |
| **Ultrasonic Analyzer** | FFT spectrum of 18–24 kHz to flag possible ultrasonic tracking beacons |
</details>

<details>
<summary><b>🎚️ Sensors</b></summary>

| Tool | What it does |
|------|--------------|
| **Sensor Dashboard** | Accelerometer, gyroscope, magnetometer, barometer, compass, level, metal detector |
| **QR Scanner** | Scans + analyzes codes for phishing URLs & suspicious TLDs before opening |
| **USB Camera** | UVC camera detection with resolution & capability listing |
| **GPS Tracker** | Live position, GNSS satellite list, raw NMEA log |
| **EMF Mapper** | Magnetometer field mapping with baseline deviation & hotspots |
</details>

<details>
<summary><b>🌐 Network</b></summary>

| Tool | What it does |
|------|--------------|
| **USB Devices** | Full USB inspection + BadUSB (HID + Mass Storage) detection |
| **Cell Tower Analyzer** | Serving cell and neighbours: identity, channel, and signal. A neighbour row opens the same fields as the registered cell. |
| **Wardriving** | Background GPS+WiFi logging with WiGLE CSV export |
</details>

<details>
<summary><b>🛡️ Security</b></summary>

| Tool | What it does |
|------|--------------|
| **Hidden Camera Detector** | 5 methods: WiFi OUI, SSID, BLE, magnetometer, port scan |
| **GPS Spoof Detector** | 7 cross-validation checks (GPS vs cell/WiFi/barometer/accelerometer) |
| **Tracker Scanner** | Find My advertisements, Tile’s service, and Chipolo’s company ID. Marks a device heard in 3 places. |
| **Rogue AP Detector** | 6 algorithms: evil twin, SSID spoofing, karma, open impersonator |
| **Network Scanner** | Subnet-wide port scan, banner grabbing, vulnerability assessment |
| **RF Bug Sweeper** | BLE module + ultrasonic + magnetic anomaly sweep |
| **Proximity Radar** | Visual radar plotting devices by estimated distance & signal |
| **Privacy Score** | 16+ checks across WiFi, Bluetooth, device, network & physical security |
| **Deauth Detector** | Receives deauth and disassoc frames on a TP-Link Archer T2U Plus or Panda PAU0A. Nothing is transmitted. |
| **Signal Logger** | Continuous WiFi+BLE timeline with arrival/departure tracking |
| **Alert Center** | Unified, persisted feed of every threat raised by the other Security tools |
</details>

---

## ❓ FAQ

<details>
<summary><b>Is Lucid Empire legal to use?</b></summary>

The app is legal. How you use it is your responsibility. Only scan, probe, or test networks and devices you **own or have written permission** to assess. See the [Disclaimer](#-disclaimer) and the in-app Ethical Use Agreement.
</details>

<details>
<summary><b>Does it require root?</b></summary>

No. Lucid Empire uses only standard Android APIs and runtime permissions — no root, no custom ROM.
</details>

<details>
<summary><b>Why does it ask for location permission?</b></summary>

Android **requires** location permission to return WiFi and Bluetooth scan results — it's a platform rule, not a data grab. Permissions are requested only when you open a feature that needs them.
</details>

<details>
<summary><b>Will every tool work on my phone?</b></summary>

No — tools depend on your hardware (IR blaster, UWB, barometer, etc. aren't on every device). The **Dashboard shows exactly which capabilities your device has** so you know what will work. See [Hardware Compatibility](docs/HARDWARE.md).
</details>

<details>
<summary><b>Does it drain my battery?</b></summary>

No. Nothing scans until you tap start, every scanner auto-stops after a timeout, and all services are lazy-loaded. The home screen costs effectively zero battery. See [Battery Optimization](docs/BATTERY.md).
</details>

<details>
<summary><b>Is my data collected?</b></summary>

Everything runs on-device. There's no login and no account. Permissions are requested only when a specific feature needs them.
</details>

---

## 🏗️ Architecture

MVVM (`Screen → @HiltViewModel → Domain → Hardware`), Hilt DI, all services lazy-initialized, no auto-start scanning. Full design decisions and directory layout: **[📖 Architecture Guide →](docs/ARCHITECTURE.md)**

---

## 🔋 Battery Optimization

Zero idle battery: no auto-start scanning, 5Hz sensor polling, `SCAN_MODE_LOW_POWER` BLE, everything auto-stops and lazy-loads. Full before/after breakdown: **[📖 Battery Optimization →](docs/BATTERY.md)**

---

## 🔐 Permissions

Lucid Empire requests permissions **only when you open a feature that needs them** — nothing at startup. Full permission-by-permission breakdown: **[📖 Permissions →](docs/PERMISSIONS.md)**

---

## 📱 Hardware Compatibility

Not all phones have all hardware. The Dashboard shows which capabilities your device has. Full hardware → feature matrix: **[📖 Hardware Compatibility →](docs/HARDWARE.md)**

---

## 🛠️ Tech Stack

<p align="center">
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img alt="Jetpack Compose" src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" />
  <img alt="Material 3" src="https://img.shields.io/badge/Material%203-757575?style=for-the-badge&logo=materialdesign&logoColor=white" />
  <img alt="Room" src="https://img.shields.io/badge/Room%20DB-003B57?style=for-the-badge&logo=sqlite&logoColor=white" />
  <img alt="CameraX" src="https://img.shields.io/badge/CameraX-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img alt="ML Kit" src="https://img.shields.io/badge/ML%20Kit-4285F4?style=for-the-badge&logo=google&logoColor=white" />
  <img alt="Gradle" src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white" />
  <img alt="Android Studio" src="https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white" />
</p>

<p align="center"><sub><b>Architecture:</b> MVVM (ViewModel + StateFlow + Compose) · Hilt DI · Room (auto-migration) · Dark Material 3 theme with JetBrains Mono</sub></p>

<details>
<summary><b>Full dependency versions</b></summary>

| Layer | Technology |
|-------|-----------|
| Language | Kotlin 2.2.0 |
| UI Framework | Jetpack Compose (BOM 2026.06.01) |
| Design System | Material 3 (dark theme, JetBrains Mono, CutCornerShape) |
| Architecture | MVVM (ViewModel + StateFlow + Compose) |
| Navigation | Jetpack Navigation Compose 2.9.8 |
| DI | Hilt 2.60.1 |
| Database | Room 2.8.4 (5 entities, auto-migration) |
| Camera | CameraX 1.6.1 |
| Barcode | ML Kit Barcode 17.3.0 + ZXing 3.5.3 |
| Location | Play Services Location 21.4.0 |
| Ranging | Jetpack Core UWB 1.0.0 |
| Permissions | Accompanist Permissions |
| Build | Gradle 9.7.0, AGP 9.3.1 |
| Min / Target SDK | 26 (Android 8.0) / 37 |
</details>

---

## ⚖️ Ethical Use

Lucid Empire shows an **Ethical Use Agreement** on first launch that cannot be dismissed. Users must accept:

- ✅ Use only on networks and devices you own or are authorized to test
- ✅ Comply with all local laws on wireless scanning and network analysis
- 🚫 No unauthorized surveillance, tracking, or network attacks
- 🛡️ Report vulnerabilities responsibly through proper channels

Declining the agreement exits the app.

---

## 💬 Support

- 🐛 **Bugs & feature requests:** [open an issue](https://github.com/Undrcrrnt/Lucid-Empire/issues)
- 🔒 **Security disclosures:** please report privately via [SECURITY.md](SECURITY.md) rather than in a public issue

If Lucid Empire is useful to you, consider **starring the repo** ⭐ — it helps others discover the project.

---

## ⚠️ Disclaimer

Lucid Empire is provided **for educational purposes, authorized security research, and defensive use only**.

The tools in this app inspect radios, sensors, and networks around you. Using them to access, monitor, disrupt, or attack networks, devices, or people **without explicit authorization is illegal** in most jurisdictions and is **not** the intended use of this software.

- You are solely responsible for how you use Lucid Empire and for complying with all applicable laws.
- Only scan, probe, or test networks and devices you **own** or have **written permission** to assess.
- What a tool shows is what the phone or the attached adapter reported. Do not treat a reading as proof of a threat, and do not treat a quiet screen as proof that nothing is there.
- The author accepts **no liability** for misuse or for any damages arising from use of this software. It is provided "as is", without warranty of any kind.

By building, installing, or using Lucid Empire, you agree to these terms and to the in-app Ethical Use Agreement.

---

## 📄 License

The combined application is licensed under the [GNU General Public License v2.0](COPYING), because it includes the receive-only USB radio drivers. The original application shell remains available under the [MIT License](LICENSE).

<p align="center">
  <sub>Built with ☕ and Kotlin · If you find Lucid Empire useful, drop a ⭐</sub>
</p>
 
