package com.swmansion.reanimated.layoutReanimation;

import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public interface NativeMethodsHolder {
    void cancelAnimation(int i);

    void checkDuplicateSharedTag(int i, int i2);

    void clearAnimationConfig(int i);

    int findPrecedingViewTagForTransition(int i);

    boolean hasAnimation(int i, int i2);

    boolean isLayoutAnimationEnabled();

    void startAnimation(int i, int i2, HashMap<String, Object> map);
}
