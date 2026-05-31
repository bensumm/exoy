package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
public interface DNSRecord {
    void remove() throws DNSSDException;

    void update(int flags, byte[] rData, int ttl) throws DNSSDException;
}
