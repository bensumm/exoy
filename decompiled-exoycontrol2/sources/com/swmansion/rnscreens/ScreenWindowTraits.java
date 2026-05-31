package com.swmansion.rnscreens;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.uimanager.ViewProps;
import com.swmansion.rnscreens.Screen;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ScreenWindowTraits.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\r\u0010\n\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\fJ\r\u0010\r\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\u000eJ\r\u0010\u000f\u001a\u00020\u000bH\u0000¢\u0006\u0002\b\u0010J\u0018\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001a\u0010\u0017\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u0004H\u0002J)\u0010\u001b\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0001¢\u0006\u0002\b J\u001f\u0010!\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0000¢\u0006\u0002\b\"J\u001f\u0010#\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0000¢\u0006\u0002\b$J\u001f\u0010%\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0000¢\u0006\u0002\b&J\u001f\u0010'\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0000¢\u0006\u0002\b(J)\u0010)\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0000¢\u0006\u0002\b*J)\u0010+\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0000¢\u0006\u0002\b,J)\u0010-\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0000¢\u0006\u0002\b.R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lcom/swmansion/rnscreens/ScreenWindowTraits;", "", "()V", "mDefaultStatusBarColor", "", "Ljava/lang/Integer;", "mDidSetNavigationBarAppearance", "", "mDidSetOrientation", "mDidSetStatusBarAppearance", "applyDidSetNavigationBarAppearance", "", "applyDidSetNavigationBarAppearance$react_native_screens_release", "applyDidSetOrientation", "applyDidSetOrientation$react_native_screens_release", "applyDidSetStatusBarAppearance", "applyDidSetStatusBarAppearance$react_native_screens_release", "checkTraitForScreen", "screen", "Lcom/swmansion/rnscreens/Screen;", "trait", "Lcom/swmansion/rnscreens/Screen$WindowTraits;", "childScreenWithTraitSet", "findParentWithTraitSet", "findScreenForTrait", "isColorLight", ViewProps.COLOR, "setColor", "activity", "Landroid/app/Activity;", "context", "Lcom/facebook/react/bridge/ReactContext;", "setColor$react_native_screens_release", "setHidden", "setHidden$react_native_screens_release", "setNavigationBarColor", "setNavigationBarColor$react_native_screens_release", "setNavigationBarHidden", "setNavigationBarHidden$react_native_screens_release", "setOrientation", "setOrientation$react_native_screens_release", "setStyle", "setStyle$react_native_screens_release", "setTranslucent", "setTranslucent$react_native_screens_release", "trySetWindowTraits", "trySetWindowTraits$react_native_screens_release", "react-native-screens_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class ScreenWindowTraits {
    public static final ScreenWindowTraits INSTANCE = new ScreenWindowTraits();
    private static Integer mDefaultStatusBarColor;
    private static boolean mDidSetNavigationBarAppearance;
    private static boolean mDidSetOrientation;
    private static boolean mDidSetStatusBarAppearance;

    /* JADX INFO: compiled from: ScreenWindowTraits.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Screen.WindowTraits.values().length];
            try {
                iArr[Screen.WindowTraits.ORIENTATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Screen.WindowTraits.COLOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Screen.WindowTraits.STYLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Screen.WindowTraits.TRANSLUCENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Screen.WindowTraits.HIDDEN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Screen.WindowTraits.ANIMATED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Screen.WindowTraits.NAVIGATION_BAR_COLOR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[Screen.WindowTraits.NAVIGATION_BAR_HIDDEN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private ScreenWindowTraits() {
    }

    public final void applyDidSetOrientation$react_native_screens_release() {
        mDidSetOrientation = true;
    }

    public final void applyDidSetStatusBarAppearance$react_native_screens_release() {
        mDidSetStatusBarAppearance = true;
    }

    public final void applyDidSetNavigationBarAppearance$react_native_screens_release() {
        mDidSetNavigationBarAppearance = true;
    }

    public final void setOrientation$react_native_screens_release(Screen screen, Activity activity) {
        Integer screenOrientation;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null) {
            return;
        }
        Screen screenFindScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.ORIENTATION);
        activity.setRequestedOrientation((screenFindScreenForTrait == null || (screenOrientation = screenFindScreenForTrait.getScreenOrientation()) == null) ? -1 : screenOrientation.intValue());
    }

    public final void setColor$react_native_screens_release(Screen screen, Activity activity, ReactContext context) {
        Integer mStatusBarColor;
        Boolean isStatusBarAnimated;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null || context == null) {
            return;
        }
        if (mDefaultStatusBarColor == null) {
            mDefaultStatusBarColor = Integer.valueOf(activity.getWindow().getStatusBarColor());
        }
        Screen screenFindScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.COLOR);
        Screen screenFindScreenForTrait2 = findScreenForTrait(screen, Screen.WindowTraits.ANIMATED);
        if (screenFindScreenForTrait == null || (mStatusBarColor = screenFindScreenForTrait.getMStatusBarColor()) == null) {
            mStatusBarColor = mDefaultStatusBarColor;
        }
        UiThreadUtil.runOnUiThread(new ScreenWindowTraits$setColor$1(context, activity, mStatusBarColor, (screenFindScreenForTrait2 == null || (isStatusBarAnimated = screenFindScreenForTrait2.getIsStatusBarAnimated()) == null) ? false : isStatusBarAnimated.booleanValue()));
    }

    public final void setStyle$react_native_screens_release(Screen screen, final Activity activity, ReactContext context) {
        final String mStatusBarStyle;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null || context == null) {
            return;
        }
        Screen screenFindScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.STYLE);
        if (screenFindScreenForTrait == null || (mStatusBarStyle = screenFindScreenForTrait.getMStatusBarStyle()) == null) {
            mStatusBarStyle = "light";
        }
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.swmansion.rnscreens.ScreenWindowTraits$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ScreenWindowTraits.setStyle$lambda$0(activity, mStatusBarStyle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setStyle$lambda$0(Activity activity, String style) {
        Intrinsics.checkNotNullParameter(style, "$style");
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "activity.window.decorView");
        new WindowInsetsControllerCompat(activity.getWindow(), decorView).setAppearanceLightStatusBars(Intrinsics.areEqual(style, "dark"));
    }

    public final void setTranslucent$react_native_screens_release(Screen screen, Activity activity, ReactContext context) {
        Boolean mStatusBarTranslucent;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null || context == null) {
            return;
        }
        Screen screenFindScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.TRANSLUCENT);
        UiThreadUtil.runOnUiThread(new ScreenWindowTraits$setTranslucent$1(context, activity, (screenFindScreenForTrait == null || (mStatusBarTranslucent = screenFindScreenForTrait.getMStatusBarTranslucent()) == null) ? false : mStatusBarTranslucent.booleanValue()));
    }

    public final void setHidden$react_native_screens_release(Screen screen, Activity activity) {
        Boolean mStatusBarHidden;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null) {
            return;
        }
        Screen screenFindScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.HIDDEN);
        final boolean zBooleanValue = (screenFindScreenForTrait == null || (mStatusBarHidden = screenFindScreenForTrait.getMStatusBarHidden()) == null) ? false : mStatusBarHidden.booleanValue();
        Window window = activity.getWindow();
        final WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, window.getDecorView());
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.swmansion.rnscreens.ScreenWindowTraits$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ScreenWindowTraits.setHidden$lambda$1(zBooleanValue, windowInsetsControllerCompat);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setHidden$lambda$1(boolean z, WindowInsetsControllerCompat controller) {
        Intrinsics.checkNotNullParameter(controller, "$controller");
        if (z) {
            controller.hide(WindowInsetsCompat.Type.statusBars());
        } else {
            controller.show(WindowInsetsCompat.Type.statusBars());
        }
    }

    public final void setNavigationBarColor$react_native_screens_release(Screen screen, Activity activity) {
        Integer mNavigationBarColor;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null) {
            return;
        }
        final Window window = activity.getWindow();
        Screen screenFindScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.NAVIGATION_BAR_COLOR);
        final int navigationBarColor = (screenFindScreenForTrait == null || (mNavigationBarColor = screenFindScreenForTrait.getMNavigationBarColor()) == null) ? window.getNavigationBarColor() : mNavigationBarColor.intValue();
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.swmansion.rnscreens.ScreenWindowTraits$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ScreenWindowTraits.setNavigationBarColor$lambda$2(window, navigationBarColor);
            }
        });
        window.setNavigationBarColor(navigationBarColor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setNavigationBarColor$lambda$2(Window window, int i) {
        new WindowInsetsControllerCompat(window, window.getDecorView()).setAppearanceLightNavigationBars(INSTANCE.isColorLight(i));
    }

    public final void setNavigationBarHidden$react_native_screens_release(Screen screen, Activity activity) {
        Boolean mNavigationBarHidden;
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        Screen screenFindScreenForTrait = findScreenForTrait(screen, Screen.WindowTraits.NAVIGATION_BAR_HIDDEN);
        boolean zBooleanValue = (screenFindScreenForTrait == null || (mNavigationBarHidden = screenFindScreenForTrait.getMNavigationBarHidden()) == null) ? false : mNavigationBarHidden.booleanValue();
        WindowCompat.setDecorFitsSystemWindows(window, zBooleanValue);
        if (zBooleanValue) {
            WindowInsetsControllerCompat windowInsetsControllerCompat = new WindowInsetsControllerCompat(window, window.getDecorView());
            windowInsetsControllerCompat.hide(WindowInsetsCompat.Type.navigationBars());
            windowInsetsControllerCompat.setSystemBarsBehavior(2);
            return;
        }
        new WindowInsetsControllerCompat(window, window.getDecorView()).show(WindowInsetsCompat.Type.navigationBars());
    }

    public final void trySetWindowTraits$react_native_screens_release(Screen screen, Activity activity, ReactContext context) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        if (mDidSetOrientation) {
            setOrientation$react_native_screens_release(screen, activity);
        }
        if (mDidSetStatusBarAppearance) {
            setColor$react_native_screens_release(screen, activity, context);
            setStyle$react_native_screens_release(screen, activity, context);
            setTranslucent$react_native_screens_release(screen, activity, context);
            setHidden$react_native_screens_release(screen, activity);
        }
        if (mDidSetNavigationBarAppearance) {
            setNavigationBarColor$react_native_screens_release(screen, activity);
            setNavigationBarHidden$react_native_screens_release(screen, activity);
        }
    }

    private final Screen findScreenForTrait(Screen screen, Screen.WindowTraits trait) {
        Screen screenChildScreenWithTraitSet = childScreenWithTraitSet(screen, trait);
        return screenChildScreenWithTraitSet != null ? screenChildScreenWithTraitSet : checkTraitForScreen(screen, trait) ? screen : findParentWithTraitSet(screen, trait);
    }

    private final Screen findParentWithTraitSet(Screen screen, Screen.WindowTraits trait) {
        for (ScreenContainer container = screen.getContainer(); container != null; container = container.getParent()) {
            if (container instanceof Screen) {
                Screen screen2 = (Screen) container;
                if (checkTraitForScreen(screen2, trait)) {
                    return screen2;
                }
            }
        }
        return null;
    }

    private final Screen childScreenWithTraitSet(Screen screen, Screen.WindowTraits trait) {
        ScreenFragmentWrapper fragmentWrapper;
        if (screen == null || (fragmentWrapper = screen.getFragmentWrapper()) == null) {
            return null;
        }
        Iterator<ScreenContainer> it = fragmentWrapper.getChildScreenContainers().iterator();
        while (it.hasNext()) {
            Screen topScreen = it.next().getTopScreen();
            ScreenWindowTraits screenWindowTraits = INSTANCE;
            Screen screenChildScreenWithTraitSet = screenWindowTraits.childScreenWithTraitSet(topScreen, trait);
            if (screenChildScreenWithTraitSet != null) {
                return screenChildScreenWithTraitSet;
            }
            if (topScreen != null && screenWindowTraits.checkTraitForScreen(topScreen, trait)) {
                return topScreen;
            }
        }
        return null;
    }

    private final boolean checkTraitForScreen(Screen screen, Screen.WindowTraits trait) {
        switch (WhenMappings.$EnumSwitchMapping$0[trait.ordinal()]) {
            case 1:
                if (screen.getScreenOrientation() != null) {
                    return true;
                }
                break;
            case 2:
                if (screen.getMStatusBarColor() != null) {
                    return true;
                }
                break;
            case 3:
                if (screen.getMStatusBarStyle() != null) {
                    return true;
                }
                break;
            case 4:
                if (screen.getMStatusBarTranslucent() != null) {
                    return true;
                }
                break;
            case 5:
                if (screen.getMStatusBarHidden() != null) {
                    return true;
                }
                break;
            case 6:
                if (screen.getIsStatusBarAnimated() != null) {
                    return true;
                }
                break;
            case 7:
                if (screen.getMNavigationBarColor() != null) {
                    return true;
                }
                break;
            case 8:
                if (screen.getMNavigationBarHidden() != null) {
                    return true;
                }
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        return false;
    }

    private final boolean isColorLight(int color) {
        return ((double) 1) - ((((((double) Color.red(color)) * 0.299d) + (((double) Color.green(color)) * 0.587d)) + (((double) Color.blue(color)) * 0.114d)) / ((double) 255)) < 0.5d;
    }
}
