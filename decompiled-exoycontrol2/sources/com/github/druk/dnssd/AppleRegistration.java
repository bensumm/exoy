package com.github.druk.dnssd;

/* JADX INFO: compiled from: InternalDNSSD.java */
/* JADX INFO: loaded from: classes.dex */
class AppleRegistration extends AppleService implements DNSSDRegistration {
    protected native int AddRecord(int flags, int rrType, byte[] rData, int ttl, AppleDNSRecord destObj);

    protected native int BeginRegister(int ifIndex, int flags, String serviceName, String regType, String domain, String host, int port, byte[] txtRecord);

    public AppleRegistration(int flags, int ifIndex, String serviceName, String regType, String domain, String host, int port, byte[] txtRecord, InternalRegisterListener client) throws DNSSDException {
        super(client);
        ThrowOnErr(BeginRegister(ifIndex, flags, serviceName, regType, domain, host, port, txtRecord));
        if (AppleDNSSD.hasAutoCallbacks) {
            return;
        }
        new Thread(this).start();
    }

    @Override // com.github.druk.dnssd.DNSSDRegistration
    public DNSRecord addRecord(int flags, int rrType, byte[] rData, int ttl) throws DNSSDException {
        AppleDNSRecord appleDNSRecord = new AppleDNSRecord(this);
        ThrowOnErr(AddRecord(flags, rrType, rData, ttl, appleDNSRecord));
        return appleDNSRecord;
    }

    @Override // com.github.druk.dnssd.DNSSDRegistration
    public DNSRecord getTXTRecord() throws DNSSDException {
        return new AppleDNSRecord(this);
    }
}
