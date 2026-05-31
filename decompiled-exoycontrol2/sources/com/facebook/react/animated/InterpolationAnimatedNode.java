package com.facebook.react.animated;

import androidx.core.graphics.ColorUtils;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
class InterpolationAnimatedNode extends ValueAnimatedNode {
    private static final String COLOR_OUTPUT_TYPE = "color";
    public static final String EXTRAPOLATE_TYPE_CLAMP = "clamp";
    public static final String EXTRAPOLATE_TYPE_EXTEND = "extend";
    public static final String EXTRAPOLATE_TYPE_IDENTITY = "identity";
    private static final Pattern sNumericPattern = Pattern.compile("[+-]?(\\d+\\.?\\d*|\\.\\d+)([eE][+-]?\\d+)?");
    private final String mExtrapolateLeft;
    private final String mExtrapolateRight;
    private final double[] mInputRange;
    private Object mObjectValue;
    private final Object mOutputRange;
    private final OutputType mOutputType;
    private ValueAnimatedNode mParent;
    private final String mPattern;

    private enum OutputType {
        Number,
        Color,
        String
    }

    private static double[] fromDoubleArray(ReadableArray readableArray) {
        int size = readableArray.size();
        double[] dArr = new double[size];
        for (int i = 0; i < size; i++) {
            dArr[i] = readableArray.getDouble(i);
        }
        return dArr;
    }

    private static int[] fromIntArray(ReadableArray readableArray) {
        int size = readableArray.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = readableArray.getInt(i);
        }
        return iArr;
    }

    private static double[][] fromStringPattern(ReadableArray readableArray) {
        int size = readableArray.size();
        double[][] dArr = new double[size][];
        Matcher matcher = sNumericPattern.matcher(readableArray.getString(0));
        ArrayList arrayList = new ArrayList();
        while (matcher.find()) {
            arrayList.add(Double.valueOf(Double.parseDouble(matcher.group())));
        }
        int size2 = arrayList.size();
        double[] dArr2 = new double[size2];
        for (int i = 0; i < arrayList.size(); i++) {
            dArr2[i] = ((Double) arrayList.get(i)).doubleValue();
        }
        dArr[0] = dArr2;
        for (int i2 = 1; i2 < size; i2++) {
            double[] dArr3 = new double[size2];
            Matcher matcher2 = sNumericPattern.matcher(readableArray.getString(i2));
            for (int i3 = 0; matcher2.find() && i3 < size2; i3++) {
                dArr3[i3] = Double.parseDouble(matcher2.group());
            }
            dArr[i2] = dArr3;
        }
        return dArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static double interpolate(double r13, double r15, double r17, double r19, double r21, java.lang.String r23, java.lang.String r24) {
        /*
            Method dump skipped, instruction units count: 230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.animated.InterpolationAnimatedNode.interpolate(double, double, double, double, double, java.lang.String, java.lang.String):double");
    }

    static double interpolate(double d, double[] dArr, double[] dArr2, String str, String str2) {
        int iFindRangeIndex = findRangeIndex(d, dArr);
        int i = iFindRangeIndex + 1;
        return interpolate(d, dArr[iFindRangeIndex], dArr[i], dArr2[iFindRangeIndex], dArr2[i], str, str2);
    }

    static int interpolateColor(double d, double[] dArr, int[] iArr) {
        int iFindRangeIndex = findRangeIndex(d, dArr);
        int i = iArr[iFindRangeIndex];
        int i2 = iFindRangeIndex + 1;
        int i3 = iArr[i2];
        if (i == i3) {
            return i;
        }
        double d2 = dArr[iFindRangeIndex];
        double d3 = dArr[i2];
        if (d2 == d3) {
            return d <= d2 ? i : i3;
        }
        return ColorUtils.blendARGB(i, i3, (float) ((d - d2) / (d3 - d2)));
    }

    static String interpolateString(String str, double d, double[] dArr, double[][] dArr2, String str2, String str3) {
        int iFindRangeIndex = findRangeIndex(d, dArr);
        StringBuffer stringBuffer = new StringBuffer(str.length());
        Matcher matcher = sNumericPattern.matcher(str);
        int i = 0;
        while (matcher.find()) {
            double[] dArr3 = dArr2[iFindRangeIndex];
            if (i >= dArr3.length) {
                break;
            }
            int i2 = iFindRangeIndex + 1;
            double dInterpolate = interpolate(d, dArr[iFindRangeIndex], dArr[i2], dArr3[i], dArr2[i2][i], str2, str3);
            int i3 = (int) dInterpolate;
            matcher.appendReplacement(stringBuffer, ((double) i3) != dInterpolate ? Double.toString(dInterpolate) : Integer.toString(i3));
            i++;
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    private static int findRangeIndex(double d, double[] dArr) {
        int i = 1;
        while (i < dArr.length - 1 && dArr[i] < d) {
            i++;
        }
        return i - 1;
    }

    public InterpolationAnimatedNode(ReadableMap readableMap) {
        this.mInputRange = fromDoubleArray(readableMap.getArray("inputRange"));
        ReadableArray array = readableMap.getArray("outputRange");
        if ("color".equals(readableMap.getString("outputType"))) {
            this.mOutputType = OutputType.Color;
            this.mOutputRange = fromIntArray(array);
            this.mPattern = null;
        } else if (array.getType(0) == ReadableType.String) {
            this.mOutputType = OutputType.String;
            this.mOutputRange = fromStringPattern(array);
            this.mPattern = array.getString(0);
        } else {
            this.mOutputType = OutputType.Number;
            this.mOutputRange = fromDoubleArray(array);
            this.mPattern = null;
        }
        this.mExtrapolateLeft = readableMap.getString("extrapolateLeft");
        this.mExtrapolateRight = readableMap.getString("extrapolateRight");
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public void onAttachedToNode(AnimatedNode animatedNode) {
        if (this.mParent != null) {
            throw new IllegalStateException("Parent already attached");
        }
        if (!(animatedNode instanceof ValueAnimatedNode)) {
            throw new IllegalArgumentException("Parent is of an invalid type");
        }
        this.mParent = (ValueAnimatedNode) animatedNode;
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public void onDetachedFromNode(AnimatedNode animatedNode) {
        if (animatedNode != this.mParent) {
            throw new IllegalArgumentException("Invalid parent node provided");
        }
        this.mParent = null;
    }

    @Override // com.facebook.react.animated.AnimatedNode
    public void update() {
        ValueAnimatedNode valueAnimatedNode = this.mParent;
        if (valueAnimatedNode == null) {
            return;
        }
        double value = valueAnimatedNode.getValue();
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$animated$InterpolationAnimatedNode$OutputType[this.mOutputType.ordinal()];
        if (i == 1) {
            this.mValue = interpolate(value, this.mInputRange, (double[]) this.mOutputRange, this.mExtrapolateLeft, this.mExtrapolateRight);
        } else if (i == 2) {
            this.mObjectValue = Integer.valueOf(interpolateColor(value, this.mInputRange, (int[]) this.mOutputRange));
        } else {
            if (i != 3) {
                return;
            }
            this.mObjectValue = interpolateString(this.mPattern, value, this.mInputRange, (double[][]) this.mOutputRange, this.mExtrapolateLeft, this.mExtrapolateRight);
        }
    }

    /* JADX INFO: renamed from: com.facebook.react.animated.InterpolationAnimatedNode$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$animated$InterpolationAnimatedNode$OutputType;

        static {
            int[] iArr = new int[OutputType.values().length];
            $SwitchMap$com$facebook$react$animated$InterpolationAnimatedNode$OutputType = iArr;
            try {
                iArr[OutputType.Number.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$InterpolationAnimatedNode$OutputType[OutputType.Color.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$react$animated$InterpolationAnimatedNode$OutputType[OutputType.String.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.facebook.react.animated.ValueAnimatedNode
    public Object getAnimatedObject() {
        return this.mObjectValue;
    }

    @Override // com.facebook.react.animated.ValueAnimatedNode, com.facebook.react.animated.AnimatedNode
    public String prettyPrint() {
        return "InterpolationAnimatedNode[" + this.mTag + "] super: " + super.prettyPrint();
    }
}
