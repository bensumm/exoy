package com.facebook.imagepipeline.producers;

import com.facebook.imagepipeline.common.ResizeOptions;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public interface ThumbnailProducer<T> extends Producer<T> {
    boolean canProvideImageForSize(@Nullable ResizeOptions resizeOptions);
}
