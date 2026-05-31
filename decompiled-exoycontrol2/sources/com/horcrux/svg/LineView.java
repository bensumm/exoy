package com.horcrux.svg;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactContext;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class LineView extends RenderableView {
    private SVGLength mX1;
    private SVGLength mX2;
    private SVGLength mY1;
    private SVGLength mY2;

    public LineView(ReactContext reactContext) {
        super(reactContext);
    }

    public void setX1(Dynamic dynamic) {
        this.mX1 = SVGLength.from(dynamic);
        invalidate();
    }

    public void setX1(String str) {
        this.mX1 = SVGLength.from(str);
        invalidate();
    }

    public void setX1(Double d) {
        this.mX1 = SVGLength.from(d);
        invalidate();
    }

    public void setY1(Dynamic dynamic) {
        this.mY1 = SVGLength.from(dynamic);
        invalidate();
    }

    public void setY1(String str) {
        this.mY1 = SVGLength.from(str);
        invalidate();
    }

    public void setY1(Double d) {
        this.mY1 = SVGLength.from(d);
        invalidate();
    }

    public void setX2(Dynamic dynamic) {
        this.mX2 = SVGLength.from(dynamic);
        invalidate();
    }

    public void setX2(String str) {
        this.mX2 = SVGLength.from(str);
        invalidate();
    }

    public void setX2(Double d) {
        this.mX2 = SVGLength.from(d);
        invalidate();
    }

    public void setY2(Dynamic dynamic) {
        this.mY2 = SVGLength.from(dynamic);
        invalidate();
    }

    public void setY2(String str) {
        this.mY2 = SVGLength.from(str);
        invalidate();
    }

    public void setY2(Double d) {
        this.mY2 = SVGLength.from(d);
        invalidate();
    }

    @Override // com.horcrux.svg.RenderableView, com.horcrux.svg.VirtualView
    Path getPath(Canvas canvas, Paint paint) {
        Path path = new Path();
        double dRelativeOnWidth = relativeOnWidth(this.mX1);
        double dRelativeOnHeight = relativeOnHeight(this.mY1);
        double dRelativeOnWidth2 = relativeOnWidth(this.mX2);
        double dRelativeOnHeight2 = relativeOnHeight(this.mY2);
        path.moveTo((float) dRelativeOnWidth, (float) dRelativeOnHeight);
        path.lineTo((float) dRelativeOnWidth2, (float) dRelativeOnHeight2);
        this.elements = new ArrayList<>();
        this.elements.add(new PathElement(ElementType.kCGPathElementMoveToPoint, new Point[]{new Point(dRelativeOnWidth, dRelativeOnHeight)}));
        this.elements.add(new PathElement(ElementType.kCGPathElementAddLineToPoint, new Point[]{new Point(dRelativeOnWidth2, dRelativeOnHeight2)}));
        return path;
    }
}
