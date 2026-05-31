package com.facebook.yoga;

/* JADX INFO: loaded from: classes.dex */
public abstract class YogaConfigFactory {
    public static YogaConfig create() {
        return new YogaConfigJNIFinalizer();
    }
}
