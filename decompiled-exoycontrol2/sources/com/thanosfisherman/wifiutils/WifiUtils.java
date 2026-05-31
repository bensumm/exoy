package com.thanosfisherman.wifiutils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import androidx.core.util.Consumer;
import com.thanosfisherman.wifiutils.WifiConnectorBuilder;
import com.thanosfisherman.wifiutils.WifiUtils;
import com.thanosfisherman.wifiutils.utils.Elvis;
import com.thanosfisherman.wifiutils.utils.VersionUtils;
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionErrorCode;
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionScanResultsListener;
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionSuccessListener;
import com.thanosfisherman.wifiutils.wifiConnect.DisconnectCallbackHolder;
import com.thanosfisherman.wifiutils.wifiConnect.TimeoutHandler;
import com.thanosfisherman.wifiutils.wifiConnect.WifiConnectionCallback;
import com.thanosfisherman.wifiutils.wifiConnect.WifiConnectionReceiver;
import com.thanosfisherman.wifiutils.wifiDisconnect.DisconnectionErrorCode;
import com.thanosfisherman.wifiutils.wifiDisconnect.DisconnectionSuccessListener;
import com.thanosfisherman.wifiutils.wifiRemove.RemoveErrorCode;
import com.thanosfisherman.wifiutils.wifiRemove.RemoveSuccessListener;
import com.thanosfisherman.wifiutils.wifiScan.ScanResultsListener;
import com.thanosfisherman.wifiutils.wifiScan.WifiScanCallback;
import com.thanosfisherman.wifiutils.wifiScan.WifiScanReceiver;
import com.thanosfisherman.wifiutils.wifiState.WifiStateCallback;
import com.thanosfisherman.wifiutils.wifiState.WifiStateListener;
import com.thanosfisherman.wifiutils.wifiState.WifiStateReceiver;
import com.thanosfisherman.wifiutils.wifiWps.ConnectionWpsListener;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public final class WifiUtils implements WifiConnectorBuilder, WifiConnectorBuilder.WifiUtilsBuilder, WifiConnectorBuilder.WifiSuccessListener, WifiConnectorBuilder.WifiWpsSuccessListener {
    private static final String TAG = "WifiUtils";
    private static Logger customLogger = null;
    private static boolean mEnableLog = true;
    private String mBssid;
    private ConnectionScanResultsListener mConnectionScanResultsListener;
    private ConnectionSuccessListener mConnectionSuccessListener;
    private ConnectionWpsListener mConnectionWpsListener;
    private final ConnectivityManager mConnectivityManager;
    private final Context mContext;
    private WeakHandler mHandler;
    private String mPassword;
    private boolean mPatternMatch;
    private ScanResultsListener mScanResultsListener;
    private ScanResult mSingleScanResult;
    private String mSsid;
    private final TimeoutHandler mTimeoutHandler;
    private final WifiConnectionCallback mWifiConnectionCallback;
    private final WifiConnectionReceiver mWifiConnectionReceiver;
    private final WifiManager mWifiManager;
    private final WifiScanReceiver mWifiScanReceiver;
    private final WifiScanCallback mWifiScanResultsCallback;
    private final WifiStateCallback mWifiStateCallback;
    private WifiStateListener mWifiStateListener;
    private final WifiStateReceiver mWifiStateReceiver;
    private String type;
    private long mWpsTimeoutMillis = 30000;
    private long mTimeoutMillis = 30000;

    /* JADX INFO: renamed from: com.thanosfisherman.wifiutils.WifiUtils$1, reason: invalid class name */
    class AnonymousClass1 implements WifiStateCallback {
        AnonymousClass1() {
        }

        @Override // com.thanosfisherman.wifiutils.wifiState.WifiStateCallback
        public void onWifiEnabled() {
            WifiUtils.wifiLog("WIFI ENABLED...");
            ConnectorUtils.unregisterReceiver(WifiUtils.this.mContext, WifiUtils.this.mWifiStateReceiver);
            Elvis.of(WifiUtils.this.mWifiStateListener).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$1$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((WifiStateListener) obj).isSuccess(true);
                }
            });
            if (WifiUtils.this.mScanResultsListener == null && WifiUtils.this.mPassword == null) {
                return;
            }
            WifiUtils.wifiLog("START SCANNING....");
            if (WifiUtils.this.mWifiManager.startScan()) {
                ConnectorUtils.registerReceiver(WifiUtils.this.mContext, WifiUtils.this.mWifiScanReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
                return;
            }
            Elvis.of(WifiUtils.this.mScanResultsListener).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$1$$ExternalSyntheticLambda1
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((ScanResultsListener) obj).onScanResults(new ArrayList());
                }
            });
            Elvis.of(WifiUtils.this.mConnectionWpsListener).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$1$$ExternalSyntheticLambda2
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((ConnectionWpsListener) obj).isSuccessful(false);
                }
            });
            WifiUtils.this.mWifiConnectionCallback.errorConnect(ConnectionErrorCode.COULD_NOT_SCAN);
            WifiUtils.wifiLog("ERROR COULDN'T SCAN");
        }
    }

    /* JADX INFO: renamed from: com.thanosfisherman.wifiutils.WifiUtils$2, reason: invalid class name */
    class AnonymousClass2 implements WifiScanCallback {
        AnonymousClass2() {
        }

        @Override // com.thanosfisherman.wifiutils.wifiScan.WifiScanCallback
        public void onScanResultsReady() {
            WifiUtils.wifiLog("GOT SCAN RESULTS");
            ConnectorUtils.unregisterReceiver(WifiUtils.this.mContext, WifiUtils.this.mWifiScanReceiver);
            final List<ScanResult> scanResults = WifiUtils.this.mWifiManager.getScanResults();
            Elvis.of(WifiUtils.this.mScanResultsListener).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$2$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((ScanResultsListener) obj).onScanResults(scanResults);
                }
            });
            Elvis.of(WifiUtils.this.mConnectionScanResultsListener).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$2$$ExternalSyntheticLambda1
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.m520x558f0fbf(scanResults, (ConnectionScanResultsListener) obj);
                }
            });
            if (WifiUtils.this.mConnectionWpsListener == null || WifiUtils.this.mBssid == null || WifiUtils.this.mPassword == null) {
                if (WifiUtils.this.mSsid != null) {
                    if (WifiUtils.this.mBssid != null) {
                        WifiUtils wifiUtils = WifiUtils.this;
                        wifiUtils.mSingleScanResult = ConnectorUtils.matchScanResult(wifiUtils.mSsid, WifiUtils.this.mBssid, scanResults);
                    } else {
                        WifiUtils wifiUtils2 = WifiUtils.this;
                        wifiUtils2.mSingleScanResult = ConnectorUtils.matchScanResultSsid(wifiUtils2.mSsid, scanResults, WifiUtils.this.mPatternMatch);
                    }
                }
                if (WifiUtils.this.mSingleScanResult == null || WifiUtils.this.mPassword == null) {
                    if (ConnectorUtils.connectToWifiHidden(WifiUtils.this.mContext, WifiUtils.this.mWifiManager, WifiUtils.this.mConnectivityManager, WifiUtils.this.mHandler, WifiUtils.this.mSsid, WifiUtils.this.type, WifiUtils.this.mPassword, WifiUtils.this.mWifiConnectionCallback)) {
                        ConnectorUtils.registerReceiver(WifiUtils.this.mContext, WifiUtils.this.mWifiConnectionReceiver.connectWith(WifiUtils.this.mSsid, WifiUtils.this.mPassword, WifiUtils.this.mConnectivityManager), new IntentFilter("android.net.wifi.supplicant.STATE_CHANGE"));
                        ConnectorUtils.registerReceiver(WifiUtils.this.mContext, WifiUtils.this.mWifiConnectionReceiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
                        WifiUtils.this.mTimeoutHandler.startTimeout(WifiUtils.this.mSingleScanResult, WifiUtils.this.mTimeoutMillis);
                        return;
                    }
                    WifiUtils.this.mWifiConnectionCallback.errorConnect(ConnectionErrorCode.COULD_NOT_CONNECT);
                    return;
                }
                if (ConnectorUtils.connectToWifi(WifiUtils.this.mContext, WifiUtils.this.mWifiManager, WifiUtils.this.mConnectivityManager, WifiUtils.this.mHandler, WifiUtils.this.mSingleScanResult, WifiUtils.this.mPassword, WifiUtils.this.mWifiConnectionCallback, WifiUtils.this.mPatternMatch, WifiUtils.this.mSsid)) {
                    ConnectorUtils.registerReceiver(WifiUtils.this.mContext, WifiUtils.this.mWifiConnectionReceiver.connectWith(WifiUtils.this.mSingleScanResult, WifiUtils.this.mPassword, WifiUtils.this.mConnectivityManager), new IntentFilter("android.net.wifi.supplicant.STATE_CHANGE"));
                    ConnectorUtils.registerReceiver(WifiUtils.this.mContext, WifiUtils.this.mWifiConnectionReceiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
                    WifiUtils.this.mTimeoutHandler.startTimeout(WifiUtils.this.mSingleScanResult, WifiUtils.this.mTimeoutMillis);
                    return;
                }
                WifiUtils.this.mWifiConnectionCallback.errorConnect(ConnectionErrorCode.COULD_NOT_CONNECT);
                return;
            }
            WifiUtils wifiUtils3 = WifiUtils.this;
            wifiUtils3.mSingleScanResult = ConnectorUtils.matchScanResultBssid(wifiUtils3.mBssid, scanResults);
            if (WifiUtils.this.mSingleScanResult == null || !VersionUtils.isLollipopOrLater()) {
                if (WifiUtils.this.mSingleScanResult == null) {
                    WifiUtils.wifiLog("Couldn't find network. Possibly out of range");
                }
                WifiUtils.this.mConnectionWpsListener.isSuccessful(false);
                return;
            }
            ConnectorUtils.connectWps(WifiUtils.this.mWifiManager, WifiUtils.this.mHandler, WifiUtils.this.mSingleScanResult, WifiUtils.this.mPassword, WifiUtils.this.mWpsTimeoutMillis, WifiUtils.this.mConnectionWpsListener);
        }

        /* JADX INFO: renamed from: lambda$onScanResultsReady$1$com-thanosfisherman-wifiutils-WifiUtils$2, reason: not valid java name */
        /* synthetic */ void m520x558f0fbf(List list, ConnectionScanResultsListener connectionScanResultsListener) {
            WifiUtils.this.mSingleScanResult = connectionScanResultsListener.onConnectWithScanResult(list);
        }
    }

    /* JADX INFO: renamed from: com.thanosfisherman.wifiutils.WifiUtils$3, reason: invalid class name */
    class AnonymousClass3 implements WifiConnectionCallback {
        AnonymousClass3() {
        }

        @Override // com.thanosfisherman.wifiutils.wifiConnect.WifiConnectionCallback
        public void successfulConnect() {
            WifiUtils.wifiLog("CONNECTED SUCCESSFULLY");
            ConnectorUtils.unregisterReceiver(WifiUtils.this.mContext, WifiUtils.this.mWifiConnectionReceiver);
            WifiUtils.this.mTimeoutHandler.stopTimeout();
            Elvis.of(WifiUtils.this.mConnectionSuccessListener).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$3$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((ConnectionSuccessListener) obj).success();
                }
            });
        }

        @Override // com.thanosfisherman.wifiutils.wifiConnect.WifiConnectionCallback
        public void errorConnect(final ConnectionErrorCode connectionErrorCode) {
            ConnectorUtils.unregisterReceiver(WifiUtils.this.mContext, WifiUtils.this.mWifiConnectionReceiver);
            WifiUtils.this.mTimeoutHandler.stopTimeout();
            if (VersionUtils.isAndroidQOrLater()) {
                DisconnectCallbackHolder.getInstance().disconnect();
            }
            ConnectorUtils.reenableAllHotspots(WifiUtils.this.mWifiManager);
            Elvis.of(WifiUtils.this.mConnectionSuccessListener).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$3$$ExternalSyntheticLambda1
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    WifiUtils.AnonymousClass3.lambda$errorConnect$0(connectionErrorCode, (ConnectionSuccessListener) obj);
                }
            });
        }

        static /* synthetic */ void lambda$errorConnect$0(ConnectionErrorCode connectionErrorCode, ConnectionSuccessListener connectionSuccessListener) {
            connectionSuccessListener.failed(connectionErrorCode);
            WifiUtils.wifiLog("DIDN'T CONNECT TO WIFI " + connectionErrorCode);
        }
    }

    private WifiUtils(Context context) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mWifiStateCallback = anonymousClass1;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mWifiScanResultsCallback = anonymousClass2;
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mWifiConnectionCallback = anonymousClass3;
        this.mContext = context;
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        this.mWifiManager = wifiManager;
        if (wifiManager == null) {
            throw new RuntimeException("WifiManager is not supposed to be null");
        }
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mWifiStateReceiver = new WifiStateReceiver(anonymousClass1);
        this.mWifiScanReceiver = new WifiScanReceiver(anonymousClass2);
        this.mHandler = new WeakHandler();
        this.mWifiConnectionReceiver = new WifiConnectionReceiver(anonymousClass3, wifiManager);
        this.mTimeoutHandler = new TimeoutHandler(wifiManager, this.mHandler, anonymousClass3);
    }

    public static WifiConnectorBuilder.WifiUtilsBuilder withContext(Context context) {
        return new WifiUtils(context);
    }

    public static void wifiLog(String str) {
        if (mEnableLog) {
            ((Logger) Elvis.of(customLogger).orElse(new Logger() { // from class: com.thanosfisherman.wifiutils.WifiUtils$$ExternalSyntheticLambda0
                @Override // com.thanosfisherman.wifiutils.Logger
                public final void log(int i, String str2, String str3) {
                    Log.println(i, WifiUtils.TAG, str3);
                }
            })).log(2, TAG, str);
        }
    }

    public static void enableLog(boolean z) {
        mEnableLog = z;
    }

    public static void forwardLog(Logger logger) {
        customLogger = logger;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public void enableWifi(WifiStateListener wifiStateListener) {
        this.mWifiStateListener = wifiStateListener;
        if (this.mWifiManager.isWifiEnabled()) {
            this.mWifiStateCallback.onWifiEnabled();
            return;
        }
        if (this.mWifiManager.setWifiEnabled(true)) {
            ConnectorUtils.registerReceiver(this.mContext, this.mWifiStateReceiver, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
            return;
        }
        Elvis.of(wifiStateListener).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((WifiStateListener) obj).isSuccess(false);
            }
        });
        Elvis.of(this.mScanResultsListener).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$$ExternalSyntheticLambda2
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((ScanResultsListener) obj).onScanResults(new ArrayList());
            }
        });
        Elvis.of(this.mConnectionWpsListener).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$$ExternalSyntheticLambda3
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((ConnectionWpsListener) obj).isSuccessful(false);
            }
        });
        this.mWifiConnectionCallback.errorConnect(ConnectionErrorCode.COULD_NOT_ENABLE_WIFI);
        wifiLog("COULDN'T ENABLE WIFI");
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public void enableWifi() {
        enableWifi(null);
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public WifiConnectorBuilder scanWifi(ScanResultsListener scanResultsListener) {
        this.mScanResultsListener = scanResultsListener;
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    @Deprecated
    public void disconnectFrom(String str, DisconnectionSuccessListener disconnectionSuccessListener) {
        disconnect(disconnectionSuccessListener);
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public void disconnect(DisconnectionSuccessListener disconnectionSuccessListener) {
        if (this.mConnectivityManager == null) {
            disconnectionSuccessListener.failed(DisconnectionErrorCode.COULD_NOT_GET_CONNECTIVITY_MANAGER);
            return;
        }
        if (this.mWifiManager == null) {
            disconnectionSuccessListener.failed(DisconnectionErrorCode.COULD_NOT_GET_WIFI_MANAGER);
            return;
        }
        if (VersionUtils.isAndroidQOrLater()) {
            DisconnectCallbackHolder.getInstance().unbindProcessFromNetwork();
            DisconnectCallbackHolder.getInstance().disconnect();
            disconnectionSuccessListener.success();
        } else if (ConnectorUtils.disconnectFromWifi(this.mWifiManager)) {
            disconnectionSuccessListener.success();
        } else {
            disconnectionSuccessListener.failed(DisconnectionErrorCode.COULD_NOT_DISCONNECT);
        }
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public void remove(String str, RemoveSuccessListener removeSuccessListener) {
        if (this.mConnectivityManager == null) {
            removeSuccessListener.failed(RemoveErrorCode.COULD_NOT_GET_CONNECTIVITY_MANAGER);
            return;
        }
        if (this.mWifiManager == null) {
            removeSuccessListener.failed(RemoveErrorCode.COULD_NOT_GET_WIFI_MANAGER);
            return;
        }
        if (VersionUtils.isAndroidQOrLater()) {
            DisconnectCallbackHolder.getInstance().disconnect();
            removeSuccessListener.success();
        } else if (ConnectorUtils.removeWifi(this.mWifiManager, str)) {
            removeSuccessListener.success();
        } else {
            removeSuccessListener.failed(RemoveErrorCode.COULD_NOT_REMOVE);
        }
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public WifiConnectorBuilder.WifiUtilsBuilder patternMatch() {
        this.mPatternMatch = true;
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public WifiConnectorBuilder.WifiSuccessListener connectWith(String str) {
        this.mSsid = str;
        this.mPassword = "";
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public WifiConnectorBuilder.WifiSuccessListener connectWith(String str, String str2) {
        this.mSsid = str;
        this.mPassword = str2;
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public WifiConnectorBuilder.WifiSuccessListener connectWith(String str, String str2, TypeEnum typeEnum) {
        this.mSsid = str;
        this.mPassword = str2;
        this.type = typeEnum.name();
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public WifiConnectorBuilder.WifiSuccessListener connectWith(String str, String str2, String str3) {
        this.mSsid = str;
        this.mBssid = str2;
        this.mPassword = str3;
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public WifiConnectorBuilder.WifiSuccessListener connectWithScanResult(String str, ConnectionScanResultsListener connectionScanResultsListener) {
        this.mConnectionScanResultsListener = connectionScanResultsListener;
        this.mPassword = str;
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public WifiConnectorBuilder.WifiWpsSuccessListener connectWithWps(String str, String str2) {
        this.mBssid = str;
        this.mPassword = str2;
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public void cancelAutoConnect() {
        ConnectorUtils.unregisterReceiver(this.mContext, this.mWifiStateReceiver);
        ConnectorUtils.unregisterReceiver(this.mContext, this.mWifiScanReceiver);
        ConnectorUtils.unregisterReceiver(this.mContext, this.mWifiConnectionReceiver);
        Elvis.of(this.mSingleScanResult).ifPresent(new Consumer() { // from class: com.thanosfisherman.wifiutils.WifiUtils$$ExternalSyntheticLambda4
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f$0.m519x7592d252((ScanResult) obj);
            }
        });
        ConnectorUtils.reenableAllHotspots(this.mWifiManager);
    }

    /* JADX INFO: renamed from: lambda$cancelAutoConnect$4$com-thanosfisherman-wifiutils-WifiUtils, reason: not valid java name */
    /* synthetic */ void m519x7592d252(ScanResult scanResult) {
        ConnectorUtils.cleanPreviousConfiguration(this.mWifiManager, scanResult);
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public boolean isWifiConnected(String str) {
        return ConnectorUtils.isAlreadyConnected(this.mWifiManager, this.mConnectivityManager, str);
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public boolean isWifiConnected() {
        return ConnectorUtils.isAlreadyConnected(this.mConnectivityManager);
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiSuccessListener
    public WifiConnectorBuilder.WifiSuccessListener setTimeout(long j) {
        this.mTimeoutMillis = j;
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiWpsSuccessListener
    public WifiConnectorBuilder.WifiWpsSuccessListener setWpsTimeout(long j) {
        this.mWpsTimeoutMillis = j;
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiWpsSuccessListener
    public WifiConnectorBuilder onConnectionWpsResult(ConnectionWpsListener connectionWpsListener) {
        this.mConnectionWpsListener = connectionWpsListener;
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiSuccessListener
    public WifiConnectorBuilder onConnectionResult(ConnectionSuccessListener connectionSuccessListener) {
        this.mConnectionSuccessListener = connectionSuccessListener;
        return this;
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder
    public void start() {
        ConnectorUtils.unregisterReceiver(this.mContext, this.mWifiStateReceiver);
        ConnectorUtils.unregisterReceiver(this.mContext, this.mWifiScanReceiver);
        ConnectorUtils.unregisterReceiver(this.mContext, this.mWifiConnectionReceiver);
        enableWifi(null);
    }

    @Override // com.thanosfisherman.wifiutils.WifiConnectorBuilder.WifiUtilsBuilder
    public void disableWifi() {
        if (this.mWifiManager.isWifiEnabled()) {
            this.mWifiManager.setWifiEnabled(false);
            ConnectorUtils.unregisterReceiver(this.mContext, this.mWifiStateReceiver);
            ConnectorUtils.unregisterReceiver(this.mContext, this.mWifiScanReceiver);
            ConnectorUtils.unregisterReceiver(this.mContext, this.mWifiConnectionReceiver);
        }
        wifiLog("WiFi Disabled");
    }
}
