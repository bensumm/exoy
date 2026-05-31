package com.google.android.material.color;

import android.content.Context;
import android.os.Build;
import androidx.core.os.BuildCompat;
import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public interface ColorResourcesOverride {
    boolean applyIfPossible(Context context, Map<Integer, Integer> map);

    Context wrapContextIfPossible(Context context, Map<Integer, Integer> map);

    /* JADX INFO: renamed from: com.google.android.material.color.ColorResourcesOverride$-CC, reason: invalid class name */
    @SynthesizedClassV2(kind = 8, versionHash = "7a5b85d3ee2e0991ca3502602e9389a98f55c0576b887125894a7ec03823f8d3")
    public final /* synthetic */ class CC {
        public static ColorResourcesOverride getInstance() {
            if (30 <= Build.VERSION.SDK_INT && Build.VERSION.SDK_INT <= 33) {
                return ResourcesLoaderColorResourcesOverride.getInstance();
            }
            if (BuildCompat.isAtLeastU()) {
                return ResourcesLoaderColorResourcesOverride.getInstance();
            }
            return null;
        }
    }
}
