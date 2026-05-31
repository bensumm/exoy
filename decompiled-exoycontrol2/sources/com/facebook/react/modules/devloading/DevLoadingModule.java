package com.facebook.react.modules.devloading;

import com.facebook.fbreact.specs.NativeDevLoadingViewSpec;
import com.facebook.react.bridge.JSExceptionHandler;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.devsupport.BridgeDevSupportManager;
import com.facebook.react.devsupport.DefaultDevLoadingViewImplementation;
import com.facebook.react.devsupport.interfaces.DevLoadingViewManager;
import com.facebook.react.module.annotations.ReactModule;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(name = NativeDevLoadingViewSpec.NAME)
public class DevLoadingModule extends NativeDevLoadingViewSpec {
    private DevLoadingViewManager mDevLoadingViewManager;
    private final JSExceptionHandler mJSExceptionHandler;

    public DevLoadingModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        JSExceptionHandler jSExceptionHandler = reactApplicationContext.getJSExceptionHandler();
        this.mJSExceptionHandler = jSExceptionHandler;
        if (jSExceptionHandler == null || !(jSExceptionHandler instanceof BridgeDevSupportManager)) {
            return;
        }
        DevLoadingViewManager devLoadingViewManager = ((BridgeDevSupportManager) jSExceptionHandler).getDevLoadingViewManager();
        this.mDevLoadingViewManager = devLoadingViewManager;
        this.mDevLoadingViewManager = devLoadingViewManager == null ? new DefaultDevLoadingViewImplementation(((BridgeDevSupportManager) jSExceptionHandler).getReactInstanceManagerHelper()) : devLoadingViewManager;
    }

    @Override // com.facebook.fbreact.specs.NativeDevLoadingViewSpec
    public void showMessage(final String str, Double d, Double d2) {
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.devloading.DevLoadingModule.1
            @Override // java.lang.Runnable
            public void run() {
                DevLoadingModule.this.mDevLoadingViewManager.showMessage(str);
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeDevLoadingViewSpec
    public void hide() {
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.devloading.DevLoadingModule.2
            @Override // java.lang.Runnable
            public void run() {
                if (DevLoadingModule.this.mDevLoadingViewManager != null) {
                    DevLoadingModule.this.mDevLoadingViewManager.hide();
                }
            }
        });
    }
}
