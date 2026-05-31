package com.balthazargronon.RCTZeroconf.rx2dnssd;

import android.net.wifi.WifiManager;
import android.util.Log;
import com.balthazargronon.RCTZeroconf.Zeroconf;
import com.balthazargronon.RCTZeroconf.ZeroconfModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.github.druk.rx2dnssd.BonjourService;
import com.github.druk.rx2dnssd.Rx2Dnssd;
import com.github.druk.rx2dnssd.Rx2DnssdBindable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class DnssdImpl implements Zeroconf {

    @Nullable
    private Disposable browseDisposable;
    private Map<String, BonjourService> mPublishedServices = new HashMap();
    private Map<String, Disposable> mRegisteredDisposables = new HashMap();
    private WifiManager.MulticastLock multicastLock;
    private ReactApplicationContext reactApplicationContext;
    private Rx2Dnssd rxDnssd;
    private ZeroconfModule zeroconfModule;

    public DnssdImpl(ZeroconfModule zeroconfModule, ReactApplicationContext reactApplicationContext) {
        this.zeroconfModule = zeroconfModule;
        this.reactApplicationContext = reactApplicationContext;
        this.rxDnssd = new Rx2DnssdBindable(reactApplicationContext);
    }

    @Override // com.balthazargronon.RCTZeroconf.Zeroconf
    public void scan(String str, String str2, String str3) {
        stop();
        if (this.multicastLock == null) {
            WifiManager.MulticastLock multicastLockCreateMulticastLock = ((WifiManager) this.reactApplicationContext.getSystemService("wifi")).createMulticastLock("multicastLock");
            this.multicastLock = multicastLockCreateMulticastLock;
            multicastLockCreateMulticastLock.setReferenceCounted(true);
            this.multicastLock.acquire();
        }
        this.browseDisposable = this.rxDnssd.browse(getServiceType(str, str2), "local.").compose(this.rxDnssd.resolve()).compose(this.rxDnssd.queryRecords()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.balthazargronon.RCTZeroconf.rx2dnssd.DnssdImpl$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m74lambda$scan$0$combalthazargrononRCTZeroconfrx2dnssdDnssdImpl((BonjourService) obj);
            }
        }, new Consumer() { // from class: com.balthazargronon.RCTZeroconf.rx2dnssd.DnssdImpl$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m75lambda$scan$1$combalthazargrononRCTZeroconfrx2dnssdDnssdImpl((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$scan$0$com-balthazargronon-RCTZeroconf-rx2dnssd-DnssdImpl, reason: not valid java name */
    /* synthetic */ void m74lambda$scan$0$combalthazargrononRCTZeroconfrx2dnssdDnssdImpl(BonjourService bonjourService) throws Exception {
        WritableMap writableMapServiceInfoToMap = serviceInfoToMap(bonjourService);
        Log.d(getClass().getName(), writableMapServiceInfoToMap.toString());
        this.zeroconfModule.sendEvent(this.reactApplicationContext, ZeroconfModule.EVENT_RESOLVE, writableMapServiceInfoToMap);
    }

    /* JADX INFO: renamed from: lambda$scan$1$com-balthazargronon-RCTZeroconf-rx2dnssd-DnssdImpl, reason: not valid java name */
    /* synthetic */ void m75lambda$scan$1$combalthazargrononRCTZeroconfrx2dnssdDnssdImpl(Throwable th) throws Exception {
        Log.e(getClass().getName(), "Error resolving service: ", th);
        this.zeroconfModule.sendEvent(this.reactApplicationContext, ZeroconfModule.EVENT_ERROR, th.getMessage());
    }

    private String getServiceType(String str, String str2) {
        return String.format("_%s._%s", str, str2);
    }

    private WritableMap serviceInfoToMap(BonjourService bonjourService) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putString("name", bonjourService.getServiceName());
        List<InetAddress> inetAddresses = bonjourService.getInetAddresses();
        Log.d("TAG", bonjourService.getServiceName());
        String serviceName = bonjourService.getServiceName();
        writableNativeMap.putString(ZeroconfModule.KEY_SERVICE_HOST, serviceName);
        WritableNativeArray writableNativeArray = new WritableNativeArray();
        Iterator<InetAddress> it = inetAddresses.iterator();
        while (it.hasNext()) {
            writableNativeArray.pushString(it.next().getHostAddress());
        }
        writableNativeMap.putArray(ZeroconfModule.KEY_SERVICE_ADDRESSES, writableNativeArray);
        writableNativeMap.putString(ZeroconfModule.KEY_SERVICE_FULL_NAME, serviceName);
        writableNativeMap.putInt(ZeroconfModule.KEY_SERVICE_PORT, bonjourService.getPort());
        WritableNativeMap writableNativeMap2 = new WritableNativeMap();
        Map<String, String> txtRecords = bonjourService.getTxtRecords();
        for (String str : txtRecords.keySet()) {
            String str2 = txtRecords.get(str);
            String str3 = String.format(Locale.getDefault(), "%s", str);
            Locale locale = Locale.getDefault();
            Object[] objArr = new Object[1];
            if (str2 == null) {
                str2 = "";
            }
            objArr[0] = str2;
            writableNativeMap2.putString(str3, String.format(locale, "%s", objArr));
        }
        writableNativeMap.putMap(ZeroconfModule.KEY_SERVICE_TXT, writableNativeMap2);
        return writableNativeMap;
    }

    @Override // com.balthazargronon.RCTZeroconf.Zeroconf
    public void stop() {
        Disposable disposable = this.browseDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.zeroconfModule.sendEvent(this.reactApplicationContext, ZeroconfModule.EVENT_STOP, null);
        }
        WifiManager.MulticastLock multicastLock = this.multicastLock;
        if (multicastLock != null) {
            multicastLock.release();
        }
        this.browseDisposable = null;
        this.multicastLock = null;
    }

    @Override // com.balthazargronon.RCTZeroconf.Zeroconf
    public void unregisterService(String str) {
        BonjourService bonjourService = this.mPublishedServices.get(str);
        if (bonjourService != null) {
            this.zeroconfModule.sendEvent(this.reactApplicationContext, ZeroconfModule.EVENT_UNREGISTERED, serviceInfoToMap(bonjourService));
            this.mPublishedServices.remove(str);
        }
        Disposable disposable = this.mRegisteredDisposables.get(str);
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        disposable.dispose();
        this.mRegisteredDisposables.remove(str);
    }

    @Override // com.balthazargronon.RCTZeroconf.Zeroconf
    public void registerService(String str, String str2, String str3, String str4, int i, ReadableMap readableMap) {
        final BonjourService bonjourServiceBuild = new BonjourService.Builder(0, 0, str4, getServiceType(str, str2), null).port(i).dnsRecords(getTxtRecordMap(readableMap)).build();
        this.mRegisteredDisposables.put(str4, this.rxDnssd.register(bonjourServiceBuild).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.balthazargronon.RCTZeroconf.rx2dnssd.DnssdImpl$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m73x9753ee9f(bonjourServiceBuild, (BonjourService) obj);
            }
        }, new Consumer() { // from class: com.balthazargronon.RCTZeroconf.rx2dnssd.DnssdImpl$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Log.e("TAG", "error", (Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$registerService$2$com-balthazargronon-RCTZeroconf-rx2dnssd-DnssdImpl, reason: not valid java name */
    /* synthetic */ void m73x9753ee9f(BonjourService bonjourService, BonjourService bonjourService2) throws Exception {
        Log.i("TAG", "Register successfully " + bonjourService2.toString());
        this.mPublishedServices.put(bonjourService.getServiceName(), bonjourService);
        this.zeroconfModule.sendEvent(this.reactApplicationContext, ZeroconfModule.EVENT_PUBLISHED, serviceInfoToMap(bonjourService2));
    }

    private Map<String, String> getTxtRecordMap(ReadableMap readableMap) {
        HashMap map = new HashMap();
        ReadableMapKeySetIterator readableMapKeySetIteratorKeySetIterator = readableMap.keySetIterator();
        while (readableMapKeySetIteratorKeySetIterator.hasNextKey()) {
            String strNextKey = readableMapKeySetIteratorKeySetIterator.nextKey();
            map.put(strNextKey, readableMap.getString(strNextKey));
        }
        return map;
    }
}
