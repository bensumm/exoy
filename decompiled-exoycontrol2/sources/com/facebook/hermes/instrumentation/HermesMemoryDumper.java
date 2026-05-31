package com.facebook.hermes.instrumentation;

/* JADX INFO: loaded from: classes.dex */
public interface HermesMemoryDumper {
    String getId();

    String getInternalStorage();

    void setMetaData(String str);

    boolean shouldSaveSnapshot();
}
