package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSScreenStackHeaderConfigManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSScreenStackHeaderConfigManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSScreenStackHeaderConfigManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSScreenStackHeaderConfigManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        byte b = -1;
        switch (str.hashCode()) {
            case -1822687399:
                if (str.equals("translucent")) {
                    b = 0;
                }
                break;
            case -1799367701:
                if (str.equals("titleColor")) {
                    b = 1;
                }
                break;
            case -1774658170:
                if (str.equals("largeTitleColor")) {
                    b = 2;
                }
                break;
            case -1715368693:
                if (str.equals("titleFontFamily")) {
                    b = 3;
                }
                break;
            case -1503810304:
                if (str.equals("disableBackButtonMenu")) {
                    b = 4;
                }
                break;
            case -1225100257:
                if (str.equals("titleFontWeight")) {
                    b = 5;
                }
                break;
            case -1217487446:
                if (str.equals(ViewProps.HIDDEN)) {
                    b = 6;
                }
                break;
            case -1094575123:
                if (str.equals("largeTitleFontSize")) {
                    b = 7;
                }
                break;
            case -1063138943:
                if (str.equals("backTitleVisible")) {
                    b = 8;
                }
                break;
            case -962590849:
                if (str.equals("direction")) {
                    b = 9;
                }
                break;
            case -389245640:
                if (str.equals("largeTitleBackgroundColor")) {
                    b = 10;
                }
                break;
            case -140063148:
                if (str.equals("backButtonInCustomView")) {
                    b = 11;
                }
                break;
            case 347216:
                if (str.equals("largeTitleFontFamily")) {
                    b = 12;
                }
                break;
            case 94842723:
                if (str.equals(ViewProps.COLOR)) {
                    b = 13;
                }
                break;
            case 110371416:
                if (str.equals("title")) {
                    b = 14;
                }
                break;
            case 183888321:
                if (str.equals("backTitleFontSize")) {
                    b = 15;
                }
                break;
            case 243070244:
                if (str.equals("backTitleFontFamily")) {
                    b = 16;
                }
                break;
            case 339462402:
                if (str.equals("hideShadow")) {
                    b = 17;
                }
                break;
            case 490615652:
                if (str.equals("largeTitleFontWeight")) {
                    b = 18;
                }
                break;
            case 1038753243:
                if (str.equals("hideBackButton")) {
                    b = 19;
                }
                break;
            case 1287124693:
                if (str.equals(ViewProps.BACKGROUND_COLOR)) {
                    b = 20;
                }
                break;
            case 1324688817:
                if (str.equals("backTitle")) {
                    b = 21;
                }
                break;
            case 1518161768:
                if (str.equals("titleFontSize")) {
                    b = 22;
                }
                break;
            case 1564506303:
                if (str.equals("largeTitleHideShadow")) {
                    b = 23;
                }
                break;
            case 2029798365:
                if (str.equals("largeTitle")) {
                    b = 24;
                }
                break;
            case 2099541337:
                if (str.equals("topInsetEnabled")) {
                    b = 25;
                }
                break;
        }
        switch (b) {
            case 0:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setTranslucent(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 1:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setTitleColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 2:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setLargeTitleColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 3:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setTitleFontFamily(t, obj != null ? (String) obj : null);
                break;
            case 4:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setDisableBackButtonMenu(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 5:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setTitleFontWeight(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setHidden(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 7:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setLargeTitleFontSize(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 8:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setBackTitleVisible(t, obj != null ? ((Boolean) obj).booleanValue() : true);
                break;
            case 9:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setDirection(t, (String) obj);
                break;
            case 10:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setLargeTitleBackgroundColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 11:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setBackButtonInCustomView(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 12:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setLargeTitleFontFamily(t, obj != null ? (String) obj : null);
                break;
            case 13:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 14:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setTitle(t, obj != null ? (String) obj : null);
                break;
            case 15:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setBackTitleFontSize(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 16:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setBackTitleFontFamily(t, obj != null ? (String) obj : null);
                break;
            case 17:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setHideShadow(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 18:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setLargeTitleFontWeight(t, obj != null ? (String) obj : null);
                break;
            case 19:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setHideBackButton(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 20:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setBackgroundColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 21:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setBackTitle(t, obj != null ? (String) obj : null);
                break;
            case 22:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setTitleFontSize(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 23:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setLargeTitleHideShadow(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 24:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setLargeTitle(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 25:
                ((RNSScreenStackHeaderConfigManagerInterface) this.mViewManager).setTopInsetEnabled(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
