package com.horcrux.svg;

import android.graphics.Path;
import android.graphics.RectF;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class PathParser {
    static ArrayList<PathElement> elements;
    private static int i;
    private static int l;
    private static Path mPath;
    private static boolean mPenDown;
    private static float mPenDownX;
    private static float mPenDownY;
    private static float mPenX;
    private static float mPenY;
    private static float mPivotX;
    private static float mPivotY;
    static float mScale;
    private static String s;

    private static boolean is_cmd(char c) {
        switch (c) {
            case 'A':
            case 'C':
            case 'H':
            case 'L':
            case 'M':
            case 'Q':
            case 'S':
            case 'T':
            case 'V':
            case 'Z':
            case 'a':
            case 'c':
            case 'h':
            case 'l':
            case 'm':
            case 'q':
            case 's':
            case 't':
            case 'v':
            case 'z':
                return true;
            default:
                return false;
        }
    }

    private static boolean is_number_start(char c) {
        return (c >= '0' && c <= '9') || c == '.' || c == '-' || c == '+';
    }

    PathParser() {
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00ad A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x020a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x002e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static android.graphics.Path parse(java.lang.String r25) {
        /*
            Method dump skipped, instruction units count: 664
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.PathParser.parse(java.lang.String):android.graphics.Path");
    }

    private static void move(float f, float f2) {
        moveTo(f + mPenX, f2 + mPenY);
    }

    private static void moveTo(float f, float f2) {
        mPenX = f;
        mPivotX = f;
        mPenDownX = f;
        mPenY = f2;
        mPivotY = f2;
        mPenDownY = f2;
        Path path = mPath;
        float f3 = mScale;
        path.moveTo(f * f3, f3 * f2);
        elements.add(new PathElement(ElementType.kCGPathElementMoveToPoint, new Point[]{new Point(f, f2)}));
    }

    private static void line(float f, float f2) {
        lineTo(f + mPenX, f2 + mPenY);
    }

    private static void lineTo(float f, float f2) {
        setPenDown();
        mPenX = f;
        mPivotX = f;
        mPenY = f2;
        mPivotY = f2;
        Path path = mPath;
        float f3 = mScale;
        path.lineTo(f * f3, f3 * f2);
        elements.add(new PathElement(ElementType.kCGPathElementAddLineToPoint, new Point[]{new Point(f, f2)}));
    }

    private static void curve(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = mPenX;
        float f8 = mPenY;
        curveTo(f + f7, f2 + f8, f3 + f7, f4 + f8, f5 + f7, f6 + f8);
    }

    private static void curveTo(float f, float f2, float f3, float f4, float f5, float f6) {
        mPivotX = f3;
        mPivotY = f4;
        cubicTo(f, f2, f3, f4, f5, f6);
    }

    private static void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        setPenDown();
        mPenX = f5;
        mPenY = f6;
        Path path = mPath;
        float f7 = mScale;
        path.cubicTo(f * f7, f2 * f7, f3 * f7, f4 * f7, f5 * f7, f6 * f7);
        elements.add(new PathElement(ElementType.kCGPathElementAddCurveToPoint, new Point[]{new Point(f, f2), new Point(f3, f4), new Point(f5, f6)}));
    }

    private static void smoothCurve(float f, float f2, float f3, float f4) {
        float f5 = mPenX;
        float f6 = mPenY;
        smoothCurveTo(f + f5, f2 + f6, f3 + f5, f4 + f6);
    }

    private static void smoothCurveTo(float f, float f2, float f3, float f4) {
        float f5 = (mPenX * 2.0f) - mPivotX;
        float f6 = (mPenY * 2.0f) - mPivotY;
        mPivotX = f;
        mPivotY = f2;
        cubicTo(f5, f6, f, f2, f3, f4);
    }

    private static void quadraticBezierCurve(float f, float f2, float f3, float f4) {
        float f5 = mPenX;
        float f6 = mPenY;
        quadraticBezierCurveTo(f + f5, f2 + f6, f3 + f5, f4 + f6);
    }

    private static void quadraticBezierCurveTo(float f, float f2, float f3, float f4) {
        mPivotX = f;
        mPivotY = f2;
        float f5 = f * 2.0f;
        float f6 = f2 * 2.0f;
        cubicTo((mPenX + f5) / 3.0f, (mPenY + f6) / 3.0f, (f3 + f5) / 3.0f, (f4 + f6) / 3.0f, f3, f4);
    }

    private static void smoothQuadraticBezierCurve(float f, float f2) {
        smoothQuadraticBezierCurveTo(f + mPenX, f2 + mPenY);
    }

    private static void smoothQuadraticBezierCurveTo(float f, float f2) {
        quadraticBezierCurveTo((mPenX * 2.0f) - mPivotX, (mPenY * 2.0f) - mPivotY, f, f2);
    }

    private static void arc(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
        arcTo(f, f2, f3, z, z2, f4 + mPenX, f5 + mPenY);
    }

    private static void arcTo(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
        float f6;
        float f7;
        float f8;
        float f9 = mPenX;
        float f10 = mPenY;
        float fAbs = Math.abs(f2 == 0.0f ? f == 0.0f ? f5 - f10 : f : f2);
        float fAbs2 = Math.abs(f == 0.0f ? f4 - f9 : f);
        if (fAbs2 == 0.0f || fAbs == 0.0f || (f4 == f9 && f5 == f10)) {
            lineTo(f4, f5);
            return;
        }
        float radians = (float) Math.toRadians(f3);
        double d = radians;
        float fCos = (float) Math.cos(d);
        float fSin = (float) Math.sin(d);
        float f11 = f4 - f9;
        float f12 = f5 - f10;
        float f13 = ((fCos * f11) / 2.0f) + ((fSin * f12) / 2.0f);
        float f14 = -fSin;
        float f15 = ((f14 * f11) / 2.0f) + ((fCos * f12) / 2.0f);
        float f16 = fAbs2 * fAbs2;
        float f17 = f16 * fAbs * fAbs;
        float f18 = (f17 - ((f16 * f15) * f15)) - (((fAbs * fAbs) * f13) * f13);
        if (f18 < 0.0f) {
            float f19 = 1.0f - (f18 / f17);
            f6 = f14;
            float fSqrt = (float) Math.sqrt(f19);
            fAbs2 *= fSqrt;
            fAbs *= fSqrt;
            f7 = f11 / 2.0f;
            f8 = f12 / 2.0f;
        } else {
            f6 = f14;
            float fSqrt2 = (float) Math.sqrt(f18 / (r16 + r18));
            if (z == z2) {
                fSqrt2 = -fSqrt2;
            }
            float f20 = (((-fSqrt2) * f15) * fAbs2) / fAbs;
            float f21 = ((fSqrt2 * f13) * fAbs) / fAbs2;
            float f22 = ((fCos * f20) - (fSin * f21)) + (f11 / 2.0f);
            float f23 = (f12 / 2.0f) + (f20 * fSin) + (f21 * fCos);
            f7 = f22;
            f8 = f23;
        }
        float f24 = fCos / fAbs2;
        float f25 = fSin / fAbs2;
        float f26 = f6 / fAbs;
        float f27 = fCos / fAbs;
        float f28 = -f7;
        float f29 = -f8;
        float f30 = fAbs;
        float f31 = fAbs2;
        float fAtan2 = (float) Math.atan2((f26 * f28) + (f27 * f29), (f28 * f24) + (f29 * f25));
        float f32 = f11 - f7;
        float f33 = f12 - f8;
        float fAtan22 = (float) Math.atan2((f26 * f32) + (f27 * f33), (f24 * f32) + (f25 * f33));
        float f34 = f7 + f9;
        float f35 = f8 + f10;
        float f36 = f11 + f9;
        float f37 = f12 + f10;
        setPenDown();
        mPivotX = f36;
        mPenX = f36;
        mPivotY = f37;
        mPenY = f37;
        if (f31 != f30 || radians != 0.0f) {
            arcToBezier(f34, f35, f31, f30, fAtan2, fAtan22, z2, radians);
            return;
        }
        float degrees = (float) Math.toDegrees(fAtan2);
        float fAbs3 = Math.abs((degrees - ((float) Math.toDegrees(fAtan22))) % 360.0f);
        if (!z ? fAbs3 > 180.0f : fAbs3 < 180.0f) {
            fAbs3 = 360.0f - fAbs3;
        }
        if (!z2) {
            fAbs3 = -fAbs3;
        }
        float f38 = mScale;
        mPath.arcTo(new RectF((f34 - f31) * f38, (f35 - f31) * f38, (f34 + f31) * f38, (f35 + f31) * f38), degrees, fAbs3);
        elements.add(new PathElement(ElementType.kCGPathElementAddCurveToPoint, new Point[]{new Point(f36, f37)}));
    }

    private static void close() {
        if (mPenDown) {
            mPenX = mPenDownX;
            mPenY = mPenDownY;
            mPenDown = false;
            mPath.close();
            elements.add(new PathElement(ElementType.kCGPathElementCloseSubpath, new Point[]{new Point(mPenX, mPenY)}));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0067 A[LOOP:0: B:12:0x0065->B:13:0x0067, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void arcToBezier(float r24, float r25, float r26, float r27, float r28, float r29, boolean r30, float r31) {
        /*
            Method dump skipped, instruction units count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.PathParser.arcToBezier(float, float, float, float, float, float, boolean, float):void");
    }

    private static void setPenDown() {
        if (mPenDown) {
            return;
        }
        mPenDownX = mPenX;
        mPenDownY = mPenY;
        mPenDown = true;
    }

    private static double round(double d) {
        return Math.round(d * r0) / Math.pow(10.0d, 4.0d);
    }

    private static void skip_spaces() {
        while (true) {
            int i2 = i;
            if (i2 >= l || !Character.isWhitespace(s.charAt(i2))) {
                return;
            } else {
                i++;
            }
        }
    }

    private static boolean is_absolute(char c) {
        return Character.isUpperCase(c);
    }

    private static boolean parse_flag() {
        skip_spaces();
        char cCharAt = s.charAt(i);
        if (cCharAt == '0' || cCharAt == '1') {
            int i2 = i + 1;
            i = i2;
            if (i2 < l && s.charAt(i2) == ',') {
                i++;
            }
            skip_spaces();
            return cCharAt == '1';
        }
        throw new Error(String.format("Unexpected flag '%c' (i=%d, s=%s)", Character.valueOf(cCharAt), Integer.valueOf(i), s));
    }

    private static float parse_list_number() {
        if (i == l) {
            throw new Error(String.format("Unexpected end (s=%s)", s));
        }
        float f = parse_number();
        skip_spaces();
        parse_list_separator();
        return f;
    }

    private static float parse_number() {
        char cCharAt;
        skip_spaces();
        int i2 = i;
        if (i2 == l) {
            throw new Error(String.format("Unexpected end (s=%s)", s));
        }
        char cCharAt2 = s.charAt(i2);
        if (cCharAt2 == '-' || cCharAt2 == '+') {
            int i3 = i + 1;
            i = i3;
            cCharAt2 = s.charAt(i3);
        }
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            skip_digits();
            int i4 = i;
            if (i4 < l) {
                cCharAt2 = s.charAt(i4);
            }
        } else if (cCharAt2 != '.') {
            throw new Error(String.format("Invalid number formating character '%c' (i=%d, s=%s)", Character.valueOf(cCharAt2), Integer.valueOf(i), s));
        }
        if (cCharAt2 == '.') {
            i++;
            skip_digits();
            int i5 = i;
            if (i5 < l) {
                cCharAt2 = s.charAt(i5);
            }
        }
        if (cCharAt2 == 'e' || cCharAt2 == 'E') {
            int i6 = i;
            if (i6 + 1 < l && (cCharAt = s.charAt(i6 + 1)) != 'm' && cCharAt != 'x') {
                int i7 = i + 1;
                i = i7;
                char cCharAt3 = s.charAt(i7);
                if (cCharAt3 == '+' || cCharAt3 == '-') {
                    i++;
                    skip_digits();
                } else if (cCharAt3 >= '0' && cCharAt3 <= '9') {
                    skip_digits();
                } else {
                    throw new Error(String.format("Invalid number formating character '%c' (i=%d, s=%s)", Character.valueOf(cCharAt3), Integer.valueOf(i), s));
                }
            }
        }
        String strSubstring = s.substring(i2, i);
        float f = Float.parseFloat(strSubstring);
        if (Float.isInfinite(f) || Float.isNaN(f)) {
            throw new Error(String.format("Invalid number '%s' (start=%d, i=%d, s=%s)", strSubstring, Integer.valueOf(i2), Integer.valueOf(i), s));
        }
        return f;
    }

    private static void parse_list_separator() {
        int i2 = i;
        if (i2 >= l || s.charAt(i2) != ',') {
            return;
        }
        i++;
    }

    private static void skip_digits() {
        while (true) {
            int i2 = i;
            if (i2 >= l || !Character.isDigit(s.charAt(i2))) {
                return;
            } else {
                i++;
            }
        }
    }
}
