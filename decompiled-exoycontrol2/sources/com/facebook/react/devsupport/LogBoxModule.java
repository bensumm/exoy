package com.facebook.react.devsupport;

import com.facebook.fbreact.specs.NativeLogBoxSpec;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.SurfaceDelegate;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.module.annotations.ReactModule;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(name = "LogBox")
public class LogBoxModule extends NativeLogBoxSpec {
    public static final String NAME = "LogBox";
    private final DevSupportManager mDevSupportManager;
    private final SurfaceDelegate mSurfaceDelegate;

    public LogBoxModule(ReactApplicationContext reactApplicationContext, DevSupportManager devSupportManager) {
        super(reactApplicationContext);
        this.mDevSupportManager = devSupportManager;
        SurfaceDelegate surfaceDelegateCreateSurfaceDelegate = devSupportManager.createSurfaceDelegate("LogBox");
        if (surfaceDelegateCreateSurfaceDelegate != null) {
            this.mSurfaceDelegate = surfaceDelegateCreateSurfaceDelegate;
        } else {
            this.mSurfaceDelegate = new LogBoxDialogSurfaceDelegate(devSupportManager);
        }
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.devsupport.LogBoxModule.1
            @Override // java.lang.Runnable
            public void run() {
                LogBoxModule.this.mSurfaceDelegate.createContentView("LogBox");
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeLogBoxSpec
    public void show() {
        if (this.mSurfaceDelegate.isContentViewReady()) {
            UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.devsupport.LogBoxModule.2
                @Override // java.lang.Runnable
                public void run() {
                    LogBoxModule.this.mSurfaceDelegate.show();
                }
            });
        }
    }

    @Override // com.facebook.fbreact.specs.NativeLogBoxSpec
    public void hide() {
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.devsupport.LogBoxModule.3
            @Override // java.lang.Runnable
            public void run() {
                LogBoxModule.this.mSurfaceDelegate.hide();
            }
        });
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule, com.facebook.react.turbomodule.core.interfaces.TurboModule
    public void invalidate() {
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.devsupport.LogBoxModule.4
            @Override // java.lang.Runnable
            public void run() {
                LogBoxModule.this.mSurfaceDelegate.destroyContentView();
            }
        });
    }
}
