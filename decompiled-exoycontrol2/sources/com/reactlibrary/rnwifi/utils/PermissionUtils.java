package com.reactlibrary.rnwifi.utils;

import android.content.Context;
import androidx.core.content.ContextCompat;
import java.security.InvalidParameterException;

/* JADX INFO: loaded from: classes.dex */
public final class PermissionUtils {
    private static boolean isMarshmallowOrLater() {
        return true;
    }

    private PermissionUtils() {
    }

    public static boolean isLocationPermissionGranted(Context context) throws InvalidParameterException {
        return !isMarshmallowOrLater() || isPermissionGranted(context, "android.permission.ACCESS_FINE_LOCATION");
    }

    private static boolean isPermissionGranted(Context context, String str) {
        return ContextCompat.checkSelfPermission(context, str) == 0;
    }
}
