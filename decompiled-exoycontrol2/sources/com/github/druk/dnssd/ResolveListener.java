package com.github.druk.dnssd;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface ResolveListener extends BaseListener {
    void serviceResolved(DNSSDService resolver, int flags, int ifIndex, String fullName, String hostName, int port, Map<String, String> txtRecord);
}
