package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGRadialGradientManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGRadialGradientManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGRadialGradientManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGRadialGradientManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        byte b = -1;
        switch (str.hashCode()) {
            case -1932235233:
                if (str.equals("gradientUnits")) {
                    b = 0;
                }
                break;
            case -1267206133:
                if (str.equals(ViewProps.OPACITY)) {
                    b = 1;
                }
                break;
            case -1081239615:
                if (str.equals("matrix")) {
                    b = 2;
                }
                break;
            case -933864895:
                if (str.equals("markerEnd")) {
                    b = 3;
                }
                break;
            case -933857362:
                if (str.equals("markerMid")) {
                    b = 4;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    b = 5;
                }
                break;
            case 3189:
                if (str.equals("cx")) {
                    b = 6;
                }
                break;
            case 3190:
                if (str.equals("cy")) {
                    b = 7;
                }
                break;
            case 3282:
                if (str.equals("fx")) {
                    b = 8;
                }
                break;
            case 3283:
                if (str.equals("fy")) {
                    b = 9;
                }
                break;
            case 3654:
                if (str.equals("rx")) {
                    b = 10;
                }
                break;
            case 3655:
                if (str.equals("ry")) {
                    b = 11;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    b = 12;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    b = 13;
                }
                break;
            case 89650992:
                if (str.equals("gradient")) {
                    b = 14;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    b = 15;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    b = 16;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    b = 17;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    b = 18;
                }
                break;
            case 1822665244:
                if (str.equals("gradientTransform")) {
                    b = 19;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    b = 20;
                }
                break;
        }
        switch (b) {
            case 0:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setGradientUnits(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 1:
                this.mViewManager.setOpacity(t, obj == null ? 1.0f : ((Double) obj).floatValue());
                break;
            case 2:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 4:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 6:
                if (obj instanceof String) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setCx(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setCx(t, (Double) obj);
                } else {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setCx(t, (Double) null);
                }
                break;
            case 7:
                if (obj instanceof String) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setCy(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setCy(t, (Double) obj);
                } else {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setCy(t, (Double) null);
                }
                break;
            case 8:
                if (obj instanceof String) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setFx(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setFx(t, (Double) obj);
                } else {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setFx(t, (Double) null);
                }
                break;
            case 9:
                if (obj instanceof String) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setFy(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setFy(t, (Double) obj);
                } else {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setFy(t, (Double) null);
                }
                break;
            case 10:
                if (obj instanceof String) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setRx(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setRx(t, (Double) obj);
                } else {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setRx(t, (Double) null);
                }
                break;
            case 11:
                if (obj instanceof String) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setRy(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setRy(t, (Double) obj);
                } else {
                    ((RNSVGRadialGradientManagerInterface) this.mViewManager).setRy(t, (Double) null);
                }
                break;
            case 12:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 13:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 14:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setGradient(t, (ReadableArray) obj);
                break;
            case 15:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 16:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 17:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 18:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 19:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setGradientTransform(t, (ReadableArray) obj);
                break;
            case 20:
                ((RNSVGRadialGradientManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
