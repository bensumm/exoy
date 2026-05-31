package com.facebook.react.modules.toast;

import android.widget.Toast;
import com.facebook.fbreact.specs.NativeToastAndroidSpec;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(name = NativeToastAndroidSpec.NAME)
public class ToastModule extends NativeToastAndroidSpec {
    private static final String DURATION_LONG_KEY = "LONG";
    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String GRAVITY_BOTTOM_KEY = "BOTTOM";
    private static final String GRAVITY_CENTER = "CENTER";
    private static final String GRAVITY_TOP_KEY = "TOP";

    public ToastModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @Override // com.facebook.fbreact.specs.NativeToastAndroidSpec
    public Map<String, Object> getTypedExportedConstants() {
        HashMap mapNewHashMap = MapBuilder.newHashMap();
        mapNewHashMap.put(DURATION_SHORT_KEY, 0);
        mapNewHashMap.put(DURATION_LONG_KEY, 1);
        mapNewHashMap.put(GRAVITY_TOP_KEY, 49);
        mapNewHashMap.put(GRAVITY_BOTTOM_KEY, 81);
        mapNewHashMap.put(GRAVITY_CENTER, 17);
        return mapNewHashMap;
    }

    @Override // com.facebook.fbreact.specs.NativeToastAndroidSpec
    public void show(final String str, double d) {
        final int i = (int) d;
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.toast.ToastModule.1
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(ToastModule.this.getReactApplicationContext(), str, i).show();
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeToastAndroidSpec
    public void showWithGravity(final String str, double d, double d2) {
        final int i = (int) d;
        final int i2 = (int) d2;
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.toast.ToastModule.2
            @Override // java.lang.Runnable
            public void run() {
                Toast toastMakeText = Toast.makeText(ToastModule.this.getReactApplicationContext(), str, i);
                toastMakeText.setGravity(i2, 0, 0);
                toastMakeText.show();
            }
        });
    }

    @Override // com.facebook.fbreact.specs.NativeToastAndroidSpec
    public void showWithGravityAndOffset(final String str, double d, double d2, double d3, double d4) {
        final int i = (int) d;
        final int i2 = (int) d2;
        final int i3 = (int) d3;
        final int i4 = (int) d4;
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.toast.ToastModule.3
            @Override // java.lang.Runnable
            public void run() {
                Toast toastMakeText = Toast.makeText(ToastModule.this.getReactApplicationContext(), str, i);
                toastMakeText.setGravity(i2, i3, i4);
                toastMakeText.show();
            }
        });
    }
}
