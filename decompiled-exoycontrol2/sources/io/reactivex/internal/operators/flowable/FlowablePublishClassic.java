package io.reactivex.internal.operators.flowable;

import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes2.dex */
public interface FlowablePublishClassic<T> {
    int publishBufferSize();

    Publisher<T> publishSource();
}
