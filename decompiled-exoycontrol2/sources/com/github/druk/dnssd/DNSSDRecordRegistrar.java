package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
public interface DNSSDRecordRegistrar extends DNSSDService {
    DNSRecord registerRecord(int flags, int ifIndex, String fullname, int rrtype, int rrclass, byte[] rData, int ttl) throws DNSSDException;
}
