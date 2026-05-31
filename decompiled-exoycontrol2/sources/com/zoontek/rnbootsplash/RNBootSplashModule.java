package com.zoontek.rnbootsplash;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
@ReactModule(name = RNBootSplashModuleImpl.NAME)
public class RNBootSplashModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return RNBootSplashModuleImpl.NAME;
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
    }

    public RNBootSplashModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContext.addLifecycleEventListener(this);
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
        RNBootSplashModuleImpl.onHostDestroy();
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public Map<String, Object> getConstants() {
        return RNBootSplashModuleImpl.getConstants(getReactApplicationContext());
    }

    @ReactMethod
    public void hide(boolean z, Promise promise) {
        RNBootSplashModuleImpl.hide(getReactApplicationContext(), z, promise);
    }

    @ReactMethod
    public void isVisible(Promise promise) {
        RNBootSplashModuleImpl.isVisible(promise);
    }
}
