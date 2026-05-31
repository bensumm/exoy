package com.facebook.react.turbomodule.core;

import com.facebook.jni.HybridData;
import com.facebook.react.turbomodule.core.interfaces.CallInvokerHolder;
import com.facebook.soloader.SoLoader;

/* JADX INFO: loaded from: classes.dex */
public class CallInvokerHolderImpl implements CallInvokerHolder {
    private static volatile boolean sIsSoLibraryLoaded;
    private final HybridData mHybridData;

    private CallInvokerHolderImpl(HybridData hybridData) {
        maybeLoadSoLibrary();
        this.mHybridData = hybridData;
    }

    private static synchronized void maybeLoadSoLibrary() {
        if (!sIsSoLibraryLoaded) {
            SoLoader.loadLibrary("turbomodulejsijni");
            sIsSoLibraryLoaded = true;
        }
    }
}
