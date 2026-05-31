package com.github.druk.dnssd;

import android.content.Context;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class DNSSDBindable extends DNSSD {
    private static final String TAG = "DNSSDBindable";
    private final Context context;

    public DNSSDBindable(Context context) {
        super(context, "jdns_sd");
        this.context = context.getApplicationContext();
    }

    @Override // com.github.druk.dnssd.DNSSD, com.github.druk.dnssd.InternalDNSSDService.DnssdServiceListener
    public void onServiceStarting() {
        super.onServiceStarting();
        try {
            this.context.getSystemService("servicediscovery");
        } catch (Exception e) {
            Log.e(TAG, "Can't start NSD_SERVICE: ", e);
        }
    }

    @Override // com.github.druk.dnssd.DNSSD, com.github.druk.dnssd.InternalDNSSDService.DnssdServiceListener
    public void onServiceStopped() {
        super.onServiceStopped();
    }

    public String getNameForIfIndex(int ifIndex) {
        return InternalDNSSD.getNameForIfIndex(ifIndex);
    }
}
