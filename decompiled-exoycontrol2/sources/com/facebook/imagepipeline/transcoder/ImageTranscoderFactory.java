package com.facebook.imagepipeline.transcoder;

import com.facebook.imageformat.ImageFormat;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public interface ImageTranscoderFactory {
    @Nullable
    ImageTranscoder createImageTranscoder(ImageFormat imageFormat, boolean isResizingEnabled);
}
