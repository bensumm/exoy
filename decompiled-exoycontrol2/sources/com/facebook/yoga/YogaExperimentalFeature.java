package com.facebook.yoga;

/* JADX INFO: loaded from: classes.dex */
public enum YogaExperimentalFeature {
    WEB_FLEX_BASIS(0),
    ABSOLUTE_PERCENTAGE_AGAINST_PADDING_EDGE(1),
    FIX_ABSOLUTE_TRAILING_COLUMN_MARGIN(2);

    private final int mIntValue;

    YogaExperimentalFeature(int i) {
        this.mIntValue = i;
    }

    public int intValue() {
        return this.mIntValue;
    }

    public static YogaExperimentalFeature fromInt(int i) {
        if (i == 0) {
            return WEB_FLEX_BASIS;
        }
        if (i == 1) {
            return ABSOLUTE_PERCENTAGE_AGAINST_PADDING_EDGE;
        }
        if (i == 2) {
            return FIX_ABSOLUTE_TRAILING_COLUMN_MARGIN;
        }
        throw new IllegalArgumentException("Unknown enum value: " + i);
    }
}
