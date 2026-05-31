package com.facebook.react.bridge;

import com.facebook.react.bridge.JSIModule;

/* JADX INFO: loaded from: classes.dex */
public interface JSIModuleProvider<T extends JSIModule> {
    T get();
}
