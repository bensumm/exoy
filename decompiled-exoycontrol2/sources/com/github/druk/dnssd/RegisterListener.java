package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
public interface RegisterListener extends BaseListener {
    void serviceRegistered(DNSSDRegistration registration, int flags, String serviceName, String regType, String domain);
}
