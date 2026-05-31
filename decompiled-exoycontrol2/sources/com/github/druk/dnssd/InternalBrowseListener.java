package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
interface InternalBrowseListener extends BaseListener {
    void serviceFound(DNSSDService browser, int flags, int ifIndex, byte[] serviceName, byte[] regType, byte[] domain);

    void serviceLost(DNSSDService browser, int flags, int ifIndex, byte[] serviceName, byte[] regType, byte[] domain);
}
