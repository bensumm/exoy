package com.github.druk.dnssd;

/* JADX INFO: compiled from: InternalDNSSD.java */
/* JADX INFO: loaded from: classes.dex */
class AppleRecordRegistrar extends AppleService implements DNSSDRecordRegistrar {
    protected native int CreateConnection();

    protected native int RegisterRecord(int flags, int ifIndex, String fullname, int rrtype, int rrclass, byte[] rdata, int ttl, AppleDNSRecord destObj);

    public AppleRecordRegistrar(RegisterRecordListener listener) throws DNSSDException {
        super(listener);
        ThrowOnErr(CreateConnection());
        if (AppleDNSSD.hasAutoCallbacks) {
            return;
        }
        new Thread(this).start();
    }

    @Override // com.github.druk.dnssd.DNSSDRecordRegistrar
    public DNSRecord registerRecord(int flags, int ifIndex, String fullname, int rrtype, int rrclass, byte[] rdata, int ttl) throws DNSSDException {
        AppleDNSRecord appleDNSRecord = new AppleDNSRecord(this);
        ThrowOnErr(RegisterRecord(flags, ifIndex, fullname, rrtype, rrclass, rdata, ttl, appleDNSRecord));
        return appleDNSRecord;
    }
}
