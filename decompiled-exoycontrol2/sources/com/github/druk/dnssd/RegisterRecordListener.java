package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
public interface RegisterRecordListener extends BaseListener {
    void recordRegistered(DNSRecord record, int flags);
}
