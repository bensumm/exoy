package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGMarkerManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGMarkerManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGMarkerManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGMarkerManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case -1008621499:
                if (str.equals("orient")) {
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
            case -53677816:
                if (str.equals("fillOpacity")) {
                    b = 12;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    b = 13;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    b = 14;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
                    b = 15;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    b = 16;
                }
                break;
            case 3351622:
                if (str.equals("minX")) {
                    b = 17;
                }
                break;
            case 3351623:
                if (str.equals("minY")) {
                    b = 18;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    b = 19;
                }
                break;
            case 3496485:
                if (str.equals("refX")) {
                    b = 20;
                }
                break;
            case 3496486:
                if (str.equals("refY")) {
                    b = 21;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    b = 22;
                }
                break;
            case 92903173:
                if (str.equals("align")) {
                    b = 23;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    b = 24;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    b = 25;
                }
                break;
            case 218785621:
                if (str.equals("markerUnits")) {
                    b = 26;
                }
                break;
            case 220478892:
                if (str.equals("markerWidth")) {
                    b = 27;
                }
                break;
            case 240482938:
                if (str.equals("vbWidth")) {
                    b = 28;
                }
                break;
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    b = 29;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    b = 30;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    b = 31;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    b = 32;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    b = 33;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    b = 34;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    b = 35;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    b = 36;
                }
                break;
            case 1908075304:
                if (str.equals("meetOrSlice")) {
                    b = 37;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    b = 38;
                }
                break;
            case 2106883585:
                if (str.equals("markerHeight")) {
                    b = 39;
                }
                break;
        }
        switch (b) {
            case 0:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setVbHeight(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 1:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 2:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setOrient(t, obj != null ? (String) obj : null);
                break;
            case 4:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 5:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 7:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 8:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontWeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontWeight(t, (Double) obj);
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontWeight(t, (Double) null);
                }
                break;
            case 9:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 10:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 11:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 12:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 13:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 14:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 15:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 16:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 17:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMinX(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 18:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMinY(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 19:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 20:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefX(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefX(t, (Double) obj);
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefX(t, (Double) null);
                }
                break;
            case 21:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefY(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefY(t, (Double) obj);
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setRefY(t, (Double) null);
                }
                break;
            case 22:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 23:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setAlign(t, obj != null ? (String) obj : null);
                break;
            case 24:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 25:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 26:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerUnits(t, obj != null ? (String) obj : null);
                break;
            case 27:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerWidth(t, (Double) obj);
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerWidth(t, (Double) null);
                }
                break;
            case 28:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setVbWidth(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 29:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontSize(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontSize(t, (Double) obj);
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setFontSize(t, (Double) null);
                }
                break;
            case 30:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeDasharray(t, (String) obj);
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeDasharray(t, (ReadableArray) obj);
                }
                break;
            case 31:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 32:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 33:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 34:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 35:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 36:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 37:
                ((RNSVGMarkerManagerInterface) this.mViewManager).setMeetOrSlice(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 38:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeWidth(t, (Double) obj);
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setStrokeWidth(t, "1");
                }
                break;
            case 39:
                if (obj instanceof String) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerHeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerHeight(t, (Double) obj);
                } else {
                    ((RNSVGMarkerManagerInterface) this.mViewManager).setMarkerHeight(t, (Double) null);
                }
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
