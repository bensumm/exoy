package com.facebook.cache.disk;

import com.facebook.cache.disk.DiskStorage;

/* JADX INFO: loaded from: classes.dex */
public class ScoreBasedEvictionComparatorSupplier implements EntryEvictionComparatorSupplier {
    private final float mAgeWeight;
    private final float mSizeWeight;

    public ScoreBasedEvictionComparatorSupplier(float ageWeight, float sizeWeight) {
        this.mAgeWeight = ageWeight;
        this.mSizeWeight = sizeWeight;
    }

    @Override // com.facebook.cache.disk.EntryEvictionComparatorSupplier
    public EntryEvictionComparator get() {
        return new EntryEvictionComparator() { // from class: com.facebook.cache.disk.ScoreBasedEvictionComparatorSupplier.1
            long now = System.currentTimeMillis();

            @Override // java.util.Comparator
            public int compare(DiskStorage.Entry lhs, DiskStorage.Entry rhs) {
                float fCalculateScore = ScoreBasedEvictionComparatorSupplier.this.calculateScore(lhs, this.now);
                float fCalculateScore2 = ScoreBasedEvictionComparatorSupplier.this.calculateScore(rhs, this.now);
                if (fCalculateScore < fCalculateScore2) {
                    return 1;
                }
                return fCalculateScore2 == fCalculateScore ? 0 : -1;
            }
        };
    }

    float calculateScore(DiskStorage.Entry entry, long now) {
        return (this.mAgeWeight * (now - entry.getTimestamp())) + (this.mSizeWeight * entry.getSize());
    }
}
