package com.facebook.react.uimanager;

import android.graphics.Rect;

/* JADX INFO: loaded from: classes.dex */
public interface ReactClippingViewGroup {
    void getClippingRect(Rect rect);

    boolean getRemoveClippedSubviews();

    void setRemoveClippedSubviews(boolean z);

    void updateClippingRect();
}
