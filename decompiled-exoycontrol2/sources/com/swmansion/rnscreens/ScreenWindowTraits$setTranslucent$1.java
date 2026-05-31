package com.swmansion.rnscreens;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.ReactContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ScreenWindowTraits.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0017¨\u0006\u0004"}, d2 = {"com/swmansion/rnscreens/ScreenWindowTraits$setTranslucent$1", "Lcom/facebook/react/bridge/GuardedRunnable;", "runGuarded", "", "react-native-screens_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class ScreenWindowTraits$setTranslucent$1 extends GuardedRunnable {
    final /* synthetic */ Activity $activity;
    final /* synthetic */ boolean $translucent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ScreenWindowTraits$setTranslucent$1(ReactContext reactContext, Activity activity, boolean z) {
        super(reactContext);
        this.$activity = activity;
        this.$translucent = z;
    }

    @Override // com.facebook.react.bridge.GuardedRunnable
    public void runGuarded() {
        View decorView = this.$activity.getWindow().getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "activity.window.decorView");
        if (this.$translucent) {
            ViewCompat.setOnApplyWindowInsetsListener(decorView, new OnApplyWindowInsetsListener() { // from class: com.swmansion.rnscreens.ScreenWindowTraits$setTranslucent$1$$ExternalSyntheticLambda0
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    return ScreenWindowTraits$setTranslucent$1.runGuarded$lambda$0(view, windowInsetsCompat);
                }
            });
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(decorView, null);
        }
        ViewCompat.requestApplyInsets(decorView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final WindowInsetsCompat runGuarded$lambda$0(View v, WindowInsetsCompat insets) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(insets, "insets");
        WindowInsetsCompat windowInsetsCompatOnApplyWindowInsets = ViewCompat.onApplyWindowInsets(v, insets);
        Intrinsics.checkNotNullExpressionValue(windowInsetsCompatOnApplyWindowInsets, "onApplyWindowInsets(v, insets)");
        if (Build.VERSION.SDK_INT >= 30) {
            Insets insets2 = windowInsetsCompatOnApplyWindowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            Intrinsics.checkNotNullExpressionValue(insets2, "defaultInsets.getInsets(…Compat.Type.statusBars())");
            return new WindowInsetsCompat.Builder().setInsets(WindowInsetsCompat.Type.statusBars(), Insets.of(insets2.left, 0, insets2.right, insets2.bottom)).build();
        }
        return windowInsetsCompatOnApplyWindowInsets.replaceSystemWindowInsets(windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetLeft(), 0, windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetRight(), windowInsetsCompatOnApplyWindowInsets.getSystemWindowInsetBottom());
    }
}
