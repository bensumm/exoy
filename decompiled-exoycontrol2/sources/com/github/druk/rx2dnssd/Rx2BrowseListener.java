package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.BrowseListener;
import com.github.druk.dnssd.DNSSDService;
import com.github.druk.rx2dnssd.BonjourService;
import io.reactivex.FlowableEmitter;

/* JADX INFO: loaded from: classes.dex */
class Rx2BrowseListener implements BrowseListener {
    private final FlowableEmitter<? super BonjourService> emitter;

    Rx2BrowseListener(FlowableEmitter<? super BonjourService> emitter) {
        this.emitter = emitter;
    }

    @Override // com.github.druk.dnssd.BrowseListener
    public void serviceFound(DNSSDService browser, int flags, int ifIndex, String serviceName, String regType, String domain) {
        if (this.emitter.isCancelled()) {
            return;
        }
        this.emitter.onNext(new BonjourService.Builder(flags, ifIndex, serviceName, regType, domain).build());
    }

    @Override // com.github.druk.dnssd.BrowseListener
    public void serviceLost(DNSSDService browser, int flags, int ifIndex, String serviceName, String regType, String domain) {
        if (this.emitter.isCancelled()) {
            return;
        }
        this.emitter.onNext(new BonjourService.Builder(flags | 256, ifIndex, serviceName, regType, domain).build());
    }

    @Override // com.github.druk.dnssd.BaseListener
    public void operationFailed(DNSSDService service, int errorCode) {
        if (this.emitter.isCancelled()) {
            return;
        }
        this.emitter.onError(new RuntimeException("DNSSD browse error: " + errorCode));
    }
}
