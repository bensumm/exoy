package com.thanosfisherman.wifiutils.wifiConnect;

import android.net.wifi.ScanResult;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface ConnectionScanResultsListener {
    ScanResult onConnectWithScanResult(List<ScanResult> list);
}
