package com.oblador.vectoricons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import com.facebook.react.views.text.ReactFontManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class VectorIconsModuleImpl {
    public static final String NAME = "RNVectorIcons";
    private static final Map<String, Typeface> sTypefaceCache = new HashMap();

    public static String getImageForFont(String str, String str2, Integer num, Integer num2, Context context) throws Throwable {
        FileOutputStream fileOutputStream;
        Throwable th;
        String str3 = context.getCacheDir().getAbsolutePath() + "/";
        float f = context.getResources().getDisplayMetrics().density;
        StringBuilder sb = new StringBuilder("@");
        int i = (int) f;
        sb.append(f == ((float) i) ? Integer.toString(i) : Float.toString(f));
        sb.append("x");
        String string = sb.toString();
        int iRound = Math.round(num.intValue() * f);
        String str4 = str3 + Integer.toString((str + ":" + str2 + ":" + num2).hashCode(), 32) + "_" + Integer.toString(num.intValue()) + string + ".png";
        String str5 = "file://" + str4;
        File file = new File(str4);
        if (file.exists()) {
            return str5;
        }
        Typeface typeface = ReactFontManager.getInstance().getTypeface(str, 0, context.getAssets());
        Paint paint = new Paint();
        paint.setTypeface(typeface);
        paint.setColor(num2.intValue());
        paint.setTextSize(iRound);
        paint.setAntiAlias(true);
        paint.getTextBounds(str2, 0, str2.length(), new Rect());
        int i2 = iRound - ((int) paint.getFontMetrics().bottom);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iRound, iRound, Bitmap.Config.ARGB_8888);
        new Canvas(bitmapCreateBitmap).drawText(str2, 0, i2, paint);
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th2) {
            fileOutputStream = null;
            th = th2;
        }
        try {
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return str5;
        } catch (Throwable th3) {
            th = th3;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            throw th;
        }
    }
}
