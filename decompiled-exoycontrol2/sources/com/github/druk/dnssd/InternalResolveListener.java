package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
interface InternalResolveListener extends BaseListener {
    void serviceResolved(DNSSDService resolver, int flags, int ifIndex, byte[] fullName, byte[] hostName, int port, TXTRecord txtRecord);
}
