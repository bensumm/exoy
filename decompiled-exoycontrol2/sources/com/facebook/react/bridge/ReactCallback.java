package com.facebook.react.bridge;

/* JADX INFO: loaded from: classes.dex */
interface ReactCallback {
    void decrementPendingJSCalls();

    void incrementPendingJSCalls();

    void onBatchComplete();
}
