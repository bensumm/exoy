package com.github.druk.dnssd;

/* JADX INFO: compiled from: InternalDNSSD.java */
/* JADX INFO: loaded from: classes.dex */
class AppleBrowser extends AppleService {
    protected native int CreateBrowser(int flags, int ifIndex, String regType, String domain);

    public AppleBrowser(int flags, int ifIndex, String regType, String domain, InternalBrowseListener client) throws DNSSDException {
        super(client);
        ThrowOnErr(CreateBrowser(flags, ifIndex, regType, domain));
        if (AppleDNSSD.hasAutoCallbacks) {
            return;
        }
        new Thread(this).start();
    }
}
