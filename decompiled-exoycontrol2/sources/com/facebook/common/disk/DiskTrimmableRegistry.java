package com.facebook.common.disk;

/* JADX INFO: loaded from: classes.dex */
public interface DiskTrimmableRegistry {
    void registerDiskTrimmable(DiskTrimmable trimmable);

    void unregisterDiskTrimmable(DiskTrimmable trimmable);
}
