package com.horcrux.svg;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.core.view.ViewCompat;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.ReactConstants;
import com.horcrux.svg.SVGLength;

/* JADX INFO: loaded from: classes.dex */
class Brush {
    private ReadableArray mColors;
    private Matrix mMatrix;
    private PatternView mPattern;
    private final SVGLength[] mPoints;
    private final BrushType mType;
    private boolean mUseContentObjectBoundingBoxUnits;
    private final boolean mUseObjectBoundingBox;
    private Rect mUserSpaceBoundingBox;

    enum BrushType {
        LINEAR_GRADIENT,
        RADIAL_GRADIENT,
        PATTERN
    }

    enum BrushUnits {
        OBJECT_BOUNDING_BOX,
        USER_SPACE_ON_USE
    }

    Brush(BrushType brushType, SVGLength[] sVGLengthArr, BrushUnits brushUnits) {
        this.mType = brushType;
        this.mPoints = sVGLengthArr;
        this.mUseObjectBoundingBox = brushUnits == BrushUnits.OBJECT_BOUNDING_BOX;
    }

    void setContentUnits(BrushUnits brushUnits) {
        this.mUseContentObjectBoundingBoxUnits = brushUnits == BrushUnits.OBJECT_BOUNDING_BOX;
    }

    void setPattern(PatternView patternView) {
        this.mPattern = patternView;
    }

    private static void parseGradientStops(ReadableArray readableArray, int i, float[] fArr, int[] iArr, float f) {
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 2;
            fArr[i2] = (float) readableArray.getDouble(i3);
            iArr[i2] = (readableArray.getInt(i3 + 1) & ViewCompat.MEASURED_SIZE_MASK) | (Math.round((r1 >>> 24) * f) << 24);
        }
    }

    void setUserSpaceBoundingBox(Rect rect) {
        this.mUserSpaceBoundingBox = rect;
    }

    void setGradientColors(ReadableArray readableArray) {
        this.mColors = readableArray;
    }

    void setGradientTransform(Matrix matrix) {
        this.mMatrix = matrix;
    }

    private RectF getPaintRect(RectF rectF) {
        float f;
        float f2;
        if (!this.mUseObjectBoundingBox) {
            rectF = new RectF(this.mUserSpaceBoundingBox);
        }
        float fWidth = rectF.width();
        float fHeight = rectF.height();
        if (this.mUseObjectBoundingBox) {
            f = rectF.left;
            f2 = rectF.top;
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        return new RectF(f, f2, fWidth + f, fHeight + f2);
    }

    private double getVal(SVGLength sVGLength, double d, float f, float f2) {
        return PropHelper.fromRelative(sVGLength, d, 0.0d, (this.mUseObjectBoundingBox && sVGLength.unit == SVGLength.UnitType.NUMBER) ? d : f, f2);
    }

    void setupPaint(Paint paint, RectF rectF, float f, float f2) {
        int[] iArr;
        float[] fArr;
        RectF paintRect = getPaintRect(rectF);
        float fWidth = paintRect.width();
        float fHeight = paintRect.height();
        float f3 = paintRect.left;
        float f4 = paintRect.top;
        float textSize = paint.getTextSize();
        if (this.mType == BrushType.PATTERN) {
            double d = fWidth;
            double val = getVal(this.mPoints[0], d, f, textSize);
            double d2 = fHeight;
            double val2 = getVal(this.mPoints[1], d2, f, textSize);
            double val3 = getVal(this.mPoints[2], d, f, textSize);
            double val4 = getVal(this.mPoints[3], d2, f, textSize);
            if (val3 <= 1.0d || val4 <= 1.0d) {
                return;
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) val3, (int) val4, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            RectF viewBox = this.mPattern.getViewBox();
            if (viewBox != null && viewBox.width() > 0.0f && viewBox.height() > 0.0f) {
                canvas.concat(ViewBox.getTransform(viewBox, new RectF((float) val, (float) val2, (float) val3, (float) val4), this.mPattern.mAlign, this.mPattern.mMeetOrSlice));
            }
            if (this.mUseContentObjectBoundingBoxUnits) {
                canvas.scale(fWidth / f, fHeight / f);
            }
            this.mPattern.draw(canvas, new Paint(), f2);
            Matrix matrix = new Matrix();
            Matrix matrix2 = this.mMatrix;
            if (matrix2 != null) {
                matrix.preConcat(matrix2);
            }
            BitmapShader bitmapShader = new BitmapShader(bitmapCreateBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            bitmapShader.setLocalMatrix(matrix);
            paint.setShader(bitmapShader);
            return;
        }
        int size = this.mColors.size();
        if (size == 0) {
            FLog.w(ReactConstants.TAG, "Gradient contains no stops");
            return;
        }
        int i = size / 2;
        int[] iArr2 = new int[i];
        float[] fArr2 = new float[i];
        parseGradientStops(this.mColors, i, fArr2, iArr2, f2);
        if (i == 1) {
            int[] iArr3 = {iArr2[0], iArr2[0]};
            float[] fArr3 = {fArr2[0], fArr2[0]};
            FLog.w(ReactConstants.TAG, "Gradient contains only one stop");
            iArr = iArr3;
            fArr = fArr3;
        } else {
            iArr = iArr2;
            fArr = fArr2;
        }
        if (this.mType == BrushType.LINEAR_GRADIENT) {
            double d3 = fWidth;
            double val5 = getVal(this.mPoints[0], d3, f, textSize);
            double d4 = f3;
            double d5 = fHeight;
            double d6 = f4;
            Shader linearGradient = new LinearGradient((float) (val5 + d4), (float) (getVal(this.mPoints[1], d5, f, textSize) + d6), (float) (getVal(this.mPoints[2], d3, f, textSize) + d4), (float) (getVal(this.mPoints[3], d5, f, textSize) + d6), iArr, fArr, Shader.TileMode.CLAMP);
            if (this.mMatrix != null) {
                Matrix matrix3 = new Matrix();
                matrix3.preConcat(this.mMatrix);
                linearGradient.setLocalMatrix(matrix3);
            }
            paint.setShader(linearGradient);
            return;
        }
        if (this.mType == BrushType.RADIAL_GRADIENT) {
            double d7 = fWidth;
            double val6 = getVal(this.mPoints[2], d7, f, textSize);
            double d8 = fHeight;
            double val7 = getVal(this.mPoints[3], d8, f, textSize) / val6;
            Shader radialGradient = new RadialGradient((float) (getVal(this.mPoints[4], d7, f, textSize) + ((double) f3)), (float) (getVal(this.mPoints[5], d8 / val7, f, textSize) + (((double) f4) / val7)), (float) val6, iArr, fArr, Shader.TileMode.CLAMP);
            Matrix matrix4 = new Matrix();
            matrix4.preScale(1.0f, (float) val7);
            Matrix matrix5 = this.mMatrix;
            if (matrix5 != null) {
                matrix4.preConcat(matrix5);
            }
            radialGradient.setLocalMatrix(matrix4);
            paint.setShader(radialGradient);
        }
    }
}
