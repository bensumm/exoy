package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGPatternManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGPatternManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGPatternManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGPatternManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        byte b = -1;
        switch (str.hashCode()) {
            case -1567958285:
                if (str.equals("vbHeight")) {
                    b = 0;
                }
                break;
            case -1267206133:
                if (str.equals(ViewProps.OPACITY)) {
                    b = 1;
                }
                break;
            case -1221029593:
                if (str.equals("height")) {
                    b = 2;
                }
                break;
            case -1081239615:
                if (str.equals("matrix")) {
                    b = 3;
                }
                break;
            case -993894751:
                if (str.equals("propList")) {
                    b = 4;
                }
                break;
            case -933864895:
                if (str.equals("markerEnd")) {
                    b = 5;
                }
                break;
            case -933857362:
                if (str.equals("markerMid")) {
                    b = 6;
                }
                break;
            case -891980232:
                if (str.equals("stroke")) {
                    b = 7;
                }
                break;
            case -734428249:
                if (str.equals(ViewProps.FONT_WEIGHT)) {
                    b = 8;
                }
                break;
            case -729118945:
                if (str.equals("fillRule")) {
                    b = 9;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    b = 10;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    b = 11;
                }
                break;
            case -207800897:
                if (str.equals("patternUnits")) {
                    b = 12;
                }
                break;
            case -128680410:
                if (str.equals("patternContentUnits")) {
                    b = 13;
                }
                break;
            case -53677816:
                if (str.equals("fillOpacity")) {
                    b = 14;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    b = 15;
                }
                break;
            case 120:
                if (str.equals("x")) {
                    b = 16;
                }
                break;
            case 121:
                if (str.equals("y")) {
                    b = 17;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    b = 18;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
                    b = 19;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    b = 20;
                }
                break;
            case 3351622:
                if (str.equals("minX")) {
                    b = 21;
                }
                break;
            case 3351623:
                if (str.equals("minY")) {
                    b = 22;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    b = 23;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    b = 24;
                }
                break;
            case 92903173:
                if (str.equals("align")) {
                    b = 25;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    b = 26;
                }
                break;
            case 113126854:
                if (str.equals("width")) {
                    b = 27;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    b = 28;
                }
                break;
            case 240482938:
                if (str.equals("vbWidth")) {
                    b = 29;
                }
                break;
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    b = 30;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    b = 31;
                }
                break;
            case 746561980:
                if (str.equals("patternTransform")) {
                    b = 32;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    b = 33;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    b = 34;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    b = 35;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    b = 36;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    b = 37;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    b = 38;
                }
                break;
            case 1908075304:
                if (str.equals("meetOrSlice")) {
                    b = 39;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    b = 40;
                }
                break;
        }
        switch (b) {
            case 0:
                ((RNSVGPatternManagerInterface) this.mViewManager).setVbHeight(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 1:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 2:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setHeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setHeight(t, (Double) obj);
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setHeight(t, (Double) null);
                }
                break;
            case 3:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 4:
                ((RNSVGPatternManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 5:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 7:
                ((RNSVGPatternManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 8:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontWeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontWeight(t, (Double) obj);
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontWeight(t, (Double) null);
                }
                break;
            case 9:
                ((RNSVGPatternManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 10:
                ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 11:
                ((RNSVGPatternManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 12:
                ((RNSVGPatternManagerInterface) this.mViewManager).setPatternUnits(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 13:
                ((RNSVGPatternManagerInterface) this.mViewManager).setPatternContentUnits(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 14:
                ((RNSVGPatternManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 15:
                ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 16:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setX(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setX(t, (Double) obj);
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setX(t, (Double) null);
                }
                break;
            case 17:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setY(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setY(t, (Double) obj);
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setY(t, (Double) null);
                }
                break;
            case 18:
                ((RNSVGPatternManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 19:
                ((RNSVGPatternManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 20:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 21:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMinX(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 22:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMinY(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 23:
                ((RNSVGPatternManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 24:
                ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 25:
                ((RNSVGPatternManagerInterface) this.mViewManager).setAlign(t, obj != null ? (String) obj : null);
                break;
            case 26:
                ((RNSVGPatternManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 27:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setWidth(t, (Double) obj);
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setWidth(t, (Double) null);
                }
                break;
            case 28:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 29:
                ((RNSVGPatternManagerInterface) this.mViewManager).setVbWidth(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 30:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontSize(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontSize(t, (Double) obj);
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setFontSize(t, (Double) null);
                }
                break;
            case 31:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeDasharray(t, (String) obj);
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeDasharray(t, (ReadableArray) obj);
                }
                break;
            case 32:
                ((RNSVGPatternManagerInterface) this.mViewManager).setPatternTransform(t, (ReadableArray) obj);
                break;
            case 33:
                ((RNSVGPatternManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 34:
                ((RNSVGPatternManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 35:
                ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 36:
                ((RNSVGPatternManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 37:
                ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 38:
                ((RNSVGPatternManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 39:
                ((RNSVGPatternManagerInterface) this.mViewManager).setMeetOrSlice(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 40:
                if (obj instanceof String) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeWidth(t, (Double) obj);
                } else {
                    ((RNSVGPatternManagerInterface) this.mViewManager).setStrokeWidth(t, "1");
                }
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
