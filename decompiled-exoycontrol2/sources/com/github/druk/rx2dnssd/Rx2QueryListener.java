package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSD;
import com.github.druk.dnssd.DNSSDService;
import com.github.druk.dnssd.QueryListener;
import com.github.druk.rx2dnssd.BonjourService;
import io.reactivex.FlowableEmitter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: classes.dex */
class Rx2QueryListener implements QueryListener {
    private final BonjourService.Builder builder;
    private final boolean completable;
    private final FlowableEmitter<? super BonjourService> emitter;

    Rx2QueryListener(FlowableEmitter<? super BonjourService> emitter, BonjourService.Builder builder, boolean completable) {
        this.emitter = emitter;
        this.builder = builder;
        this.completable = completable;
    }

    @Override // com.github.druk.dnssd.QueryListener
    public void queryAnswered(DNSSDService query, int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata, int ttl) {
        if (this.emitter.isCancelled()) {
            return;
        }
        if (rrtype == 1 || rrtype == 28) {
            try {
                this.builder.inetAddress(InetAddress.getByAddress(rdata));
            } catch (UnknownHostException e) {
                this.emitter.tryOnError(e);
            }
        } else if (rrtype == 16) {
            this.builder.dnsRecords(DNSSD.parseTXTRecords(rdata));
        } else {
            this.emitter.tryOnError(new Exception("Unsupported type of record: " + rrtype));
        }
        this.emitter.onNext(this.builder.build());
        if (this.completable) {
            this.emitter.onComplete();
        }
    }

    @Override // com.github.druk.dnssd.BaseListener
    public void operationFailed(DNSSDService service, int errorCode) {
        if (this.emitter.isCancelled()) {
            return;
        }
        this.emitter.onError(new RuntimeException("DNSSD queryRecord error: " + errorCode));
    }
}
