package com.github.druk.dnssd;

/* JADX INFO: compiled from: InternalDNSSD.java */
/* JADX INFO: loaded from: classes.dex */
class AppleService implements DNSSDService, Runnable {
    protected BaseListener fListener;
    protected long fNativeContext = 0;

    protected native int BlockForData();

    protected native synchronized void HaltOperation();

    protected native int ProcessResults();

    public AppleService(BaseListener listener) {
        this.fListener = listener;
    }

    @Override // com.github.druk.dnssd.DNSSDService
    public void stop() {
        HaltOperation();
    }

    protected void ThrowOnErr(int rc) throws DNSSDException {
        if (rc != 0) {
            throw new AppleDNSSDException(rc);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            int iBlockForData = BlockForData();
            synchronized (this) {
                if (this.fNativeContext == 0) {
                    return;
                }
                if (iBlockForData != 0) {
                    int iProcessResults = ProcessResults();
                    if (this.fNativeContext == 0) {
                        return;
                    }
                    if (iProcessResults != 0) {
                        this.fListener.operationFailed(this, iProcessResults);
                        return;
                    }
                }
            }
        }
    }
}
