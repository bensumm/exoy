# Exoy Controller — Project Plan

## Protocol — Fully Reverse Engineered ✅

From static analysis of the APK (jadx + hermes-dec), we have confirmed:

| Property | Value |
|---|---|
| Discovery | mDNS `_http._tcp.local.`, filter name contains `"exoyone"` |
| Transport | UDP to device IP, **port 8888** |
| Format | `JSON.stringify({messageType: value})` as raw UTF-8 bytes |
| Response | Device sends back JSON state object on same socket |

**Full command reference:** see `reverse/api-discovery.md`

---

## Phase 1 — Git Setup

1. Generate SSH key on this machine:
   ```powershell
   ssh-keygen -t ed25519 -C "benlsummers@gmail.com"
   cat ~/.ssh/id_ed25519.pub   # paste into GitHub → Settings → SSH Keys
   ```

2. Clone the repo:
   ```powershell
   cd "C:\Users\benls\OneDrive\Documents\Claude\Projects"
   git clone git@github.com:bensumm/exoy.git
   ```

3. Copy in existing work:
   ```powershell
   # Copy the APK, decompiled sources, disassembly, and docs
   # into the cloned repo, then:
   git add .
   git commit -m "Initial commit: APK, decompiled sources, protocol docs"
   git push
   ```

4. Suggested repo structure:
   ```
   exoy/
   ├── apk/                          # original APK
   ├── decompiled-exoycontrol2/      # jadx output
   ├── exoy-disasm/                  # hbctool output (if generated)
   ├── reverse/
   │   └── api-discovery.md          # ✅ complete protocol docs
   ├── app/                          # new app (built in Phase 4)
   └── PLAN.md
   ```

---

## Phase 2 — Protocol Validation (Optional, Recommended)

Static analysis is complete but a quick live test will confirm value ranges and response timing before building.

**Quick test script** — run on your Windows machine while device is on WiFi:

```python
import socket, json

DEVICE_IP = "192.168.x.x"   # find from router or mDNS browser
PORT = 8888

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind(('', PORT))  # listen on same port for responses

# Ask for current state
cmd = json.dumps({"getData": 1}).encode('utf-8')
sock.sendto(cmd, (DEVICE_IP, PORT))

# Read response
data, addr = sock.recvfrom(4096)
print(json.loads(data))

# Try setting brightness to 50%
sock.sendto(json.dumps({"setBrightness": 50}).encode(), (DEVICE_IP, PORT))
```

Find device IP: open your router's admin page and look for a device named "exoyone…", or install [Bonjour Browser](https://hobbyistsoftware.com/bonjourbrowser) on Windows.

---

## Phase 3 — Architecture

### Platform Decision

Given you're on Windows for development and targeting iPhone:

**Recommended: Progressive Web App (React + Vite)**

- Built on Windows, runs in Safari on iPhone — no Mac needed
- Add to iPhone home screen for app-like experience
- Requires a **small Node.js proxy** running on your Windows PC (or a Raspberry Pi) to bridge WebSocket → UDP, since browsers can't send raw UDP

```
Safari on iPhone
    ↕  WebSocket (port 3001)
Node.js proxy (Windows PC / Pi)
    ↕  UDP port 8888
Exoy device
```

**If you ever get Mac access:** A native SwiftUI app using `Network.framework` for UDP + `NWBrowser` for mDNS would be the best long-term option. Everything learned here translates directly.

### Tech Stack

```
app/
├── frontend/          # React + Vite + TypeScript
│   ├── src/
│   │   ├── api/       # WebSocket client → proxy
│   │   ├── components/
│   │   │   ├── BrightnessSlider.tsx
│   │   │   ├── ColorPicker.tsx     # hue + saturation wheel
│   │   │   ├── ScenePicker.tsx     # mod pack + effect grid
│   │   │   ├── SpeedSlider.tsx
│   │   │   └── PowerToggle.tsx
│   │   ├── pages/
│   │   │   ├── Control.tsx         # main control panel
│   │   │   ├── Scenes.tsx
│   │   │   └── Settings.tsx
│   │   └── App.tsx
│   └── package.json
├── proxy/             # Node.js + TypeScript
│   ├── src/
│   │   ├── udp.ts     # dgram socket → device
│   │   ├── mdns.ts    # bonjour discovery
│   │   └── server.ts  # Express + ws
│   └── package.json
└── README.md
```

---

## Phase 4 — Build

### Milestone 1: Proxy server

```typescript
// proxy/src/server.ts
import dgram from 'dgram'
import { WebSocketServer } from 'ws'
import mdns from 'mdns'  // or 'bonjour-service'

const EXOY_PORT = 8888
let deviceIP: string | null = null

// mDNS discovery
const browser = mdns.createBrowser(mdns.tcp('http'))
browser.on('serviceUp', svc => {
  if (svc.name.includes('exoyone')) {
    deviceIP = svc.addresses[0]
    console.log('Found Exoy at', deviceIP)
  }
})
browser.start()

// UDP socket
const udp = dgram.createSocket('udp4')
udp.bind(EXOY_PORT)

// WebSocket server (browser connects here)
const wss = new WebSocketServer({ port: 3001 })

wss.on('connection', ws => {
  // Browser → device
  ws.on('message', msg => {
    if (!deviceIP) return ws.send(JSON.stringify({error: 'No device found'}))
    udp.send(msg as Buffer, EXOY_PORT, deviceIP)
  })
  // Device → browser  
  udp.on('message', (msg, rinfo) => {
    ws.send(msg)
  })
})
```

### Milestone 2: React frontend

Key commands to implement in order of usefulness:
1. `getData` → display current state on load
2. `togglePower` → on/off button
3. `setBrightness` → slider (0–100)
4. `setSpeed` → slider
5. `setModPack` + `setEffect` → scene picker grid
6. `setHue` + `setSaturation` → color wheel
7. `toggleDirection`, `toggleMusicSync`, `setCycleSpeed` → settings panel

### Milestone 3: Polish

- PWA manifest + service worker → "Add to Home Screen" on iPhone
- Persist last-used scene in localStorage
- Dark mode (the original app is dark-themed)
- Device name display from `userDefinedName`

---

## Phase 5 — Enhanced Features

Beyond the original app:

- **Schedules** — turn on at sunset, off at midnight (node-cron in proxy)
- **Favorites** — save named presets as JSON, one-tap recall
- **Scene editor** — visual builder for custom patterns (need live device to map indices)
- **Multi-device sync** — already supported in protocol (`sendControlOnlyToMainDevice` flag)
- **OTA updates** — embed the device's web UI via WebView (just open `http://{deviceIP}`)
- **Shortcuts / Automations** — expose proxy as REST API, use iOS Shortcuts app to trigger scenes

---

## Immediate Next Steps

1. **Git setup** — SSH key → clone → push existing work
2. **Validate protocol** — run the Python test script above against the live device
3. **Scaffold the app** — `npm create vite@latest frontend -- --template react-ts` and proxy package
4. Start with the proxy + a single working command (`getData` → display state), then iterate

