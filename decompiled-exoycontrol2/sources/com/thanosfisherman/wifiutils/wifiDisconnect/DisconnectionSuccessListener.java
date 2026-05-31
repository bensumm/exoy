package com.thanosfisherman.wifiutils.wifiDisconnect;

/* JADX INFO: loaded from: classes2.dex */
public interface DisconnectionSuccessListener {
    void failed(DisconnectionErrorCode disconnectionErrorCode);

    void success();
}
