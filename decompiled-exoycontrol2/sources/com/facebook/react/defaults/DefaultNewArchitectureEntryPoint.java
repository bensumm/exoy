package com.facebook.react.defaults;

import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.soloader.SoLoader;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DefaultNewArchitectureEntryPoint.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u000e\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0007J&\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u000e\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0007R\u001a\u0010\u0003\u001a\u00020\u00048FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\u00048FX\u0087\u0004¢\u0006\f\u0012\u0004\b\t\u0010\u0002\u001a\u0004\b\n\u0010\u0007R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u00048FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u000f\u0010\u0002\u001a\u0004\b\u0010\u0010\u0007¨\u0006\u0015"}, d2 = {"Lcom/facebook/react/defaults/DefaultNewArchitectureEntryPoint;", "", "()V", "concurrentReactEnabled", "", "getConcurrentReactEnabled$annotations", "getConcurrentReactEnabled", "()Z", "fabricEnabled", "getFabricEnabled$annotations", "getFabricEnabled", "privateConcurrentReactEnabled", "privateFabricEnabled", "privateTurboModulesEnabled", "turboModulesEnabled", "getTurboModulesEnabled$annotations", "getTurboModulesEnabled", "load", "", "dynamicLibraryName", "", "ReactAndroid_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
public final class DefaultNewArchitectureEntryPoint {
    public static final DefaultNewArchitectureEntryPoint INSTANCE = new DefaultNewArchitectureEntryPoint();
    private static boolean privateConcurrentReactEnabled;
    private static boolean privateFabricEnabled;
    private static boolean privateTurboModulesEnabled;

    @JvmStatic
    public static /* synthetic */ void getConcurrentReactEnabled$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getFabricEnabled$annotations() {
    }

    @JvmStatic
    public static /* synthetic */ void getTurboModulesEnabled$annotations() {
    }

    @JvmStatic
    public static final void load() {
        load$default(false, false, null, 7, null);
    }

    @JvmStatic
    public static final void load(boolean z) {
        load$default(z, false, null, 6, null);
    }

    @JvmStatic
    public static final void load(boolean z, boolean z2) {
        load$default(z, z2, null, 4, null);
    }

    private DefaultNewArchitectureEntryPoint() {
    }

    public static /* synthetic */ void load$default(boolean z, boolean z2, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        if ((i & 4) != 0) {
            str = "appmodules";
        }
        load(z, z2, str);
    }

    @JvmStatic
    public static final void load(boolean turboModulesEnabled, boolean fabricEnabled, String dynamicLibraryName) {
        Intrinsics.checkNotNullParameter(dynamicLibraryName, "dynamicLibraryName");
        ReactFeatureFlags.useTurboModules = turboModulesEnabled;
        ReactFeatureFlags.enableFabricRenderer = fabricEnabled;
        ReactFeatureFlags.unstable_useFabricInterop = fabricEnabled;
        privateFabricEnabled = fabricEnabled;
        privateTurboModulesEnabled = turboModulesEnabled;
        privateConcurrentReactEnabled = fabricEnabled;
        SoLoader.loadLibrary("react_newarchdefaults");
        SoLoader.loadLibrary(dynamicLibraryName);
    }

    public static /* synthetic */ void load$default(DefaultNewArchitectureEntryPoint defaultNewArchitectureEntryPoint, boolean z, boolean z2, boolean z3, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        if ((i & 4) != 0) {
            z3 = true;
        }
        if ((i & 8) != 0) {
            str = "appmodules";
        }
        defaultNewArchitectureEntryPoint.load(z, z2, z3, str);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Calling DefaultNewArchitectureEntryPoint.load() with different fabricEnabled and concurrentReactEnabled is deprecated. Please use a single flag for both Fabric and Concurrent React", replaceWith = @ReplaceWith(expression = "load(turboModulesEnabled, fabricEnabled, dynamicLibraryName)", imports = {}))
    public final void load(boolean turboModulesEnabled, boolean fabricEnabled, boolean concurrentReactEnabled, String dynamicLibraryName) {
        Intrinsics.checkNotNullParameter(dynamicLibraryName, "dynamicLibraryName");
        load(turboModulesEnabled, fabricEnabled, dynamicLibraryName);
    }

    public static final boolean getFabricEnabled() {
        return privateFabricEnabled;
    }

    public static final boolean getTurboModulesEnabled() {
        return privateTurboModulesEnabled;
    }

    public static final boolean getConcurrentReactEnabled() {
        return privateConcurrentReactEnabled;
    }
}
