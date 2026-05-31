package com.facebook.react.uimanager;

import com.facebook.yoga.YogaConfig;
import com.facebook.yoga.YogaConfigFactory;

/* JADX INFO: loaded from: classes.dex */
public class ReactYogaConfigProvider {
    private static YogaConfig YOGA_CONFIG;

    public static YogaConfig get() {
        if (YOGA_CONFIG == null) {
            YogaConfig yogaConfigCreate = YogaConfigFactory.create();
            YOGA_CONFIG = yogaConfigCreate;
            yogaConfigCreate.setPointScaleFactor(0.0f);
            YOGA_CONFIG.setUseLegacyStretchBehaviour(true);
        }
        return YOGA_CONFIG;
    }
}
