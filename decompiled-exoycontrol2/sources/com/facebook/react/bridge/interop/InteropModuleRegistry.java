package com.facebook.react.bridge.interop;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.config.ReactFeatureFlags;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class InteropModuleRegistry {
    private final HashMap<Class, Object> supportedModules = new HashMap<>();

    public <T extends JavaScriptModule> boolean shouldReturnInteropModule(Class<T> cls) {
        return checkReactFeatureFlagsConditions() && this.supportedModules.containsKey(cls);
    }

    public <T extends JavaScriptModule> T getInteropModule(Class<T> cls) {
        if (checkReactFeatureFlagsConditions()) {
            return (T) this.supportedModules.get(cls);
        }
        return null;
    }

    public <T extends JavaScriptModule> void registerInteropModule(Class<T> cls, Object obj) {
        if (checkReactFeatureFlagsConditions()) {
            this.supportedModules.put(cls, obj);
        }
    }

    private boolean checkReactFeatureFlagsConditions() {
        return ReactFeatureFlags.enableFabricRenderer && ReactFeatureFlags.unstable_useFabricInterop;
    }
}
