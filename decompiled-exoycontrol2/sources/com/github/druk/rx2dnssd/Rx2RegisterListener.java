package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSDRegistration;
import com.github.druk.dnssd.DNSSDService;
import com.github.druk.dnssd.RegisterListener;
import com.github.druk.rx2dnssd.BonjourService;
import io.reactivex.FlowableEmitter;

/* JADX INFO: loaded from: classes.dex */
class Rx2RegisterListener implements RegisterListener {
    private final FlowableEmitter<? super BonjourService> emitter;

    Rx2RegisterListener(FlowableEmitter<? super BonjourService> emitter) {
        this.emitter = emitter;
    }

    @Override // com.github.druk.dnssd.RegisterListener
    public void serviceRegistered(DNSSDRegistration registration, int flags, String serviceName, String regType, String domain) {
        if (this.emitter.isCancelled()) {
            return;
        }
        this.emitter.onNext(new BonjourService.Builder(flags, 0, serviceName, regType, domain).build());
    }

    @Override // com.github.druk.dnssd.BaseListener
    public void operationFailed(DNSSDService service, int errorCode) {
        if (this.emitter.isCancelled()) {
            return;
        }
        this.emitter.onError(new RuntimeException("DNSSD browse error: " + errorCode));
    }
}
