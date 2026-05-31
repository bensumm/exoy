package com.facebook.drawee.drawable;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public class InstrumentedDrawable extends ForwardingDrawable {
    private boolean mIsChecked;
    private final Listener mListener;
    private final String mScaleType;

    public interface Listener {
        void track(int viewWidth, int viewHeight, int imageWidth, int imageHeight, int scaledWidth, int scaledHeight, String scaleType);
    }

    public InstrumentedDrawable(Drawable drawable, Listener listener) {
        super(drawable);
        this.mIsChecked = false;
        this.mListener = listener;
        this.mScaleType = getScaleType(drawable);
    }

    private String getScaleType(Drawable drawable) {
        return drawable instanceof ScaleTypeDrawable ? ((ScaleTypeDrawable) drawable).getScaleType().toString() : "none";
    }

    @Override // com.facebook.drawee.drawable.ForwardingDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (!this.mIsChecked) {
            this.mIsChecked = true;
            RectF rectF = new RectF();
            getRootBounds(rectF);
            int iWidth = (int) rectF.width();
            int iHeight = (int) rectF.height();
            getTransformedBounds(rectF);
            int iWidth2 = (int) rectF.width();
            int iHeight2 = (int) rectF.height();
            int intrinsicWidth = getIntrinsicWidth();
            int intrinsicHeight = getIntrinsicHeight();
            Listener listener = this.mListener;
            if (listener != null) {
                listener.track(iWidth, iHeight, intrinsicWidth, intrinsicHeight, iWidth2, iHeight2, this.mScaleType);
            }
        }
        super.draw(canvas);
    }
}
