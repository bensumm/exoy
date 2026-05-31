package com.tradle.react;

import android.net.wifi.WifiManager;
import android.util.SparseArray;
import com.balthazargronon.RCTZeroconf.ZeroconfModule;
import com.facebook.common.logging.FLog;
import com.facebook.common.util.UriUtil;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.tradle.react.UdpSocketClient;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public final class UdpSockets extends ReactContextBaseJavaModule implements UdpSocketClient.OnDataReceivedListener, UdpSocketClient.OnRuntimeExceptionListener {
    private static final int N_THREADS = 2;
    private static final String TAG = "UdpSockets";
    private final ExecutorService executorService;
    private final SparseArray<UdpSocketClient> mClients;
    private WifiManager.MulticastLock mMulticastLock;

    @Override // com.facebook.react.bridge.NativeModule
    @Nonnull
    public String getName() {
        return TAG;
    }

    public UdpSockets(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mClients = new SparseArray<>();
        this.executorService = Executors.newFixedThreadPool(2);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        this.executorService.execute(new Thread(new Runnable() { // from class: com.tradle.react.UdpSockets.1
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < UdpSockets.this.mClients.size(); i++) {
                    UdpSocketClient udpSocketClient = (UdpSocketClient) UdpSockets.this.mClients.valueAt(i);
                    udpSocketClient.close();
                    if (UdpSockets.this.mMulticastLock != null && UdpSockets.this.mMulticastLock.isHeld() && udpSocketClient.isMulticast()) {
                        UdpSockets.this.mMulticastLock.release();
                    }
                }
                UdpSockets.this.mClients.clear();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public UdpSocketClient findClient(Integer num, Callback callback) {
        UdpSocketClient udpSocketClient = this.mClients.get(num.intValue());
        if (udpSocketClient == null) {
            if (callback == null) {
                FLog.e(TAG, "missing callback parameter.");
            } else {
                callback.invoke(UdpErrorUtil.getError(UdpErrorCodes.clientNotFound.name(), "no client found with id " + num), null);
            }
        }
        return udpSocketClient;
    }

    @ReactMethod
    public void createSocket(Integer num, ReadableMap readableMap) {
        if (num == null) {
            FLog.e(TAG, "createSocket called with nil id parameter.");
        } else if (this.mClients.get(num.intValue()) != null) {
            FLog.e(TAG, "createSocket called twice with the same id.");
        } else {
            this.mClients.put(num.intValue(), new UdpSocketClient(this, this));
        }
    }

    @ReactMethod
    public void bind(final Integer num, final Integer num2, @Nullable final String str, @Nullable ReadableMap readableMap, final Callback callback) {
        this.executorService.execute(new Thread(new Runnable() { // from class: com.tradle.react.UdpSockets.2
            @Override // java.lang.Runnable
            public void run() {
                UdpSocketClient udpSocketClientFindClient = UdpSockets.this.findClient(num, callback);
                if (udpSocketClientFindClient == null) {
                    return;
                }
                try {
                    udpSocketClientFindClient.bind(num2, str);
                    WritableMap writableMapCreateMap = Arguments.createMap();
                    writableMapCreateMap.putString("address", str);
                    writableMapCreateMap.putInt(ZeroconfModule.KEY_SERVICE_PORT, num2.intValue());
                    callback.invoke(null, writableMapCreateMap);
                } catch (Exception e) {
                    callback.invoke(UdpErrorUtil.getError(UdpErrorCodes.socketAlreadyBoundError.name(), e.getMessage()));
                }
            }
        }));
    }

    @ReactMethod
    public void addMembership(final Integer num, final String str) {
        this.executorService.execute(new Thread(new Runnable() { // from class: com.tradle.react.UdpSockets.3
            @Override // java.lang.Runnable
            public void run() {
                UdpSocketClient udpSocketClientFindClient = UdpSockets.this.findClient(num, null);
                if (udpSocketClientFindClient == null) {
                    return;
                }
                if (UdpSockets.this.mMulticastLock == null) {
                    WifiManager wifiManager = (WifiManager) UdpSockets.this.getReactApplicationContext().getApplicationContext().getSystemService("wifi");
                    UdpSockets.this.mMulticastLock = wifiManager.createMulticastLock("react-native-udp");
                    UdpSockets.this.mMulticastLock.setReferenceCounted(true);
                }
                try {
                    UdpSockets.this.mMulticastLock.acquire();
                    udpSocketClientFindClient.addMembership(str);
                } catch (IOException e) {
                    if (UdpSockets.this.mMulticastLock != null && UdpSockets.this.mMulticastLock.isHeld()) {
                        UdpSockets.this.mMulticastLock.release();
                    }
                    FLog.e(UdpSockets.TAG, "addMembership", e);
                } catch (IllegalStateException e2) {
                    if (UdpSockets.this.mMulticastLock != null && UdpSockets.this.mMulticastLock.isHeld()) {
                        UdpSockets.this.mMulticastLock.release();
                    }
                    FLog.e(UdpSockets.TAG, "addMembership", e2);
                } catch (UnknownHostException e3) {
                    if (UdpSockets.this.mMulticastLock != null && UdpSockets.this.mMulticastLock.isHeld()) {
                        UdpSockets.this.mMulticastLock.release();
                    }
                    FLog.e(UdpSockets.TAG, "addMembership", e3);
                }
            }
        }));
    }

    @ReactMethod
    public void dropMembership(final Integer num, final String str) {
        this.executorService.execute(new Thread(new Runnable() { // from class: com.tradle.react.UdpSockets.4
            @Override // java.lang.Runnable
            public void run() {
                UdpSocketClient udpSocketClientFindClient = UdpSockets.this.findClient(num, null);
                if (udpSocketClientFindClient == null) {
                    return;
                }
                try {
                    try {
                        udpSocketClientFindClient.dropMembership(str);
                        if (UdpSockets.this.mMulticastLock == null || !UdpSockets.this.mMulticastLock.isHeld()) {
                            return;
                        }
                    } catch (IOException e) {
                        FLog.e(UdpSockets.TAG, "dropMembership", e);
                        if (UdpSockets.this.mMulticastLock == null || !UdpSockets.this.mMulticastLock.isHeld()) {
                            return;
                        }
                    }
                    UdpSockets.this.mMulticastLock.release();
                } catch (Throwable th) {
                    if (UdpSockets.this.mMulticastLock != null && UdpSockets.this.mMulticastLock.isHeld()) {
                        UdpSockets.this.mMulticastLock.release();
                    }
                    throw th;
                }
            }
        }));
    }

    @ReactMethod
    public void send(final Integer num, final String str, final Integer num2, final String str2, final Callback callback) {
        this.executorService.execute(new Thread(new Runnable() { // from class: com.tradle.react.UdpSockets.5
            @Override // java.lang.Runnable
            public void run() {
                UdpSocketClient udpSocketClientFindClient = UdpSockets.this.findClient(num, callback);
                if (udpSocketClientFindClient == null) {
                    return;
                }
                try {
                    udpSocketClientFindClient.send(str, num2, str2, callback);
                } catch (Exception e) {
                    callback.invoke(UdpErrorUtil.getError(UdpErrorCodes.sendError.name(), e.getMessage()));
                }
            }
        }));
    }

    @ReactMethod
    public void close(final Integer num, final Callback callback) {
        this.executorService.execute(new Thread(new Runnable() { // from class: com.tradle.react.UdpSockets.6
            @Override // java.lang.Runnable
            public void run() {
                UdpSocketClient udpSocketClientFindClient = UdpSockets.this.findClient(num, callback);
                if (udpSocketClientFindClient == null) {
                    return;
                }
                if (UdpSockets.this.mMulticastLock != null && UdpSockets.this.mMulticastLock.isHeld() && udpSocketClientFindClient.isMulticast()) {
                    UdpSockets.this.mMulticastLock.release();
                }
                udpSocketClientFindClient.close();
                callback.invoke(new Object[0]);
                UdpSockets.this.mClients.remove(num.intValue());
            }
        }));
    }

    @ReactMethod
    public void setBroadcast(final Integer num, final Boolean bool, final Callback callback) {
        this.executorService.execute(new Thread(new Runnable() { // from class: com.tradle.react.UdpSockets.7
            @Override // java.lang.Runnable
            public void run() {
                UdpSocketClient udpSocketClientFindClient = UdpSockets.this.findClient(num, callback);
                if (udpSocketClientFindClient == null) {
                    return;
                }
                try {
                    udpSocketClientFindClient.setBroadcast(bool.booleanValue());
                    callback.invoke(new Object[0]);
                } catch (SocketException e) {
                    callback.invoke(UdpErrorUtil.getError(UdpErrorCodes.setBroadcast.name(), e.getMessage()));
                }
            }
        }));
    }

    @Override // com.tradle.react.UdpSocketClient.OnDataReceivedListener
    public void didReceiveData(final UdpSocketClient udpSocketClient, final String str, final String str2, final int i) {
        final long jCurrentTimeMillis = System.currentTimeMillis();
        this.executorService.execute(new Thread(new Runnable() { // from class: com.tradle.react.UdpSockets.8
            @Override // java.lang.Runnable
            public void run() {
                int iKeyAt = -1;
                for (int i2 = 0; i2 < UdpSockets.this.mClients.size(); i2++) {
                    iKeyAt = UdpSockets.this.mClients.keyAt(i2);
                    if (udpSocketClient.equals(UdpSockets.this.mClients.get(iKeyAt))) {
                        break;
                    }
                }
                if (iKeyAt == -1) {
                    return;
                }
                WritableMap writableMapCreateMap = Arguments.createMap();
                writableMapCreateMap.putString(UriUtil.DATA_SCHEME, str);
                writableMapCreateMap.putString("address", str2);
                writableMapCreateMap.putInt(ZeroconfModule.KEY_SERVICE_PORT, i);
                writableMapCreateMap.putString("ts", Long.toString(jCurrentTimeMillis));
                ((DeviceEventManagerModule.RCTDeviceEventEmitter) UdpSockets.this.getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("udp-" + iKeyAt + "-data", writableMapCreateMap);
            }
        }));
    }

    @Override // com.tradle.react.UdpSocketClient.OnDataReceivedListener
    public void didReceiveError(UdpSocketClient udpSocketClient, String str) {
        FLog.e(TAG, str);
    }

    @Override // com.tradle.react.UdpSocketClient.OnRuntimeExceptionListener
    public void didReceiveException(RuntimeException runtimeException) {
        getReactApplicationContext().handleException(runtimeException);
    }
}
