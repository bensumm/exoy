package com.github.druk.dnssd;

/* JADX INFO: loaded from: classes.dex */
public interface DNSSDRegistration extends DNSSDService {
    DNSRecord addRecord(int flags, int rrType, byte[] rData, int ttl) throws DNSSDException;

    DNSRecord getTXTRecord() throws DNSSDException;
}
