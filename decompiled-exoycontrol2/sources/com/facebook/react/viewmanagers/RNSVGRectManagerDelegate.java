package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGRectManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGRectManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGRectManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGRectManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case -1221029593:
                if (str.equals("height")) {
                    b = 1;
                }
                break;
            case -1081239615:
                if (str.equals("matrix")) {
                    b = 2;
                }
                break;
            case -993894751:
                if (str.equals("propList")) {
                    b = 3;
                }
                break;
            case -933864895:
                if (str.equals("markerEnd")) {
                    b = 4;
                }
                break;
            case -933857362:
                if (str.equals("markerMid")) {
                    b = 5;
                }
                break;
            case -891980232:
                if (str.equals("stroke")) {
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
            case 120:
                if (str.equals("x")) {
                    b = 12;
                }
                break;
            case 121:
                if (str.equals("y")) {
                    b = 13;
                }
                break;
            case 3654:
                if (str.equals("rx")) {
                    b = 14;
                }
                break;
            case 3655:
                if (str.equals("ry")) {
                    b = 15;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    b = 16;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    b = 17;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    b = 18;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    b = 19;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    b = 20;
                }
                break;
            case 113126854:
                if (str.equals("width")) {
                    b = 21;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    b = 22;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    b = 23;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    b = 24;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    b = 25;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    b = 26;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    b = 27;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    b = 28;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    b = 29;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    b = 30;
                }
                break;
        }
        switch (b) {
            case 0:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 1:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setHeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setHeight(t, (Double) obj);
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setHeight(t, (Double) null);
                }
                break;
            case 2:
                ((RNSVGRectManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGRectManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 4:
                ((RNSVGRectManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGRectManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGRectManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 7:
                ((RNSVGRectManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 8:
                ((RNSVGRectManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 9:
                ((RNSVGRectManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 10:
                ((RNSVGRectManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 11:
                ((RNSVGRectManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 12:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setX(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setX(t, (Double) obj);
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setX(t, (Double) null);
                }
                break;
            case 13:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setY(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setY(t, (Double) obj);
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setY(t, (Double) null);
                }
                break;
            case 14:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRx(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRx(t, (Double) obj);
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRx(t, (Double) null);
                }
                break;
            case 15:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRy(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRy(t, (Double) obj);
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setRy(t, (Double) null);
                }
                break;
            case 16:
                ((RNSVGRectManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 17:
                ((RNSVGRectManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 18:
                ((RNSVGRectManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 19:
                ((RNSVGRectManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 20:
                ((RNSVGRectManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 21:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setWidth(t, (Double) obj);
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setWidth(t, (Double) null);
                }
                break;
            case 22:
                ((RNSVGRectManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 23:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setStrokeDasharray(t, (String) obj);
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setStrokeDasharray(t, (ReadableArray) obj);
                }
                break;
            case 24:
                ((RNSVGRectManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 25:
                ((RNSVGRectManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 26:
                ((RNSVGRectManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 27:
                ((RNSVGRectManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 28:
                ((RNSVGRectManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 29:
                ((RNSVGRectManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 30:
                if (obj instanceof String) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setStrokeWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGRectManagerInterface) this.mViewManager).setStrokeWidth(t, (Double) obj);
                } else {
                    ((RNSVGRectManagerInterface) this.mViewManager).setStrokeWidth(t, "1");
                }
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
