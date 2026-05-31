# Exoy Protocol — Reverse Engineering Findings

**Source:** Static analysis of `eu.exoy.exoycontrol2.apk` (versionName: "json-rewrite")  
**Method:** jadx DEX decompilation + hermes-dec HBC disassembly of the React Native bundle

---

## Device Discovery

**Protocol:** mDNS / Bonjour (react-native-zeroconf)  
**Service type:** `_http._tcp.local.`  
**Filter:** App filters resolved services to those whose `name` includes `"exoyone"`

The resolved service provides:
- `name` — device mDNS name (contains "exoyone")
- `ipAddress` — first address from `addresses[]`
- `port` — from the mDNS TXT record (used for the web UI, not UDP commands)

**AP mode WiFi SSID:** `exoyone` (connect here when device is in access point mode)

---

## Command Transport

**Protocol:** UDP  
**Port:** `8888` (hardcoded in JS — `r14 = 8888`)  
**Encoding:** `JSON.stringify(payload)` → raw UTF-8 bytes sent as UDP datagram  
**Direction:** App → Device (fire and forget, no ACK)

The device also sends state back to the app over UDP (same socket, same port).  
Incoming data arrives as a JSON object (the device state — see getData response below).

---

## Command Format

Each command is a JSON object with **one or more key-value pairs**:

```json
{ "messageType": value }
```

Multiple commands can be combined:
```json
{ "setModPack": 3, "setEffect": 7 }
```

The third argument to `sendCommand(ipAddress, payload, syncToAll)` is a boolean — when `true`, the same command is also sent to all devices in the sync group.

---

## Command Reference

| Message key | Value type | Description |
|---|---|---|
| `getData` | `1` | Request current device state (device replies with full state JSON) |
| `togglePower` | `0` or `1` | Power off / on |
| `setBrightness` | `0–100` | Set brightness level |
| `setSpeed` | integer | Set animation speed |
| `setHue` | integer | Set hue value |
| `setSaturation` | integer | Set saturation value |
| `setHueAndSat` | `{hue, saturation}` | Set hue and saturation together |
| `setModPack` | integer | Set scene/mod pack index |
| `setEffect` | integer | Set effect index within current mod pack |
| `setPattern` | integer | Set pattern number |
| `setRenderMode` | integer | Set render mode number |
| `setPalette` | integer | Set palette number |
| `setColorMode` | integer | Set color mode number |
| `setCycleSpeed` | integer | Set auto mode cycle interval (seconds) |
| `toggleModeCycle` | `0` or `1` | Toggle automatic mode cycling |
| `toggleMusicSync` | `0` or `1` | Toggle music sync |
| `toggleDirection` | `0` or `1` | Toggle animation direction |
| `toggleButtonLock` | `0` or `1` | Lock/unlock physical button |
| `toggleSceneGeneration` | `0` or `1` | Toggle scene generation |
| `poweredByPowerbank` | `0` or `1` | Toggle powerbank mode |
| `setName` | string | Set user-defined device name |
| `setShutdownTimer` | integer | Set auto shutdown timer |
| `connectToWifi` | `{ssid, password}` | Connect device to WiFi network |
| `restartInApMode` | `1` | Restart device in access point mode |

---

## getData Response (Device → App)

Sent by device in response to `{"getData": 1}`, also pushed periodically:

```json
{
  "mdnsName": "exoyone12345",
  "type": 0,
  "brightness": 75,
  "currentModpack": 2,
  "modeIndex": 4,
  "speed": 50,
  "hue": 180,
  "saturation": 100,
  "autoChange": false,
  "musicSync": false,
  "fadingOff": false,
  "buttonEnabled": true,
  "sceneGeneration": false,
  "lockColorWheel": false,
  "forceMusicSync": false,
  "selectedPattern": 0,
  "selectedRenderMode": 0,
  "selectedColorMode": 0,
  "selectedPalette": 0,
  "userDefinedName": "My Exoy",
  "cycleSpeed": 30,
  "shutdownTimer": 0,
  "direction": false,
  "connectedToWiFi": true,
  "firmwareVersion": "1.3.7",
  "poweredByPowerbank": false
}
```

---

## Web Interface

The device hosts an HTTP server (discovered via `_http._tcp.local.`).  
In AP mode, accessible at: `http://exoylocal:{unit_number_5digits}`  
Likely serves a web UI — exact routes TBD from live testing.  
The React Native app includes a WebView component, suggesting it embeds this web UI for some screens (likely OTA update / WiFi config).

---

## Native Layer (Java/Kotlin)

- **UDP library:** `react-native-udp` (`com.tradle.react`) — standard DatagramSocket  
- **Discovery library:** `react-native-zeroconf` (`com.balthazargronon.RCTZeroconf`) — uses Android NSD + Rx2Dnssd (Bonjour)  
- Data is transmitted as raw UTF-8 bytes (Buffer.from(jsonString) in JS → byte[] in Java)

---

## Open Questions

- Exact value ranges for brightness, speed, hue, saturation (likely 0–100 or 0–255)
- Whether `setModPack`/`setEffect` indices are 0-based or 1-based
- Full set of available mod packs, effects, patterns, palettes (need live device)
- Whether device pushes state unprompted or only responds to `getData`
- Exact HTTP routes served by the device web UI
