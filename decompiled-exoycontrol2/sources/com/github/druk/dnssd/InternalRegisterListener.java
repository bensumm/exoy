package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
interface InternalRegisterListener extends BaseListener {
    void serviceRegistered(DNSSDRegistration registration, int flags, byte[] serviceName, byte[] regType, byte[] domain);
}
