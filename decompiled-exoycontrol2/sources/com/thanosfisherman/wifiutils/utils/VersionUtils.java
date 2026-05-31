package com.thanosfisherman.wifiutils.utils;

import android.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class VersionUtils {
    public static boolean isJellyBeanOrLater() {
        return true;
    }

    public static boolean isLollipopOrLater() {
        return true;
    }

    public static boolean isMarshmallowOrLater() {
        return true;
    }

    private VersionUtils() {
    }

    public static boolean isAndroidQOrLater() {
        return Build.VERSION.SDK_INT >= 29;
    }
}
