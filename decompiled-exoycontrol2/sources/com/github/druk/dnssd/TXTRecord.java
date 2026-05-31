package com.github.druk.dnssd;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.CharEncoding;

/* JADX INFO: loaded from: classes.dex */
public class TXTRecord {
    protected static final byte kAttrSep = 61;
    protected byte[] fBytes;

    public TXTRecord() {
        this.fBytes = new byte[0];
    }

    public TXTRecord(byte[] initBytes) {
        this.fBytes = (byte[]) initBytes.clone();
    }

    public void set(String key, String value) {
        set(key, value != null ? value.getBytes() : null);
    }

    public void set(String key, byte[] value) {
        int length = value != null ? value.length : 0;
        try {
            byte[] bytes = key.getBytes(CharEncoding.US_ASCII);
            for (byte b : bytes) {
                if (b == 61) {
                    throw new IllegalArgumentException();
                }
            }
            if (bytes.length + length >= 255) {
                throw new ArrayIndexOutOfBoundsException();
            }
            int iRemove = remove(key);
            if (iRemove == -1) {
                iRemove = size();
            }
            insert(bytes, value, iRemove);
        } catch (UnsupportedEncodingException unused) {
            throw new IllegalArgumentException();
        }
    }

    protected void insert(byte[] keyBytes, byte[] value, int index) {
        byte[] bArr = this.fBytes;
        int length = value != null ? value.length : 0;
        int i = 0;
        for (int i2 = 0; i2 < index; i2++) {
            byte[] bArr2 = this.fBytes;
            if (i >= bArr2.length) {
                break;
            }
            i += (bArr2[i] + 1) & 255;
        }
        int length2 = keyBytes.length + length + (value != null ? 1 : 0);
        int length3 = bArr.length + length2 + 1;
        byte[] bArr3 = new byte[length3];
        this.fBytes = bArr3;
        System.arraycopy(bArr, 0, bArr3, 0, i);
        int length4 = bArr.length - i;
        System.arraycopy(bArr, i, this.fBytes, length3 - length4, length4);
        byte[] bArr4 = this.fBytes;
        bArr4[i] = (byte) length2;
        int i3 = i + 1;
        System.arraycopy(keyBytes, 0, bArr4, i3, keyBytes.length);
        if (value != null) {
            byte[] bArr5 = this.fBytes;
            bArr5[i3 + keyBytes.length] = 61;
            System.arraycopy(value, 0, bArr5, i + keyBytes.length + 2, length);
        }
    }

    public int remove(String key) {
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.fBytes;
            if (i >= bArr.length) {
                return -1;
            }
            int i3 = bArr[i];
            if (key.length() <= i3 && ((key.length() == i3 || this.fBytes[key.length() + i + 1] == 61) && key.compareToIgnoreCase(new String(this.fBytes, i + 1, key.length())) == 0)) {
                byte[] bArr2 = this.fBytes;
                byte[] bArr3 = new byte[(bArr2.length - i3) - 1];
                this.fBytes = bArr3;
                System.arraycopy(bArr2, 0, bArr3, 0, i);
                System.arraycopy(bArr2, i + i3 + 1, this.fBytes, i, ((bArr2.length - i) - i3) - 1);
                return i2;
            }
            i += (i3 + 1) & 255;
            i2++;
        }
    }

    public int size() {
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.fBytes;
            if (i >= bArr.length) {
                return i2;
            }
            i += (bArr[i] + 1) & 255;
            i2++;
        }
    }

    public boolean contains(String key) {
        int i = 0;
        while (true) {
            String key2 = getKey(i);
            if (key2 == null) {
                return false;
            }
            if (key.compareToIgnoreCase(key2) == 0) {
                return true;
            }
            i++;
        }
    }

    public String getKey(int index) {
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < index; i3++) {
            byte[] bArr = this.fBytes;
            if (i2 >= bArr.length) {
                break;
            }
            i2 += bArr[i2] + 1;
        }
        byte[] bArr2 = this.fBytes;
        if (i2 >= bArr2.length) {
            return null;
        }
        byte b = bArr2[i2];
        while (i < b && this.fBytes[i2 + i + 1] != 61) {
            i++;
        }
        return new String(this.fBytes, i2 + 1, i);
    }

    public byte[] getValue(int index) {
        int i = 0;
        for (int i2 = 0; i2 < index; i2++) {
            byte[] bArr = this.fBytes;
            if (i >= bArr.length) {
                break;
            }
            i += bArr[i] + 1;
        }
        byte[] bArr2 = this.fBytes;
        if (i < bArr2.length) {
            int i3 = bArr2[i];
            for (int i4 = 0; i4 < i3; i4++) {
                byte[] bArr3 = this.fBytes;
                int i5 = i + i4;
                if (bArr3[i5 + 1] == 61) {
                    int i6 = (i3 - i4) - 1;
                    byte[] bArr4 = new byte[i6];
                    System.arraycopy(bArr3, i5 + 2, bArr4, 0, i6);
                    return bArr4;
                }
            }
        }
        return null;
    }

    public String getValueAsString(int index) {
        byte[] value = getValue(index);
        if (value != null) {
            return new String(value);
        }
        return null;
    }

    public byte[] getValue(String forKey) {
        int i = 0;
        while (true) {
            String key = getKey(i);
            if (key == null) {
                return null;
            }
            if (forKey.compareToIgnoreCase(key) == 0) {
                return getValue(i);
            }
            i++;
        }
    }

    public String getValueAsString(String forKey) {
        byte[] value = getValue(forKey);
        if (value != null) {
            return new String(value);
        }
        return null;
    }

    public byte[] getRawBytes() {
        return (byte[]) this.fBytes.clone();
    }

    public String toString() {
        String str;
        String str2 = null;
        int i = 0;
        while (true) {
            String key = getKey(i);
            if (key == null) {
                break;
            }
            String str3 = String.valueOf(i) + "={" + key;
            String valueAsString = getValueAsString(i);
            if (valueAsString != null) {
                str = str3 + "=" + valueAsString + "}";
            } else {
                str = str3 + "}";
            }
            if (str2 == null) {
                str2 = str;
            } else {
                str2 = str2 + ", " + str;
            }
            i++;
        }
        return str2 != null ? str2 : "";
    }
}
