package com.facebook.react.views.scroll;

import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
public class OnScrollDispatchHelper {
    private static final int MIN_EVENT_SEPARATION_MS = 10;
    private int mPrevX = Integer.MIN_VALUE;
    private int mPrevY = Integer.MIN_VALUE;
    private float mXFlingVelocity = 0.0f;
    private float mYFlingVelocity = 0.0f;
    private long mLastScrollEventTimeMs = -11;

    public boolean onScrollChanged(int i, int i2) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        long j = this.mLastScrollEventTimeMs;
        boolean z = (jUptimeMillis - j <= 10 && this.mPrevX == i && this.mPrevY == i2) ? false : true;
        if (jUptimeMillis - j != 0) {
            this.mXFlingVelocity = (i - this.mPrevX) / (jUptimeMillis - j);
            this.mYFlingVelocity = (i2 - this.mPrevY) / (jUptimeMillis - j);
        }
        this.mLastScrollEventTimeMs = jUptimeMillis;
        this.mPrevX = i;
        this.mPrevY = i2;
        return z;
    }

    public float getXFlingVelocity() {
        return this.mXFlingVelocity;
    }

    public float getYFlingVelocity() {
        return this.mYFlingVelocity;
    }
}
