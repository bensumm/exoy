package com.github.druk.dnssd;

/* JADX INFO: compiled from: InternalDNSSD.java */
/* JADX INFO: loaded from: classes.dex */
class AppleQuery extends AppleService {
    protected native int CreateQuery(int flags, int ifIndex, String serviceName, int rrtype, int rrclass);

    public AppleQuery(int flags, int ifIndex, String serviceName, int rrtype, int rrclass, InternalQueryListener client) throws DNSSDException {
        super(client);
        ThrowOnErr(CreateQuery(flags, ifIndex, serviceName, rrtype, rrclass));
        if (AppleDNSSD.hasAutoCallbacks) {
            return;
        }
        new Thread(this).start();
    }
}
