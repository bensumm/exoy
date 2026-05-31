package com.github.druk.dnssd;

import com.github.druk.dnssd.InternalDNSSDService;

/* JADX INFO: loaded from: classes.dex */
class InternalDNSSDRecordRegistrar implements DNSSDRecordRegistrar {
    private boolean isStopped = false;
    private final InternalDNSSDService.DnssdServiceListener listener;
    private final DNSSDRecordRegistrar originalService;

    InternalDNSSDRecordRegistrar(InternalDNSSDService.DnssdServiceListener listener, DNSSDRecordRegistrar registration) {
        this.listener = listener;
        this.originalService = registration;
    }

    @Override // com.github.druk.dnssd.DNSSDRecordRegistrar
    public DNSRecord registerRecord(int flags, int ifIndex, String fullname, int rrtype, int rrclass, byte[] rData, int ttl) throws DNSSDException {
        return this.originalService.registerRecord(flags, ifIndex, fullname, rrtype, rrclass, rData, ttl);
    }

    @Override // com.github.druk.dnssd.DNSSDService
    public void stop() {
        this.originalService.stop();
        synchronized (this) {
            if (!this.isStopped) {
                this.listener.onServiceStopped();
                this.isStopped = true;
            }
        }
    }
}
