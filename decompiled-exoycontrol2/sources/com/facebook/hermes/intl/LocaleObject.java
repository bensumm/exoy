package com.facebook.hermes.intl;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class LocaleObject {
    public static ILocaleObject createDefault() {
        return Build.VERSION.SDK_INT >= 24 ? LocaleObjectICU.createDefault() : LocaleObjectAndroid.createDefault();
    }

    public static ILocaleObject createFromLocaleId(String str) throws JSRangeErrorException {
        if (Build.VERSION.SDK_INT >= 24) {
            return LocaleObjectICU.createFromLocaleId(str);
        }
        return LocaleObjectAndroid.createFromLocaleId(str);
    }
}
