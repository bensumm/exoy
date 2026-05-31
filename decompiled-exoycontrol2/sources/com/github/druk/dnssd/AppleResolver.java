package com.github.druk.dnssd;

/* JADX INFO: compiled from: InternalDNSSD.java */
/* JADX INFO: loaded from: classes.dex */
class AppleResolver extends AppleService {
    protected native int CreateResolver(int flags, int ifIndex, String serviceName, String regType, String domain);

    public AppleResolver(int flags, int ifIndex, String serviceName, String regType, String domain, InternalResolveListener client) throws DNSSDException {
        super(client);
        ThrowOnErr(CreateResolver(flags, ifIndex, serviceName, regType, domain));
        if (AppleDNSSD.hasAutoCallbacks) {
            return;
        }
        new Thread(this).start();
    }
}
