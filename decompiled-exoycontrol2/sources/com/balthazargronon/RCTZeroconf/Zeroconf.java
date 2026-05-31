package com.balthazargronon.RCTZeroconf;

import com.facebook.react.bridge.ReadableMap;

/* JADX INFO: loaded from: classes.dex */
public interface Zeroconf {
    void registerService(String str, String str2, String str3, String str4, int i, ReadableMap readableMap);

    void scan(String str, String str2, String str3);

    void stop();

    void unregisterService(String str);
}
