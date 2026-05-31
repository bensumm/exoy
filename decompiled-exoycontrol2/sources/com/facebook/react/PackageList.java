package com.facebook.react;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import com.BV.LinearGradient.LinearGradientPackage;
import com.apsl.versionnumber.RNVersionNumberPackage;
import com.balthazargronon.RCTZeroconf.ZeroconfReactPackage;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.shell.MainReactPackage;
import com.fastbase64.FastBase64Package;
import com.horcrux.svg.SvgPackage;
import com.oblador.vectoricons.VectorIconsPackage;
import com.reactcommunity.rndatetimepicker.RNDateTimePickerPackage;
import com.reactlibrary.rnwifi.RNWifiPackage;
import com.reactnativecommunity.asyncstorage.AsyncStoragePackage;
import com.reactnativecommunity.webview.RNCWebViewPackage;
import com.swmansion.gesturehandler.RNGestureHandlerPackage;
import com.swmansion.reanimated.ReanimatedPackage;
import com.swmansion.rnscreens.RNScreensPackage;
import com.th3rdwave.safeareacontext.SafeAreaContextPackage;
import com.tradle.react.UdpSocketsModule;
import com.zoontek.rnbootsplash.RNBootSplashPackage;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class PackageList {
    private Application application;
    private MainPackageConfig mConfig;
    private ReactNativeHost reactNativeHost;

    public PackageList(ReactNativeHost reactNativeHost) {
        this(reactNativeHost, (MainPackageConfig) null);
    }

    public PackageList(Application application) {
        this(application, (MainPackageConfig) null);
    }

    public PackageList(ReactNativeHost reactNativeHost, MainPackageConfig mainPackageConfig) {
        this.reactNativeHost = reactNativeHost;
        this.mConfig = mainPackageConfig;
    }

    public PackageList(Application application, MainPackageConfig mainPackageConfig) {
        this.reactNativeHost = null;
        this.application = application;
        this.mConfig = mainPackageConfig;
    }

    private ReactNativeHost getReactNativeHost() {
        return this.reactNativeHost;
    }

    private Resources getResources() {
        return getApplication().getResources();
    }

    private Application getApplication() {
        ReactNativeHost reactNativeHost = this.reactNativeHost;
        return reactNativeHost == null ? this.application : reactNativeHost.getApplication();
    }

    private Context getApplicationContext() {
        return getApplication().getApplicationContext();
    }

    public ArrayList<ReactPackage> getPackages() {
        return new ArrayList<>(Arrays.asList(new MainReactPackage(this.mConfig), new AsyncStoragePackage(), new RNDateTimePickerPackage(), new RNBootSplashPackage(), new FastBase64Package(), new RNGestureHandlerPackage(), new LinearGradientPackage(), new ReanimatedPackage(), new SafeAreaContextPackage(), new RNScreensPackage(), new SvgPackage(), new UdpSocketsModule(), new VectorIconsPackage(), new RNVersionNumberPackage(), new RNCWebViewPackage(), new RNWifiPackage(), new ZeroconfReactPackage()));
    }
}
