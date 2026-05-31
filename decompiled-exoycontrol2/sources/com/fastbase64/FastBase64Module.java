package com.fastbase64;

import android.util.Log;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(name = FastBase64Module.NAME)
public class FastBase64Module extends ReactContextBaseJavaModule {
    public static final String NAME = "FastBase64";

    private native void nativeInstall(long j);

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    public FastBase64Module(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    static {
        try {
            System.loadLibrary("fastbase64");
        } catch (Exception unused) {
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public void install() {
        try {
            nativeInstall(getReactApplicationContext().getJavaScriptContextHolder().get());
        } catch (Exception unused) {
            Log.e("FastBase64Module", "JSI Runtime is not available in debug mode");
        }
    }
}
