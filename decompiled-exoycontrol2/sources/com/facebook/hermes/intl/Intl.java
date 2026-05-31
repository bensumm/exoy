package com.facebook.hermes.intl;

import android.icu.lang.UCharacter;
import android.icu.util.ULocale;
import android.os.Build;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class Intl {
    private static List<String> canonicalizeLocaleList(List<String> list) throws JSRangeErrorException {
        if (list.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            if (str == null) {
                throw new JSRangeErrorException("Incorrect locale information provided");
            }
            if (str.isEmpty()) {
                throw new JSRangeErrorException("Incorrect locale information provided");
            }
            String strCanonicalizeLocaleId = LocaleIdentifier.canonicalizeLocaleId(str);
            if (!strCanonicalizeLocaleId.isEmpty() && !arrayList.contains(strCanonicalizeLocaleId)) {
                arrayList.add(strCanonicalizeLocaleId);
            }
        }
        return arrayList;
    }

    public static List<String> getCanonicalLocales(List<String> list) throws JSRangeErrorException {
        return canonicalizeLocaleList(list);
    }

    public static String toLocaleLowerCase(List<String> list, String str) throws JSRangeErrorException {
        String[] strArr = new String[list.size()];
        if (Build.VERSION.SDK_INT >= 24) {
            return UCharacter.toLowerCase((ULocale) LocaleMatcher.bestFitMatch((String[]) list.toArray(strArr)).matchedLocale.getLocale(), str);
        }
        return str.toLowerCase((Locale) LocaleMatcher.lookupMatch((String[]) list.toArray(strArr)).matchedLocale.getLocale());
    }

    public static String toLocaleUpperCase(List<String> list, String str) throws JSRangeErrorException {
        String[] strArr = new String[list.size()];
        if (Build.VERSION.SDK_INT >= 24) {
            return UCharacter.toUpperCase((ULocale) LocaleMatcher.bestFitMatch((String[]) list.toArray(strArr)).matchedLocale.getLocale(), str);
        }
        return str.toUpperCase((Locale) LocaleMatcher.lookupMatch((String[]) list.toArray(strArr)).matchedLocale.getLocale());
    }
}
