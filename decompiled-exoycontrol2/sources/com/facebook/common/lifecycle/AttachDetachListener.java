package com.facebook.common.lifecycle;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface AttachDetachListener {
    void onAttachToView(View view);

    void onDetachFromView(View view);
}
