package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
interface InternalQueryListener extends BaseListener {
    void queryAnswered(DNSSDService query, int flags, int ifIndex, byte[] fullName, int rrtype, int rrclass, byte[] rdata, int ttl);
}
