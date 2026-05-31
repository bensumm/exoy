package com.reactlibrary.rnwifi;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.reactlibrary.rnwifi.errors.ConnectErrorCodes;
import com.reactlibrary.rnwifi.errors.DisconnectErrorCodes;
import com.reactlibrary.rnwifi.errors.ForceWifiUsageErrorCodes;
import com.reactlibrary.rnwifi.errors.GetCurrentWifiSSIDErrorCodes;
import com.reactlibrary.rnwifi.errors.IsEnabledErrorCodes;
import com.reactlibrary.rnwifi.errors.IsRemoveWifiNetworkErrorCodes;
import com.reactlibrary.rnwifi.errors.LoadWifiListErrorCodes;
import com.reactlibrary.rnwifi.mappers.WifiScanResultsMapper;
import com.reactlibrary.rnwifi.receivers.WifiScanResultReceiver;
import com.reactlibrary.rnwifi.utils.LocationUtils;
import com.reactlibrary.rnwifi.utils.PermissionUtils;
import com.thanosfisherman.wifiutils.WifiUtils;
import com.thanosfisherman.wifiutils.wifiConnect.DisconnectCallbackHolder;
import com.thanosfisherman.wifiutils.wifiDisconnect.DisconnectionErrorCode;
import com.thanosfisherman.wifiutils.wifiDisconnect.DisconnectionSuccessListener;
import com.thanosfisherman.wifiutils.wifiRemove.RemoveErrorCode;
import com.thanosfisherman.wifiutils.wifiRemove.RemoveSuccessListener;

/* JADX INFO: loaded from: classes.dex */
public class RNWifiModule extends ReactContextBaseJavaModule {
    private static String TAG = "RNWifiModule";
    private final ReactApplicationContext context;
    private final WifiManager wifi;

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "WifiManager";
    }

    RNWifiModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.wifi = (WifiManager) reactApplicationContext.getApplicationContext().getSystemService("wifi");
        this.context = reactApplicationContext;
    }

    @ReactMethod
    public void loadWifiList(Promise promise) {
        if (assertLocationPermissionGranted(promise)) {
            try {
                promise.resolve(WifiScanResultsMapper.mapWifiScanResults(this.wifi.getScanResults()));
            } catch (Exception e) {
                promise.reject(LoadWifiListErrorCodes.exception.toString(), e.getMessage());
            }
        }
    }

    @ReactMethod
    @Deprecated
    public void forceWifiUsage(boolean z, Promise promise) {
        forceWifiUsageWithOptions(z, null, promise);
    }

    @ReactMethod
    public void forceWifiUsageWithOptions(boolean z, ReadableMap readableMap, final Promise promise) {
        if (z) {
            try {
                boolean zCanWrite = Settings.System.canWrite(this.context);
                int iCheckCallingOrSelfPermission = this.context.checkCallingOrSelfPermission("android.permission.CHANGE_NETWORK_STATE");
                if (!zCanWrite && iCheckCallingOrSelfPermission != 0) {
                    Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
                    intent.setData(Uri.parse("package:" + this.context.getPackageName()));
                    intent.addFlags(268435456);
                    this.context.startActivity(intent);
                }
            } catch (Exception e) {
                promise.reject("", e.getMessage());
                return;
            }
        }
        final ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService("connectivity");
        if (connectivityManager == null) {
            promise.reject(ForceWifiUsageErrorCodes.couldNotGetConnectivityManager.toString(), "Failed to get the ConnectivityManager.");
            return;
        }
        if (z) {
            NetworkRequest.Builder builderAddTransportType = new NetworkRequest.Builder().addTransportType(1);
            if (readableMap != null && readableMap.getBoolean("noInternet")) {
                builderAddTransportType.removeCapability(12);
            }
            connectivityManager.requestNetwork(builderAddTransportType.build(), new ConnectivityManager.NetworkCallback() { // from class: com.reactlibrary.rnwifi.RNWifiModule.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    connectivityManager.bindProcessToNetwork(network);
                    connectivityManager.unregisterNetworkCallback(this);
                    promise.resolve(null);
                }
            });
            return;
        }
        connectivityManager.bindProcessToNetwork(null);
        promise.resolve(null);
    }

    @ReactMethod
    public void isEnabled(Promise promise) {
        WifiManager wifiManager = this.wifi;
        if (wifiManager == null) {
            promise.reject(IsEnabledErrorCodes.couldNotGetWifiManager.toString(), "Failed to initialize the WifiManager.");
        } else {
            promise.resolve(Boolean.valueOf(wifiManager.isWifiEnabled()));
        }
    }

    @ReactMethod
    public void setEnabled(boolean z) {
        if (isAndroidTenOrLater()) {
            openWifiSettings();
        } else {
            this.wifi.setWifiEnabled(z);
        }
    }

    @ReactMethod
    public void openWifiSettings() {
        Intent intent = new Intent("android.settings.panel.action.WIFI");
        intent.addFlags(268435456);
        this.context.startActivity(intent);
    }

    @ReactMethod
    public void connectToProtectedSSID(final String str, final String str2, boolean z, final boolean z2, final Promise promise) {
        if (assertLocationPermissionGranted(promise)) {
            if (!this.wifi.isWifiEnabled() && !this.wifi.setWifiEnabled(true)) {
                promise.reject(ConnectErrorCodes.couldNotEnableWifi.toString(), "On Android 10, the user has to enable wifi manually.");
            } else {
                removeWifiNetwork(str, promise, new Runnable() { // from class: com.reactlibrary.rnwifi.RNWifiModule$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m466xc426d56b(str, str2, z2, promise);
                    }
                });
            }
        }
    }

    @ReactMethod
    public void connectionStatus(Promise promise) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getReactApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            promise.resolve(false);
            return;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        if (networkInfo == null) {
            promise.resolve(false);
        } else {
            promise.resolve(Boolean.valueOf(networkInfo.isConnected()));
        }
    }

    @ReactMethod
    public void disconnect(final Promise promise) {
        WifiUtils.withContext(this.context).disconnect(new DisconnectionSuccessListener() { // from class: com.reactlibrary.rnwifi.RNWifiModule.2
            @Override // com.thanosfisherman.wifiutils.wifiDisconnect.DisconnectionSuccessListener
            public void success() {
                promise.resolve(true);
            }

            @Override // com.thanosfisherman.wifiutils.wifiDisconnect.DisconnectionSuccessListener
            public void failed(DisconnectionErrorCode disconnectionErrorCode) {
                int i = AnonymousClass5.$SwitchMap$com$thanosfisherman$wifiutils$wifiDisconnect$DisconnectionErrorCode[disconnectionErrorCode.ordinal()];
                if (i == 1) {
                    promise.reject(DisconnectErrorCodes.couldNotGetWifiManager.toString(), "Could not get WifiManager.");
                } else {
                    if (i == 2) {
                    }
                    promise.resolve(false);
                }
                promise.reject(DisconnectErrorCodes.couldNotGetConnectivityManager.toString(), "Could not get Connectivity Manager.");
                promise.resolve(false);
            }
        });
    }

    @ReactMethod
    public void getCurrentWifiSSID(Promise promise) {
        if (assertLocationPermissionGranted(promise)) {
            String wifiSSID = getWifiSSID();
            if (wifiSSID == null) {
                promise.reject(GetCurrentWifiSSIDErrorCodes.couldNotDetectSSID.toString(), "Not connected or connecting.");
            } else {
                promise.resolve(wifiSSID);
            }
        }
    }

    @ReactMethod
    public void getBSSID(Promise promise) {
        promise.resolve(this.wifi.getConnectionInfo().getBSSID().toUpperCase());
    }

    @ReactMethod
    public void getCurrentSignalStrength(Promise promise) {
        promise.resolve(Integer.valueOf(this.wifi.getConnectionInfo().getRssi()));
    }

    @ReactMethod
    public void getFrequency(Promise promise) {
        promise.resolve(Integer.valueOf(this.wifi.getConnectionInfo().getFrequency()));
    }

    @ReactMethod
    public void getIP(Promise promise) {
        promise.resolve(longToIP(this.wifi.getConnectionInfo().getIpAddress()));
    }

    @ReactMethod
    public void isRemoveWifiNetwork(String str, Promise promise) {
        removeWifiNetwork(str, promise, null);
    }

    private void removeWifiNetwork(String str, final Promise promise, final Runnable runnable) {
        if (!PermissionUtils.isLocationPermissionGranted(this.context)) {
            promise.reject(IsRemoveWifiNetworkErrorCodes.locationPermissionMissing.toString(), "Location permission (ACCESS_FINE_LOCATION) is not granted");
        } else {
            WifiUtils.withContext(this.context).remove(str, new RemoveSuccessListener() { // from class: com.reactlibrary.rnwifi.RNWifiModule.3
                @Override // com.thanosfisherman.wifiutils.wifiRemove.RemoveSuccessListener
                public void success() {
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    } else {
                        promise.resolve(true);
                    }
                }

                /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
                /* JADX WARN: Removed duplicated region for block: B:13:0x0031  */
                @Override // com.thanosfisherman.wifiutils.wifiRemove.RemoveSuccessListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void failed(com.thanosfisherman.wifiutils.wifiRemove.RemoveErrorCode r3) {
                    /*
                        r2 = this;
                        int[] r0 = com.reactlibrary.rnwifi.RNWifiModule.AnonymousClass5.$SwitchMap$com$thanosfisherman$wifiutils$wifiRemove$RemoveErrorCode
                        int r3 = r3.ordinal()
                        r3 = r0[r3]
                        r0 = 1
                        if (r3 == r0) goto Lf
                        r0 = 2
                        if (r3 == r0) goto L1c
                        goto L29
                    Lf:
                        com.facebook.react.bridge.Promise r3 = r3
                        com.reactlibrary.rnwifi.errors.IsRemoveWifiNetworkErrorCodes r0 = com.reactlibrary.rnwifi.errors.IsRemoveWifiNetworkErrorCodes.couldNotGetWifiManager
                        java.lang.String r0 = r0.toString()
                        java.lang.String r1 = "Could not get WifiManager."
                        r3.reject(r0, r1)
                    L1c:
                        com.facebook.react.bridge.Promise r3 = r3
                        com.reactlibrary.rnwifi.errors.IsRemoveWifiNetworkErrorCodes r0 = com.reactlibrary.rnwifi.errors.IsRemoveWifiNetworkErrorCodes.couldNotGetConnectivityManager
                        java.lang.String r0 = r0.toString()
                        java.lang.String r1 = "Could not get Connectivity Manager."
                        r3.reject(r0, r1)
                    L29:
                        java.lang.Runnable r3 = r2
                        if (r3 == 0) goto L31
                        r3.run()
                        return
                    L31:
                        com.facebook.react.bridge.Promise r3 = r3
                        r0 = 0
                        java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                        r3.resolve(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.reactlibrary.rnwifi.RNWifiModule.AnonymousClass3.failed(com.thanosfisherman.wifiutils.wifiRemove.RemoveErrorCode):void");
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.reactlibrary.rnwifi.RNWifiModule$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$thanosfisherman$wifiutils$wifiDisconnect$DisconnectionErrorCode;
        static final /* synthetic */ int[] $SwitchMap$com$thanosfisherman$wifiutils$wifiRemove$RemoveErrorCode;

        static {
            int[] iArr = new int[RemoveErrorCode.values().length];
            $SwitchMap$com$thanosfisherman$wifiutils$wifiRemove$RemoveErrorCode = iArr;
            try {
                iArr[RemoveErrorCode.COULD_NOT_GET_WIFI_MANAGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$thanosfisherman$wifiutils$wifiRemove$RemoveErrorCode[RemoveErrorCode.COULD_NOT_GET_CONNECTIVITY_MANAGER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$thanosfisherman$wifiutils$wifiRemove$RemoveErrorCode[RemoveErrorCode.COULD_NOT_REMOVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[DisconnectionErrorCode.values().length];
            $SwitchMap$com$thanosfisherman$wifiutils$wifiDisconnect$DisconnectionErrorCode = iArr2;
            try {
                iArr2[DisconnectionErrorCode.COULD_NOT_GET_WIFI_MANAGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$thanosfisherman$wifiutils$wifiDisconnect$DisconnectionErrorCode[DisconnectionErrorCode.COULD_NOT_GET_CONNECTIVITY_MANAGER.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$thanosfisherman$wifiutils$wifiDisconnect$DisconnectionErrorCode[DisconnectionErrorCode.COULD_NOT_DISCONNECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @ReactMethod
    public void reScanAndLoadWifiList(Promise promise) {
        if (assertLocationPermissionGranted(promise)) {
            boolean zStartScan = this.wifi.startScan();
            Log.d(TAG, "wifi start scan: " + zStartScan);
            if (zStartScan) {
                getReactApplicationContext().registerReceiver(new WifiScanResultReceiver(this.wifi, promise), new IntentFilter("android.net.wifi.SCAN_RESULTS"));
            } else {
                Log.d(TAG, "Wifi scan rejected");
                promise.resolve("Starting Android 9, it's only allowed to scan 4 times per 2 minuts in a foreground app.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: connectToWifiDirectly, reason: merged with bridge method [inline-methods] */
    public void m466xc426d56b(String str, String str2, boolean z, Promise promise) {
        if (isAndroidTenOrLater()) {
            connectAndroidQ(str, str2, z, promise);
        } else {
            connectPreAndroidQ(str, str2, promise);
        }
    }

    private void connectPreAndroidQ(String str, String str2, Promise promise) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = formatWithBackslashes(str);
        if (!isNullOrEmpty(str2)) {
            stuffWifiConfigurationWithWPA2(wifiConfiguration, str2);
        } else {
            stuffWifiConfigurationWithoutEncryption(wifiConfiguration);
        }
        int iAddNetwork = this.wifi.addNetwork(wifiConfiguration);
        if (iAddNetwork == -1) {
            promise.reject(ConnectErrorCodes.unableToConnect.toString(), String.format("Could not add or update network configuration with SSID %s", str));
            return;
        }
        if (!this.wifi.enableNetwork(iAddNetwork, true)) {
            promise.reject(ConnectErrorCodes.unableToConnect.toString(), String.format("Failed to enable network with %s", str));
            return;
        }
        if (!this.wifi.reconnect()) {
            promise.reject(ConnectErrorCodes.unableToConnect.toString(), String.format("Failed to reconnect with %s", str));
        } else if (!pollForValidSSID(10, str)) {
            promise.reject(ConnectErrorCodes.unableToConnect.toString(), String.format("Failed to connect with %s", str));
        } else {
            promise.resolve("connected");
        }
    }

    private void connectAndroidQ(final String str, String str2, boolean z, final Promise promise) {
        WifiNetworkSpecifier.Builder ssid = new WifiNetworkSpecifier.Builder().setIsHiddenSsid(z).setSsid(str);
        if (!isNullOrEmpty(str2)) {
            ssid.setWpa2Passphrase(str2);
        }
        NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addTransportType(1).setNetworkSpecifier(ssid.build()).addCapability(13).build();
        final ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService("connectivity");
        DisconnectCallbackHolder.getInstance().addNetworkCallback(new ConnectivityManager.NetworkCallback() { // from class: com.reactlibrary.rnwifi.RNWifiModule.4
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                super.onAvailable(network);
                DisconnectCallbackHolder.getInstance().bindProcessToNetwork(network);
                connectivityManager.setNetworkPreference(1);
                if (!RNWifiModule.this.pollForValidSSID(3, str)) {
                    promise.reject(ConnectErrorCodes.android10ImmediatelyDroppedConnection.toString(), "Firmware bugs on OnePlus prevent it from connecting on some firmware versions.");
                } else {
                    promise.resolve("connected");
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onUnavailable() {
                super.onUnavailable();
                promise.reject(ConnectErrorCodes.didNotFindNetwork.toString(), "Network not found or network request cannot be fulfilled.");
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                super.onLost(network);
                DisconnectCallbackHolder.getInstance().unbindProcessFromNetwork();
                DisconnectCallbackHolder.getInstance().disconnect();
            }
        }, connectivityManager);
        DisconnectCallbackHolder.getInstance().requestNetwork(networkRequestBuild);
    }

    private static String longToIP(int i) {
        StringBuilder sb = new StringBuilder();
        String[] strArr = {strValueOf, String.valueOf((65535 & i) >>> 8), String.valueOf((16777215 & i) >>> 16), String.valueOf(i >>> 24)};
        String strValueOf = String.valueOf(i & 255);
        sb.append(strValueOf);
        sb.append(".");
        sb.append(strArr[1]);
        sb.append(".");
        sb.append(strArr[2]);
        sb.append(".");
        sb.append(strArr[3]);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean pollForValidSSID(int i, String str) {
        for (int i2 = 0; i2 < i; i2++) {
            try {
                String wifiSSID = getWifiSSID();
                if (wifiSSID != null && wifiSSID.equalsIgnoreCase(str)) {
                    return true;
                }
                Thread.sleep(1000L);
            } catch (InterruptedException unused) {
            }
        }
        return false;
    }

    private String getWifiSSID() {
        String ssid = this.wifi.getConnectionInfo().getSSID();
        if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        if (ssid.equals("<unknown ssid>")) {
            return null;
        }
        return ssid;
    }

    private boolean isAndroidTenOrLater() {
        return Build.VERSION.SDK_INT >= 29;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private void stuffWifiConfigurationWithWPA2(WifiConfiguration wifiConfiguration, String str) {
        if (str.matches("[0-9A-Fa-f]{64}")) {
            wifiConfiguration.preSharedKey = str;
        } else {
            wifiConfiguration.preSharedKey = formatWithBackslashes(str);
        }
        wifiConfiguration.allowedProtocols.set(1);
        wifiConfiguration.allowedProtocols.set(0);
        wifiConfiguration.allowedKeyManagement.set(1);
        wifiConfiguration.status = 2;
        wifiConfiguration.allowedGroupCiphers.set(2);
        wifiConfiguration.allowedGroupCiphers.set(3);
        wifiConfiguration.allowedPairwiseCiphers.set(1);
        wifiConfiguration.allowedPairwiseCiphers.set(2);
    }

    private void stuffWifiConfigurationWithoutEncryption(WifiConfiguration wifiConfiguration) {
        wifiConfiguration.allowedKeyManagement.set(0);
    }

    private String formatWithBackslashes(String str) {
        return String.format("\"%s\"", str);
    }

    private boolean assertLocationPermissionGranted(Promise promise) {
        if (!PermissionUtils.isLocationPermissionGranted(this.context)) {
            promise.reject(ConnectErrorCodes.locationPermissionMissing.toString(), "Location permission (ACCESS_FINE_LOCATION) is not granted");
            return false;
        }
        if (LocationUtils.isLocationOn(this.context)) {
            return true;
        }
        promise.reject(ConnectErrorCodes.locationServicesOff.toString(), "Location service is turned off");
        return false;
    }
}
