package com.swmansion.reanimated.nativeProxy;

import com.facebook.jni.HybridData;

/* JADX INFO: loaded from: classes2.dex */
public class SensorSetter {
    private final HybridData mHybridData;

    public native void sensorSetter(float[] fArr, int i);

    private SensorSetter(HybridData hybridData) {
        this.mHybridData = hybridData;
    }
}
