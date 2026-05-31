package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGImageManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGImageManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGImageManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGImageManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case 114148:
                if (str.equals("src")) {
                    b = 14;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    b = 15;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    b = 16;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    b = 17;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    b = 18;
                }
                break;
            case 92903173:
                if (str.equals("align")) {
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
            case 1908075304:
                if (str.equals("meetOrSlice")) {
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
                    ((RNSVGImageManagerInterface) this.mViewManager).setHeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setHeight(t, (Double) obj);
                } else {
                    ((RNSVGImageManagerInterface) this.mViewManager).setHeight(t, (Double) null);
                }
                break;
            case 2:
                ((RNSVGImageManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGImageManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 4:
                ((RNSVGImageManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGImageManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGImageManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 7:
                ((RNSVGImageManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 8:
                ((RNSVGImageManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 9:
                ((RNSVGImageManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 10:
                ((RNSVGImageManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 11:
                ((RNSVGImageManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 12:
                if (obj instanceof String) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setX(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setX(t, (Double) obj);
                } else {
                    ((RNSVGImageManagerInterface) this.mViewManager).setX(t, (Double) null);
                }
                break;
            case 13:
                if (obj instanceof String) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setY(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setY(t, (Double) obj);
                } else {
                    ((RNSVGImageManagerInterface) this.mViewManager).setY(t, (Double) null);
                }
                break;
            case 14:
                ((RNSVGImageManagerInterface) this.mViewManager).setSrc(t, (ReadableMap) obj);
                break;
            case 15:
                ((RNSVGImageManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 16:
                ((RNSVGImageManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 17:
                ((RNSVGImageManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 18:
                ((RNSVGImageManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 19:
                ((RNSVGImageManagerInterface) this.mViewManager).setAlign(t, obj != null ? (String) obj : null);
                break;
            case 20:
                ((RNSVGImageManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 21:
                if (obj instanceof String) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setWidth(t, (Double) obj);
                } else {
                    ((RNSVGImageManagerInterface) this.mViewManager).setWidth(t, (Double) null);
                }
                break;
            case 22:
                ((RNSVGImageManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 23:
                if (obj instanceof String) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setStrokeDasharray(t, (String) obj);
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setStrokeDasharray(t, (ReadableArray) obj);
                }
                break;
            case 24:
                ((RNSVGImageManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 25:
                ((RNSVGImageManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 26:
                ((RNSVGImageManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 27:
                ((RNSVGImageManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 28:
                ((RNSVGImageManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 29:
                ((RNSVGImageManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 30:
                ((RNSVGImageManagerInterface) this.mViewManager).setMeetOrSlice(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 31:
                if (obj instanceof String) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setStrokeWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGImageManagerInterface) this.mViewManager).setStrokeWidth(t, (Double) obj);
                } else {
                    ((RNSVGImageManagerInterface) this.mViewManager).setStrokeWidth(t, "1");
                }
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
