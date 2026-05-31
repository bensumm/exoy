package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.viewmanagers.RNSVGTextPathManagerInterface;

/* JADX INFO: loaded from: classes.dex */
public class RNSVGTextPathManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & RNSVGTextPathManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    /* JADX WARN: Incorrect types in method signature: (TU;)V */
    public RNSVGTextPathManagerDelegate(BaseViewManagerInterface baseViewManagerInterface) {
        super(baseViewManagerInterface);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.facebook.react.uimanager.BaseViewManagerDelegate, com.facebook.react.uimanager.ViewManagerDelegate
    public void setProperty(T t, String str, Object obj) {
        str.hashCode();
        byte b = -1;
        switch (str.hashCode()) {
            case -2012158909:
                if (str.equals("spacing")) {
                    b = 0;
                }
                break;
            case -1993948267:
                if (str.equals("startOffset")) {
                    b = 1;
                }
                break;
            case -1603134955:
                if (str.equals("lengthAdjust")) {
                    b = 2;
                }
                break;
            case -1267206133:
                if (str.equals(ViewProps.OPACITY)) {
                    b = 3;
                }
                break;
            case -1171891896:
                if (str.equals("alignmentBaseline")) {
                    b = 4;
                }
                break;
            case -1139902161:
                if (str.equals("verticalAlign")) {
                    b = 5;
                }
                break;
            case -1081239615:
                if (str.equals("matrix")) {
                    b = 6;
                }
                break;
            case -1077554975:
                if (str.equals("method")) {
                    b = 7;
                }
                break;
            case -993894751:
                if (str.equals("propList")) {
                    b = 8;
                }
                break;
            case -933864895:
                if (str.equals("markerEnd")) {
                    b = 9;
                }
                break;
            case -933857362:
                if (str.equals("markerMid")) {
                    b = 10;
                }
                break;
            case -925180581:
                if (str.equals("rotate")) {
                    b = 11;
                }
                break;
            case -891980232:
                if (str.equals("stroke")) {
                    b = 12;
                }
                break;
            case -734428249:
                if (str.equals(ViewProps.FONT_WEIGHT)) {
                    b = 13;
                }
                break;
            case -729118945:
                if (str.equals("fillRule")) {
                    b = 14;
                }
                break;
            case -416535885:
                if (str.equals("strokeOpacity")) {
                    b = 15;
                }
                break;
            case -293492298:
                if (str.equals(ViewProps.POINTER_EVENTS)) {
                    b = 16;
                }
                break;
            case -53677816:
                if (str.equals("fillOpacity")) {
                    b = 17;
                }
                break;
            case -44578051:
                if (str.equals("strokeDashoffset")) {
                    b = 18;
                }
                break;
            case 120:
                if (str.equals("x")) {
                    b = 19;
                }
                break;
            case 121:
                if (str.equals("y")) {
                    b = 20;
                }
                break;
            case 3220:
                if (str.equals("dx")) {
                    b = 21;
                }
                break;
            case 3221:
                if (str.equals("dy")) {
                    b = 22;
                }
                break;
            case 3143043:
                if (str.equals("fill")) {
                    b = 23;
                }
                break;
            case 3148879:
                if (str.equals("font")) {
                    b = 24;
                }
                break;
            case 3211051:
                if (str.equals("href")) {
                    b = 25;
                }
                break;
            case 3344108:
                if (str.equals("mask")) {
                    b = 26;
                }
                break;
            case 3373707:
                if (str.equals("name")) {
                    b = 27;
                }
                break;
            case 3530071:
                if (str.equals("side")) {
                    b = 28;
                }
                break;
            case 78845486:
                if (str.equals("strokeMiterlimit")) {
                    b = 29;
                }
                break;
            case 104482996:
                if (str.equals("vectorEffect")) {
                    b = 30;
                }
                break;
            case 217109576:
                if (str.equals("markerStart")) {
                    b = 31;
                }
                break;
            case 275888445:
                if (str.equals("baselineShift")) {
                    b = 32;
                }
                break;
            case 365601008:
                if (str.equals(ViewProps.FONT_SIZE)) {
                    b = 33;
                }
                break;
            case 401643183:
                if (str.equals("strokeDasharray")) {
                    b = 34;
                }
                break;
            case 778043962:
                if (str.equals("inlineSize")) {
                    b = 35;
                }
                break;
            case 917656469:
                if (str.equals("clipPath")) {
                    b = 36;
                }
                break;
            case 917735020:
                if (str.equals("clipRule")) {
                    b = 37;
                }
                break;
            case 1027575302:
                if (str.equals("strokeLinecap")) {
                    b = 38;
                }
                break;
            case 1054434908:
                if (str.equals("midLine")) {
                    b = 39;
                }
                break;
            case 1637488243:
                if (str.equals("textLength")) {
                    b = 40;
                }
                break;
            case 1671764162:
                if (str.equals("display")) {
                    b = 41;
                }
                break;
            case 1790285174:
                if (str.equals("strokeLinejoin")) {
                    b = 42;
                }
                break;
            case 1847674614:
                if (str.equals("responsible")) {
                    b = 43;
                }
                break;
            case 1924065902:
                if (str.equals("strokeWidth")) {
                    b = 44;
                }
                break;
        }
        switch (b) {
            case 0:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setSpacing(t, obj != null ? (String) obj : null);
                break;
            case 1:
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStartOffset(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStartOffset(t, (Double) obj);
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStartOffset(t, (Double) null);
                }
                break;
            case 2:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setLengthAdjust(t, obj != null ? (String) obj : null);
                break;
            case 3:
                this.mViewManager.setOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 4:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setAlignmentBaseline(t, obj != null ? (String) obj : null);
                break;
            case 5:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setVerticalAlign(t, obj != null ? (String) obj : null);
                break;
            case 6:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMatrix(t, (ReadableArray) obj);
                break;
            case 7:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMethod(t, obj != null ? (String) obj : null);
                break;
            case 8:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setPropList(t, (ReadableArray) obj);
                break;
            case 9:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMarkerEnd(t, obj != null ? (String) obj : null);
                break;
            case 10:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMarkerMid(t, obj != null ? (String) obj : null);
                break;
            case 11:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setRotate(t, (ReadableArray) obj);
                break;
            case 12:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStroke(t, (ReadableMap) obj);
                break;
            case 13:
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontWeight(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontWeight(t, (Double) obj);
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontWeight(t, (Double) null);
                }
                break;
            case 14:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setFillRule(t, obj != null ? ((Double) obj).intValue() : 1);
                break;
            case 15:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 16:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setPointerEvents(t, obj != null ? (String) obj : null);
                break;
            case 17:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setFillOpacity(t, obj != null ? ((Double) obj).floatValue() : 1.0f);
                break;
            case 18:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeDashoffset(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 19:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setX(t, (ReadableArray) obj);
                break;
            case 20:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setY(t, (ReadableArray) obj);
                break;
            case 21:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setDx(t, (ReadableArray) obj);
                break;
            case 22:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setDy(t, (ReadableArray) obj);
                break;
            case 23:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setFill(t, (ReadableMap) obj);
                break;
            case 24:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setFont(t, (ReadableMap) obj);
                break;
            case 25:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setHref(t, obj != null ? (String) obj : null);
                break;
            case 26:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMask(t, obj != null ? (String) obj : null);
                break;
            case 27:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setName(t, obj != null ? (String) obj : null);
                break;
            case 28:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setSide(t, obj != null ? (String) obj : null);
                break;
            case 29:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeMiterlimit(t, obj != null ? ((Double) obj).floatValue() : 0.0f);
                break;
            case 30:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setVectorEffect(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 31:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMarkerStart(t, obj != null ? (String) obj : null);
                break;
            case 32:
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setBaselineShift(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setBaselineShift(t, (Double) obj);
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setBaselineShift(t, (Double) null);
                }
                break;
            case 33:
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontSize(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontSize(t, (Double) obj);
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setFontSize(t, (Double) null);
                }
                break;
            case 34:
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeDasharray(t, (String) obj);
                } else if (obj instanceof ReadableArray) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeDasharray(t, (ReadableArray) obj);
                }
                break;
            case 35:
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setInlineSize(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setInlineSize(t, (Double) obj);
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setInlineSize(t, (Double) null);
                }
                break;
            case 36:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setClipPath(t, obj != null ? (String) obj : null);
                break;
            case 37:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setClipRule(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 38:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeLinecap(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 39:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setMidLine(t, obj != null ? (String) obj : null);
                break;
            case 40:
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setTextLength(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setTextLength(t, (Double) obj);
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setTextLength(t, (Double) null);
                }
                break;
            case 41:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setDisplay(t, obj != null ? (String) obj : null);
                break;
            case 42:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeLinejoin(t, obj != null ? ((Double) obj).intValue() : 0);
                break;
            case 43:
                ((RNSVGTextPathManagerInterface) this.mViewManager).setResponsible(t, obj != null ? ((Boolean) obj).booleanValue() : false);
                break;
            case 44:
                if (obj instanceof String) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeWidth(t, (String) obj);
                } else if (obj instanceof Double) {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeWidth(t, (Double) obj);
                } else {
                    ((RNSVGTextPathManagerInterface) this.mViewManager).setStrokeWidth(t, "1");
                }
                break;
            default:
                super.setProperty(t, str, obj);
                break;
        }
    }
}
