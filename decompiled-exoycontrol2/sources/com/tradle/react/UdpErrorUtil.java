package com.tradle.react;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import javax.annotation.Nonnull;

/* JADX INFO: loaded from: classes2.dex */
public class UdpErrorUtil {
    static WritableMap getError(@Nonnull String str, String str2) {
        WritableMap writableMapCreateMap = Arguments.createMap();
        writableMapCreateMap.putString("key", str);
        writableMapCreateMap.putString("message", str2);
        return writableMapCreateMap;
    }
}
