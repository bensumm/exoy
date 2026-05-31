package com.facebook.react.jstasks;

import javax.annotation.CheckReturnValue;

/* JADX INFO: loaded from: classes.dex */
public interface HeadlessJsTaskRetryPolicy {
    boolean canRetry();

    HeadlessJsTaskRetryPolicy copy();

    int getDelay();

    @CheckReturnValue
    HeadlessJsTaskRetryPolicy update();
}
