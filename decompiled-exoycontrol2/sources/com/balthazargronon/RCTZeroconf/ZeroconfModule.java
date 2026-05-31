package com.balthazargronon.RCTZeroconf;

import android.util.Log;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class ZeroconfModule extends ReactContextBaseJavaModule {
    public static final String EVENT_ERROR = "RNZeroconfError";
    public static final String EVENT_FOUND = "RNZeroconfFound";
    public static final String EVENT_PUBLISHED = "RNZeroconfServiceRegistered";
    public static final String EVENT_REMOVE = "RNZeroconfRemove";
    public static final String EVENT_RESOLVE = "RNZeroconfResolved";
    public static final String EVENT_START = "RNZeroconfStart";
    public static final String EVENT_STOP = "RNZeroconfStop";
    public static final String EVENT_UNREGISTERED = "RNZeroconfServiceUnregistered";
    public static final String KEY_SERVICE_ADDRESSES = "addresses";
    public static final String KEY_SERVICE_FULL_NAME = "fullName";
    public static final String KEY_SERVICE_HOST = "host";
    public static final String KEY_SERVICE_NAME = "name";
    public static final String KEY_SERVICE_PORT = "port";
    public static final String KEY_SERVICE_TXT = "txt";
    private ZeroConfImplFactory zeroConfFactory;

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "RNZeroconf";
    }

    public ZeroconfModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.zeroConfFactory = new ZeroConfImplFactory(this, getReactApplicationContext());
    }

    @ReactMethod
    public void scan(String str, String str2, String str3, String str4) {
        try {
            getZeroconfImpl(str4).scan(str, str2, str3);
        } catch (Throwable th) {
            Log.e(getClass().getName(), th.getMessage(), th);
            sendEvent(getReactApplicationContext(), EVENT_ERROR, "Exception During Scan: " + th.getMessage());
        }
    }

    @ReactMethod
    public void stop(String str) {
        try {
            getZeroconfImpl(str).stop();
        } catch (Throwable th) {
            Log.e(getClass().getName(), th.getMessage(), th);
            sendEvent(getReactApplicationContext(), EVENT_ERROR, "Exception During Stop: " + th.getMessage());
        }
    }

    private Zeroconf getZeroconfImpl(String str) {
        return this.zeroConfFactory.getZeroconf(str);
    }

    @ReactMethod
    public void registerService(String str, String str2, String str3, String str4, int i, ReadableMap readableMap, String str5) {
        try {
            getZeroconfImpl(str5).registerService(str, str2, str3, str4, i, readableMap);
        } catch (Throwable th) {
            Log.e(getClass().getName(), th.getMessage(), th);
            sendEvent(getReactApplicationContext(), EVENT_ERROR, "Exception During Register Service: " + th.getMessage());
        }
    }

    @ReactMethod
    public void unregisterService(String str, String str2) {
        try {
            getZeroconfImpl(str2).unregisterService(str);
        } catch (Throwable th) {
            Log.e(getClass().getName(), th.getMessage(), th);
            sendEvent(getReactApplicationContext(), EVENT_ERROR, "Exception During Unregister Service: " + th.getMessage());
        }
    }

    public void sendEvent(ReactContext reactContext, String str, @Nullable Object obj) {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, obj);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        try {
            stop(ZeroConfImplFactory.NSD_IMPL);
            stop(ZeroConfImplFactory.DNSSD_IMPL);
        } catch (Throwable th) {
            Log.e(getClass().getName(), th.getMessage(), th);
            sendEvent(getReactApplicationContext(), EVENT_ERROR, "Exception During Catalyst Destroy: " + th.getMessage());
        }
    }
}
