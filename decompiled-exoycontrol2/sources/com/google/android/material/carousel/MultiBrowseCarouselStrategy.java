package com.google.android.material.carousel;

import android.content.Context;
import android.view.View;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.carousel.KeylineState;

/* JADX INFO: loaded from: classes.dex */
public final class MultiBrowseCarouselStrategy extends CarouselStrategy {
    private static final float MEDIUM_ITEM_FLEX_PERCENTAGE = 0.1f;
    private final boolean forceCompactArrangement;
    private static final int[] SMALL_COUNTS = {1};
    private static final int[] MEDIUM_COUNTS = {1, 0};
    private static final int[] MEDIUM_COUNTS_COMPACT = {0};

    public MultiBrowseCarouselStrategy() {
        this(false);
    }

    public MultiBrowseCarouselStrategy(boolean z) {
        this.forceCompactArrangement = z;
    }

    private float getExtraSmallSize(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_gone_size);
    }

    private float getSmallSizeMin(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_min);
    }

    private float getSmallSizeMax(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_max);
    }

    @Override // com.google.android.material.carousel.CarouselStrategy
    KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View view) {
        float containerWidth = carousel.getContainerWidth();
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        float f = layoutParams.leftMargin + layoutParams.rightMargin;
        float smallSizeMin = getSmallSizeMin(view.getContext()) + f;
        float smallSizeMax = getSmallSizeMax(view.getContext()) + f;
        float measuredWidth = view.getMeasuredWidth();
        float fMin = Math.min(measuredWidth + f, containerWidth);
        float fClamp = MathUtils.clamp((measuredWidth / 3.0f) + f, getSmallSizeMin(view.getContext()) + f, getSmallSizeMax(view.getContext()) + f);
        float f2 = (fMin + fClamp) / 2.0f;
        int[] iArr = SMALL_COUNTS;
        int[] iArr2 = this.forceCompactArrangement ? MEDIUM_COUNTS_COMPACT : MEDIUM_COUNTS;
        int iMax = (int) Math.max(1.0d, Math.floor(((containerWidth - (maxValue(iArr2) * f2)) - (maxValue(iArr) * smallSizeMax)) / fMin));
        int iCeil = (int) Math.ceil(containerWidth / fMin);
        int i = (iCeil - iMax) + 1;
        int[] iArr3 = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr3[i2] = iCeil - i2;
        }
        Arrangement arrangementFindLowestCostArrangement = findLowestCostArrangement(containerWidth, fClamp, smallSizeMin, smallSizeMax, iArr, f2, iArr2, fMin, iArr3);
        float extraSmallSize = getExtraSmallSize(view.getContext()) + f;
        float f3 = extraSmallSize / 2.0f;
        float f4 = 0.0f - f3;
        float f5 = (arrangementFindLowestCostArrangement.largeSize / 2.0f) + 0.0f;
        float fMax = f5 + (Math.max(0, arrangementFindLowestCostArrangement.largeCount - 1) * arrangementFindLowestCostArrangement.largeSize);
        float f6 = (arrangementFindLowestCostArrangement.largeSize / 2.0f) + fMax;
        if (arrangementFindLowestCostArrangement.mediumCount > 0) {
            fMax = (arrangementFindLowestCostArrangement.mediumSize / 2.0f) + f6;
        }
        if (arrangementFindLowestCostArrangement.mediumCount > 0) {
            f6 = (arrangementFindLowestCostArrangement.mediumSize / 2.0f) + fMax;
        }
        float f7 = arrangementFindLowestCostArrangement.smallCount > 0 ? f6 + (arrangementFindLowestCostArrangement.smallSize / 2.0f) : fMax;
        float containerWidth2 = carousel.getContainerWidth() + f3;
        float childMaskPercentage = getChildMaskPercentage(extraSmallSize, arrangementFindLowestCostArrangement.largeSize, f);
        float childMaskPercentage2 = getChildMaskPercentage(arrangementFindLowestCostArrangement.smallSize, arrangementFindLowestCostArrangement.largeSize, f);
        float childMaskPercentage3 = getChildMaskPercentage(arrangementFindLowestCostArrangement.mediumSize, arrangementFindLowestCostArrangement.largeSize, f);
        KeylineState.Builder builderAddKeylineRange = new KeylineState.Builder(arrangementFindLowestCostArrangement.largeSize).addKeyline(f4, childMaskPercentage, extraSmallSize).addKeylineRange(f5, 0.0f, arrangementFindLowestCostArrangement.largeSize, arrangementFindLowestCostArrangement.largeCount, true);
        if (arrangementFindLowestCostArrangement.mediumCount > 0) {
            builderAddKeylineRange.addKeyline(fMax, childMaskPercentage3, arrangementFindLowestCostArrangement.mediumSize);
        }
        if (arrangementFindLowestCostArrangement.smallCount > 0) {
            builderAddKeylineRange.addKeylineRange(f7, childMaskPercentage2, arrangementFindLowestCostArrangement.smallSize, arrangementFindLowestCostArrangement.smallCount);
        }
        builderAddKeylineRange.addKeyline(containerWidth2, childMaskPercentage, extraSmallSize);
        return builderAddKeylineRange.build();
    }

    private static Arrangement findLowestCostArrangement(float f, float f2, float f3, float f4, int[] iArr, float f5, int[] iArr2, float f6, int[] iArr3) {
        Arrangement arrangement = null;
        int i = 1;
        for (int i2 : iArr3) {
            int length = iArr2.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = iArr2[i3];
                int length2 = iArr.length;
                int i5 = 0;
                while (i5 < length2) {
                    int i6 = i5;
                    int i7 = length2;
                    int i8 = i3;
                    int i9 = length;
                    Arrangement arrangement2 = new Arrangement(i, f2, f3, f4, iArr[i5], f5, i4, f6, i2, f);
                    if (arrangement == null || arrangement2.cost < arrangement.cost) {
                        if (arrangement2.cost == 0.0f) {
                            return arrangement2;
                        }
                        arrangement = arrangement2;
                    }
                    i++;
                    i5 = i6 + 1;
                    length2 = i7;
                    i3 = i8;
                    length = i9;
                }
                i3++;
            }
        }
        return arrangement;
    }

    private static int maxValue(int[] iArr) {
        int i = Integer.MIN_VALUE;
        for (int i2 : iArr) {
            if (i2 > i) {
                i = i2;
            }
        }
        return i;
    }

    static final class Arrangement {
        final float cost;
        final int largeCount;
        float largeSize;
        final int mediumCount;
        float mediumSize;
        final int priority;
        final int smallCount;
        float smallSize;

        private float calculateLargeSize(float f, int i, float f2, int i2, int i3) {
            if (i <= 0) {
                f2 = 0.0f;
            }
            float f3 = i2 / 2.0f;
            return (f - ((i + f3) * f2)) / (i3 + f3);
        }

        Arrangement(int i, float f, float f2, float f3, int i2, float f4, int i3, float f5, int i4, float f6) {
            this.priority = i;
            this.smallSize = MathUtils.clamp(f, f2, f3);
            this.smallCount = i2;
            this.mediumSize = f4;
            this.mediumCount = i3;
            this.largeSize = f5;
            this.largeCount = i4;
            fit(f6, f2, f3, f5);
            this.cost = cost(f5);
        }

        public String toString() {
            return "Arrangement [priority=" + this.priority + ", smallCount=" + this.smallCount + ", smallSize=" + this.smallSize + ", mediumCount=" + this.mediumCount + ", mediumSize=" + this.mediumSize + ", largeCount=" + this.largeCount + ", largeSize=" + this.largeSize + ", cost=" + this.cost + "]";
        }

        private float getSpace() {
            return (this.largeSize * this.largeCount) + (this.mediumSize * this.mediumCount) + (this.smallSize * this.smallCount);
        }

        private void fit(float f, float f2, float f3, float f4) {
            float space = f - getSpace();
            int i = this.smallCount;
            if (i > 0 && space > 0.0f) {
                float f5 = this.smallSize;
                this.smallSize = f5 + Math.min(space / i, f3 - f5);
            } else if (i > 0 && space < 0.0f) {
                float f6 = this.smallSize;
                this.smallSize = f6 + Math.max(space / i, f2 - f6);
            }
            float fCalculateLargeSize = calculateLargeSize(f, this.smallCount, this.smallSize, this.mediumCount, this.largeCount);
            this.largeSize = fCalculateLargeSize;
            float f7 = (this.smallSize + fCalculateLargeSize) / 2.0f;
            this.mediumSize = f7;
            int i2 = this.mediumCount;
            if (i2 <= 0 || fCalculateLargeSize == f4) {
                return;
            }
            float f8 = (f4 - fCalculateLargeSize) * this.largeCount;
            float fMin = Math.min(Math.abs(f8), f7 * 0.1f * i2);
            if (f8 > 0.0f) {
                this.mediumSize -= fMin / this.mediumCount;
                this.largeSize += fMin / this.largeCount;
            } else {
                this.mediumSize += fMin / this.mediumCount;
                this.largeSize -= fMin / this.largeCount;
            }
        }

        private boolean isValid() {
            int i = this.largeCount;
            if (i <= 0 || this.smallCount <= 0 || this.mediumCount <= 0) {
                return i <= 0 || this.smallCount <= 0 || this.largeSize > this.smallSize;
            }
            float f = this.largeSize;
            float f2 = this.mediumSize;
            return f > f2 && f2 > this.smallSize;
        }

        private float cost(float f) {
            if (isValid()) {
                return Math.abs(f - this.largeSize) * this.priority;
            }
            return Float.MAX_VALUE;
        }
    }
}
