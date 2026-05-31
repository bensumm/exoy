package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSD;
import com.github.druk.dnssd.DNSSDException;
import com.github.druk.dnssd.DNSSDService;
import com.github.druk.dnssd.TXTRecord;
import com.github.druk.rx2dnssd.BonjourService;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import java.util.Map;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes.dex */
abstract class Rx2DnssdCommon implements Rx2Dnssd {
    private final DNSSD mDNSSD;

    /* JADX INFO: Access modifiers changed from: private */
    interface DNSSDServiceCreator<T> {
        DNSSDService getService(FlowableEmitter<? super T> emitter) throws DNSSDException;
    }

    Rx2DnssdCommon(DNSSD dnssd) {
        this.mDNSSD = dnssd;
    }

    public DNSSD getDNSSD() {
        return this.mDNSSD;
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    public Flowable<BonjourService> browse(final String regType, final String domain) {
        return createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda17
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m386lambda$browse$0$comgithubdrukrx2dnssdRx2DnssdCommon(regType, domain, flowableEmitter);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$browse$0$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m386lambda$browse$0$comgithubdrukrx2dnssdRx2DnssdCommon(final String regType, final String domain, FlowableEmitter emitter) throws DNSSDException {
        return this.mDNSSD.browse(0, 0, regType, domain, new Rx2BrowseListener(emitter));
    }

    /* JADX INFO: renamed from: lambda$resolve$3$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ Publisher m407lambda$resolve$3$comgithubdrukrx2dnssdRx2DnssdCommon(Flowable flowable) {
        return flowable.flatMap(new Function() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda15
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m393lambda$null$2$comgithubdrukrx2dnssdRx2DnssdCommon((BonjourService) obj);
            }
        });
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    public FlowableTransformer<BonjourService, BonjourService> resolve() {
        return new FlowableTransformer() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda18
            @Override // io.reactivex.FlowableTransformer
            public final Publisher apply(Flowable flowable) {
                return this.f$0.m407lambda$resolve$3$comgithubdrukrx2dnssdRx2DnssdCommon(flowable);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$null$2$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ Publisher m393lambda$null$2$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs) throws Exception {
        if ((bs.getFlags() & 256) == 256) {
            return Flowable.just(bs);
        }
        return createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda10
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m387lambda$null$1$comgithubdrukrx2dnssdRx2DnssdCommon(bs, flowableEmitter);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$null$1$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m387lambda$null$1$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, FlowableEmitter emitter) throws DNSSDException {
        return this.mDNSSD.resolve(bs.getFlags(), bs.getIfIndex(), bs.getServiceName(), bs.getRegType(), bs.getDomain(), new Rx2ResolveListener(emitter, bs));
    }

    /* JADX INFO: renamed from: lambda$queryRecords$7$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ Publisher m405lambda$queryRecords$7$comgithubdrukrx2dnssdRx2DnssdCommon(Flowable flowable) {
        return flowable.flatMap(new Function() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m396lambda$null$6$comgithubdrukrx2dnssdRx2DnssdCommon((BonjourService) obj);
            }
        });
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    @Deprecated
    public FlowableTransformer<BonjourService, BonjourService> queryRecords() {
        return new FlowableTransformer() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda16
            @Override // io.reactivex.FlowableTransformer
            public final Publisher apply(Flowable flowable) {
                return this.f$0.m405lambda$queryRecords$7$comgithubdrukrx2dnssdRx2DnssdCommon(flowable);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$null$6$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ Publisher m396lambda$null$6$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs) throws Exception {
        if ((bs.getFlags() & 256) == 256) {
            return Flowable.just(bs);
        }
        final BonjourService.Builder builder = new BonjourService.Builder(bs);
        return createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda1
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m394lambda$null$4$comgithubdrukrx2dnssdRx2DnssdCommon(bs, builder, flowableEmitter);
            }
        }).mergeWith(createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda2
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m395lambda$null$5$comgithubdrukrx2dnssdRx2DnssdCommon(bs, builder, flowableEmitter);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$null$4$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m394lambda$null$4$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, final BonjourService.Builder builder, FlowableEmitter emitter) throws DNSSDException {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 1, 1, true, new Rx2QueryListener(emitter, builder, true));
    }

    /* JADX INFO: renamed from: lambda$null$5$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m395lambda$null$5$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, final BonjourService.Builder builder, FlowableEmitter emitter) throws DNSSDException {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 28, 1, true, new Rx2QueryListener(emitter, builder, true));
    }

    /* JADX INFO: renamed from: lambda$queryIPRecords$11$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ Publisher m399lambda$queryIPRecords$11$comgithubdrukrx2dnssdRx2DnssdCommon(Flowable flowable) {
        return flowable.flatMap(new Function() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m388lambda$null$10$comgithubdrukrx2dnssdRx2DnssdCommon((BonjourService) obj);
            }
        });
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    public FlowableTransformer<BonjourService, BonjourService> queryIPRecords() {
        return new FlowableTransformer() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda3
            @Override // io.reactivex.FlowableTransformer
            public final Publisher apply(Flowable flowable) {
                return this.f$0.m399lambda$queryIPRecords$11$comgithubdrukrx2dnssdRx2DnssdCommon(flowable);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$null$10$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ Publisher m388lambda$null$10$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs) throws Exception {
        if ((bs.getFlags() & 256) == 256) {
            return Flowable.just(bs);
        }
        final BonjourService.Builder builder = new BonjourService.Builder(bs);
        return createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda11
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m397lambda$null$8$comgithubdrukrx2dnssdRx2DnssdCommon(bs, builder, flowableEmitter);
            }
        }).mergeWith(createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda14
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m398lambda$null$9$comgithubdrukrx2dnssdRx2DnssdCommon(bs, builder, flowableEmitter);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$null$8$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m397lambda$null$8$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, final BonjourService.Builder builder, FlowableEmitter emitter) throws DNSSDException {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 1, 1, true, new Rx2QueryListener(emitter, builder, true));
    }

    /* JADX INFO: renamed from: lambda$null$9$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m398lambda$null$9$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, final BonjourService.Builder builder, FlowableEmitter emitter) throws DNSSDException {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 28, 1, true, new Rx2QueryListener(emitter, builder, true));
    }

    /* JADX INFO: renamed from: lambda$queryIPV4Records$14$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ Publisher m402xa7efd3de(Flowable flowable) {
        return flowable.flatMap(new Function() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda12
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m390lambda$null$13$comgithubdrukrx2dnssdRx2DnssdCommon((BonjourService) obj);
            }
        });
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    public FlowableTransformer<BonjourService, BonjourService> queryIPV4Records() {
        return new FlowableTransformer() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda20
            @Override // io.reactivex.FlowableTransformer
            public final Publisher apply(Flowable flowable) {
                return this.f$0.m402xa7efd3de(flowable);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$null$13$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ Publisher m390lambda$null$13$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs) throws Exception {
        if ((bs.getFlags() & 256) == 256) {
            return Flowable.just(bs);
        }
        return createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda6
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m389lambda$null$12$comgithubdrukrx2dnssdRx2DnssdCommon(bs, flowableEmitter);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$null$12$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m389lambda$null$12$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, FlowableEmitter emitter) throws DNSSDException {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 1, 1, true, new Rx2QueryListener(emitter, new BonjourService.Builder(bs), true));
    }

    /* JADX INFO: renamed from: lambda$queryIPV6Records$17$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ Publisher m403xd4e69063(Flowable flowable) {
        return flowable.flatMap(new Function() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.m392lambda$null$16$comgithubdrukrx2dnssdRx2DnssdCommon((BonjourService) obj);
            }
        });
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    public FlowableTransformer<BonjourService, BonjourService> queryIPV6Records() {
        return new FlowableTransformer() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda21
            @Override // io.reactivex.FlowableTransformer
            public final Publisher apply(Flowable flowable) {
                return this.f$0.m403xd4e69063(flowable);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$null$16$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ Publisher m392lambda$null$16$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs) throws Exception {
        if ((bs.getFlags() & 256) == 256) {
            return Flowable.just(bs);
        }
        return createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda4
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m391lambda$null$15$comgithubdrukrx2dnssdRx2DnssdCommon(bs, flowableEmitter);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$null$15$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m391lambda$null$15$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, FlowableEmitter emitter) throws DNSSDException {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 28, 1, true, new Rx2QueryListener(emitter, new BonjourService.Builder(bs), true));
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    public Flowable<BonjourService> queryIPRecords(final BonjourService bs) {
        if ((bs.getFlags() & 256) == 256) {
            return Flowable.just(bs);
        }
        final BonjourService.Builder builder = new BonjourService.Builder(bs);
        return createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda7
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m400lambda$queryIPRecords$18$comgithubdrukrx2dnssdRx2DnssdCommon(bs, builder, flowableEmitter);
            }
        }).mergeWith(createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda8
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m401lambda$queryIPRecords$19$comgithubdrukrx2dnssdRx2DnssdCommon(bs, builder, flowableEmitter);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$queryIPRecords$18$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m400lambda$queryIPRecords$18$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, final BonjourService.Builder builder, FlowableEmitter subscriber) throws DNSSDException {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 1, 1, false, new Rx2QueryListener(subscriber, builder, false));
    }

    /* JADX INFO: renamed from: lambda$queryIPRecords$19$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m401lambda$queryIPRecords$19$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, final BonjourService.Builder builder, FlowableEmitter subscriber) throws DNSSDException {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), 28, 1, false, new Rx2QueryListener(subscriber, builder, false));
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    public Flowable<BonjourService> queryIPV4Records(BonjourService bs) {
        return queryRecords(bs, 1);
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    public Flowable<BonjourService> queryIPV6Records(BonjourService bs) {
        return queryRecords(bs, 28);
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    public Flowable<BonjourService> queryTXTRecords(BonjourService bs) {
        return queryRecords(bs, 16);
    }

    private Flowable<BonjourService> queryRecords(final BonjourService bs, final int type) {
        if ((bs.getFlags() & 256) == 256) {
            return Flowable.just(bs);
        }
        return createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda19
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m404lambda$queryRecords$20$comgithubdrukrx2dnssdRx2DnssdCommon(bs, type, flowableEmitter);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$queryRecords$20$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m404lambda$queryRecords$20$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, final int type, FlowableEmitter subscriber) throws DNSSDException {
        return this.mDNSSD.queryRecord(0, bs.getIfIndex(), bs.getHostname(), type, 1, false, new Rx2QueryListener(subscriber, new BonjourService.Builder(bs), false));
    }

    @Override // com.github.druk.rx2dnssd.Rx2Dnssd
    public Flowable<BonjourService> register(final BonjourService bs) {
        return createFlowable(new DNSSDServiceCreator() { // from class: com.github.druk.rx2dnssd.Rx2DnssdCommon$$ExternalSyntheticLambda0
            @Override // com.github.druk.rx2dnssd.Rx2DnssdCommon.DNSSDServiceCreator
            public final DNSSDService getService(FlowableEmitter flowableEmitter) {
                return this.f$0.m406lambda$register$21$comgithubdrukrx2dnssdRx2DnssdCommon(bs, flowableEmitter);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$register$21$com-github-druk-rx2dnssd-Rx2DnssdCommon, reason: not valid java name */
    /* synthetic */ DNSSDService m406lambda$register$21$comgithubdrukrx2dnssdRx2DnssdCommon(final BonjourService bs, FlowableEmitter emitter) throws DNSSDException {
        return this.mDNSSD.register(bs.getFlags(), bs.getIfIndex(), bs.getServiceName(), bs.getRegType(), bs.getDomain(), null, bs.getPort(), createTxtRecord(bs.getTxtRecords()), new Rx2RegisterListener(emitter));
    }

    private static class DNSSDServiceAction<T> implements FlowableOnSubscribe<T>, Action {
        private final DNSSDServiceCreator<T> creator;
        private DNSSDService service;

        DNSSDServiceAction(DNSSDServiceCreator<T> creator) {
            this.creator = creator;
        }

        @Override // io.reactivex.FlowableOnSubscribe
        public void subscribe(FlowableEmitter<T> emitter) {
            DNSSDServiceCreator<T> dNSSDServiceCreator;
            if (emitter.isCancelled() || (dNSSDServiceCreator = this.creator) == null) {
                return;
            }
            try {
                this.service = dNSSDServiceCreator.getService(emitter);
            } catch (DNSSDException e) {
                emitter.onError(e);
            }
        }

        @Override // io.reactivex.functions.Action
        public void run() {
            DNSSDService dNSSDService = this.service;
            if (dNSSDService != null) {
                dNSSDService.stop();
                this.service = null;
            }
        }
    }

    private <T> Flowable<T> createFlowable(DNSSDServiceCreator<T> creator) {
        DNSSDServiceAction dNSSDServiceAction = new DNSSDServiceAction(creator);
        return Flowable.create(dNSSDServiceAction, BackpressureStrategy.BUFFER).doFinally(dNSSDServiceAction);
    }

    private static TXTRecord createTxtRecord(Map<String, String> records) {
        TXTRecord tXTRecord = new TXTRecord();
        for (Map.Entry<String, String> entry : records.entrySet()) {
            tXTRecord.set(entry.getKey(), entry.getValue());
        }
        return tXTRecord;
    }
}
