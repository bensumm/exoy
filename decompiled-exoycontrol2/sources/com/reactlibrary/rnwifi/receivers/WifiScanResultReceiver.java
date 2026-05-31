package com.reactlibrary.rnwifi.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import com.facebook.react.bridge.Promise;
import com.reactlibrary.rnwifi.mappers.WifiScanResultsMapper;

/* JADX INFO: loaded from: classes.dex */
public class WifiScanResultReceiver extends BroadcastReceiver {
    private final Promise promise;
    private final WifiManager wifiManager;

    public WifiScanResultReceiver(WifiManager wifiManager, Promise promise) {
        this.promise = promise;
        this.wifiManager = wifiManager;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        context.unregisterReceiver(this);
        try {
            this.promise.resolve(WifiScanResultsMapper.mapWifiScanResults(this.wifiManager.getScanResults()));
        } catch (Exception e) {
            this.promise.reject("exception", e.getMessage());
        }
    }
}
