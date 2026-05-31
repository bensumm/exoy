package com.facebook.drawee.view;

import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class AspectRatioMeasure {

    public static class Spec {
        public int height;
        public int width;
    }

    private static boolean shouldAdjust(int layoutDimension) {
        return layoutDimension == 0 || layoutDimension == -2;
    }

    public static void updateMeasureSpec(Spec spec, float aspectRatio, @Nullable ViewGroup.LayoutParams layoutParams, int widthPadding, int heightPadding) {
        if (aspectRatio <= 0.0f || layoutParams == null) {
            return;
        }
        if (shouldAdjust(layoutParams.height)) {
            spec.height = View.MeasureSpec.makeMeasureSpec(View.resolveSize((int) (((View.MeasureSpec.getSize(spec.width) - widthPadding) / aspectRatio) + heightPadding), spec.height), BasicMeasure.EXACTLY);
        } else if (shouldAdjust(layoutParams.width)) {
            spec.width = View.MeasureSpec.makeMeasureSpec(View.resolveSize((int) (((View.MeasureSpec.getSize(spec.height) - heightPadding) * aspectRatio) + widthPadding), spec.width), BasicMeasure.EXACTLY);
        }
    }
}
