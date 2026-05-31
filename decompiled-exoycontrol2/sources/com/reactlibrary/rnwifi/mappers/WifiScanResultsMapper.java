package com.reactlibrary.rnwifi.mappers;

import android.net.wifi.ScanResult;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WifiScanResultsMapper {
    private WifiScanResultsMapper() {
    }

    private static String parseSSID(ScanResult scanResult) {
        return scanResult.SSID.equals("") ? "(hidden SSID)" : scanResult.SSID;
    }

    public static WritableArray mapWifiScanResults(List<ScanResult> list) {
        WritableNativeArray writableNativeArray = new WritableNativeArray();
        for (ScanResult scanResult : list) {
            WritableNativeMap writableNativeMap = new WritableNativeMap();
            writableNativeMap.putString("SSID", parseSSID(scanResult));
            writableNativeMap.putString("BSSID", scanResult.BSSID);
            writableNativeMap.putString("capabilities", scanResult.capabilities);
            writableNativeMap.putInt("frequency", scanResult.frequency);
            writableNativeMap.putInt("level", scanResult.level);
            writableNativeMap.putDouble("timestamp", scanResult.timestamp);
            writableNativeArray.pushMap(writableNativeMap);
        }
        return writableNativeArray;
    }
}
