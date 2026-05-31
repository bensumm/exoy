package com.facebook.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class ScalingUtils {

    public interface ScaleType {
        public static final ScaleType FIT_XY = ScaleTypeFitXY.INSTANCE;
        public static final ScaleType FIT_X = ScaleTypeFitX.INSTANCE;
        public static final ScaleType FIT_Y = ScaleTypeFitY.INSTANCE;
        public static final ScaleType FIT_START = ScaleTypeFitStart.INSTANCE;
        public static final ScaleType FIT_CENTER = ScaleTypeFitCenter.INSTANCE;
        public static final ScaleType FIT_END = ScaleTypeFitEnd.INSTANCE;
        public static final ScaleType CENTER = ScaleTypeCenter.INSTANCE;
        public static final ScaleType CENTER_INSIDE = ScaleTypeCenterInside.INSTANCE;
        public static final ScaleType CENTER_CROP = ScaleTypeCenterCrop.INSTANCE;
        public static final ScaleType FOCUS_CROP = ScaleTypeFocusCrop.INSTANCE;
        public static final ScaleType FIT_BOTTOM_START = ScaleTypeFitBottomStart.INSTANCE;

        Matrix getTransform(Matrix outTransform, Rect parentBounds, int childWidth, int childHeight, float focusX, float focusY);
    }

    public interface StatefulScaleType {
        Object getState();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public static ScaleTypeDrawable getActiveScaleTypeDrawable(@Nullable Drawable drawable) {
        if (drawable == 0) {
            return null;
        }
        if (drawable instanceof ScaleTypeDrawable) {
            return (ScaleTypeDrawable) drawable;
        }
        if (drawable instanceof DrawableParent) {
            return getActiveScaleTypeDrawable(((DrawableParent) drawable).getDrawable());
        }
        if (drawable instanceof ArrayDrawable) {
            ArrayDrawable arrayDrawable = (ArrayDrawable) drawable;
            int numberOfLayers = arrayDrawable.getNumberOfLayers();
            for (int i = 0; i < numberOfLayers; i++) {
                ScaleTypeDrawable activeScaleTypeDrawable = getActiveScaleTypeDrawable(arrayDrawable.getDrawable(i));
                if (activeScaleTypeDrawable != null) {
                    return activeScaleTypeDrawable;
                }
            }
        }
        return null;
    }

    public static abstract class AbstractScaleType implements ScaleType {
        public abstract void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY);

        @Override // com.facebook.drawee.drawable.ScalingUtils.ScaleType
        public Matrix getTransform(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY) {
            getTransformImpl(outTransform, parentRect, childWidth, childHeight, focusX, focusY, parentRect.width() / childWidth, parentRect.height() / childHeight);
            return outTransform;
        }
    }

    private static class ScaleTypeFitXY extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitXY();

        public String toString() {
            return "fit_xy";
        }

        private ScaleTypeFitXY() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float f = parentRect.left;
            float f2 = parentRect.top;
            outTransform.setScale(scaleX, scaleY);
            outTransform.postTranslate((int) (f + 0.5f), (int) (f2 + 0.5f));
        }
    }

    private static class ScaleTypeFitStart extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitStart();

        public String toString() {
            return "fit_start";
        }

        private ScaleTypeFitStart() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float fMin = Math.min(scaleX, scaleY);
            float f = parentRect.left;
            float f2 = parentRect.top;
            outTransform.setScale(fMin, fMin);
            outTransform.postTranslate((int) (f + 0.5f), (int) (f2 + 0.5f));
        }
    }

    private static class ScaleTypeFitBottomStart extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitBottomStart();

        public String toString() {
            return "fit_bottom_start";
        }

        private ScaleTypeFitBottomStart() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float fMin = Math.min(scaleX, scaleY);
            float f = parentRect.left;
            float fHeight = parentRect.top + (parentRect.height() - (childHeight * fMin));
            outTransform.setScale(fMin, fMin);
            outTransform.postTranslate((int) (f + 0.5f), (int) (fHeight + 0.5f));
        }
    }

    private static class ScaleTypeFitCenter extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitCenter();

        public String toString() {
            return "fit_center";
        }

        private ScaleTypeFitCenter() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float fMin = Math.min(scaleX, scaleY);
            float fWidth = parentRect.left + ((parentRect.width() - (childWidth * fMin)) * 0.5f);
            float fHeight = parentRect.top + ((parentRect.height() - (childHeight * fMin)) * 0.5f);
            outTransform.setScale(fMin, fMin);
            outTransform.postTranslate((int) (fWidth + 0.5f), (int) (fHeight + 0.5f));
        }
    }

    private static class ScaleTypeFitEnd extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitEnd();

        public String toString() {
            return "fit_end";
        }

        private ScaleTypeFitEnd() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float fMin = Math.min(scaleX, scaleY);
            float fWidth = parentRect.left + (parentRect.width() - (childWidth * fMin));
            float fHeight = parentRect.top + (parentRect.height() - (childHeight * fMin));
            outTransform.setScale(fMin, fMin);
            outTransform.postTranslate((int) (fWidth + 0.5f), (int) (fHeight + 0.5f));
        }
    }

    private static class ScaleTypeCenter extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenter();

        public String toString() {
            return "center";
        }

        private ScaleTypeCenter() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            outTransform.setTranslate((int) (parentRect.left + ((parentRect.width() - childWidth) * 0.5f) + 0.5f), (int) (parentRect.top + ((parentRect.height() - childHeight) * 0.5f) + 0.5f));
        }
    }

    private static class ScaleTypeCenterInside extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenterInside();

        public String toString() {
            return "center_inside";
        }

        private ScaleTypeCenterInside() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float fMin = Math.min(Math.min(scaleX, scaleY), 1.0f);
            float fWidth = parentRect.left + ((parentRect.width() - (childWidth * fMin)) * 0.5f);
            float fHeight = parentRect.top + ((parentRect.height() - (childHeight * fMin)) * 0.5f);
            outTransform.setScale(fMin, fMin);
            outTransform.postTranslate((int) (fWidth + 0.5f), (int) (fHeight + 0.5f));
        }
    }

    private static class ScaleTypeCenterCrop extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenterCrop();

        public String toString() {
            return "center_crop";
        }

        private ScaleTypeCenterCrop() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float fHeight;
            float fWidth;
            if (scaleY > scaleX) {
                fWidth = parentRect.left + ((parentRect.width() - (childWidth * scaleY)) * 0.5f);
                fHeight = parentRect.top;
                scaleX = scaleY;
            } else {
                float f = parentRect.left;
                fHeight = ((parentRect.height() - (childHeight * scaleX)) * 0.5f) + parentRect.top;
                fWidth = f;
            }
            outTransform.setScale(scaleX, scaleX);
            outTransform.postTranslate((int) (fWidth + 0.5f), (int) (fHeight + 0.5f));
        }
    }

    private static class ScaleTypeFocusCrop extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFocusCrop();

        public String toString() {
            return "focus_crop";
        }

        private ScaleTypeFocusCrop() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float fMax;
            float fMax2;
            if (scaleY > scaleX) {
                float f = childWidth * scaleY;
                fMax = parentRect.left + Math.max(Math.min((parentRect.width() * 0.5f) - (focusX * f), 0.0f), parentRect.width() - f);
                fMax2 = parentRect.top;
                scaleX = scaleY;
            } else {
                fMax = parentRect.left;
                float f2 = childHeight * scaleX;
                fMax2 = Math.max(Math.min((parentRect.height() * 0.5f) - (focusY * f2), 0.0f), parentRect.height() - f2) + parentRect.top;
            }
            outTransform.setScale(scaleX, scaleX);
            outTransform.postTranslate((int) (fMax + 0.5f), (int) (fMax2 + 0.5f));
        }
    }

    private static class ScaleTypeFitX extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitX();

        public String toString() {
            return "fit_x";
        }

        private ScaleTypeFitX() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float f = parentRect.left;
            float fHeight = parentRect.top + ((parentRect.height() - (childHeight * scaleX)) * 0.5f);
            outTransform.setScale(scaleX, scaleX);
            outTransform.postTranslate((int) (f + 0.5f), (int) (fHeight + 0.5f));
        }
    }

    private static class ScaleTypeFitY extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitY();

        public String toString() {
            return "fit_y";
        }

        private ScaleTypeFitY() {
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.AbstractScaleType
        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float fWidth = parentRect.left + ((parentRect.width() - (childWidth * scaleY)) * 0.5f);
            float f = parentRect.top;
            outTransform.setScale(scaleY, scaleY);
            outTransform.postTranslate((int) (fWidth + 0.5f), (int) (f + 0.5f));
        }
    }

    public static class InterpolatingScaleType implements ScaleType, StatefulScaleType {

        @Nullable
        private final Rect mBoundsFrom;

        @Nullable
        private final Rect mBoundsTo;

        @Nullable
        private final PointF mFocusPointFrom;

        @Nullable
        private final PointF mFocusPointTo;
        private float mInterpolatingValue;
        private final float[] mMatrixValuesFrom;
        private final float[] mMatrixValuesInterpolated;
        private final float[] mMatrixValuesTo;
        private final ScaleType mScaleTypeFrom;
        private final ScaleType mScaleTypeTo;

        public InterpolatingScaleType(ScaleType scaleTypeFrom, ScaleType scaleTypeTo, @Nullable Rect boundsFrom, @Nullable Rect boundsTo, @Nullable PointF focusPointFrom, @Nullable PointF focusPointTo) {
            this.mMatrixValuesFrom = new float[9];
            this.mMatrixValuesTo = new float[9];
            this.mMatrixValuesInterpolated = new float[9];
            this.mScaleTypeFrom = scaleTypeFrom;
            this.mScaleTypeTo = scaleTypeTo;
            this.mBoundsFrom = boundsFrom;
            this.mBoundsTo = boundsTo;
            this.mFocusPointFrom = focusPointFrom;
            this.mFocusPointTo = focusPointTo;
        }

        public InterpolatingScaleType(ScaleType scaleTypeFrom, ScaleType scaleTypeTo, @Nullable Rect boundsFrom, @Nullable Rect boundsTo) {
            this(scaleTypeFrom, scaleTypeTo, boundsFrom, boundsTo, null, null);
        }

        public InterpolatingScaleType(ScaleType scaleTypeFrom, ScaleType scaleTypeTo) {
            this(scaleTypeFrom, scaleTypeTo, null, null);
        }

        public ScaleType getScaleTypeFrom() {
            return this.mScaleTypeFrom;
        }

        public ScaleType getScaleTypeTo() {
            return this.mScaleTypeTo;
        }

        @Nullable
        public Rect getBoundsFrom() {
            return this.mBoundsFrom;
        }

        @Nullable
        public Rect getBoundsTo() {
            return this.mBoundsTo;
        }

        @Nullable
        public PointF getFocusPointFrom() {
            return this.mFocusPointFrom;
        }

        @Nullable
        public PointF getFocusPointTo() {
            return this.mFocusPointTo;
        }

        public void setValue(float value) {
            this.mInterpolatingValue = value;
        }

        public float getValue() {
            return this.mInterpolatingValue;
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.StatefulScaleType
        public Object getState() {
            return Float.valueOf(this.mInterpolatingValue);
        }

        @Override // com.facebook.drawee.drawable.ScalingUtils.ScaleType
        public Matrix getTransform(Matrix transform, Rect parentBounds, int childWidth, int childHeight, float focusX, float focusY) {
            Rect rect = this.mBoundsFrom;
            Rect rect2 = rect != null ? rect : parentBounds;
            Rect rect3 = this.mBoundsTo;
            Rect rect4 = rect3 != null ? rect3 : parentBounds;
            ScaleType scaleType = this.mScaleTypeFrom;
            PointF pointF = this.mFocusPointFrom;
            float f = pointF == null ? focusX : pointF.x;
            PointF pointF2 = this.mFocusPointFrom;
            scaleType.getTransform(transform, rect2, childWidth, childHeight, f, pointF2 == null ? focusY : pointF2.y);
            transform.getValues(this.mMatrixValuesFrom);
            ScaleType scaleType2 = this.mScaleTypeTo;
            PointF pointF3 = this.mFocusPointTo;
            float f2 = pointF3 == null ? focusX : pointF3.x;
            PointF pointF4 = this.mFocusPointTo;
            scaleType2.getTransform(transform, rect4, childWidth, childHeight, f2, pointF4 == null ? focusY : pointF4.y);
            transform.getValues(this.mMatrixValuesTo);
            for (int i = 0; i < 9; i++) {
                float[] fArr = this.mMatrixValuesInterpolated;
                float f3 = this.mMatrixValuesFrom[i];
                float f4 = this.mInterpolatingValue;
                fArr[i] = (f3 * (1.0f - f4)) + (this.mMatrixValuesTo[i] * f4);
            }
            transform.setValues(this.mMatrixValuesInterpolated);
            return transform;
        }

        public String toString() {
            return String.format("InterpolatingScaleType(%s (%s) -> %s (%s))", String.valueOf(this.mScaleTypeFrom), String.valueOf(this.mFocusPointFrom), String.valueOf(this.mScaleTypeTo), String.valueOf(this.mFocusPointTo));
        }
    }
}
