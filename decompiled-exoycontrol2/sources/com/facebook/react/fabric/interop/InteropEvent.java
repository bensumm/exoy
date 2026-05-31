package com.facebook.react.fabric.interop;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;

/* JADX INFO: loaded from: classes.dex */
class InteropEvent extends Event<InteropEvent> {
    private final WritableMap mEventData;
    private final String mName;

    InteropEvent(String str, WritableMap writableMap, int i, int i2) {
        super(i, i2);
        this.mName = str;
        this.mEventData = writableMap;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return this.mName;
    }

    @Override // com.facebook.react.uimanager.events.Event
    public WritableMap getEventData() {
        return this.mEventData;
    }
}
