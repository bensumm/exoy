package com.facebook.react.views.drawer.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;

/* JADX INFO: loaded from: classes.dex */
public class DrawerSlideEvent extends Event<DrawerSlideEvent> {
    public static final String EVENT_NAME = "topDrawerSlide";
    private final float mOffset;

    @Override // com.facebook.react.uimanager.events.Event
    public String getEventName() {
        return EVENT_NAME;
    }

    @Deprecated
    public DrawerSlideEvent(int i, float f) {
        this(-1, i, f);
    }

    public DrawerSlideEvent(int i, int i2, float f) {
        super(i, i2);
        this.mOffset = f;
    }

    public float getOffset() {
        return this.mOffset;
    }

    @Override // com.facebook.react.uimanager.events.Event
    protected WritableMap getEventData() {
        WritableMap writableMapCreateMap = Arguments.createMap();
        writableMapCreateMap.putDouble("offset", getOffset());
        return writableMapCreateMap;
    }
}
