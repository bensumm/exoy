package com.facebook.react.uimanager.events;

import android.view.MotionEvent;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactSoftExceptionLogger;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.systrace.Systrace;

/* JADX INFO: loaded from: classes.dex */
public class TouchesHelper {
    public static final String CHANGED_TOUCHES_KEY = "changedTouches";
    private static final String LOCATION_X_KEY = "locationX";
    private static final String LOCATION_Y_KEY = "locationY";
    private static final String PAGE_X_KEY = "pageX";
    private static final String PAGE_Y_KEY = "pageY";
    private static final String POINTER_IDENTIFIER_KEY = "identifier";
    private static final String TAG = "TouchesHelper";
    public static final String TARGET_KEY = "target";
    public static final String TARGET_SURFACE_KEY = "targetSurface";
    private static final String TIMESTAMP_KEY = "timestamp";
    public static final String TOUCHES_KEY = "touches";

    private static WritableMap[] createPointersArray(TouchEvent touchEvent) {
        MotionEvent motionEvent = touchEvent.getMotionEvent();
        WritableMap[] writableMapArr = new WritableMap[motionEvent.getPointerCount()];
        float x = motionEvent.getX() - touchEvent.getViewX();
        float y = motionEvent.getY() - touchEvent.getViewY();
        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
            WritableMap writableMapCreateMap = Arguments.createMap();
            writableMapCreateMap.putDouble(PAGE_X_KEY, PixelUtil.toDIPFromPixel(motionEvent.getX(i)));
            writableMapCreateMap.putDouble(PAGE_Y_KEY, PixelUtil.toDIPFromPixel(motionEvent.getY(i)));
            float x2 = motionEvent.getX(i) - x;
            float y2 = motionEvent.getY(i) - y;
            writableMapCreateMap.putDouble(LOCATION_X_KEY, PixelUtil.toDIPFromPixel(x2));
            writableMapCreateMap.putDouble(LOCATION_Y_KEY, PixelUtil.toDIPFromPixel(y2));
            writableMapCreateMap.putInt(TARGET_SURFACE_KEY, touchEvent.getSurfaceId());
            writableMapCreateMap.putInt(TARGET_KEY, touchEvent.getViewTag());
            writableMapCreateMap.putDouble(TIMESTAMP_KEY, touchEvent.getTimestampMs());
            writableMapCreateMap.putDouble(POINTER_IDENTIFIER_KEY, motionEvent.getPointerId(i));
            writableMapArr[i] = writableMapCreateMap;
        }
        return writableMapArr;
    }

    public static void sendTouchesLegacy(RCTEventEmitter rCTEventEmitter, TouchEvent touchEvent) {
        TouchEventType touchEventType = touchEvent.getTouchEventType();
        WritableArray writableArray = getWritableArray(false, createPointersArray(touchEvent));
        MotionEvent motionEvent = touchEvent.getMotionEvent();
        WritableArray writableArrayCreateArray = Arguments.createArray();
        if (touchEventType == TouchEventType.MOVE || touchEventType == TouchEventType.CANCEL) {
            for (int i = 0; i < motionEvent.getPointerCount(); i++) {
                writableArrayCreateArray.pushInt(i);
            }
        } else if (touchEventType == TouchEventType.START || touchEventType == TouchEventType.END) {
            writableArrayCreateArray.pushInt(motionEvent.getActionIndex());
        } else {
            throw new RuntimeException("Unknown touch type: " + touchEventType);
        }
        rCTEventEmitter.receiveTouches(TouchEventType.getJSEventName(touchEventType), writableArray, writableArrayCreateArray);
    }

    public static void sendTouchEvent(RCTModernEventEmitter rCTModernEventEmitter, TouchEvent touchEvent) {
        WritableMap[] writableMapArr;
        WritableMap[] writableMapArr2;
        Systrace.beginSection(0L, "TouchesHelper.sentTouchEventModern(" + touchEvent.getEventName() + ")");
        TouchEventType touchEventType = touchEvent.getTouchEventType();
        MotionEvent motionEvent = touchEvent.getMotionEvent();
        if (motionEvent == null) {
            ReactSoftExceptionLogger.logSoftException(TAG, new IllegalStateException("Cannot dispatch a TouchEvent that has no MotionEvent; the TouchEvent has been recycled"));
            return;
        }
        WritableMap[] writableMapArrCreatePointersArray = createPointersArray(touchEvent);
        int i = AnonymousClass1.$SwitchMap$com$facebook$react$uimanager$events$TouchEventType[touchEventType.ordinal()];
        if (i != 1) {
            if (i == 2) {
                int actionIndex = motionEvent.getActionIndex();
                WritableMap writableMap = writableMapArrCreatePointersArray[actionIndex];
                writableMapArrCreatePointersArray[actionIndex] = null;
                writableMapArr2 = new WritableMap[]{writableMap};
            } else if (i == 3) {
                writableMapArr2 = new WritableMap[writableMapArrCreatePointersArray.length];
                for (int i2 = 0; i2 < writableMapArrCreatePointersArray.length; i2++) {
                    writableMapArr2[i2] = writableMapArrCreatePointersArray[i2].copy();
                }
            } else if (i != 4) {
                writableMapArr = writableMapArrCreatePointersArray;
                writableMapArrCreatePointersArray = null;
            } else {
                writableMapArr = new WritableMap[0];
            }
            writableMapArrCreatePointersArray = writableMapArr2;
            writableMapArr = writableMapArrCreatePointersArray;
        } else {
            writableMapArr = writableMapArrCreatePointersArray;
            writableMapArrCreatePointersArray = new WritableMap[]{writableMapArrCreatePointersArray[motionEvent.getActionIndex()].copy()};
        }
        for (WritableMap writableMap2 : writableMapArrCreatePointersArray) {
            WritableMap writableMapCopy = writableMap2.copy();
            WritableArray writableArray = getWritableArray(true, writableMapArrCreatePointersArray);
            WritableArray writableArray2 = getWritableArray(true, writableMapArr);
            writableMapCopy.putArray(CHANGED_TOUCHES_KEY, writableArray);
            writableMapCopy.putArray(TOUCHES_KEY, writableArray2);
            rCTModernEventEmitter.receiveEvent(touchEvent.getSurfaceId(), touchEvent.getViewTag(), touchEvent.getEventName(), touchEvent.canCoalesce(), 0, writableMapCopy, touchEvent.getEventCategory());
        }
        Systrace.endSection(0L);
    }

    /* JADX INFO: renamed from: com.facebook.react.uimanager.events.TouchesHelper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$uimanager$events$TouchEventType;

        static {
            int[] iArr = new int[TouchEventType.values().length];
            $SwitchMap$com$facebook$react$uimanager$events$TouchEventType = iArr;
            try {
                iArr[TouchEventType.START.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$TouchEventType[TouchEventType.END.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$TouchEventType[TouchEventType.MOVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$facebook$react$uimanager$events$TouchEventType[TouchEventType.CANCEL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static WritableArray getWritableArray(boolean z, WritableMap... writableMapArr) {
        WritableArray writableArrayCreateArray = Arguments.createArray();
        for (WritableMap writableMapCopy : writableMapArr) {
            if (writableMapCopy != null) {
                if (z) {
                    writableMapCopy = writableMapCopy.copy();
                }
                writableArrayCreateArray.pushMap(writableMapCopy);
            }
        }
        return writableArrayCreateArray;
    }
}
