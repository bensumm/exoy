package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
class InternalDNSSDService implements DNSSDService {
    private boolean isStopped = false;
    private final DnssdServiceListener listener;
    private final DNSSDService originalDNSSDService;

    interface DnssdServiceListener {
        void onServiceStarting();

        void onServiceStopped();
    }

    InternalDNSSDService(DnssdServiceListener listener, DNSSDService DNSSDService) {
        this.listener = listener;
        this.originalDNSSDService = DNSSDService;
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
