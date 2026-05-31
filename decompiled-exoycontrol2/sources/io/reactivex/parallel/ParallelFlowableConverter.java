package io.reactivex.parallel;

/* JADX INFO: loaded from: classes2.dex */
public interface ParallelFlowableConverter<T, R> {
    R apply(ParallelFlowable<T> parallelFlowable);
}
