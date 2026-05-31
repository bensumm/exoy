package org.apache.commons.lang3.concurrent;

/* JADX INFO: loaded from: classes2.dex */
public interface ConcurrentInitializer<T> {
    T get() throws ConcurrentException;
}
