package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
public interface DomainListener extends BaseListener {
    void domainFound(DNSSDService domainEnum, int flags, int ifIndex, String domain);

    void domainLost(DNSSDService domainEnum, int flags, int ifIndex, String domain);
}
