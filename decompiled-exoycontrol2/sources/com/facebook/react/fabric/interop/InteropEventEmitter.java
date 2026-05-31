package com.facebook.react.fabric.interop;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/* JADX INFO: loaded from: classes.dex */
public class InteropEventEmitter implements RCTEventEmitter {
    private EventDispatcher mEventDispatcherOverride;
    private final ReactContext mReactContext;

    public InteropEventEmitter(ReactContext reactContext) {
        this.mReactContext = reactContext;
    }

    @Override // com.facebook.react.uimanager.events.RCTEventEmitter
    public void receiveEvent(int i, String str, WritableMap writableMap) {
        EventDispatcher eventDispatcherForReactTag = this.mEventDispatcherOverride;
        if (eventDispatcherForReactTag == null) {
            eventDispatcherForReactTag = UIManagerHelper.getEventDispatcherForReactTag(this.mReactContext, i);
        }
        int surfaceId = UIManagerHelper.getSurfaceId(this.mReactContext);
        if (eventDispatcherForReactTag != null) {
            eventDispatcherForReactTag.dispatchEvent(new InteropEvent(str, writableMap, surfaceId, i));
        }
    }

    @Override // com.facebook.react.uimanager.events.RCTEventEmitter
    public void receiveTouches(String str, WritableArray writableArray, WritableArray writableArray2) {
        throw new UnsupportedOperationException("EventEmitter#receiveTouches is not supported by the Fabric Interop Layer");
    }

    void overrideEventDispatcher(EventDispatcher eventDispatcher) {
        this.mEventDispatcherOverride = eventDispatcher;
    }
}
