package com.balthazargronon.RCTZeroconf.nsd;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiManager;
import com.balthazargronon.RCTZeroconf.Zeroconf;
import com.balthazargronon.RCTZeroconf.ZeroconfModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NsdServiceImpl implements Zeroconf {
    private NsdManager.DiscoveryListener mDiscoveryListener;
    private NsdManager mNsdManager;
    private Map<String, NsdManager.RegistrationListener> mPublishedServices = new HashMap();
    private WifiManager.MulticastLock multicastLock;
    private ReactApplicationContext reactApplicationContext;
    private ZeroconfModule zeroconfModule;

    public NsdServiceImpl(ZeroconfModule zeroconfModule, ReactApplicationContext reactApplicationContext) {
        this.zeroconfModule = zeroconfModule;
        this.reactApplicationContext = reactApplicationContext;
    }

    @Override // com.balthazargronon.RCTZeroconf.Zeroconf
    public void scan(String str, String str2, String str3) {
        if (this.mNsdManager == null) {
            this.mNsdManager = (NsdManager) getReactApplicationContext().getSystemService("servicediscovery");
        }
        stop();
        if (this.multicastLock == null) {
            WifiManager.MulticastLock multicastLockCreateMulticastLock = ((WifiManager) getReactApplicationContext().getSystemService("wifi")).createMulticastLock("multicastLock");
            this.multicastLock = multicastLockCreateMulticastLock;
            multicastLockCreateMulticastLock.setReferenceCounted(true);
            this.multicastLock.acquire();
        }
        this.mDiscoveryListener = new NsdManager.DiscoveryListener() { // from class: com.balthazargronon.RCTZeroconf.nsd.NsdServiceImpl.1
            @Override // android.net.nsd.NsdManager.DiscoveryListener
            public void onStartDiscoveryFailed(String str4, int i) {
                NsdServiceImpl.this.zeroconfModule.sendEvent(NsdServiceImpl.this.getReactApplicationContext(), ZeroconfModule.EVENT_ERROR, "Starting service discovery failed with code: " + i);
            }

            @Override // android.net.nsd.NsdManager.DiscoveryListener
            public void onStopDiscoveryFailed(String str4, int i) {
                NsdServiceImpl.this.zeroconfModule.sendEvent(NsdServiceImpl.this.getReactApplicationContext(), ZeroconfModule.EVENT_ERROR, "Stopping service discovery failed with code: " + i);
            }

            @Override // android.net.nsd.NsdManager.DiscoveryListener
            public void onDiscoveryStarted(String str4) {
                System.out.println("On Discovery Started");
                NsdServiceImpl.this.zeroconfModule.sendEvent(NsdServiceImpl.this.getReactApplicationContext(), ZeroconfModule.EVENT_START, null);
            }

            @Override // android.net.nsd.NsdManager.DiscoveryListener
            public void onDiscoveryStopped(String str4) {
                System.out.println("On Discovery Stopped");
                NsdServiceImpl.this.zeroconfModule.sendEvent(NsdServiceImpl.this.getReactApplicationContext(), ZeroconfModule.EVENT_STOP, null);
            }

            @Override // android.net.nsd.NsdManager.DiscoveryListener
            public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
                System.out.println("On Service Found");
                WritableNativeMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("name", nsdServiceInfo.getServiceName());
                NsdServiceImpl.this.zeroconfModule.sendEvent(NsdServiceImpl.this.getReactApplicationContext(), ZeroconfModule.EVENT_FOUND, writableNativeMap);
                NsdServiceImpl.this.mNsdManager.resolveService(nsdServiceInfo, new ZeroResolveListener());
            }

            @Override // android.net.nsd.NsdManager.DiscoveryListener
            public void onServiceLost(NsdServiceInfo nsdServiceInfo) {
                System.out.println("On Service Lost");
                WritableNativeMap writableNativeMap = new WritableNativeMap();
                writableNativeMap.putString("name", nsdServiceInfo.getServiceName());
                NsdServiceImpl.this.zeroconfModule.sendEvent(NsdServiceImpl.this.getReactApplicationContext(), ZeroconfModule.EVENT_REMOVE, writableNativeMap);
            }
        };
        this.mNsdManager.discoverServices(String.format("_%s._%s.", str, str2), 1, this.mDiscoveryListener);
    }

    @Override // com.balthazargronon.RCTZeroconf.Zeroconf
    public void stop() {
        NsdManager.DiscoveryListener discoveryListener = this.mDiscoveryListener;
        if (discoveryListener != null) {
            this.mNsdManager.stopServiceDiscovery(discoveryListener);
        }
        WifiManager.MulticastLock multicastLock = this.multicastLock;
        if (multicastLock != null) {
            multicastLock.release();
        }
        this.mDiscoveryListener = null;
        this.multicastLock = null;
    }

    @Override // com.balthazargronon.RCTZeroconf.Zeroconf
    public void registerService(String str, String str2, String str3, String str4, int i, ReadableMap readableMap) {
        String str5 = String.format("_%s._%s.", str, str2);
        NsdManager nsdManager = getNsdManager();
        NsdServiceInfo nsdServiceInfo = new NsdServiceInfo();
        nsdServiceInfo.setServiceName(str4);
        nsdServiceInfo.setServiceType(str5);
        nsdServiceInfo.setPort(i);
        ReadableMapKeySetIterator readableMapKeySetIteratorKeySetIterator = readableMap.keySetIterator();
        while (readableMapKeySetIteratorKeySetIterator.hasNextKey()) {
            String strNextKey = readableMapKeySetIteratorKeySetIterator.nextKey();
            nsdServiceInfo.setAttribute(strNextKey, readableMap.getString(strNextKey));
        }
        nsdManager.registerService(nsdServiceInfo, 1, new ServiceRegistrationListener());
    }

    @Override // com.balthazargronon.RCTZeroconf.Zeroconf
    public void unregisterService(String str) {
        NsdManager nsdManager = getNsdManager();
        NsdManager.RegistrationListener registrationListener = this.mPublishedServices.get(str);
        if (registrationListener != null) {
            this.mPublishedServices.remove(str);
            nsdManager.unregisterService(registrationListener);
        }
    }

    private NsdManager getNsdManager() {
        if (this.mNsdManager == null) {
            this.mNsdManager = (NsdManager) getReactApplicationContext().getSystemService("servicediscovery");
        }
        return this.mNsdManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ReactApplicationContext getReactApplicationContext() {
        return this.reactApplicationContext;
    }

    private class ZeroResolveListener implements NsdManager.ResolveListener {
        private ZeroResolveListener() {
        }

        @Override // android.net.nsd.NsdManager.ResolveListener
        public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {
            if (i == 3) {
                NsdServiceImpl.this.mNsdManager.resolveService(nsdServiceInfo, this);
                return;
            }
            NsdServiceImpl.this.zeroconfModule.sendEvent(NsdServiceImpl.this.getReactApplicationContext(), ZeroconfModule.EVENT_ERROR, "Resolving service failed with code: " + i);
        }

        @Override // android.net.nsd.NsdManager.ResolveListener
        public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
            NsdServiceImpl.this.zeroconfModule.sendEvent(NsdServiceImpl.this.getReactApplicationContext(), ZeroconfModule.EVENT_RESOLVE, NsdServiceImpl.this.serviceInfoToMap(nsdServiceInfo));
        }
    }

    private class ServiceRegistrationListener implements NsdManager.RegistrationListener {
        @Override // android.net.nsd.NsdManager.RegistrationListener
        public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
        }

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
        }

        private ServiceRegistrationListener() {
        }

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {
            NsdServiceImpl.this.mPublishedServices.put(nsdServiceInfo.getServiceName(), this);
            NsdServiceImpl.this.zeroconfModule.sendEvent(NsdServiceImpl.this.getReactApplicationContext(), ZeroconfModule.EVENT_PUBLISHED, NsdServiceImpl.this.serviceInfoToMap(nsdServiceInfo));
        }

        @Override // android.net.nsd.NsdManager.RegistrationListener
        public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {
            NsdServiceImpl.this.zeroconfModule.sendEvent(NsdServiceImpl.this.getReactApplicationContext(), ZeroconfModule.EVENT_UNREGISTERED, NsdServiceImpl.this.serviceInfoToMap(nsdServiceInfo));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WritableMap serviceInfoToMap(NsdServiceInfo nsdServiceInfo) {
        String serviceName;
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putString("name", nsdServiceInfo.getServiceName());
        InetAddress host = nsdServiceInfo.getHost();
        if (host == null) {
            serviceName = nsdServiceInfo.getServiceName();
        } else {
            String str = host.getHostName() + nsdServiceInfo.getServiceType();
            writableNativeMap.putString(ZeroconfModule.KEY_SERVICE_HOST, host.getHostName());
            WritableNativeArray writableNativeArray = new WritableNativeArray();
            writableNativeArray.pushString(host.getHostAddress());
            writableNativeMap.putArray(ZeroconfModule.KEY_SERVICE_ADDRESSES, writableNativeArray);
            serviceName = str;
        }
        writableNativeMap.putString(ZeroconfModule.KEY_SERVICE_FULL_NAME, serviceName);
        writableNativeMap.putInt(ZeroconfModule.KEY_SERVICE_PORT, nsdServiceInfo.getPort());
        WritableNativeMap writableNativeMap2 = new WritableNativeMap();
        Map<String, byte[]> attributes = nsdServiceInfo.getAttributes();
        for (String str2 : attributes.keySet()) {
            try {
                byte[] bArr = attributes.get(str2);
                String str3 = String.format(Locale.getDefault(), "%s", str2);
                Locale locale = Locale.getDefault();
                Object[] objArr = new Object[1];
                objArr[0] = bArr != null ? new String(bArr, "UTF_8") : "";
                writableNativeMap2.putString(str3, String.format(locale, "%s", objArr));
            } catch (UnsupportedEncodingException e) {
                this.zeroconfModule.sendEvent(getReactApplicationContext(), ZeroconfModule.EVENT_ERROR, "Failed to encode txtRecord: " + e);
            }
        }
        writableNativeMap.putMap(ZeroconfModule.KEY_SERVICE_TXT, writableNativeMap2);
        return writableNativeMap;
    }
}
