package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGForeignObjectManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGForeignObjectManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGForeignObjectManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGForeignObjectManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case -734428249:
                if (str.equals(ViewProps.FONT_WEIGHT)) {
                    b = 7;
                }
                break;
            case -729118945:
                if (str.equals("fillRule")) {
                    b = 8;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    b = 9;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    b = 10;
                }
                break;
            case -53677816:
                if (str.equals("fillOpacity")) {
                    b = 11;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    b = 12;
                }
                break;
            case 120:
                if (str.equals("x")) {
                    b = 13;
                }
                break;
            case 121:
                if (str.equals("y")) {
                    b = 14;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    b = 15;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
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
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    b = 23;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    b = 24;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    b = 25;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    b = 26;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    b = 27;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    b = 28;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    b = 29;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    b = 30;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    b = 31;
                }
                break;
        }
        switch (b) {
            case 0:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 1:
                if (obj instanceof String) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setHeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setHeight(t, (Double) obj);
                } else {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setHeight(t, (Double) null);
                }
                break;
            case 2:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 4:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 7:
                if (obj instanceof String) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setFontWeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setFontWeight(t, (Double) obj);
                } else {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setFontWeight(t, (Double) null);
                }
                break;
            case 8:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 9:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 10:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 11:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 12:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 13:
                if (obj instanceof String) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setX(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setX(t, (Double) obj);
                } else {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setX(t, (Double) null);
                }
                break;
            case 14:
                if (obj instanceof String) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setY(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setY(t, (Double) obj);
                } else {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setY(t, (Double) null);
                }
                break;
            case 15:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 16:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 17:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 18:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 19:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 20:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 21:
                if (obj instanceof String) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setWidth(t, (Double) obj);
                } else {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setWidth(t, (Double) null);
                }
                break;
            case 22:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 23:
                if (obj instanceof String) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setFontSize(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setFontSize(t, (Double) obj);
                } else {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setFontSize(t, (Double) null);
                }
                break;
            case 24:
                if (obj instanceof String) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStrokeDasharray(t, (String) obj);
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStrokeDasharray(t, (ReadableArray) obj);
                }
                break;
            case 25:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 26:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 27:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 28:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 29:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 30:
                ((RNSVGForeignObjectManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 31:
                if (obj instanceof String) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStrokeWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStrokeWidth(t, (Double) obj);
                } else {
                    ((RNSVGForeignObjectManagerInterface) this.mViewManager).setStrokeWidth(t, "1");
                }
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
