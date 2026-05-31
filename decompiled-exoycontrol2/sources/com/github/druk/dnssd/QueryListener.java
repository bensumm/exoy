package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
public interface QueryListener extends BaseListener {
    void queryAnswered(DNSSDService query, int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata, int ttl);
}
