package com.horcrux.svg;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.views.text.TypefaceStyle;
import com.horcrux.svg.TextProperties;

/* JADX INFO: loaded from: classes.dex */
class FontData {
    static final double DEFAULT_FONT_SIZE = 12.0d;
    private static final double DEFAULT_KERNING = 0.0d;
    private static final double DEFAULT_LETTER_SPACING = 0.0d;
    private static final double DEFAULT_WORD_SPACING = 0.0d;
    static final FontData Defaults = new FontData();
    private static final String FONT_DATA = "fontData";
    private static final String FONT_FEATURE_SETTINGS = "fontFeatureSettings";
    private static final String FONT_VARIANT_LIGATURES = "fontVariantLigatures";
    private static final String FONT_VARIATION_SETTINGS = "fontVariationSettings";
    private static final String KERNING = "kerning";
    private static final String LETTER_SPACING = "letterSpacing";
    private static final String TEXT_ANCHOR = "textAnchor";
    private static final String TEXT_DECORATION = "textDecoration";
    private static final String WORD_SPACING = "wordSpacing";
    int absoluteFontWeight;
    final ReadableMap fontData;
    final String fontFamily;
    final String fontFeatureSettings;
    final double fontSize;
    final TextProperties.FontStyle fontStyle;
    final TextProperties.FontVariantLigatures fontVariantLigatures;
    final String fontVariationSettings;
    TextProperties.FontWeight fontWeight;
    final double kerning;
    final double letterSpacing;
    final boolean manualKerning;
    final TextProperties.TextAnchor textAnchor;
    private final TextProperties.TextDecoration textDecoration;
    final double wordSpacing;

    static class AbsoluteFontWeight {
        private static final TextProperties.FontWeight[] WEIGHTS = {TextProperties.FontWeight.w100, TextProperties.FontWeight.w100, TextProperties.FontWeight.w200, TextProperties.FontWeight.w300, TextProperties.FontWeight.Normal, TextProperties.FontWeight.w500, TextProperties.FontWeight.w600, TextProperties.FontWeight.Bold, TextProperties.FontWeight.w800, TextProperties.FontWeight.w900, TextProperties.FontWeight.w900};
        private static final int[] absoluteFontWeights = {400, TypefaceStyle.BOLD, 100, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 300, 400, 500, 600, TypefaceStyle.BOLD, 800, 900};
        static final int normal = 400;

        private static int bolder(int i) {
            if (i < 350) {
                return 400;
            }
            if (i < 550) {
                return TypefaceStyle.BOLD;
            }
            if (i < 900) {
                return 900;
            }
            return i;
        }

        private static int lighter(int i) {
            if (i < 100) {
                return i;
            }
            if (i < 550) {
                return 100;
            }
            if (i < 750) {
                return 400;
            }
            return TypefaceStyle.BOLD;
        }

        AbsoluteFontWeight() {
        }

        static TextProperties.FontWeight nearestFontWeight(int i) {
            return WEIGHTS[Math.round(i / 100.0f)];
        }

        static int from(TextProperties.FontWeight fontWeight, FontData fontData) {
            if (fontWeight == TextProperties.FontWeight.Bolder) {
                return bolder(fontData.absoluteFontWeight);
            }
            if (fontWeight == TextProperties.FontWeight.Lighter) {
                return lighter(fontData.absoluteFontWeight);
            }
            return absoluteFontWeights[fontWeight.ordinal()];
        }
    }

    private FontData() {
        this.fontData = null;
        this.fontFamily = "";
        this.fontStyle = TextProperties.FontStyle.normal;
        this.fontWeight = TextProperties.FontWeight.Normal;
        this.absoluteFontWeight = 400;
        this.fontFeatureSettings = "";
        this.fontVariationSettings = "";
        this.fontVariantLigatures = TextProperties.FontVariantLigatures.normal;
        this.textAnchor = TextProperties.TextAnchor.start;
        this.textDecoration = TextProperties.TextDecoration.None;
        this.manualKerning = false;
        this.kerning = 0.0d;
        this.fontSize = DEFAULT_FONT_SIZE;
        this.wordSpacing = 0.0d;
        this.letterSpacing = 0.0d;
    }

    private double toAbsolute(ReadableMap readableMap, String str, double d, double d2, double d3) {
        if (readableMap.getType(str) == ReadableType.Number) {
            return readableMap.getDouble(str);
        }
        return PropHelper.fromRelative(readableMap.getString(str), d3, d, d2);
    }

    private void setInheritedWeight(FontData fontData) {
        this.absoluteFontWeight = fontData.absoluteFontWeight;
        this.fontWeight = fontData.fontWeight;
    }

    private void handleNumericWeight(FontData fontData, double d) {
        long jRound = Math.round(d);
        if (jRound >= 1 && jRound <= 1000) {
            int i = (int) jRound;
            this.absoluteFontWeight = i;
            this.fontWeight = AbsoluteFontWeight.nearestFontWeight(i);
            return;
        }
        setInheritedWeight(fontData);
    }

    FontData(ReadableMap readableMap, FontData fontData, double d) {
        String string;
        String string2;
        TextProperties.FontVariantLigatures fontVariantLigaturesValueOf;
        TextProperties.TextAnchor textAnchorValueOf;
        TextProperties.TextDecoration textDecoration;
        double absolute;
        double absolute2;
        double d2 = fontData.fontSize;
        if (readableMap.hasKey(ViewProps.FONT_SIZE)) {
            this.fontSize = toAbsolute(readableMap, ViewProps.FONT_SIZE, 1.0d, d2, d2);
        } else {
            this.fontSize = d2;
        }
        if (readableMap.hasKey(ViewProps.FONT_WEIGHT)) {
            if (readableMap.getType(ViewProps.FONT_WEIGHT) == ReadableType.Number) {
                handleNumericWeight(fontData, readableMap.getDouble(ViewProps.FONT_WEIGHT));
            } else {
                String string3 = readableMap.getString(ViewProps.FONT_WEIGHT);
                if (TextProperties.FontWeight.hasEnum(string3)) {
                    int iFrom = AbsoluteFontWeight.from(TextProperties.FontWeight.get(string3), fontData);
                    this.absoluteFontWeight = iFrom;
                    this.fontWeight = AbsoluteFontWeight.nearestFontWeight(iFrom);
                } else if (string3 != null) {
                    handleNumericWeight(fontData, Double.parseDouble(string3));
                } else {
                    setInheritedWeight(fontData);
                }
            }
        } else {
            setInheritedWeight(fontData);
        }
        this.fontData = readableMap.hasKey(FONT_DATA) ? readableMap.getMap(FONT_DATA) : fontData.fontData;
        this.fontFamily = readableMap.hasKey(ViewProps.FONT_FAMILY) ? readableMap.getString(ViewProps.FONT_FAMILY) : fontData.fontFamily;
        this.fontStyle = readableMap.hasKey(ViewProps.FONT_STYLE) ? TextProperties.FontStyle.valueOf(readableMap.getString(ViewProps.FONT_STYLE)) : fontData.fontStyle;
        if (readableMap.hasKey(FONT_FEATURE_SETTINGS)) {
            string = readableMap.getString(FONT_FEATURE_SETTINGS);
        } else {
            string = fontData.fontFeatureSettings;
        }
        this.fontFeatureSettings = string;
        if (readableMap.hasKey(FONT_VARIATION_SETTINGS)) {
            string2 = readableMap.getString(FONT_VARIATION_SETTINGS);
        } else {
            string2 = fontData.fontVariationSettings;
        }
        this.fontVariationSettings = string2;
        if (readableMap.hasKey(FONT_VARIANT_LIGATURES)) {
            fontVariantLigaturesValueOf = TextProperties.FontVariantLigatures.valueOf(readableMap.getString(FONT_VARIANT_LIGATURES));
        } else {
            fontVariantLigaturesValueOf = fontData.fontVariantLigatures;
        }
        this.fontVariantLigatures = fontVariantLigaturesValueOf;
        if (readableMap.hasKey(TEXT_ANCHOR)) {
            textAnchorValueOf = TextProperties.TextAnchor.valueOf(readableMap.getString(TEXT_ANCHOR));
        } else {
            textAnchorValueOf = fontData.textAnchor;
        }
        this.textAnchor = textAnchorValueOf;
        if (readableMap.hasKey(TEXT_DECORATION)) {
            textDecoration = TextProperties.TextDecoration.getEnum(readableMap.getString(TEXT_DECORATION));
        } else {
            textDecoration = fontData.textDecoration;
        }
        this.textDecoration = textDecoration;
        boolean zHasKey = readableMap.hasKey(KERNING);
        this.manualKerning = zHasKey || fontData.manualKerning;
        this.kerning = zHasKey ? toAbsolute(readableMap, KERNING, d, this.fontSize, 0.0d) : fontData.kerning;
        if (readableMap.hasKey(WORD_SPACING)) {
            absolute = toAbsolute(readableMap, WORD_SPACING, d, this.fontSize, 0.0d);
        } else {
            absolute = fontData.wordSpacing;
        }
        this.wordSpacing = absolute;
        if (readableMap.hasKey("letterSpacing")) {
            absolute2 = toAbsolute(readableMap, "letterSpacing", d, this.fontSize, 0.0d);
        } else {
            absolute2 = fontData.letterSpacing;
        }
        this.letterSpacing = absolute2;
    }
}
