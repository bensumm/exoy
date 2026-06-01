/**
 * Device state: the JSON object the device returns in reply to `{ getData: 1 }`.
 * The device does NOT push state unprompted — it must be polled.
 * See reverse/api-discovery.md for an example payload.
 */

export interface DeviceState {
  mdnsName: string;
  type: number;
  brightness: number;
  /** Active modpack index (0-based). Wire key: `currentModpack`. */
  currentModpack: number;
  /** Active effect index within the modpack (0-based). Wire key: `modeIndex`. */
  modeIndex: number;
  speed: number;
  hue: number;
  saturation: number;
  autoChange: boolean;
  musicSync: boolean;
  fadingOff: boolean;
  buttonEnabled: boolean;
  sceneGeneration: boolean;
  lockColorWheel: boolean;
  forceMusicSync: boolean;
  selectedPattern: number;
  selectedRenderMode: number;
  selectedColorMode: number;
  selectedPalette: number;
  userDefinedName: string;
  cycleSpeed: number;
  shutdownTimer: number;
  direction: boolean;
  connectedToWiFi: boolean;
  firmwareVersion: string;
  poweredByPowerbank: boolean;
  [key: string]: unknown;
}

const asNumber = (v: unknown, fallback = 0): number =>
  typeof v === 'number' && Number.isFinite(v) ? v : fallback;

const asBoolean = (v: unknown, fallback = false): boolean => {
  if (typeof v === 'boolean') return v;
  if (typeof v === 'number') return v !== 0;
  return fallback;
};

const asString = (v: unknown, fallback = ''): string =>
  typeof v === 'string' ? v : fallback;

export function parseDeviceState(raw: unknown): DeviceState {
  if (typeof raw !== 'object' || raw === null) {
    throw new TypeError(`Expected device-state object, got ${typeof raw}`);
  }
  const r = raw as Record<string, unknown>;
  return {
    ...r,
    mdnsName: asString(r.mdnsName),
    type: asNumber(r.type),
    brightness: asNumber(r.brightness),
    currentModpack: asNumber(r.currentModpack),
    modeIndex: asNumber(r.modeIndex),
    speed: asNumber(r.speed),
    hue: asNumber(r.hue),
    saturation: asNumber(r.saturation),
    autoChange: asBoolean(r.autoChange),
    musicSync: asBoolean(r.musicSync),
    fadingOff: asBoolean(r.fadingOff),
    buttonEnabled: asBoolean(r.buttonEnabled, true),
    sceneGeneration: asBoolean(r.sceneGeneration),
    lockColorWheel: asBoolean(r.lockColorWheel),
    forceMusicSync: asBoolean(r.forceMusicSync),
    selectedPattern: asNumber(r.selectedPattern),
    selectedRenderMode: asNumber(r.selectedRenderMode),
    selectedColorMode: asNumber(r.selectedColorMode),
    selectedPalette: asNumber(r.selectedPalette),
    userDefinedName: asString(r.userDefinedName),
    cycleSpeed: asNumber(r.cycleSpeed),
    shutdownTimer: asNumber(r.shutdownTimer),
    direction: asBoolean(r.direction),
    connectedToWiFi: asBoolean(r.connectedToWiFi),
    firmwareVersion: asString(r.firmwareVersion),
    poweredByPowerbank: asBoolean(r.poweredByPowerbank),
  };
}

export function parseDeviceStateBytes(bytes: Uint8Array | string): DeviceState {
  const text = typeof bytes === 'string' ? bytes : new TextDecoder().decode(bytes);
  return parseDeviceState(JSON.parse(text));
}
