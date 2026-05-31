package com.github.druk.dnssd;

/* JADX INFO: compiled from: InternalDNSSD.java */
/* JADX INFO: loaded from: classes.dex */
class AppleDNSSD extends InternalDNSSD {
    public static boolean hasAutoCallbacks;

    protected static native int InitLibrary(int callerVersion);

    protected native int ConstructName(String serviceName, String regType, String domain, String[] pOut);

    protected native int GetIfIndexForName(String ifName);

    protected native byte[] GetNameForIfIndex(int ifIndex);

    protected native int ReconfirmRecord(int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata);

    AppleDNSSD() {
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected void _init(String lib) {
        System.loadLibrary(lib);
        int iInitLibrary = InitLibrary(2);
        if (iInitLibrary == 0) {
            return;
        }
        throw new InternalError("cannot instantiate DNSSD: " + new AppleDNSSDException(iInitLibrary).getMessage());
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected DNSSDService _makeBrowser(int flags, int ifIndex, String regType, String domain, InternalBrowseListener client) throws DNSSDException {
        return new AppleBrowser(flags, ifIndex, regType, domain, client);
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected DNSSDService _resolve(int flags, int ifIndex, String serviceName, String regType, String domain, InternalResolveListener client) throws DNSSDException {
        return new AppleResolver(flags, ifIndex, serviceName, regType, domain, client);
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected DNSSDRegistration _register(int flags, int ifIndex, String serviceName, String regType, String domain, String host, int port, TXTRecord txtRecord, InternalRegisterListener client) throws DNSSDException {
        return new AppleRegistration(flags, ifIndex, serviceName, regType, domain, host, port, txtRecord != null ? txtRecord.getRawBytes() : null, client);
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected DNSSDRecordRegistrar _createRecordRegistrar(RegisterRecordListener listener) throws DNSSDException {
        return new AppleRecordRegistrar(listener);
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected DNSSDService _queryRecord(int flags, int ifIndex, String serviceName, int rrtype, int rrclass, InternalQueryListener client) throws DNSSDException {
        return new AppleQuery(flags, ifIndex, serviceName, rrtype, rrclass, client);
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected DNSSDService _enumerateDomains(int flags, int ifIndex, InternalDomainListener listener) throws DNSSDException {
        return new AppleDomainEnum(flags, ifIndex, listener);
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected String _constructFullName(String serviceName, String regType, String domain) throws DNSSDException {
        String[] strArr = new String[1];
        int iConstructName = ConstructName(serviceName, regType, domain, strArr);
        if (iConstructName == 0) {
            return strArr[0];
        }
        throw new AppleDNSSDException(iConstructName);
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected int _reconfirmRecord(int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata) {
        return ReconfirmRecord(flags, ifIndex, fullName, rrtype, rrclass, rdata);
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected String _getNameForIfIndex(int ifIndex) {
        return new String(GetNameForIfIndex(ifIndex));
    }

    @Override // com.github.druk.dnssd.InternalDNSSD
    protected int _getIfIndexForName(String ifName) {
        return GetIfIndexForName(ifName);
    }
}
