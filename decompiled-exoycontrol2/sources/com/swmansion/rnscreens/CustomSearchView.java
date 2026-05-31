package com.swmansion.rnscreens;

import android.content.Context;
import android.view.View;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import com.reactcommunity.rndatetimepicker.RNConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CustomSearchView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\u0017J\b\u0010\u0019\u001a\u00020\u0017H\u0014J\b\u0010\u001a\u001a\u00020\u0017H\u0014J\u0012\u0010\u001b\u001a\u00020\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\nH\u0016J\u0012\u0010\u001d\u001a\u00020\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\fH\u0016J\u000e\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020 R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006!"}, d2 = {"Lcom/swmansion/rnscreens/CustomSearchView;", "Landroidx/appcompat/widget/SearchView;", "context", "Landroid/content/Context;", "fragment", "Landroidx/fragment/app/Fragment;", "(Landroid/content/Context;Landroidx/fragment/app/Fragment;)V", "backPressOverrider", "Lcom/swmansion/rnscreens/FragmentBackPressOverrider;", "mCustomOnCloseListener", "Landroidx/appcompat/widget/SearchView$OnCloseListener;", "mCustomOnSearchClickedListener", "Landroid/view/View$OnClickListener;", "mOnBackPressedCallback", "Landroidx/activity/OnBackPressedCallback;", RNConstants.ARG_VALUE, "", "overrideBackAction", "getOverrideBackAction", "()Z", "setOverrideBackAction", "(Z)V", "clearText", "", "focus", "onAttachedToWindow", "onDetachedFromWindow", "setOnCloseListener", "listener", "setOnSearchClickListener", "setText", "text", "", "react-native-screens_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class CustomSearchView extends SearchView {
    private final FragmentBackPressOverrider backPressOverrider;
    private SearchView.OnCloseListener mCustomOnCloseListener;
    private View.OnClickListener mCustomOnSearchClickedListener;
    private OnBackPressedCallback mOnBackPressedCallback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomSearchView(Context context, Fragment fragment) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback() { // from class: com.swmansion.rnscreens.CustomSearchView$mOnBackPressedCallback$1
            {
                super(true);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                this.this$0.setIconified(true);
            }
        };
        this.mOnBackPressedCallback = onBackPressedCallback;
        this.backPressOverrider = new FragmentBackPressOverrider(fragment, onBackPressedCallback);
        super.setOnSearchClickListener(new View.OnClickListener() { // from class: com.swmansion.rnscreens.CustomSearchView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomSearchView._init_$lambda$0(this.f$0, view);
            }
        });
        super.setOnCloseListener(new SearchView.OnCloseListener() { // from class: com.swmansion.rnscreens.CustomSearchView$$ExternalSyntheticLambda1
            @Override // androidx.appcompat.widget.SearchView.OnCloseListener
            public final boolean onClose() {
                return CustomSearchView._init_$lambda$1(this.f$0);
            }
        });
        setMaxWidth(Integer.MAX_VALUE);
    }

    public final void setOverrideBackAction(boolean z) {
        this.backPressOverrider.setOverrideBackAction(z);
    }

    public final boolean getOverrideBackAction() {
        return this.backPressOverrider.getOverrideBackAction();
    }

    public final void focus() {
        setIconified(false);
        requestFocusFromTouch();
    }

    public final void clearText() {
        setQuery("", false);
    }

    public final void setText(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        setQuery(text, false);
    }

    @Override // androidx.appcompat.widget.SearchView
    public void setOnCloseListener(SearchView.OnCloseListener listener) {
        this.mCustomOnCloseListener = listener;
    }

    @Override // androidx.appcompat.widget.SearchView
    public void setOnSearchClickListener(View.OnClickListener listener) {
        this.mCustomOnSearchClickedListener = listener;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isIconified()) {
            return;
        }
        this.backPressOverrider.maybeAddBackCallback();
    }

    @Override // androidx.appcompat.widget.SearchView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.backPressOverrider.removeBackCallbackIfAdded();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$0(CustomSearchView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        View.OnClickListener onClickListener = this$0.mCustomOnSearchClickedListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
        this$0.backPressOverrider.maybeAddBackCallback();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean _init_$lambda$1(CustomSearchView this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        SearchView.OnCloseListener onCloseListener = this$0.mCustomOnCloseListener;
        boolean zOnClose = onCloseListener != null ? onCloseListener.onClose() : false;
        this$0.backPressOverrider.removeBackCallbackIfAdded();
        return zOnClose;
    }
}
