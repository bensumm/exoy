package com.facebook.fresco.ui.common;

import android.net.Uri;
import com.facebook.common.internal.Fn;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public abstract class MultiUriHelper {
    @Nullable
    public static <T> Uri getMainUri(@Nullable T mainRequest, @Nullable T lowResRequest, @Nullable T[] firstAvailableRequest, Fn<T, Uri> requestToUri) {
        T t;
        Uri uriApply;
        Uri uriApply2;
        if (mainRequest != null && (uriApply2 = requestToUri.apply(mainRequest)) != null) {
            return uriApply2;
        }
        if (firstAvailableRequest != null && firstAvailableRequest.length > 0 && (t = firstAvailableRequest[0]) != null && (uriApply = requestToUri.apply(t)) != null) {
            return uriApply;
        }
        if (lowResRequest != null) {
            return requestToUri.apply(lowResRequest);
        }
        return null;
    }
}
