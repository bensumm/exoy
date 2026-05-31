package com.github.druk.rx2dnssd;

import android.content.Context;
import com.github.druk.dnssd.DNSSD;
import com.github.druk.dnssd.DNSSDBindable;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

/* JADX INFO: loaded from: classes.dex */
public class Rx2DnssdBindable extends Rx2DnssdCommon {
    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    public /* bridge */ /* synthetic */ Flowable browse(final String regType, final String domain) {
        return super.browse(regType, domain);
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon
    public /* bridge */ /* synthetic */ DNSSD getDNSSD() {
        return super.getDNSSD();
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    public /* bridge */ /* synthetic */ Flowable queryIPRecords(BonjourService bs) {
        return super.queryIPRecords(bs);
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    public /* bridge */ /* synthetic */ FlowableTransformer queryIPRecords() {
        return super.queryIPRecords();
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    public /* bridge */ /* synthetic */ Flowable queryIPV4Records(BonjourService bs) {
        return super.queryIPV4Records(bs);
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    public /* bridge */ /* synthetic */ FlowableTransformer queryIPV4Records() {
        return super.queryIPV4Records();
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    public /* bridge */ /* synthetic */ Flowable queryIPV6Records(BonjourService bs) {
        return super.queryIPV6Records(bs);
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    public /* bridge */ /* synthetic */ FlowableTransformer queryIPV6Records() {
        return super.queryIPV6Records();
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    @Deprecated
    public /* bridge */ /* synthetic */ FlowableTransformer queryRecords() {
        return super.queryRecords();
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    public /* bridge */ /* synthetic */ Flowable queryTXTRecords(BonjourService bs) {
        return super.queryTXTRecords(bs);
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    public /* bridge */ /* synthetic */ Flowable register(final BonjourService bs) {
        return super.register(bs);
    }

    @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon, com.github.druk.rx2dnssd.Rx2Dnssd
    public /* bridge */ /* synthetic */ FlowableTransformer resolve() {
        return super.resolve();
    }

    public Rx2DnssdBindable(Context context) {
        super(new DNSSDBindable(context));
    }
}
