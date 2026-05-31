package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
abstract class InternalDNSSD {
    public static final int ALL_INTERFACES = 0;
    public static final int BROWSE_DOMAINS = 64;
    public static final int DEFAULT = 4;
    public static final int LOCALHOST_ONLY = -1;
    public static final int MAX_DOMAIN_NAME = 1009;
    public static final int MORE_COMING = 1;
    public static final int NO_AUTO_RENAME = 8;
    public static final int REGISTRATION_DOMAINS = 128;
    public static final int SHARED = 16;
    public static final int UNIQUE = 32;
    protected static InternalDNSSD fInstance;

    protected abstract String _constructFullName(String serviceName, String regType, String domain) throws DNSSDException;

    protected abstract DNSSDRecordRegistrar _createRecordRegistrar(RegisterRecordListener listener) throws DNSSDException;

    protected abstract DNSSDService _enumerateDomains(int flags, int ifIndex, InternalDomainListener listener) throws DNSSDException;

    protected abstract int _getIfIndexForName(String ifName);

    protected abstract String _getNameForIfIndex(int ifIndex);

    protected abstract void _init(String lib);

    protected abstract DNSSDService _makeBrowser(int flags, int ifIndex, String regType, String domain, InternalBrowseListener listener) throws DNSSDException;

    protected abstract DNSSDService _queryRecord(int flags, int ifIndex, String serviceName, int rrtype, int rrclass, InternalQueryListener listener) throws DNSSDException;

    protected abstract int _reconfirmRecord(int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata);

    protected abstract DNSSDRegistration _register(int flags, int ifIndex, String serviceName, String regType, String domain, String host, int port, TXTRecord txtRecord, InternalRegisterListener listener) throws DNSSDException;

    protected abstract DNSSDService _resolve(int flags, int ifIndex, String serviceName, String regType, String domain, InternalResolveListener listener) throws DNSSDException;

    public static void init(String lib) {
        getInstance()._init(lib);
    }

    public static DNSSDService browse(int flags, int ifIndex, String regType, String domain, InternalBrowseListener listener) throws DNSSDException {
        return getInstance()._makeBrowser(flags, ifIndex, regType, domain, listener);
    }

    public static DNSSDService browse(String regType, InternalBrowseListener listener) throws DNSSDException {
        return browse(0, 0, regType, "", listener);
    }

    public static DNSSDService resolve(int flags, int ifIndex, String serviceName, String regType, String domain, InternalResolveListener listener) throws DNSSDException {
        return getInstance()._resolve(flags, ifIndex, serviceName, regType, domain, listener);
    }

    public static DNSSDRegistration register(int flags, int ifIndex, String serviceName, String regType, String domain, String host, int port, TXTRecord txtRecord, InternalRegisterListener listener) throws DNSSDException {
        return getInstance()._register(flags, ifIndex, serviceName, regType, domain, host, port, txtRecord, listener);
    }

    public static DNSSDRegistration register(String serviceName, String regType, int port, InternalRegisterListener listener) throws DNSSDException {
        return register(0, 0, serviceName, regType, null, null, port, null, listener);
    }

    public static DNSSDRecordRegistrar createRecordRegistrar(RegisterRecordListener listener) throws DNSSDException {
        return getInstance()._createRecordRegistrar(listener);
    }

    public static DNSSDService queryRecord(int flags, int ifIndex, String serviceName, int rrtype, int rrclass, InternalQueryListener listener) throws DNSSDException {
        return getInstance()._queryRecord(flags, ifIndex, serviceName, rrtype, rrclass, listener);
    }

    public static DNSSDService enumerateDomains(int flags, int ifIndex, InternalDomainListener listener) throws DNSSDException {
        return getInstance()._enumerateDomains(flags, ifIndex, listener);
    }

    public static String constructFullName(String serviceName, String regType, String domain) throws DNSSDException {
        return getInstance()._constructFullName(serviceName, regType, domain);
    }

    public static int reconfirmRecord(int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata) {
        return getInstance()._reconfirmRecord(flags, ifIndex, fullName, rrtype, rrclass, rdata);
    }

    public static String getNameForIfIndex(int ifIndex) {
        return getInstance()._getNameForIfIndex(ifIndex);
    }

    public static int getIfIndexForName(String ifName) {
        return getInstance()._getIfIndexForName(ifName);
    }

    protected InternalDNSSD() {
    }

    protected static final InternalDNSSD getInstance() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getDNSSDInstance"));
        }
        return fInstance;
    }

    static {
        try {
            String property = System.getProperty("com.github.druk.dnssd.DNSSD");
            if (property == null) {
                property = "com.github.druk.dnssd.AppleDNSSD";
            }
            fInstance = (InternalDNSSD) Class.forName(property).newInstance();
        } catch (Exception e) {
            throw new InternalError("cannot instantiate DNSSD" + e);
        }
    }
}
