package com.facebook.react.views.scroll;

import androidx.core.util.Pools;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactSoftExceptionLogger;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.TouchesHelper;

/* JADX INFO: loaded from: classes.dex */
public class ScrollEvent extends Event<ScrollEvent> {
    private static final Pools.SynchronizedPool<ScrollEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(3);
    private static String TAG = "ScrollEvent";
    private int mContentHeight;
    private int mContentWidth;
    private ScrollEventType mScrollEventType;
    private int mScrollViewHeight;
    private int mScrollViewWidth;
    private float mScrollX;
    private float mScrollY;
    private float mXVelocity;
    private float mYVelocity;

    @Deprecated
    public static ScrollEvent obtain(int i, ScrollEventType scrollEventType, float f, float f2, float f3, float f4, int i2, int i3, int i4, int i5) {
        return obtain(-1, i, scrollEventType, f, f2, f3, f4, i2, i3, i4, i5);
    }

    public static ScrollEvent obtain(int i, int i2, ScrollEventType scrollEventType, float f, float f2, float f3, float f4, int i3, int i4, int i5, int i6) {
        ScrollEvent scrollEventAcquire = EVENTS_POOL.acquire();
        if (scrollEventAcquire == null) {
            scrollEventAcquire = new ScrollEvent();
        }
        scrollEventAcquire.init(i, i2, scrollEventType, f, f2, f3, f4, i3, i4, i5, i6);
        return scrollEventAcquire;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public void onDispose() {
        try {
            EVENTS_POOL.release(this);
        } catch (IllegalStateException e) {
            ReactSoftExceptionLogger.logSoftException(TAG, e);
        }
    }

    private ScrollEvent() {
    }

    private void init(int i, int i2, ScrollEventType scrollEventType, float f, float f2, float f3, float f4, int i3, int i4, int i5, int i6) {
        super.init(i, i2);
        this.mScrollEventType = scrollEventType;
        this.mScrollX = f;
        this.mScrollY = f2;
        this.mXVelocity = f3;
        this.mYVelocity = f4;
        this.mContentWidth = i3;
        this.mContentHeight = i4;
        this.mScrollViewWidth = i5;
        this.mScrollViewHeight = i6;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return ScrollEventType.getJSEventName((ScrollEventType) Assertions.assertNotNull(this.mScrollEventType));
    }

    @Override // com.facebook.react.uimanager.events.Event
    public boolean canCoalesce() {
        return this.mScrollEventType == ScrollEventType.SCROLL;
    }

    @Override // com.facebook.react.uimanager.events.Event
    protected WritableMap getEventData() {
        WritableMap writableMapCreateMap = Arguments.createMap();
        writableMapCreateMap.putDouble(ViewProps.TOP, 0.0d);
        writableMapCreateMap.putDouble(ViewProps.BOTTOM, 0.0d);
        writableMapCreateMap.putDouble(ViewProps.LEFT, 0.0d);
        writableMapCreateMap.putDouble(ViewProps.RIGHT, 0.0d);
        WritableMap writableMapCreateMap2 = Arguments.createMap();
        writableMapCreateMap2.putDouble("x", PixelUtil.toDIPFromPixel(this.mScrollX));
        writableMapCreateMap2.putDouble("y", PixelUtil.toDIPFromPixel(this.mScrollY));
        WritableMap writableMapCreateMap3 = Arguments.createMap();
        writableMapCreateMap3.putDouble("width", PixelUtil.toDIPFromPixel(this.mContentWidth));
        writableMapCreateMap3.putDouble("height", PixelUtil.toDIPFromPixel(this.mContentHeight));
        WritableMap writableMapCreateMap4 = Arguments.createMap();
        writableMapCreateMap4.putDouble("width", PixelUtil.toDIPFromPixel(this.mScrollViewWidth));
        writableMapCreateMap4.putDouble("height", PixelUtil.toDIPFromPixel(this.mScrollViewHeight));
        WritableMap writableMapCreateMap5 = Arguments.createMap();
        writableMapCreateMap5.putDouble("x", this.mXVelocity);
        writableMapCreateMap5.putDouble("y", this.mYVelocity);
        WritableMap writableMapCreateMap6 = Arguments.createMap();
        writableMapCreateMap6.putMap("contentInset", writableMapCreateMap);
        writableMapCreateMap6.putMap("contentOffset", writableMapCreateMap2);
        writableMapCreateMap6.putMap("contentSize", writableMapCreateMap3);
        writableMapCreateMap6.putMap("layoutMeasurement", writableMapCreateMap4);
        writableMapCreateMap6.putMap("velocity", writableMapCreateMap5);
        writableMapCreateMap6.putInt(TouchesHelper.TARGET_KEY, getViewTag());
        writableMapCreateMap6.putBoolean("responderIgnoreScroll", true);
        return writableMapCreateMap6;
    }
}
