package com.facebook.react.modules.share;

import android.app.Activity;
import android.content.Intent;
import androidx.webkit.internal.AssetHelper;
import com.facebook.fbreact.specs.NativeShareModuleSpec;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(name = NativeShareModuleSpec.NAME)
public class ShareModule extends NativeShareModuleSpec {
    static final String ACTION_SHARED = "sharedAction";
    static final String ERROR_INVALID_CONTENT = "E_INVALID_CONTENT";
    static final String ERROR_UNABLE_TO_OPEN_DIALOG = "E_UNABLE_TO_OPEN_DIALOG";

    public ShareModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @Override // com.facebook.fbreact.specs.NativeShareModuleSpec
    public void share(ReadableMap readableMap, String str, Promise promise) {
        if (readableMap == null) {
            promise.reject(ERROR_INVALID_CONTENT, "Content cannot be null");
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setTypeAndNormalize(AssetHelper.DEFAULT_MIME_TYPE);
            if (readableMap.hasKey("title")) {
                intent.putExtra("android.intent.extra.SUBJECT", readableMap.getString("title"));
            }
            if (readableMap.hasKey("message")) {
                intent.putExtra("android.intent.extra.TEXT", readableMap.getString("message"));
            }
            Intent intentCreateChooser = Intent.createChooser(intent, str);
            intentCreateChooser.addCategory("android.intent.category.DEFAULT");
            Activity currentActivity = getCurrentActivity();
            if (currentActivity != null) {
                currentActivity.startActivity(intentCreateChooser);
            } else {
                getReactApplicationContext().startActivity(intentCreateChooser);
            }
            WritableMap writableMapCreateMap = Arguments.createMap();
            writableMapCreateMap.putString("action", ACTION_SHARED);
            promise.resolve(writableMapCreateMap);
        } catch (Exception unused) {
            promise.reject(ERROR_UNABLE_TO_OPEN_DIALOG, "Failed to open share dialog");
        }
    }
}
