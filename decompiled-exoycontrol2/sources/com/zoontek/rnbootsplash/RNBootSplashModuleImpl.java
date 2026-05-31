package com.zoontek.rnbootsplash;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.window.SplashScreen;
import android.window.SplashScreenView;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.uimanager.PixelUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes2.dex */
public class RNBootSplashModuleImpl {
    public static final String NAME = "RNBootSplash";
    private static final RNBootSplashQueue<Promise> mPromiseQueue = new RNBootSplashQueue<>();
    private static Status mStatus = Status.HIDDEN;
    private static int mThemeResId = -1;
    private static RNBootSplashDialog mInitialDialog = null;
    private static RNBootSplashDialog mFadeOutDialog = null;

    private enum Status {
        HIDDEN,
        HIDING,
        INITIALIZING,
        VISIBLE
    }

    protected static void init(final Activity activity, int i) {
        int i2;
        if (mThemeResId != -1) {
            FLog.w(ReactConstants.TAG, "RNBootSplash: Ignored initialization, module is already initialized.");
            return;
        }
        mThemeResId = i;
        if (activity == null) {
            FLog.w(ReactConstants.TAG, "RNBootSplash: Ignored initialization, current activity is null.");
            return;
        }
        TypedValue typedValue = new TypedValue();
        if (activity.getTheme().resolveAttribute(R.attr.postBootSplashTheme, typedValue, true) && (i2 = typedValue.resourceId) != 0) {
            activity.setTheme(i2);
        }
        final View viewFindViewById = activity.findViewById(android.R.id.content);
        mStatus = Status.INITIALIZING;
        viewFindViewById.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.zoontek.rnbootsplash.RNBootSplashModuleImpl.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                if (RNBootSplashModuleImpl.mStatus == Status.INITIALIZING) {
                    return false;
                }
                viewFindViewById.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= 31) {
            activity.getSplashScreen().setOnExitAnimationListener(new SplashScreen.OnExitAnimationListener() { // from class: com.zoontek.rnbootsplash.RNBootSplashModuleImpl.2
                @Override // android.window.SplashScreen.OnExitAnimationListener
                public void onSplashScreenExit(SplashScreenView splashScreenView) {
                    splashScreenView.remove();
                    activity.getSplashScreen().clearOnExitAnimationListener();
                }
            });
        }
        mInitialDialog = new RNBootSplashDialog(activity, mThemeResId, false);
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.zoontek.rnbootsplash.RNBootSplashModuleImpl.3
            @Override // java.lang.Runnable
            public void run() {
                RNBootSplashModuleImpl.mInitialDialog.show(new Runnable() { // from class: com.zoontek.rnbootsplash.RNBootSplashModuleImpl.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        RNBootSplashModuleImpl.mStatus = Status.VISIBLE;
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void clearPromiseQueue() {
        while (true) {
            RNBootSplashQueue<Promise> rNBootSplashQueue = mPromiseQueue;
            if (rNBootSplashQueue.isEmpty()) {
                return;
            }
            Promise promiseShift = rNBootSplashQueue.shift();
            if (promiseShift != null) {
                promiseShift.resolve(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void hideAndClearPromiseQueue(final ReactApplicationContext reactApplicationContext, final boolean z) {
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.zoontek.rnbootsplash.RNBootSplashModuleImpl.4
            @Override // java.lang.Runnable
            public void run() {
                Activity currentActivity = reactApplicationContext.getCurrentActivity();
                if (RNBootSplashModuleImpl.mStatus == Status.INITIALIZING || currentActivity == null || currentActivity.isFinishing() || currentActivity.isDestroyed()) {
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask() { // from class: com.zoontek.rnbootsplash.RNBootSplashModuleImpl.4.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            timer.cancel();
                            RNBootSplashModuleImpl.hideAndClearPromiseQueue(reactApplicationContext, z);
                        }
                    }, 100L);
                    return;
                }
                if (RNBootSplashModuleImpl.mStatus == Status.HIDING) {
                    return;
                }
                if (RNBootSplashModuleImpl.mInitialDialog == null || RNBootSplashModuleImpl.mStatus == Status.HIDDEN) {
                    RNBootSplashModuleImpl.clearPromiseQueue();
                    return;
                }
                RNBootSplashModuleImpl.mStatus = Status.HIDING;
                if (!z) {
                    RNBootSplashModuleImpl.mInitialDialog.dismiss(new Runnable() { // from class: com.zoontek.rnbootsplash.RNBootSplashModuleImpl.4.2
                        @Override // java.lang.Runnable
                        public void run() {
                            RNBootSplashModuleImpl.mStatus = Status.HIDDEN;
                            RNBootSplashModuleImpl.mInitialDialog = null;
                            RNBootSplashModuleImpl.clearPromiseQueue();
                        }
                    });
                } else {
                    RNBootSplashModuleImpl.mFadeOutDialog = new RNBootSplashDialog(currentActivity, RNBootSplashModuleImpl.mThemeResId, true);
                    RNBootSplashModuleImpl.mFadeOutDialog.show(new Runnable() { // from class: com.zoontek.rnbootsplash.RNBootSplashModuleImpl.4.3
                        @Override // java.lang.Runnable
                        public void run() {
                            RNBootSplashModuleImpl.mInitialDialog.dismiss(new Runnable() { // from class: com.zoontek.rnbootsplash.RNBootSplashModuleImpl.4.3.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    RNBootSplashModuleImpl.mFadeOutDialog.dismiss(new Runnable() { // from class: com.zoontek.rnbootsplash.RNBootSplashModuleImpl.4.3.1.1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            RNBootSplashModuleImpl.mStatus = Status.HIDDEN;
                                            RNBootSplashModuleImpl.mInitialDialog = null;
                                            RNBootSplashModuleImpl.mFadeOutDialog = null;
                                            RNBootSplashModuleImpl.clearPromiseQueue();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public static boolean isSamsungOneUI4() {
        try {
            return (Build.VERSION.class.getDeclaredField("SEM_PLATFORM_INT").getInt(null) - 90000) / 10000 == 4;
        } catch (Exception unused) {
            return false;
        }
    }

    protected static void onHostDestroy() {
        mStatus = Status.HIDDEN;
        mThemeResId = -1;
        clearPromiseQueue();
        RNBootSplashDialog rNBootSplashDialog = mInitialDialog;
        if (rNBootSplashDialog != null) {
            rNBootSplashDialog.dismiss();
            mInitialDialog = null;
        }
        RNBootSplashDialog rNBootSplashDialog2 = mFadeOutDialog;
        if (rNBootSplashDialog2 != null) {
            rNBootSplashDialog2.dismiss();
            mFadeOutDialog = null;
        }
    }

    public static Map<String, Object> getConstants(ReactApplicationContext reactApplicationContext) {
        Resources resources = reactApplicationContext.getResources();
        HashMap map = new HashMap();
        int i = reactApplicationContext.getResources().getConfiguration().uiMode & 48;
        int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
        int identifier2 = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        float dIPFromPixel = 0.0f;
        float dIPFromPixel2 = identifier > 0 ? PixelUtil.toDIPFromPixel(resources.getDimensionPixelSize(identifier)) : 0.0f;
        if (identifier2 > 0 && !ViewConfiguration.get(reactApplicationContext).hasPermanentMenuKey()) {
            dIPFromPixel = PixelUtil.toDIPFromPixel(resources.getDimensionPixelSize(identifier2));
        }
        map.put("darkModeEnabled", Boolean.valueOf(i == 32));
        map.put("logoSizeRatio", Double.valueOf(isSamsungOneUI4() ? 0.5d : 1.0d));
        map.put("navigationBarHeight", Float.valueOf(dIPFromPixel));
        map.put("statusBarHeight", Float.valueOf(dIPFromPixel2));
        return map;
    }

    public static void hide(ReactApplicationContext reactApplicationContext, boolean z, Promise promise) {
        mPromiseQueue.push(promise);
        hideAndClearPromiseQueue(reactApplicationContext, z);
    }

    public static void isVisible(Promise promise) {
        promise.resolve(Boolean.valueOf(mStatus != Status.HIDDEN));
    }
}
