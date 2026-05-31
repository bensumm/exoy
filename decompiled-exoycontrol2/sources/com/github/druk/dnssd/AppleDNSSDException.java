package com.github.druk.dnssd;

/* JADX INFO: compiled from: InternalDNSSD.java */
/* JADX INFO: loaded from: classes.dex */
class AppleDNSSDException extends DNSSDException {
    protected int fErrorCode;

    public AppleDNSSDException(int errorCode) {
        this.fErrorCode = errorCode;
    }

    @Override // com.github.druk.dnssd.DNSSDException
    public int getErrorCode() {
        return this.fErrorCode;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String[] strArr = {"UNKNOWN", "NO_SUCH_NAME", "NO_MEMORY", "BAD_PARAM", "BAD_REFERENCE", "BAD_STATE", "BAD_FLAGS", "UNSUPPORTED", "NOT_INITIALIZED", "NO_CACHE", "ALREADY_REGISTERED", "NAME_CONFLICT", "INVALID", "FIREWALL", "INCOMPATIBLE", "BAD_INTERFACE_INDEX", "REFUSED", "NOSUCHRECORD", "NOAUTH", "NOSUCHKEY", "NATTRAVERSAL", "DOUBLENAT", "BADTIME", "BADSIG", "BADKEY", "TRANSIENT", "SERVICENOTRUNNING", "NATPORTMAPPINGUNSUPPORTED", "NATPORTMAPPINGDISABLED"};
        int i = this.fErrorCode;
        if (i <= -65537 && i > -65566) {
            return "DNS-SD Error " + String.valueOf(this.fErrorCode) + ": " + strArr[DNSSDException.UNKNOWN - this.fErrorCode];
        }
        return super.getMessage() + "(" + String.valueOf(this.fErrorCode) + ")";
    }
}
