package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ColorPropConverter;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGSvgViewAndroidManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGSvgViewAndroidManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGSvgViewAndroidManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGSvgViewAndroidManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        byte b = -1;
        switch (str.hashCode()) {
            case -2064426617:
                if (str.equals("bbHeight")) {
                    b = 0;
                }
                break;
            case -1989576717:
                if (str.equals(ViewProps.BORDER_RIGHT_COLOR)) {
                    b = 1;
                }
                break;
            case -1697814026:
                if (str.equals("backfaceVisibility")) {
                    b = 2;
                }
                break;
            case -1567958285:
                if (str.equals("vbHeight")) {
                    b = 3;
                }
                break;
            case -1470826662:
                if (str.equals(ViewProps.BORDER_TOP_COLOR)) {
                    b = 4;
                }
                break;
            case -1308858324:
                if (str.equals(ViewProps.BORDER_BOTTOM_COLOR)) {
                    b = 5;
                }
                break;
            case -1228066334:
                if (str.equals(ViewProps.BORDER_TOP_LEFT_RADIUS)) {
                    b = 6;
                }
                break;
            case -1141400650:
                if (str.equals("accessible")) {
                    b = 7;
                }
                break;
            case -1122140597:
                if (str.equals(ViewProps.BORDER_TOP_START_RADIUS)) {
                    b = 8;
                }
                break;
            case -867333731:
                if (str.equals(ViewProps.BORDER_BOTTOM_START_RADIUS)) {
                    b = 9;
                }
                break;
            case -679581037:
                if (str.equals("hasTVPreferredFocus")) {
                    b = 10;
                }
                break;
            case -631506969:
                if (str.equals("nextFocusDown")) {
                    b = 11;
                }
                break;
            case -631278772:
                if (str.equals("nextFocusLeft")) {
                    b = 12;
                }
                break;
            case -483490364:
                if (str.equals(ViewProps.BORDER_TOP_END_RADIUS)) {
                    b = 13;
                }
                break;
            case -329721498:
                if (str.equals("bbWidth")) {
                    b = 14;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    b = 15;
                }
                break;
            case -252105751:
                if (str.equals(ReactClippingViewGroupHelper.PROP_REMOVE_CLIPPED_SUBVIEWS)) {
                    b = 16;
                }
                break;
            case -242276144:
                if (str.equals(ViewProps.BORDER_LEFT_COLOR)) {
                    b = 17;
                }
                break;
            case -148030058:
                if (str.equals(ViewProps.BORDER_BOTTOM_END_RADIUS)) {
                    b = 18;
                }
                break;
            case -109689771:
                if (str.equals("nativeForegroundAndroid")) {
                    b = 19;
                }
                break;
            case 3351622:
                if (str.equals("minX")) {
                    b = 20;
                }
                break;
            case 3351623:
                if (str.equals("minY")) {
                    b = 21;
                }
                break;
            case 92903173:
                if (str.equals("align")) {
                    b = 22;
                }
                break;
            case 94842723:
                if (str.equals(ViewProps.COLOR)) {
                    b = 23;
                }
                break;
            case 240482938:
                if (str.equals("vbWidth")) {
                    b = 24;
                }
                break;
            case 333432965:
                if (str.equals(ViewProps.BORDER_TOP_RIGHT_RADIUS)) {
                    b = 25;
                }
                break;
            case 503397728:
                if (str.equals("nextFocusForward")) {
                    b = 26;
                }
                break;
            case 581268560:
                if (str.equals(ViewProps.BORDER_BOTTOM_LEFT_RADIUS)) {
                    b = 27;
                }
                break;
            case 588239831:
                if (str.equals(ViewProps.BORDER_BOTTOM_RIGHT_RADIUS)) {
                    b = 28;
                }
                break;
            case 660795168:
                if (str.equals("nextFocusUp")) {
                    b = 29;
                }
                break;
            case 722830999:
                if (str.equals(ViewProps.BORDER_COLOR)) {
                    b = 30;
                }
                break;
            case 737768677:
                if (str.equals("borderStyle")) {
                    b = 31;
                }
                break;
            case 926871597:
                if (str.equals("hitSlop")) {
                    b = 32;
                }
                break;
            case 1220735892:
                if (str.equals(ViewProps.BORDER_END_COLOR)) {
                    b = 33;
                }
                break;
            case 1327599912:
                if (str.equals("tintColor")) {
                    b = 34;
                }
                break;
            case 1349188574:
                if (str.equals("borderRadius")) {
                    b = 35;
                }
                break;
            case 1629011506:
                if (str.equals("focusable")) {
                    b = 36;
                }
                break;
            case 1667773924:
                if (str.equals(ViewProps.NEEDS_OFFSCREEN_ALPHA_COMPOSITING)) {
                    b = 37;
                }
                break;
            case 1747724810:
                if (str.equals("nativeBackgroundAndroid")) {
                    b = 38;
                }
                break;
            case 1908075304:
                if (str.equals("meetOrSlice")) {
                    b = 39;
                }
                break;
            case 1910855543:
                if (str.equals("nextFocusRight")) {
                    b = 40;
                }
                break;
            case 2119889261:
                if (str.equals(ViewProps.BORDER_START_COLOR)) {
                    b = 41;
                }
                break;
        }
        switch (b) {
            case 0:
                if (obj instanceof String) {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbHeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbHeight(t, (Double) obj);
                } else {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbHeight(t, (Double) null);
                }
                break;
            case 1:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderRightColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 2:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBackfaceVisibility(t, obj != null ? (String) obj : null);
                break;
            case 3:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setVbHeight(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 4:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderTopColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 5:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderBottomColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 6:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderTopLeftRadius(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case 7:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setAccessible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 8:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderTopStartRadius(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 9:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderBottomStartRadius(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 10:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setHasTVPreferredFocus(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 11:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNextFocusDown(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 12:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNextFocusLeft(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 13:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderTopEndRadius(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 14:
                if (obj instanceof String) {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbWidth(t, (Double) obj);
                } else {
                    ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBbWidth(t, (Double) null);
                }
                break;
            case 15:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 16:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setRemoveClippedSubviews(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 17:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderLeftColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 18:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderBottomEndRadius(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 19:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNativeForegroundAndroid(t, (ReadableMap) obj);
                break;
            case 20:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setMinX(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 21:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setMinY(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 22:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setAlign(t, obj != null ? (String) obj : null);
                break;
            case 23:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 24:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setVbWidth(t, obj != null ? ((Double) obj).floatValue() : Float.NaN);
                break;
            case 25:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderTopRightRadius(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case 26:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNextFocusForward(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 27:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderBottomLeftRadius(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case 28:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderBottomRightRadius(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case 29:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNextFocusUp(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 30:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 31:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderStyle(t, obj != null ? (String) obj : null);
                break;
            case 32:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setHitSlop(t, (ReadableMap) obj);
                break;
            case 33:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderEndColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 34:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setTintColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            case 35:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderRadius(t, obj != null ? ((Double) obj).doubleValue() : 0.0d);
                break;
            case 36:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setFocusable(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 37:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNeedsOffscreenAlphaCompositing(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 38:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNativeBackgroundAndroid(t, (ReadableMap) obj);
                break;
            case 39:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setMeetOrSlice(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 40:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setNextFocusRight(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 41:
                ((RNSVGSvgViewAndroidManagerInterface) this.mViewManager).setBorderStartColor(t, ColorPropConverter.getColor(obj, t.getContext()));
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
