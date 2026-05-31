package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGEllipseManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGEllipseManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGEllipseManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGEllipseManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
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
            case 3189:
                if (str.equals("cx")) {
                    b = 11;
                }
                break;
            case 3190:
                if (str.equals("cy")) {
                    b = 12;
                }
                break;
            case 3654:
                if (str.equals("rx")) {
                    b = 13;
                }
                break;
            case 3655:
                if (str.equals("ry")) {
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
            case 104482996:
                if (str.equals("vectorEffect")) {
                    b = 19;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    b = 20;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    b = 21;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    b = 22;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    b = 23;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    b = 24;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    b = 25;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    b = 26;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    b = 27;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    b = 28;
                }
                break;
        }
        switch (b) {
            case 0:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 1:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 2:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 3:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 4:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 6:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 7:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 8:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 9:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 10:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 11:
                if (obj instanceof String) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setCx(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setCx(t, (Double) obj);
                } else {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setCx(t, (Double) null);
                }
                break;
            case 12:
                if (obj instanceof String) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setCy(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setCy(t, (Double) obj);
                } else {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setCy(t, (Double) null);
                }
                break;
            case 13:
                if (obj instanceof String) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setRx(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setRx(t, (Double) obj);
                } else {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setRx(t, (Double) null);
                }
                break;
            case 14:
                if (obj instanceof String) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setRy(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setRy(t, (Double) obj);
                } else {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setRy(t, (Double) null);
                }
                break;
            case 15:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 16:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 17:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 18:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 19:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 20:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 21:
                if (obj instanceof String) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setStrokeDasharray(t, (String) obj);
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setStrokeDasharray(t, (ReadableArray) obj);
                }
                break;
            case 22:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 23:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 24:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 25:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 26:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 27:
                ((RNSVGEllipseManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 28:
                if (obj instanceof String) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setStrokeWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setStrokeWidth(t, (Double) obj);
                } else {
                    ((RNSVGEllipseManagerInterface) this.mViewManager).setStrokeWidth(t, "1");
                }
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
