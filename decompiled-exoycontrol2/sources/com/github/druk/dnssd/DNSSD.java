package com.github.druk.dnssd;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.balthazargronon.RCTZeroconf.ZeroConfImplFactory;
import com.github.druk.dnssd.DNSSD;
import com.github.druk.dnssd.InternalDNSSDService;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.CharEncoding;

/* JADX INFO: loaded from: classes.dex */
public abstract class DNSSD implements InternalDNSSDService.DnssdServiceListener {
    public static final int ALL_INTERFACES = 0;
    public static final int BROWSE_DOMAINS = 64;
    public static final int DEFAULT = 4;
    public static final int DNSSD_DEFAULT_TIMEOUT = 60000;
    public static final int LOCALHOST_ONLY = -1;
    public static final int MAX_DOMAIN_NAME = 1009;
    public static final int MORE_COMING = 1;
    private static final String MULTICAST_LOCK_NAME = "com.github.druk.dnssd.DNSSD";
    public static final int NO_AUTO_RENAME = 8;
    public static final int REGISTRATION_DOMAINS = 128;
    public static final int SHARED = 16;
    public static final int UNIQUE = 32;
    private static final Charset UTF_8 = Charset.forName(CharEncoding.UTF_8);
    private final Context context;
    private final Handler handler;
    private volatile WifiManager.MulticastLock multicastLock;
    private final int serviceTimeout;

    DNSSD(Context context, String lib) {
        this(context, lib, Looper.getMainLooper());
    }

    DNSSD(Context context, String lib, Looper looper) {
        this.multicastLock = null;
        this.context = context.getApplicationContext();
        InternalDNSSD.init(lib);
        this.handler = new Handler(looper);
        this.serviceTimeout = DNSSD_DEFAULT_TIMEOUT;
    }

    DNSSD(Context context, String lib, Handler handler) {
        this.multicastLock = null;
        this.context = context.getApplicationContext();
        InternalDNSSD.init(lib);
        this.handler = handler;
        this.serviceTimeout = DNSSD_DEFAULT_TIMEOUT;
    }

    DNSSD(Context context, String lib, Handler handler, int serviceTimeout) {
        this.multicastLock = null;
        this.context = context.getApplicationContext();
        InternalDNSSD.init(lib);
        this.handler = handler;
        this.serviceTimeout = serviceTimeout;
    }

    public DNSSDService browse(int flags, int ifIndex, String regType, String domain, final BrowseListener listener) throws DNSSDException {
        onServiceStarting();
        InternalDNSSDService internalDNSSDService = new InternalDNSSDService(this, InternalDNSSD.browse(flags, ifIndex, regType, domain, new AnonymousClass1(listener, internalDNSSDServiceArr)));
        InternalDNSSDService[] internalDNSSDServiceArr = {internalDNSSDService};
        return internalDNSSDService;
    }

    /* JADX INFO: renamed from: com.github.druk.dnssd.DNSSD$1, reason: invalid class name */
    class AnonymousClass1 implements InternalBrowseListener {
        final /* synthetic */ BrowseListener val$listener;
        final /* synthetic */ InternalDNSSDService[] val$services;

        AnonymousClass1(final BrowseListener val$services, final InternalDNSSDService[] val$listener) {
            this.val$listener = val$services;
            this.val$services = val$listener;
        }

        @Override // com.github.druk.dnssd.InternalBrowseListener
        public void serviceFound(final DNSSDService browser, final int flags, final int ifIndex, final byte[] serviceName, final byte[] regType, final byte[] domain) {
            final String str = new String(serviceName, DNSSD.UTF_8);
            final String str2 = new String(regType, DNSSD.UTF_8);
            final String str3 = new String(domain, DNSSD.UTF_8);
            Handler handler = DNSSD.this.handler;
            final BrowseListener browseListener = this.val$listener;
            final InternalDNSSDService[] internalDNSSDServiceArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    browseListener.serviceFound(internalDNSSDServiceArr[0], flags, ifIndex, str, str2, str3);
                }
            });
        }

        @Override // com.github.druk.dnssd.InternalBrowseListener
        public void serviceLost(DNSSDService browser, final int flags, final int ifIndex, byte[] serviceName, byte[] regType, byte[] domain) {
            final String str = new String(serviceName, DNSSD.UTF_8);
            final String str2 = new String(regType, DNSSD.UTF_8);
            final String str3 = new String(domain, DNSSD.UTF_8);
            Handler handler = DNSSD.this.handler;
            final BrowseListener browseListener = this.val$listener;
            final InternalDNSSDService[] internalDNSSDServiceArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    browseListener.serviceLost(internalDNSSDServiceArr[0], flags, ifIndex, str, str2, str3);
                }
            });
        }

        @Override // com.github.druk.dnssd.BaseListener
        public void operationFailed(final DNSSDService service, final int errorCode) {
            Handler handler = DNSSD.this.handler;
            final BrowseListener browseListener = this.val$listener;
            final InternalDNSSDService[] internalDNSSDServiceArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    browseListener.operationFailed(internalDNSSDServiceArr[0], errorCode);
                }
            });
        }
    }

    public DNSSDService browse(String regType, BrowseListener listener) throws DNSSDException {
        return browse(0, 0, regType, "", listener);
    }

    public DNSSDService resolve(int flags, int ifIndex, String serviceName, String regType, String domain, final ResolveListener listener) throws DNSSDException {
        onServiceStarting();
        final DNSSDService[] dNSSDServiceArr = new DNSSDService[1];
        Runnable runnable = new Runnable() { // from class: com.github.druk.dnssd.DNSSD$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                dNSSDServiceArr[0].stop();
            }
        };
        dNSSDServiceArr[0] = new InternalDNSSDService(this, InternalDNSSD.resolve(flags, ifIndex, serviceName, regType, domain, new AnonymousClass2(runnable, listener, dNSSDServiceArr)));
        this.handler.postDelayed(runnable, this.serviceTimeout);
        return dNSSDServiceArr[0];
    }

    /* JADX INFO: renamed from: com.github.druk.dnssd.DNSSD$2, reason: invalid class name */
    class AnonymousClass2 implements InternalResolveListener {
        final /* synthetic */ ResolveListener val$listener;
        final /* synthetic */ DNSSDService[] val$services;
        final /* synthetic */ Runnable val$timeoutRunnable;

        AnonymousClass2(final Runnable val$services, final ResolveListener val$listener, final DNSSDService[] val$timeoutRunnable) {
            this.val$timeoutRunnable = val$services;
            this.val$listener = val$listener;
            this.val$services = val$timeoutRunnable;
        }

        @Override // com.github.druk.dnssd.InternalResolveListener
        public void serviceResolved(final DNSSDService resolver, final int flags, final int ifIndex, byte[] fullName, byte[] hostName, final int port, TXTRecord txtRecord) {
            final String str = new String(fullName, DNSSD.UTF_8);
            final String str2 = new String(hostName, DNSSD.UTF_8);
            final Map<String, String> tXTRecords = DNSSD.parseTXTRecords(txtRecord);
            DNSSD.this.handler.removeCallbacks(this.val$timeoutRunnable);
            Handler handler = DNSSD.this.handler;
            final ResolveListener resolveListener = this.val$listener;
            final DNSSDService[] dNSSDServiceArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DNSSD.AnonymousClass2.lambda$serviceResolved$0(resolveListener, dNSSDServiceArr, flags, ifIndex, str, str2, port, tXTRecords);
                }
            });
        }

        static /* synthetic */ void lambda$serviceResolved$0(final ResolveListener listener, final DNSSDService[] services, final int flags, final int ifIndex, final String fullNameStr, final String hostNameStr, final int port, final Map record) {
            listener.serviceResolved(services[0], flags, ifIndex, fullNameStr, hostNameStr, port, record);
            services[0].stop();
        }

        @Override // com.github.druk.dnssd.BaseListener
        public void operationFailed(final DNSSDService service, final int errorCode) {
            DNSSD.this.handler.removeCallbacks(this.val$timeoutRunnable);
            Handler handler = DNSSD.this.handler;
            final ResolveListener resolveListener = this.val$listener;
            final DNSSDService[] dNSSDServiceArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DNSSD.AnonymousClass2.lambda$operationFailed$1(resolveListener, dNSSDServiceArr, errorCode);
                }
            });
        }

        static /* synthetic */ void lambda$operationFailed$1(final ResolveListener listener, final DNSSDService[] services, final int errorCode) {
            listener.operationFailed(services[0], errorCode);
            services[0].stop();
        }
    }

    public DNSSDRegistration register(int flags, int ifIndex, String serviceName, String regType, String domain, String host, int port, TXTRecord txtRecord, final RegisterListener listener) throws DNSSDException {
        onServiceStarting();
        InternalDNSSDRegistration internalDNSSDRegistration = new InternalDNSSDRegistration(this, InternalDNSSD.register(flags, ifIndex, serviceName, regType, domain, host, port, txtRecord, new AnonymousClass3(listener, dNSSDRegistrationArr)));
        DNSSDRegistration[] dNSSDRegistrationArr = {internalDNSSDRegistration};
        return internalDNSSDRegistration;
    }

    /* JADX INFO: renamed from: com.github.druk.dnssd.DNSSD$3, reason: invalid class name */
    class AnonymousClass3 implements InternalRegisterListener {
        final /* synthetic */ RegisterListener val$listener;
        final /* synthetic */ DNSSDRegistration[] val$services;

        AnonymousClass3(final RegisterListener val$services, final DNSSDRegistration[] val$listener) {
            this.val$listener = val$services;
            this.val$services = val$listener;
        }

        @Override // com.github.druk.dnssd.InternalRegisterListener
        public void serviceRegistered(DNSSDRegistration registration, final int flags, final byte[] serviceName, byte[] regType, final byte[] domain) {
            final String str = new String(serviceName, DNSSD.UTF_8);
            final String str2 = new String(regType, DNSSD.UTF_8);
            final String str3 = new String(domain, DNSSD.UTF_8);
            Handler handler = DNSSD.this.handler;
            final RegisterListener registerListener = this.val$listener;
            final DNSSDRegistration[] dNSSDRegistrationArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$3$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    registerListener.serviceRegistered(dNSSDRegistrationArr[0], flags, str, str2, str3);
                }
            });
        }

        @Override // com.github.druk.dnssd.BaseListener
        public void operationFailed(DNSSDService service, final int errorCode) {
            Handler handler = DNSSD.this.handler;
            final RegisterListener registerListener = this.val$listener;
            final DNSSDRegistration[] dNSSDRegistrationArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    registerListener.operationFailed(dNSSDRegistrationArr[0], errorCode);
                }
            });
        }
    }

    public DNSSDService register(String serviceName, String regType, int port, RegisterListener listener) throws DNSSDException {
        return register(0, 0, serviceName, regType, null, null, port, null, listener);
    }

    public DNSSDRecordRegistrar createRecordRegistrar(RegisterRecordListener listener) throws DNSSDException {
        onServiceStarting();
        return new InternalDNSSDRecordRegistrar(this, InternalDNSSD.createRecordRegistrar(listener));
    }

    public DNSSDService queryRecord(int flags, int ifIndex, final String serviceName, int rrtype, int rrclass, final QueryListener listener) throws DNSSDException {
        return queryRecord(flags, ifIndex, serviceName, rrtype, rrclass, false, listener);
    }

    public DNSSDService queryRecord(int flags, int ifIndex, final String serviceName, int rrtype, int rrclass, boolean autoStop, final QueryListener listener) throws DNSSDException {
        onServiceStarting();
        final DNSSDService[] dNSSDServiceArr = new DNSSDService[1];
        Runnable runnable = new Runnable() { // from class: com.github.druk.dnssd.DNSSD$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                dNSSDServiceArr[0].stop();
            }
        };
        dNSSDServiceArr[0] = new InternalDNSSDService(this, InternalDNSSD.queryRecord(flags, ifIndex, serviceName, rrtype, rrclass, new AnonymousClass4(runnable, listener, dNSSDServiceArr, autoStop)));
        if (autoStop) {
            this.handler.postDelayed(runnable, this.serviceTimeout);
        }
        return dNSSDServiceArr[0];
    }

    /* JADX INFO: renamed from: com.github.druk.dnssd.DNSSD$4, reason: invalid class name */
    class AnonymousClass4 implements InternalQueryListener {
        final /* synthetic */ boolean val$autoStop;
        final /* synthetic */ QueryListener val$listener;
        final /* synthetic */ DNSSDService[] val$services;
        final /* synthetic */ Runnable val$timeoutRunnable;

        AnonymousClass4(final Runnable val$autoStop, final QueryListener val$services, final DNSSDService[] val$listener, final boolean val$timeoutRunnable) {
            this.val$timeoutRunnable = val$autoStop;
            this.val$listener = val$services;
            this.val$services = val$listener;
            this.val$autoStop = val$timeoutRunnable;
        }

        @Override // com.github.druk.dnssd.InternalQueryListener
        public void queryAnswered(DNSSDService query, final int flags, final int ifIndex, byte[] fullName, final int rrtype, final int rrclass, final byte[] rdata, final int ttl) {
            final String str = new String(fullName, DNSSD.UTF_8);
            DNSSD.this.handler.removeCallbacks(this.val$timeoutRunnable);
            Handler handler = DNSSD.this.handler;
            final QueryListener queryListener = this.val$listener;
            final DNSSDService[] dNSSDServiceArr = this.val$services;
            final boolean z = this.val$autoStop;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DNSSD.AnonymousClass4.lambda$queryAnswered$0(queryListener, dNSSDServiceArr, flags, ifIndex, str, rrtype, rrclass, rdata, ttl, z);
                }
            });
        }

        static /* synthetic */ void lambda$queryAnswered$0(final QueryListener listener, final DNSSDService[] services, final int flags, final int ifIndex, final String fullNameStr, final int rrtype, final int rrclass, final byte[] rdata, final int ttl, final boolean autoStop) {
            listener.queryAnswered(services[0], flags, ifIndex, fullNameStr, rrtype, rrclass, rdata, ttl);
            if (autoStop) {
                services[0].stop();
            }
        }

        @Override // com.github.druk.dnssd.BaseListener
        public void operationFailed(DNSSDService service, final int errorCode) {
            DNSSD.this.handler.removeCallbacks(this.val$timeoutRunnable);
            Handler handler = DNSSD.this.handler;
            final QueryListener queryListener = this.val$listener;
            final DNSSDService[] dNSSDServiceArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$4$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DNSSD.AnonymousClass4.lambda$operationFailed$1(queryListener, dNSSDServiceArr, errorCode);
                }
            });
        }

        static /* synthetic */ void lambda$operationFailed$1(final QueryListener listener, final DNSSDService[] services, final int errorCode) {
            listener.operationFailed(services[0], errorCode);
            services[0].stop();
        }
    }

    public DNSSDService enumerateDomains(int flags, int ifIndex, final DomainListener listener) throws DNSSDException {
        onServiceStarting();
        InternalDNSSDService internalDNSSDService = new InternalDNSSDService(this, InternalDNSSD.enumerateDomains(flags, ifIndex, new AnonymousClass5(listener, dNSSDServiceArr)));
        DNSSDService[] dNSSDServiceArr = {internalDNSSDService};
        return internalDNSSDService;
    }

    /* JADX INFO: renamed from: com.github.druk.dnssd.DNSSD$5, reason: invalid class name */
    class AnonymousClass5 implements InternalDomainListener {
        final /* synthetic */ DomainListener val$listener;
        final /* synthetic */ DNSSDService[] val$services;

        AnonymousClass5(final DomainListener val$services, final DNSSDService[] val$listener) {
            this.val$listener = val$services;
            this.val$services = val$listener;
        }

        @Override // com.github.druk.dnssd.InternalDomainListener
        public void domainFound(DNSSDService domainEnum, final int flags, final int ifIndex, byte[] domain) {
            final String str = new String(domain, DNSSD.UTF_8);
            Handler handler = DNSSD.this.handler;
            final DomainListener domainListener = this.val$listener;
            final DNSSDService[] dNSSDServiceArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    domainListener.domainFound(dNSSDServiceArr[0], flags, ifIndex, str);
                }
            });
        }

        @Override // com.github.druk.dnssd.InternalDomainListener
        public void domainLost(DNSSDService domainEnum, final int flags, final int ifIndex, byte[] domain) {
            final String str = new String(domain, DNSSD.UTF_8);
            Handler handler = DNSSD.this.handler;
            final DomainListener domainListener = this.val$listener;
            final DNSSDService[] dNSSDServiceArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$5$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    domainListener.domainLost(dNSSDServiceArr[0], flags, ifIndex, str);
                }
            });
        }

        @Override // com.github.druk.dnssd.BaseListener
        public void operationFailed(final DNSSDService service, final int errorCode) {
            Handler handler = DNSSD.this.handler;
            final DomainListener domainListener = this.val$listener;
            final DNSSDService[] dNSSDServiceArr = this.val$services;
            handler.post(new Runnable() { // from class: com.github.druk.dnssd.DNSSD$5$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    domainListener.operationFailed(dNSSDServiceArr[0], errorCode);
                }
            });
        }
    }

    public String constructFullName(String serviceName, String regType, String domain) throws DNSSDException {
        onServiceStarting();
        String strConstructFullName = InternalDNSSD.constructFullName(serviceName, regType, domain);
        onServiceStopped();
        return strConstructFullName;
    }

    public int reconfirmRecord(int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata) {
        onServiceStarting();
        int iReconfirmRecord = InternalDNSSD.reconfirmRecord(flags, ifIndex, fullName, rrtype, rrclass, rdata);
        onServiceStopped();
        return iReconfirmRecord;
    }

    @Override // com.github.druk.dnssd.InternalDNSSDService.DnssdServiceListener
    public void onServiceStarting() {
        if (this.multicastLock == null) {
            synchronized (this) {
                if (this.multicastLock == null) {
                    WifiManager wifiManager = (WifiManager) this.context.getApplicationContext().getSystemService("wifi");
                    if (wifiManager == null) {
                        Log.wtf(ZeroConfImplFactory.DNSSD_IMPL, "Can't get WIFI Service");
                        return;
                    } else {
                        this.multicastLock = wifiManager.createMulticastLock(MULTICAST_LOCK_NAME);
                        this.multicastLock.setReferenceCounted(true);
                    }
                }
            }
        }
        this.multicastLock.acquire();
    }

    @Override // com.github.druk.dnssd.InternalDNSSDService.DnssdServiceListener
    public void onServiceStopped() {
        if (this.multicastLock == null) {
            Log.wtf(ZeroConfImplFactory.DNSSD_IMPL, "Multicast lock doesn't exist");
        } else {
            this.multicastLock.release();
        }
    }

    public static int getIfIndexForName(String ifName) {
        return InternalDNSSD.getIfIndexForName(ifName);
    }

    public static Map<String, String> parseTXTRecords(byte[] data) {
        return parseTXTRecords(new TXTRecord(data));
    }

    static Map<String, String> parseTXTRecords(TXTRecord record) {
        HashMap map = new HashMap(record.size());
        for (int i = 0; i < record.size(); i++) {
            try {
                if (!TextUtils.isEmpty(record.getKey(i))) {
                    map.put(record.getKey(i), record.getValueAsString(i));
                }
            } catch (Exception e) {
                Log.w("RxResolveListener", "Parsing error of " + i + " TXT record", e);
            }
        }
        return map;
    }
}
