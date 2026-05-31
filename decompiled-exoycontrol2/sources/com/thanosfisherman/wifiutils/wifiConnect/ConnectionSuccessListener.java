package com.thanosfisherman.wifiutils.wifiConnect;

/* JADX INFO: loaded from: classes2.dex */
public interface ConnectionSuccessListener {
    void failed(ConnectionErrorCode connectionErrorCode);

    void success();
}
