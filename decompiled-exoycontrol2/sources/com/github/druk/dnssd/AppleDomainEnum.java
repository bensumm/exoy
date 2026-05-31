package com.github.druk.dnssd;

/* JADX INFO: compiled from: InternalDNSSD.java */
/* JADX INFO: loaded from: classes.dex */
class AppleDomainEnum extends AppleService {
    protected native int BeginEnum(int flags, int ifIndex);

    public AppleDomainEnum(int flags, int ifIndex, InternalDomainListener client) throws DNSSDException {
        super(client);
        ThrowOnErr(BeginEnum(flags, ifIndex));
        if (AppleDNSSD.hasAutoCallbacks) {
            return;
        }
        new Thread(this).start();
    }
}
