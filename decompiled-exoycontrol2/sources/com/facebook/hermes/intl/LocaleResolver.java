package com.facebook.hermes.intl;

import android.os.Build;
import com.facebook.hermes.intl.LocaleMatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class LocaleResolver {
    public static HashMap<String, Object> resolveLocale(List<String> list, Object obj, List<String> list2) throws JSRangeErrorException {
        LocaleMatcher.LocaleMatchResult localeMatchResultLookupMatch;
        HashMap<String, Object> map = new HashMap<>();
        String javaString = JSObjects.getJavaString(JSObjects.Get(obj, Constants.LOCALEMATCHER));
        if (Build.VERSION.SDK_INT < 24 || javaString.equals(Constants.LOCALEMATCHER_LOOKUP)) {
            localeMatchResultLookupMatch = LocaleMatcher.lookupMatch((String[]) list.toArray(new String[list.size()]));
        } else {
            localeMatchResultLookupMatch = LocaleMatcher.bestFitMatch((String[]) list.toArray(new String[list.size()]));
        }
        HashSet<String> hashSet = new HashSet();
        for (String str : list2) {
            Object objNull = JSObjects.Null();
            Object obj2 = objNull;
            if (!localeMatchResultLookupMatch.extensions.isEmpty()) {
                obj2 = objNull;
                if (localeMatchResultLookupMatch.extensions.containsKey(str)) {
                    String str2 = localeMatchResultLookupMatch.extensions.get(str);
                    boolean zIsEmpty = str2.isEmpty();
                    Object objNewString = str2;
                    if (zIsEmpty) {
                        objNewString = JSObjects.newString("true");
                    }
                    hashSet.add(str);
                    obj2 = objNewString;
                }
            }
            Object obj3 = obj2;
            if (JSObjects.getJavaMap(obj).containsKey(str)) {
                Object objGet = JSObjects.Get(obj, str);
                boolean zIsString = JSObjects.isString(objGet);
                Object objNewBoolean = objGet;
                if (zIsString) {
                    boolean zIsEmpty2 = JSObjects.getJavaString(objGet).isEmpty();
                    objNewBoolean = objGet;
                    if (zIsEmpty2) {
                        objNewBoolean = JSObjects.newBoolean(true);
                    }
                }
                obj3 = obj2;
                if (!JSObjects.isUndefined(objNewBoolean)) {
                    boolean zEquals = objNewBoolean.equals(obj2);
                    obj3 = obj2;
                    if (!zEquals) {
                        hashSet.remove(str);
                        obj3 = objNewBoolean;
                    }
                }
            }
            boolean zIsNull = JSObjects.isNull(obj3);
            Object objResolveKnownAliases = obj3;
            if (!zIsNull) {
                objResolveKnownAliases = UnicodeExtensionKeys.resolveKnownAliases(str, obj3);
            }
            if (JSObjects.isString(objResolveKnownAliases) && !UnicodeExtensionKeys.isValidKeyword(str, JSObjects.getJavaString(objResolveKnownAliases), localeMatchResultLookupMatch.matchedLocale)) {
                map.put(str, JSObjects.Null());
            } else {
                map.put(str, objResolveKnownAliases);
            }
        }
        for (String str3 : hashSet) {
            ArrayList<String> arrayList = new ArrayList<>();
            String javaString2 = JSObjects.getJavaString(UnicodeExtensionKeys.resolveKnownAliases(str3, JSObjects.newString(localeMatchResultLookupMatch.extensions.get(str3))));
            if (!JSObjects.isString(javaString2) || UnicodeExtensionKeys.isValidKeyword(str3, JSObjects.getJavaString(javaString2), localeMatchResultLookupMatch.matchedLocale)) {
                arrayList.add(javaString2);
                localeMatchResultLookupMatch.matchedLocale.setUnicodeExtensions(str3, arrayList);
            }
        }
        map.put(Constants.LOCALE, localeMatchResultLookupMatch.matchedLocale);
        return map;
    }
}
