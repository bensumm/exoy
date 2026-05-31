package com.facebook.react.views.image;

import android.graphics.Shader;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.react.common.ReactConstants;

/* JADX INFO: loaded from: classes.dex */
public class ImageResizeMode {
    private static final String RESIZE_MODE_CENTER = "center";
    private static final String RESIZE_MODE_CONTAIN = "contain";
    private static final String RESIZE_MODE_COVER = "cover";
    private static final String RESIZE_MODE_REPEAT = "repeat";
    private static final String RESIZE_MODE_STRETCH = "stretch";

    public static ScalingUtils.ScaleType toScaleType(String str) {
        if (RESIZE_MODE_CONTAIN.equals(str)) {
            return ScalingUtils.ScaleType.FIT_CENTER;
        }
        if (RESIZE_MODE_COVER.equals(str)) {
            return ScalingUtils.ScaleType.CENTER_CROP;
        }
        if (RESIZE_MODE_STRETCH.equals(str)) {
            return ScalingUtils.ScaleType.FIT_XY;
        }
        if (RESIZE_MODE_CENTER.equals(str)) {
            return ScalingUtils.ScaleType.CENTER_INSIDE;
        }
        if (RESIZE_MODE_REPEAT.equals(str)) {
            return ScaleTypeStartInside.INSTANCE;
        }
        if (str != null) {
            FLog.w(ReactConstants.TAG, "Invalid resize mode: '" + str + "'");
        }
        return defaultValue();
    }

    public static Shader.TileMode toTileMode(String str) {
        if (RESIZE_MODE_CONTAIN.equals(str) || RESIZE_MODE_COVER.equals(str) || RESIZE_MODE_STRETCH.equals(str) || RESIZE_MODE_CENTER.equals(str)) {
            return Shader.TileMode.CLAMP;
        }
        if (RESIZE_MODE_REPEAT.equals(str)) {
            return Shader.TileMode.REPEAT;
        }
        if (str != null) {
            FLog.w(ReactConstants.TAG, "Invalid resize mode: '" + str + "'");
        }
        return defaultTileMode();
    }

    public static ScalingUtils.ScaleType defaultValue() {
        return ScalingUtils.ScaleType.CENTER_CROP;
    }

    public static Shader.TileMode defaultTileMode() {
        return Shader.TileMode.CLAMP;
    }
}
