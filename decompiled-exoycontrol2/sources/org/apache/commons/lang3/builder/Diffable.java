package org.apache.commons.lang3.builder;

/* JADX INFO: loaded from: classes2.dex */
@FunctionalInterface
public interface Diffable<T> {
    DiffResult<T> diff(T t);
}
