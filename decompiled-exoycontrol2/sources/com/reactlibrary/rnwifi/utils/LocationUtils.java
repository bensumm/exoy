package com.reactlibrary.rnwifi.utils;

import android.content.Context;
import android.provider.Settings;

/* JADX INFO: loaded from: classes.dex */
public final class LocationUtils {
    private static boolean isMarshmallowOrLater() {
        return true;
    }

    private LocationUtils() {
    }

    public static boolean isLocationOn(Context context) {
        return !isMarshmallowOrLater() || isLocationTurnedOn(context);
    }

    private static boolean isLocationTurnedOn(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "location_mode", 0) != 0;
    }
}
