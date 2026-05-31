package com.thanosfisherman.wifiutils.wifiConnect;

/* JADX INFO: loaded from: classes2.dex */
public interface WifiConnectionCallback {
    void errorConnect(ConnectionErrorCode connectionErrorCode);

    void successfulConnect();
}
