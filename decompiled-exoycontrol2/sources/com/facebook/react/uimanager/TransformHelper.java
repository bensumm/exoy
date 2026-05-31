package com.facebook.react.uimanager;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.common.ReactConstants;

/* JADX INFO: loaded from: classes.dex */
public class TransformHelper {
    private static ThreadLocal<double[]> sHelperMatrix = new ThreadLocal<double[]>() { // from class: com.facebook.react.uimanager.TransformHelper.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public double[] initialValue() {
            return new double[16];
        }
    };

    private static double convertToRadians(ReadableMap readableMap, String str) {
        double d;
        boolean z = true;
        if (readableMap.getType(str) == ReadableType.String) {
            String string = readableMap.getString(str);
            if (string.endsWith("rad")) {
                string = string.substring(0, string.length() - 3);
            } else if (string.endsWith("deg")) {
                string = string.substring(0, string.length() - 3);
                z = false;
            }
            d = Float.parseFloat(string);
        } else {
            d = readableMap.getDouble(str);
        }
        return z ? d : MatrixMathHelper.degreesToRadians(d);
    }

    public static void processTransform(ReadableArray readableArray, double[] dArr) {
        double[] dArr2 = sHelperMatrix.get();
        MatrixMathHelper.resetIdentityMatrix(dArr);
        if (readableArray.size() == 16 && readableArray.getType(0) == ReadableType.Number) {
            for (int i = 0; i < readableArray.size(); i++) {
                dArr[i] = readableArray.getDouble(i);
            }
            return;
        }
        int size = readableArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            ReadableMap map = readableArray.getMap(i2);
            String strNextKey = map.keySetIterator().nextKey();
            MatrixMathHelper.resetIdentityMatrix(dArr2);
            if ("matrix".equals(strNextKey)) {
                ReadableArray array = map.getArray(strNextKey);
                for (int i3 = 0; i3 < 16; i3++) {
                    dArr2[i3] = array.getDouble(i3);
                }
            } else if ("perspective".equals(strNextKey)) {
                MatrixMathHelper.applyPerspective(dArr2, map.getDouble(strNextKey));
            } else if ("rotateX".equals(strNextKey)) {
                MatrixMathHelper.applyRotateX(dArr2, convertToRadians(map, strNextKey));
            } else if ("rotateY".equals(strNextKey)) {
                MatrixMathHelper.applyRotateY(dArr2, convertToRadians(map, strNextKey));
            } else if ("rotate".equals(strNextKey) || "rotateZ".equals(strNextKey)) {
                MatrixMathHelper.applyRotateZ(dArr2, convertToRadians(map, strNextKey));
            } else if ("scale".equals(strNextKey)) {
                double d = map.getDouble(strNextKey);
                MatrixMathHelper.applyScaleX(dArr2, d);
                MatrixMathHelper.applyScaleY(dArr2, d);
            } else if (ViewProps.SCALE_X.equals(strNextKey)) {
                MatrixMathHelper.applyScaleX(dArr2, map.getDouble(strNextKey));
            } else if (ViewProps.SCALE_Y.equals(strNextKey)) {
                MatrixMathHelper.applyScaleY(dArr2, map.getDouble(strNextKey));
            } else if ("translate".equals(strNextKey)) {
                ReadableArray array2 = map.getArray(strNextKey);
                MatrixMathHelper.applyTranslate3D(dArr2, array2.getDouble(0), array2.getDouble(1), array2.size() > 2 ? array2.getDouble(2) : 0.0d);
            } else if (ViewProps.TRANSLATE_X.equals(strNextKey)) {
                MatrixMathHelper.applyTranslate2D(dArr2, map.getDouble(strNextKey), 0.0d);
            } else if (ViewProps.TRANSLATE_Y.equals(strNextKey)) {
                MatrixMathHelper.applyTranslate2D(dArr2, 0.0d, map.getDouble(strNextKey));
            } else if ("skewX".equals(strNextKey)) {
                MatrixMathHelper.applySkewX(dArr2, convertToRadians(map, strNextKey));
            } else if ("skewY".equals(strNextKey)) {
                MatrixMathHelper.applySkewY(dArr2, convertToRadians(map, strNextKey));
            } else {
                FLog.w(ReactConstants.TAG, "Unsupported transform type: " + strNextKey);
            }
            MatrixMathHelper.multiplyInto(dArr, dArr, dArr2);
        }
    }
}
