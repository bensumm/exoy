package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
interface InternalDomainListener extends BaseListener {
    void domainFound(DNSSDService domainEnum, int flags, int ifIndex, byte[] domain);

    void domainLost(DNSSDService domainEnum, int flags, int ifIndex, byte[] domain);
}
