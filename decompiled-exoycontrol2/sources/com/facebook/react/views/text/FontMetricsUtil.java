package com.facebook.react.views.text;

import android.content.Context;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

/* JADX INFO: loaded from: classes.dex */
public class FontMetricsUtil {
    private static final float AMPLIFICATION_FACTOR = 100.0f;
    private static final String CAP_HEIGHT_MEASUREMENT_TEXT = "T";
    private static final String X_HEIGHT_MEASUREMENT_TEXT = "x";

    public static WritableArray getFontMetrics(CharSequence charSequence, Layout layout, TextPaint textPaint, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        WritableArray writableArrayCreateArray = Arguments.createArray();
        TextPaint textPaint2 = new TextPaint(textPaint);
        textPaint2.setTextSize(textPaint2.getTextSize() * AMPLIFICATION_FACTOR);
        int i = 0;
        int i2 = 1;
        textPaint2.getTextBounds(CAP_HEIGHT_MEASUREMENT_TEXT, 0, 1, new Rect());
        double dHeight = (r5.height() / AMPLIFICATION_FACTOR) / displayMetrics.density;
        textPaint2.getTextBounds(X_HEIGHT_MEASUREMENT_TEXT, 0, 1, new Rect());
        double dHeight2 = (r5.height() / AMPLIFICATION_FACTOR) / displayMetrics.density;
        int i3 = 0;
        while (i3 < layout.getLineCount()) {
            float lineMax = ((charSequence.length() <= 0 || charSequence.charAt(layout.getLineEnd(i3) - i2) != '\n') ? i : i2) != 0 ? layout.getLineMax(i3) : layout.getLineWidth(i3);
            layout.getLineBounds(i3, new Rect());
            WritableMap writableMapCreateMap = Arguments.createMap();
            double d = dHeight;
            writableMapCreateMap.putDouble(X_HEIGHT_MEASUREMENT_TEXT, layout.getLineLeft(i3) / displayMetrics.density);
            writableMapCreateMap.putDouble("y", r13.top / displayMetrics.density);
            writableMapCreateMap.putDouble("width", lineMax / displayMetrics.density);
            writableMapCreateMap.putDouble("height", r13.height() / displayMetrics.density);
            writableMapCreateMap.putDouble("descender", layout.getLineDescent(i3) / displayMetrics.density);
            writableMapCreateMap.putDouble("ascender", (-layout.getLineAscent(i3)) / displayMetrics.density);
            writableMapCreateMap.putDouble("baseline", layout.getLineBaseline(i3) / displayMetrics.density);
            writableMapCreateMap.putDouble("capHeight", d);
            writableMapCreateMap.putDouble("xHeight", dHeight2);
            writableMapCreateMap.putString("text", charSequence.subSequence(layout.getLineStart(i3), layout.getLineEnd(i3)).toString());
            writableArrayCreateArray.pushMap(writableMapCreateMap);
            i3++;
            dHeight = d;
            i = 0;
            i2 = 1;
        }
        return writableArrayCreateArray;
    }
}
