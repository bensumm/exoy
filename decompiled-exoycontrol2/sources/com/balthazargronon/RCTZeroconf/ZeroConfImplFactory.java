package com.balthazargronon.RCTZeroconf;

import com.balthazargronon.RCTZeroconf.nsd.NsdServiceImpl;
import com.balthazargronon.RCTZeroconf.rx2dnssd.DnssdImpl;
import com.facebook.react.bridge.ReactApplicationContext;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class ZeroConfImplFactory {
    public static final String DNSSD_IMPL = "DNSSD";
    public static final String NSD_IMPL = "NSD";
    private ReactApplicationContext context;
    private Map<String, Zeroconf> zeroconfMap = new HashMap();
    private ZeroconfModule zeroconfModule;

    public ZeroConfImplFactory(ZeroconfModule zeroconfModule, ReactApplicationContext reactApplicationContext) {
        this.zeroconfModule = zeroconfModule;
        this.context = reactApplicationContext;
    }

    public Zeroconf getZeroconf(String str) {
        if (StringUtils.isBlank(str)) {
            str = NSD_IMPL;
        }
        return getOrCreateImpl(str);
    }

    private Zeroconf getOrCreateImpl(String str) {
        if (!this.zeroconfMap.containsKey(str)) {
            str.hashCode();
            if (str.equals(NSD_IMPL)) {
                this.zeroconfMap.put(NSD_IMPL, new NsdServiceImpl(this.zeroconfModule, this.context));
            } else if (str.equals(DNSSD_IMPL)) {
                this.zeroconfMap.put(DNSSD_IMPL, new DnssdImpl(this.zeroconfModule, this.context));
            } else {
                throw new IllegalArgumentException(String.format("%s implType is not supported. Only %s and %s are supported", str, NSD_IMPL, DNSSD_IMPL));
            }
        }
        return this.zeroconfMap.get(str);
    }
}
