package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSDService;
import com.github.druk.dnssd.ResolveListener;
import com.github.druk.rx2dnssd.BonjourService;
import io.reactivex.FlowableEmitter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class Rx2ResolveListener implements ResolveListener {
    private final BonjourService.Builder builder;
    private final FlowableEmitter<? super BonjourService> emitter;

    Rx2ResolveListener(FlowableEmitter<? super BonjourService> emitter, BonjourService service) {
        this.emitter = emitter;
        this.builder = new BonjourService.Builder(service);
    }

    @Override // com.github.druk.dnssd.ResolveListener
    public void serviceResolved(DNSSDService resolver, int flags, int ifIndex, String fullName, String hostName, int port, Map<String, String> txtRecord) {
        if (this.emitter.isCancelled()) {
            return;
        }
        this.emitter.onNext(this.builder.port(port).hostname(hostName).dnsRecords(txtRecord).build());
        this.emitter.onComplete();
    }

    @Override // com.github.druk.dnssd.BaseListener
    public void operationFailed(DNSSDService service, int errorCode) {
        if (this.emitter.isCancelled()) {
            return;
        }
        this.emitter.onError(new RuntimeException("DNSSD resolve error: " + errorCode));
    }
}
