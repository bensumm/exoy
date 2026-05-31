package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGClipPathManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGClipPathManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGClipPathManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGClipPathManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        byte b = -1;
        switch (str.hashCode()) {
            case -1267206133:
                if (str.equals(ViewProps.OPACITY)) {
                    b = 0;
                }
                break;
            case -1081239615:
                if (str.equals("matrix")) {
                    b = 1;
                }
                break;
            case -993894751:
                if (str.equals("propList")) {
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
            case -891980232:
                if (str.equals("stroke")) {
                    b = 5;
                }
                break;
            case -734428249:
                if (str.equals(ViewProps.FONT_WEIGHT)) {
                    b = 6;
                }
                break;
            case -729118945:
                if (str.equals("fillRule")) {
                    b = 7;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    b = 8;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    b = 9;
                }
                break;
            case -53677816:
                if (str.equals("fillOpacity")) {
                    b = 10;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    b = 11;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    b = 12;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
                    b = 13;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    b = 14;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    b = 15;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    b = 16;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    b = 17;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    b = 18;
                }
                break;
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    b = 19;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    b = 20;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    b = 21;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    b = 22;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    b = 23;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    b = 24;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    b = 25;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    b = 26;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    b = 27;
                }
                break;
        }
        switch (b) {
            case 0:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 1:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 2:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 4:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 6:
                if (obj instanceof String) {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setFontWeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setFontWeight(t, (Double) obj);
                } else {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setFontWeight(t, (Double) null);
                }
                break;
            case 7:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 8:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 9:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 10:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 11:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 12:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 13:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 14:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 15:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 16:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 17:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 18:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 19:
                if (obj instanceof String) {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setFontSize(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setFontSize(t, (Double) obj);
                } else {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setFontSize(t, (Double) null);
                }
                break;
            case 20:
                if (obj instanceof String) {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setStrokeDasharray(t, (String) obj);
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setStrokeDasharray(t, (ReadableArray) obj);
                }
                break;
            case 21:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 22:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 23:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 24:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 25:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 26:
                ((RNSVGClipPathManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 27:
                if (obj instanceof String) {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setStrokeWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setStrokeWidth(t, (Double) obj);
                } else {
                    ((RNSVGClipPathManagerInterface) this.mViewManager).setStrokeWidth(t, "1");
                }
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
