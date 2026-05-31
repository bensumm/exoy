package com.thanosfisherman.wifiutils.wifiConnect;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import androidx.arch.core.util.Function;
import com.thanosfisherman.wifiutils.ConnectorUtils;
import com.thanosfisherman.wifiutils.WeakHandler;
import com.thanosfisherman.wifiutils.WifiUtils;
import com.thanosfisherman.wifiutils.utils.Elvis;
import com.thanosfisherman.wifiutils.utils.VersionUtils;

/* JADX INFO: loaded from: classes2.dex */
public class TimeoutHandler {
    private final WeakHandler mHandler;
    private ScanResult mScanResult;
    private final WifiConnectionCallback mWifiConnectionCallback;
    private final WifiManager mWifiManager;
    private final Runnable timeoutCallback = new AnonymousClass1();

    /* JADX INFO: renamed from: com.thanosfisherman.wifiutils.wifiConnect.TimeoutHandler$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            WifiUtils.wifiLog("Connection Timed out...");
            if (!VersionUtils.isAndroidQOrLater()) {
                ConnectorUtils.reEnableNetworkIfPossible(TimeoutHandler.this.mWifiManager, TimeoutHandler.this.mScanResult);
            }
            if (ConnectorUtils.isAlreadyConnected(TimeoutHandler.this.mWifiManager, (String) Elvis.of(TimeoutHandler.this.mScanResult).next(new Function() { // from class: com.thanosfisherman.wifiutils.wifiConnect.TimeoutHandler$1$$ExternalSyntheticLambda0
                @Override // androidx.arch.core.util.Function
                public final Object apply(Object obj) {
                    return ((ScanResult) obj).BSSID;
                }
            }).get())) {
                TimeoutHandler.this.mWifiConnectionCallback.successfulConnect();
            } else {
                TimeoutHandler.this.mWifiConnectionCallback.errorConnect(ConnectionErrorCode.TIMEOUT_OCCURRED);
            }
            TimeoutHandler.this.mHandler.removeCallbacks(this);
        }
    }

    public TimeoutHandler(WifiManager wifiManager, WeakHandler weakHandler, WifiConnectionCallback wifiConnectionCallback) {
        this.mWifiManager = wifiManager;
        this.mHandler = weakHandler;
        this.mWifiConnectionCallback = wifiConnectionCallback;
    }

    public void startTimeout(ScanResult scanResult, long j) {
        this.mHandler.removeCallbacks(this.timeoutCallback);
        this.mScanResult = scanResult;
        this.mHandler.postDelayed(this.timeoutCallback, j);
    }

    public void stopTimeout() {
        this.mHandler.removeCallbacks(this.timeoutCallback);
    }
}
