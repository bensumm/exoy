package com.facebook.imagepipeline.transformation;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public final class TransformationUtils {
    public static boolean maybeApplyTransformation(@Nullable BitmapTransformation transformation, @Nullable CloseableReference<Bitmap> bitmapReference) {
        if (transformation == null || bitmapReference == null) {
            return false;
        }
        Bitmap bitmap = bitmapReference.get();
        if (transformation.modifiesTransparency()) {
            bitmap.setHasAlpha(true);
        }
        transformation.transform(bitmap);
        return true;
    }
}
