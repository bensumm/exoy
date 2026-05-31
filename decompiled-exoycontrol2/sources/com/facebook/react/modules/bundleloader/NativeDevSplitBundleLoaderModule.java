package com.facebook.react.modules.bundleloader;

import com.facebook.fbreact.specs.NativeDevSplitBundleLoaderSpec;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.DebugServerException;
import com.facebook.react.devsupport.interfaces.DevSplitBundleCallback;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.module.annotations.ReactModule;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(name = NativeDevSplitBundleLoaderSpec.NAME)
public class NativeDevSplitBundleLoaderModule extends NativeDevSplitBundleLoaderSpec {
    private static final String REJECTION_CODE = "E_BUNDLE_LOAD_ERROR";
    private final DevSupportManager mDevSupportManager;

    public NativeDevSplitBundleLoaderModule(ReactApplicationContext reactApplicationContext, DevSupportManager devSupportManager) {
        super(reactApplicationContext);
        this.mDevSupportManager = devSupportManager;
    }

    @Override // com.facebook.fbreact.specs.NativeDevSplitBundleLoaderSpec
    public void loadBundle(String str, final Promise promise) {
        this.mDevSupportManager.loadSplitBundleFromServer(str, new DevSplitBundleCallback() { // from class: com.facebook.react.modules.bundleloader.NativeDevSplitBundleLoaderModule.1
            @Override // com.facebook.react.devsupport.interfaces.DevSplitBundleCallback
            public void onSuccess() {
                promise.resolve(true);
            }

            @Override // com.facebook.react.devsupport.interfaces.DevSplitBundleCallback
            public void onError(String str2, Throwable th) {
                String originalMessage;
                if (th instanceof DebugServerException) {
                    originalMessage = ((DebugServerException) th).getOriginalMessage();
                } else {
                    originalMessage = "Unknown error fetching '" + str2 + "'.";
                }
                promise.reject(NativeDevSplitBundleLoaderModule.REJECTION_CODE, originalMessage, th);
            }
        });
    }
}
