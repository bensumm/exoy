/**
 * Confirmed value ranges for Exoy device parameters.
 * Source: decompiled UI slider configs (~L215343-215349) + live observation.
 * See reverse/api-discovery.md.
 */

export interface NumericRange {
  readonly min: number;
  readonly max: number;
}

export const BRIGHTNESS: NumericRange = { min: 60, max: 255 };
export const SPEED: NumericRange = { min: 1, max: 255 };
export const SATURATION: NumericRange = { min: 0, max: 255 };
/** Upper bound unconfirmed; treating as a full byte until verified live. */
export const HUE: NumericRange = { min: 0, max: 255 };

/** Clamp `value` into `[range.min, range.max]`, rounding to an integer. */
export function clamp(value: number, range: NumericRange): number {
  const rounded = Math.round(value);
  if (rounded < range.min) return range.min;
  if (rounded > range.max) return range.max;
  return rounded;
}
