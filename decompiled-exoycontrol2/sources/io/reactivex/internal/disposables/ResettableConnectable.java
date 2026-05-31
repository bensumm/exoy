package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;

/* JADX INFO: loaded from: classes2.dex */
public interface ResettableConnectable {
    void resetIf(Disposable disposable);
}
