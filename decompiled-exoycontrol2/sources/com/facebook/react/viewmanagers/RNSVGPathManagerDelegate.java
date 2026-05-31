package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGPathManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGPathManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGPathManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGPathManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case -729118945:
                if (str.equals("fillRule")) {
                    b = 6;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    b = 7;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    b = 8;
                }
                break;
            case -53677816:
                if (str.equals("fillOpacity")) {
                    b = 9;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    b = 10;
                }
                break;
            case 100:
                if (str.equals("d")) {
                    b = 11;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    b = 12;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    b = 13;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    b = 14;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    b = 15;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    b = 16;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    b = 17;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    b = 18;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    b = 19;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    b = 20;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    b = 21;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    b = 22;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    b = 23;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    b = 24;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    b = 25;
                }
                break;
        }
        switch (b) {
            case 0:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 1:
                ((RNSVGPathManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 2:
                ((RNSVGPathManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGPathManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 4:
                ((RNSVGPathManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGPathManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 6:
                ((RNSVGPathManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 7:
                ((RNSVGPathManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 8:
                ((RNSVGPathManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 9:
                ((RNSVGPathManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 10:
                ((RNSVGPathManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 11:
                ((RNSVGPathManagerInterface) this.mViewManager).setD(t, obj != null ? (String) obj : null);
                break;
            case 12:
                ((RNSVGPathManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 13:
                ((RNSVGPathManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 14:
                ((RNSVGPathManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 15:
                ((RNSVGPathManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 16:
                ((RNSVGPathManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 17:
                ((RNSVGPathManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 18:
                if (obj instanceof String) {
                    ((RNSVGPathManagerInterface) this.mViewManager).setStrokeDasharray(t, (String) obj);
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGPathManagerInterface) this.mViewManager).setStrokeDasharray(t, (ReadableArray) obj);
                }
                break;
            case 19:
                ((RNSVGPathManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 20:
                ((RNSVGPathManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 21:
                ((RNSVGPathManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 22:
                ((RNSVGPathManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 23:
                ((RNSVGPathManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 24:
                ((RNSVGPathManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 25:
                if (obj instanceof String) {
                    ((RNSVGPathManagerInterface) this.mViewManager).setStrokeWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGPathManagerInterface) this.mViewManager).setStrokeWidth(t, (Double) obj);
                } else {
                    ((RNSVGPathManagerInterface) this.mViewManager).setStrokeWidth(t, "1");
                }
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
