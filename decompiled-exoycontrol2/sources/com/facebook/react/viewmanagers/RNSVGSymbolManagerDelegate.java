package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGSymbolManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGSymbolManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGSymbolManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGSymbolManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case 3143043:
                if (str.equals("fill")) {
                    b = 13;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
                    b = 14;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    b = 15;
                }
                break;
            case 3351622:
                if (str.equals("minX")) {
                    b = 16;
                }
                break;
            case 3351623:
                if (str.equals("minY")) {
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
            case 92903173:
                if (str.equals("align")) {
                    b = 20;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    b = 21;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    b = 22;
                }
                break;
            case 240482938:
                if (str.equals("vbWidth")) {
                    b = 23;
                }
                break;
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    b = 24;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    b = 25;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    b = 26;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    b = 27;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    b = 28;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    b = 29;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    b = 30;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    b = 31;
                }
                break;
            case 1908075304:
                if (str.equals("meetOrSlice")) {
                    b = 32;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    b = 33;
                }
                break;
        }
        switch (b) {
            case 0:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setVbHeight(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 1:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 2:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 4:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 7:
                if (obj instanceof String) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontWeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontWeight(t, (Double) obj);
                } else {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontWeight(t, (Double) null);
                }
                break;
            case 8:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 9:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 10:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 11:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 12:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 13:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 14:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 15:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 16:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMinX(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 17:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMinY(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 18:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 19:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 20:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setAlign(t, obj != null ? (String) obj : null);
                break;
            case 21:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 22:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 23:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setVbWidth(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 24:
                if (obj instanceof String) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontSize(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontSize(t, (Double) obj);
                } else {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setFontSize(t, (Double) null);
                }
                break;
            case 25:
                if (obj instanceof String) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeDasharray(t, (String) obj);
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeDasharray(t, (ReadableArray) obj);
                }
                break;
            case 26:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 27:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 28:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 29:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 30:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 31:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 32:
                ((RNSVGSymbolManagerInterface) this.mViewManager).setMeetOrSlice(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 33:
                if (obj instanceof String) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeWidth(t, (Double) obj);
                } else {
                    ((RNSVGSymbolManagerInterface) this.mViewManager).setStrokeWidth(t, "1");
                }
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
