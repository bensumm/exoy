package com.facebook.react.modules.network;

/* JADX INFO: loaded from: classes.dex */
public class HeaderUtil {
    public static String stripHeaderName(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        int length = str.length();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt <= ' ' || cCharAt >= 127) {
                z = true;
            } else {
                sb.append(cCharAt);
            }
        }
        return z ? sb.toString() : str;
    }

    public static String stripHeaderValue(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        int length = str.length();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if ((cCharAt <= 31 || cCharAt >= 127) && cCharAt != '\t') {
                z = true;
            } else {
                sb.append(cCharAt);
            }
        }
        return z ? sb.toString() : str;
    }
}
