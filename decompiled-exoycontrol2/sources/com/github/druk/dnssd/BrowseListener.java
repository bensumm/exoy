package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
public interface BrowseListener extends BaseListener {
    void serviceFound(DNSSDService browser, int flags, int ifIndex, String serviceName, String regType, String domain);

    void serviceLost(DNSSDService browser, int flags, int ifIndex, String serviceName, String regType, String domain);
}
