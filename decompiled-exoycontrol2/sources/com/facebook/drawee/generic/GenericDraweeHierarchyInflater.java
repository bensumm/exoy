package com.facebook.drawee.generic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class GenericDraweeHierarchyInflater {
    public static GenericDraweeHierarchy inflateHierarchy(Context context, @Nullable AttributeSet attrs) {
        return inflateBuilder(context, attrs).build();
    }

    public static GenericDraweeHierarchyBuilder inflateBuilder(Context context, @Nullable AttributeSet attrs) throws Throwable {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("GenericDraweeHierarchyBuilder#inflateBuilder");
        }
        GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilderUpdateBuilder = updateBuilder(new GenericDraweeHierarchyBuilder(context.getResources()), context, attrs);
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
        return genericDraweeHierarchyBuilderUpdateBuilder;
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x01e0 A[PHI: r1 r2 r3
  0x01e0: PHI (r1v18 boolean) = (r1v14 boolean), (r1v20 boolean) binds: [B:130:0x01de, B:115:0x01c4] A[DONT_GENERATE, DONT_INLINE]
  0x01e0: PHI (r2v13 boolean) = (r2v10 boolean), (r2v15 boolean) binds: [B:130:0x01de, B:115:0x01c4] A[DONT_GENERATE, DONT_INLINE]
  0x01e0: PHI (r3v9 boolean) = (r3v6 boolean), (r3v11 boolean) binds: [B:130:0x01de, B:115:0x01c4] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.facebook.drawee.generic.GenericDraweeHierarchyBuilder updateBuilder(com.facebook.drawee.generic.GenericDraweeHierarchyBuilder r17, android.content.Context r18, @javax.annotation.Nullable android.util.AttributeSet r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 569
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.drawee.generic.GenericDraweeHierarchyInflater.updateBuilder(com.facebook.drawee.generic.GenericDraweeHierarchyBuilder, android.content.Context, android.util.AttributeSet):com.facebook.drawee.generic.GenericDraweeHierarchyBuilder");
    }

    private static RoundingParams getRoundingParams(GenericDraweeHierarchyBuilder builder) {
        if (builder.getRoundingParams() == null) {
            builder.setRoundingParams(new RoundingParams());
        }
        return builder.getRoundingParams();
    }

    @Nullable
    private static Drawable getDrawable(Context context, TypedArray gdhAttrs, int attrId) {
        int resourceId = gdhAttrs.getResourceId(attrId, 0);
        if (resourceId == 0) {
            return null;
        }
        return context.getResources().getDrawable(resourceId);
    }

    @Nullable
    private static ScalingUtils.ScaleType getScaleTypeFromXml(TypedArray gdhAttrs, int attrId) {
        switch (gdhAttrs.getInt(attrId, -2)) {
            case -1:
                return null;
            case 0:
                return ScalingUtils.ScaleType.FIT_XY;
            case 1:
                return ScalingUtils.ScaleType.FIT_START;
            case 2:
                return ScalingUtils.ScaleType.FIT_CENTER;
            case 3:
                return ScalingUtils.ScaleType.FIT_END;
            case 4:
                return ScalingUtils.ScaleType.CENTER;
            case 5:
                return ScalingUtils.ScaleType.CENTER_INSIDE;
            case 6:
                return ScalingUtils.ScaleType.CENTER_CROP;
            case 7:
                return ScalingUtils.ScaleType.FOCUS_CROP;
            case 8:
                return ScalingUtils.ScaleType.FIT_BOTTOM_START;
            default:
                throw new RuntimeException("XML attribute not specified!");
        }
    }
}
