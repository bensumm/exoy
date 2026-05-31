package com.github.druk.rx2dnssd;

import android.os.Parcel;
import android.os.Parcelable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class BonjourService implements Parcelable {
    public static final Parcelable.Creator<BonjourService> CREATOR = new Parcelable.Creator<BonjourService>() { // from class: com.github.druk.rx2dnssd.BonjourService.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BonjourService createFromParcel(Parcel source) {
            return new BonjourService(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BonjourService[] newArray(int size) {
            return new BonjourService[size];
        }
    };
    public static final int LOST = 256;
    private final Map<String, String> dnsRecords;
    private final String domain;
    private final int flags;
    private final String hostname;
    private final int ifIndex;
    private final List<InetAddress> inetAddresses;
    private final int port;
    private final String regType;
    private final String serviceName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected BonjourService(Builder builder) {
        this.flags = builder.flags;
        this.serviceName = builder.serviceName;
        this.regType = builder.regType;
        this.domain = builder.domain;
        this.ifIndex = builder.ifIndex;
        this.inetAddresses = Collections.unmodifiableList(builder.inetAddresses);
        this.dnsRecords = Collections.unmodifiableMap(builder.dnsRecords);
        this.hostname = builder.hostname;
        this.port = builder.port;
    }

    protected BonjourService(Parcel in) {
        this.flags = in.readInt();
        this.serviceName = in.readString();
        this.regType = in.readString();
        this.domain = in.readString();
        this.dnsRecords = readMap(in);
        this.inetAddresses = readAddresses(in);
        this.ifIndex = in.readInt();
        this.hostname = in.readString();
        this.port = in.readInt();
    }

    private static void writeAddresses(Parcel dest, List<InetAddress> val) {
        if (val == null) {
            dest.writeInt(-1);
            return;
        }
        dest.writeInt(val.size());
        Iterator<InetAddress> it = val.iterator();
        while (it.hasNext()) {
            dest.writeSerializable(it.next());
        }
    }

    private static List<InetAddress> readAddresses(Parcel in) {
        int i = in.readInt();
        if (i < 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add((InetAddress) in.readSerializable());
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static void writeMap(Parcel dest, Map<String, String> val) {
        if (val == null) {
            dest.writeInt(-1);
            return;
        }
        dest.writeInt(val.size());
        for (Map.Entry<String, String> entry : val.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    private static Map<String, String> readMap(Parcel in) {
        int i = in.readInt();
        if (i < 0) {
            return null;
        }
        HashMap map = new HashMap();
        for (int i2 = 0; i2 < i; i2++) {
            String string = in.readString();
            String string2 = in.readString();
            if (string != null && string2 != null) {
                map.put(string, string2);
            }
        }
        return Collections.unmodifiableMap(map);
    }

    public int getFlags() {
        return this.flags;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getRegType() {
        return this.regType;
    }

    public String getDomain() {
        return this.domain;
    }

    public int getIfIndex() {
        return this.ifIndex;
    }

    public String getHostname() {
        return this.hostname;
    }

    public int getPort() {
        return this.port;
    }

    public Map<String, String> getTxtRecords() {
        return this.dnsRecords;
    }

    public Inet4Address getInet4Address() {
        for (InetAddress inetAddress : this.inetAddresses) {
            if (inetAddress instanceof Inet4Address) {
                return (Inet4Address) inetAddress;
            }
        }
        return null;
    }

    public Inet6Address getInet6Address() {
        for (InetAddress inetAddress : this.inetAddresses) {
            if (inetAddress instanceof Inet6Address) {
                return (Inet6Address) inetAddress;
            }
        }
        return null;
    }

    public List<InetAddress> getInetAddresses() {
        return this.inetAddresses;
    }

    public boolean isLost() {
        return (this.flags & 256) == 256;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonjourService)) {
            return false;
        }
        BonjourService bonjourService = (BonjourService) o;
        if (this.ifIndex != bonjourService.ifIndex) {
            return false;
        }
        String str = this.serviceName;
        if (str == null ? bonjourService.serviceName != null : !str.equals(bonjourService.serviceName)) {
            return false;
        }
        String str2 = this.regType;
        if (str2 == null ? bonjourService.regType != null : !str2.equals(bonjourService.regType)) {
            return false;
        }
        String str3 = this.domain;
        String str4 = bonjourService.domain;
        if (str3 != null) {
            if (str3.equals(str4)) {
                return true;
            }
        } else if (str4 == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.serviceName;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.regType;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.domain;
        return ((iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31) + this.ifIndex;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.flags);
        dest.writeString(this.serviceName);
        dest.writeString(this.regType);
        dest.writeString(this.domain);
        writeMap(dest, this.dnsRecords);
        writeAddresses(dest, this.inetAddresses);
        dest.writeInt(this.ifIndex);
        dest.writeString(this.hostname);
        dest.writeInt(this.port);
    }

    public String toString() {
        return "BonjourService{flags=" + this.flags + ", domain='" + this.domain + "', regType='" + this.regType + "', serviceName='" + this.serviceName + "', dnsRecords=" + this.dnsRecords + ", ifIndex=" + this.ifIndex + ", hostname='" + this.hostname + "', port=" + this.port + '}';
    }

    public static class Builder {
        private Map<String, String> dnsRecords;
        private final String domain;
        private final int flags;
        private String hostname;
        private final int ifIndex;
        private List<InetAddress> inetAddresses;
        private int port;
        private final String regType;
        private final String serviceName;

        public Builder(int flags, int ifIndex, String serviceName, String regType, String domain) {
            this.inetAddresses = new ArrayList();
            this.dnsRecords = new HashMap();
            this.flags = flags;
            this.serviceName = serviceName;
            this.regType = regType;
            this.domain = domain;
            this.ifIndex = ifIndex;
        }

        public Builder(BonjourService service) {
            this.inetAddresses = new ArrayList();
            this.dnsRecords = new HashMap();
            this.flags = service.flags;
            this.serviceName = service.serviceName;
            this.regType = service.regType;
            this.domain = service.domain;
            this.ifIndex = service.ifIndex;
            this.dnsRecords = new HashMap(service.dnsRecords);
            this.inetAddresses = new ArrayList(service.inetAddresses);
            this.hostname = service.hostname;
            this.port = service.port;
        }

        public Builder hostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder dnsRecords(Map<String, String> dnsRecords) {
            this.dnsRecords = dnsRecords;
            return this;
        }

        public Builder inet4Address(Inet4Address inet4Address) {
            this.inetAddresses.add(inet4Address);
            return this;
        }

        public Builder inet6Address(Inet6Address inet6Address) {
            this.inetAddresses.add(inet6Address);
            return this;
        }

        public BonjourService build() {
            return new BonjourService(this);
        }

        public void inetAddress(InetAddress inetAddress) {
            this.inetAddresses.add(inetAddress);
        }
    }
}
