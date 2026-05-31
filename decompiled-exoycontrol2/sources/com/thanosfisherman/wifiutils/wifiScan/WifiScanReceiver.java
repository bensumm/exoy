package com.thanosfisherman.wifiutils.wifiScan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes2.dex */
public class WifiScanReceiver extends BroadcastReceiver {
    private final WifiScanCallback callback;

    public WifiScanReceiver(WifiScanCallback wifiScanCallback) {
        this.callback = wifiScanCallback;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        this.callback.onScanResultsReady();
    }
}
