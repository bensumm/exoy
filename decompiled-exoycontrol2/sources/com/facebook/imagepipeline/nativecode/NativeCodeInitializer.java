package com.facebook.imagepipeline.nativecode;

import android.content.Context;
import com.facebook.soloader.SoLoader;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class NativeCodeInitializer {
    public static void init(Context context) throws IOException {
        SoLoader.init(context, 0);
    }
}
