/**
 * Stock scene catalog: 9 modpacks / 70 effects.
 *
 * Recovered from the decompiled `packsAndModes` table (~L214867-214922).
 * `setModPack` = modpack index (0-based); `setEffect` = effect index within the pack (0-based).
 * NOTE: order/indices must be verified against live hardware. See reverse/api-discovery.md.
 */

export interface Modpack {
  readonly index: number;
  readonly name: string;
  readonly effects: readonly string[];
}

export const MODPACKS: readonly Modpack[] = [
  {
    index: 0,
    name: 'Rave',
    effects: ['Beats Burst', 'Laser Lash', 'Electric Ave', 'Hypnosis', 'Glow Groove', 'Whirling Spectrum', 'Rainbow Rumble'],
  },
  {
    index: 1,
    name: 'Party',
    effects: ['Dance Floor', 'Rhythm Run', 'Pop Party', 'Funky Flash', 'Jubulo', 'Gloze', 'Color Rush', 'Confetti', 'Rising Rhythm', 'Pulse Peaks', 'Beat Orbit'],
  },
  {
    index: 2,
    name: 'Chill',
    effects: ['Soothing Sound', 'Euphoria', 'Dreamscape', 'Serene Symphony', 'Svelte', 'Tranquilo'],
  },
  {
    index: 3,
    name: 'Mood',
    effects: ['Mood Swing', 'Emotional Express', 'Feeling Flow', 'Zenith', 'Nimbus'],
  },
  {
    index: 4,
    name: 'Genre',
    effects: ["Rockin' Rhythms", 'Jazz Live', 'Hip Hop Hues', 'Country Crescendo', 'EDM Flow', 'Hardstyle Beats'],
  },
  {
    index: 5,
    name: 'Themes',
    effects: ['Cosmic Carnival', 'Summer Breeze', 'Desert Miracle', 'Winter Wonderland', 'Underwater Oasis', 'Rainbow Ripple', 'Error', 'Matrix', 'Beating Heart'],
  },
  {
    index: 6,
    name: 'Nature',
    effects: ['Wildfire', 'Thunderstorm', 'Aurora', 'Rainbow Reflection', 'Bloom', 'Celestial', 'Ebb & Flow', 'Majestic', 'Whirlwind', 'Lightning', 'Snowstorm'],
  },
  {
    index: 7,
    name: 'Cosmic',
    effects: ['Galaxy', 'Cosmic Dust', 'Solar Flare', 'Sabre Fight', 'Black Hole', 'Meteor', 'Astro', 'Super Nova'],
  },
  {
    index: 8,
    name: 'Background',
    effects: ['Ambient Aura', 'Twilight', 'Dawn', 'Custom Hue', 'Color Carousel', 'Shimmer', 'Whisper'],
  },
] as const;

export const TOTAL_EFFECTS = MODPACKS.reduce((sum, pack) => sum + pack.effects.length, 0);

export function getModpack(modpackIndex: number): Modpack | undefined {
  return MODPACKS.find((pack) => pack.index === modpackIndex);
}

export function getEffectName(modpackIndex: number, effectIndex: number): string | undefined {
  return getModpack(modpackIndex)?.effects[effectIndex];
}

export interface EffectRef {
  readonly modpackIndex: number;
  readonly effectIndex: number;
  readonly modpackName: string;
  readonly effectName: string;
}

export function findEffect(effectName: string): EffectRef | undefined {
  const needle = effectName.trim().toLowerCase();
  for (const pack of MODPACKS) {
    const effectIndex = pack.effects.findIndex((name) => name.toLowerCase() === needle);
    if (effectIndex !== -1) {
      return { modpackIndex: pack.index, effectIndex, modpackName: pack.name, effectName: pack.effects[effectIndex]! };
    }
  }
  return undefined;
}
