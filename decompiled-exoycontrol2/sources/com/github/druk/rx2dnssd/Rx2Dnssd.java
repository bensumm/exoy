package com.github.druk.rx2dnssd;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

/* JADX INFO: loaded from: classes.dex */
public interface Rx2Dnssd {
    Flowable<BonjourService> browse(final String regType, final String domain);

    Flowable<BonjourService> queryIPRecords(BonjourService bs);

    FlowableTransformer<BonjourService, BonjourService> queryIPRecords();

    Flowable<BonjourService> queryIPV4Records(BonjourService bs);

    FlowableTransformer<BonjourService, BonjourService> queryIPV4Records();

    Flowable<BonjourService> queryIPV6Records(BonjourService bs);

    FlowableTransformer<BonjourService, BonjourService> queryIPV6Records();

    @Deprecated
    FlowableTransformer<BonjourService, BonjourService> queryRecords();

    Flowable<BonjourService> queryTXTRecords(BonjourService bs);

    Flowable<BonjourService> register(final BonjourService bs);

    FlowableTransformer<BonjourService, BonjourService> resolve();
}
