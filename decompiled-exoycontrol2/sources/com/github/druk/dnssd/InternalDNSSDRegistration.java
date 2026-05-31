package com.github.druk.dnssd;

import com.github.druk.dnssd.InternalDNSSDService;

/* JADX INFO: loaded from: classes.dex */
class InternalDNSSDRegistration implements DNSSDRegistration {
    private boolean isStopped = false;
    private final InternalDNSSDService.DnssdServiceListener listener;
    private final DNSSDRegistration originalDNSSDService;

    InternalDNSSDRegistration(InternalDNSSDService.DnssdServiceListener listener, DNSSDRegistration registration) {
        this.listener = listener;
        this.originalDNSSDService = registration;
    }

    @Override // com.github.druk.dnssd.DNSSDRegistration
    public DNSRecord getTXTRecord() throws DNSSDException {
        return this.originalDNSSDService.getTXTRecord();
    }

    @Override // com.github.druk.dnssd.DNSSDRegistration
    public DNSRecord addRecord(int flags, int rrType, byte[] rData, int ttl) throws DNSSDException {
        return this.originalDNSSDService.addRecord(flags, rrType, rData, ttl);
    }

    @Override // com.github.druk.dnssd.DNSSDService
    public void stop() {
        this.originalDNSSDService.stop();
        synchronized (this) {
            if (!this.isStopped) {
                this.listener.onServiceStopped();
                this.isStopped = true;
            }
        }
    }
}
