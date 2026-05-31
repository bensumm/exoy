package com.facebook.react.packagerconnection;

/* JADX INFO: loaded from: classes.dex */
public interface RequestHandler {
    void onNotification(Object obj);

    void onRequest(Object obj, Responder responder);
}
