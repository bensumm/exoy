package com.facebook.react.common;

import com.facebook.infer.annotation.Assertions;

/* JADX INFO: loaded from: classes.dex */
public class SingleThreadAsserter {
    private Thread mThread = null;

    public void assertNow() {
        Thread threadCurrentThread = Thread.currentThread();
        if (this.mThread == null) {
            this.mThread = threadCurrentThread;
        }
        Assertions.assertCondition(this.mThread == threadCurrentThread);
    }
}
