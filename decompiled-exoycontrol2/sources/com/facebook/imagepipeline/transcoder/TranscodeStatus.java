package com.facebook.imagepipeline.transcoder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
@Retention(RetentionPolicy.SOURCE)
public @interface TranscodeStatus {
    public static final int TRANSCODING_ERROR = 2;
    public static final int TRANSCODING_NO_RESIZING = 1;
    public static final int TRANSCODING_SUCCESS = 0;
}
