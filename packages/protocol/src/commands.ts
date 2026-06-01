/**
 * Command builders for the Exoy UDP protocol.
 *
 * Each command serializes to JSON and is sent as raw UTF-8 bytes to UDP port 8888.
 * Builders apply range clamping so out-of-range values never reach the device.
 * See reverse/api-discovery.md.
 */

import { BRIGHTNESS, HUE, SATURATION, SPEED, clamp } from './ranges.js';

export type Command = Record<string, unknown>;

export const getData = (): Command => ({ getData: 1 });
export const togglePower = (on: boolean): Command => ({ togglePower: on ? 1 : 0 });
export const setBrightness = (value: number): Command => ({ setBrightness: clamp(value, BRIGHTNESS) });
export const setSpeed = (value: number): Command => ({ setSpeed: clamp(value, SPEED) });
export const setHue = (value: number): Command => ({ setHue: clamp(value, HUE) });
export const setSaturation = (value: number): Command => ({ setSaturation: clamp(value, SATURATION) });
export const setHueAndSat = (hue: number, saturation: number): Command => ({
  setHueAndSat: { hue: clamp(hue, HUE), saturation: clamp(saturation, SATURATION) },
});
export const setModPackEffect = (modpackIndex: number, effectIndex: number): Command => ({
  setModPack: Math.max(0, Math.round(modpackIndex)),
  setEffect: Math.max(0, Math.round(effectIndex)),
});
export const setModPack = (modpackIndex: number): Command => ({ setModPack: Math.max(0, Math.round(modpackIndex)) });
export const setEffect = (effectIndex: number): Command => ({ setEffect: Math.max(0, Math.round(effectIndex)) });
export const setCycleSpeed = (seconds: number): Command => ({ setCycleSpeed: Math.max(0, Math.round(seconds)) });
export const toggleModeCycle = (on: boolean): Command => ({ toggleModeCycle: on ? 1 : 0 });
export const toggleMusicSync = (on: boolean): Command => ({ toggleMusicSync: on ? 1 : 0 });
export const toggleDirection = (on: boolean): Command => ({ toggleDirection: on ? 1 : 0 });
export const toggleButtonLock = (on: boolean): Command => ({ toggleButtonLock: on ? 1 : 0 });
export const toggleSceneGeneration = (on: boolean): Command => ({ toggleSceneGeneration: on ? 1 : 0 });
export const poweredByPowerbank = (on: boolean): Command => ({ poweredByPowerbank: on ? 1 : 0 });
export const setName = (name: string): Command => ({ setName: name });
export const setShutdownTimer = (value: number): Command => ({ setShutdownTimer: Math.max(0, Math.round(value)) });
export const connectToWifi = (ssid: string, password: string): Command => ({ connectToWifi: { ssid, password } });
export const restartInApMode = (): Command => ({ restartInApMode: 1 });

/** Merge multiple commands into one datagram payload (later keys win on collision). */
export const combine = (...commands: Command[]): Command => Object.assign({}, ...commands);

/** Serialize a command to the exact UTF-8 string sent on the wire. */
export const encodeCommand = (command: Command): string => JSON.stringify(command);
