package com.facebook.react.views.text;

import java.text.BreakIterator;

/* JADX INFO: loaded from: classes.dex */
public enum TextTransform {
    NONE,
    UPPERCASE,
    LOWERCASE,
    CAPITALIZE,
    UNSET;

    /* JADX INFO: renamed from: com.facebook.react.views.text.TextTransform$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$views$text$TextTransform;

        static {
            int[] iArr = new int[TextTransform.values().length];
            $SwitchMap$com$facebook$react$views$text$TextTransform = iArr;
            try {
                iArr[TextTransform.UPPERCASE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$react$views$text$TextTransform[TextTransform.LOWERCASE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$react$views$text$TextTransform[TextTransform.CAPITALIZE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static String apply(String str, TextTransform textTransform) {
        if (str == null) {
            return null;
        }
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$views$text$TextTransform[textTransform.ordinal()];
        if (i == 1) {
            return str.toUpperCase();
        }
        if (i != 2) {
            return i != 3 ? str : capitalize(str);
        }
        return str.toLowerCase();
    }

    private static String capitalize(String str) {
        BreakIterator wordInstance = BreakIterator.getWordInstance();
        wordInstance.setText(str);
        StringBuilder sb = new StringBuilder(str.length());
        int iFirst = wordInstance.first();
        while (true) {
            int i = iFirst;
            iFirst = wordInstance.next();
            if (iFirst != -1) {
                String strSubstring = str.substring(i, iFirst);
                if (Character.isLetterOrDigit(strSubstring.charAt(0))) {
                    sb.append(Character.toUpperCase(strSubstring.charAt(0)));
                    sb.append(strSubstring.substring(1).toLowerCase());
                } else {
                    sb.append(strSubstring);
                }
            } else {
                return sb.toString();
            }
        }
    }
}
