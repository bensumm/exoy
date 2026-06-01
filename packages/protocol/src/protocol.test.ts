import { describe, expect, it } from 'vitest';
import { BRIGHTNESS, SPEED, clamp } from './ranges.js';
import { MODPACKS, TOTAL_EFFECTS, findEffect, getEffectName, getModpack } from './catalog.js';
import { combine, connectToWifi, encodeCommand, setBrightness, setHueAndSat, setModPackEffect, togglePower } from './commands.js';
import { parseDeviceState, parseDeviceStateBytes } from './state.js';

describe('ranges / clamp', () => {
  it('clamps below min up to min', () => expect(clamp(10, BRIGHTNESS)).toBe(60));
  it('clamps above max down to max', () => expect(clamp(999, BRIGHTNESS)).toBe(255));
  it('rounds in-range values', () => expect(clamp(127.6, SPEED)).toBe(128));
});

describe('command builders', () => {
  it('setBrightness clamps to the 60 floor', () => expect(setBrightness(10)).toEqual({ setBrightness: 60 }));
  it('togglePower maps boolean to 0/1', () => {
    expect(togglePower(true)).toEqual({ togglePower: 1 });
    expect(togglePower(false)).toEqual({ togglePower: 0 });
  });
  it('setModPackEffect combines both keys', () => expect(setModPackEffect(6, 2)).toEqual({ setModPack: 6, setEffect: 2 }));
  it('setHueAndSat nests + clamps', () => expect(setHueAndSat(180, 999)).toEqual({ setHueAndSat: { hue: 180, saturation: 255 } }));
  it('connectToWifi nests credentials', () => expect(connectToWifi('home', 'pw')).toEqual({ connectToWifi: { ssid: 'home', password: 'pw' } }));
  it('combine merges payloads', () => expect(combine(setBrightness(200), togglePower(true))).toEqual({ setBrightness: 200, togglePower: 1 }));
  it('encodeCommand produces wire JSON', () => expect(encodeCommand({ getData: 1 })).toBe('{"getData":1}'));
});

describe('catalog', () => {
  it('has 9 modpacks and 70 effects', () => {
    expect(MODPACKS).toHaveLength(9);
    expect(TOTAL_EFFECTS).toBe(70);
  });
  it('resolves Nature/Aurora at (6, 2)', () => {
    expect(getModpack(6)?.name).toBe('Nature');
    expect(getEffectName(6, 2)).toBe('Aurora');
  });
  it('two-way lookup finds Aurora at (6,2)', () => {
    expect(findEffect('aurora')).toMatchObject({ modpackIndex: 6, effectIndex: 2 });
  });
  it('returns undefined for unknown effect', () => expect(findEffect('nope')).toBeUndefined());
});

describe('state parser', () => {
  const sample = {
    mdnsName: 'exoyone38651', type: 4, brightness: 89, currentModpack: 2, modeIndex: 2,
    speed: 28, hue: 10, saturation: 255, autoChange: false, lockColorWheel: true,
    userDefinedName: 'A/B exoyone', firmwareVersion: '2.1', connectedToWiFi: true,
  };
  it('parses observed live payload', () => {
    const s = parseDeviceState(sample);
    expect(s.currentModpack).toBe(2);
    expect(s.saturation).toBe(255);
    expect(s.firmwareVersion).toBe('2.1');
  });
  it('coerces numeric booleans and preserves unknown keys', () => {
    const s = parseDeviceState({ ...sample, musicSync: 1, futureKey: 'x' });
    expect(s.musicSync).toBe(true);
    expect(s.futureKey).toBe('x');
  });
  it('round-trips through bytes', () => {
    expect(parseDeviceStateBytes(JSON.stringify(sample)).mdnsName).toBe('exoyone38651');
  });
  it('throws on non-object input', () => expect(() => parseDeviceState(42)).toThrow(TypeError));
});
