package com.facebook.common.internal;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class ByteStreams {
    private static final int BUF_SIZE = 4096;

    private ByteStreams() {
    }

    public static long copy(InputStream from, OutputStream to) throws IOException {
        Preconditions.checkNotNull(from);
        Preconditions.checkNotNull(to);
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int i = from.read(bArr);
            if (i == -1) {
                return j;
            }
            to.write(bArr, 0, i);
            j += (long) i;
        }
    }

    public static int read(InputStream in, byte[] b, int off, int len) throws IOException {
        Preconditions.checkNotNull(in);
        Preconditions.checkNotNull(b);
        if (len < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        int i = 0;
        while (i < len) {
            int i2 = in.read(b, off + i, len - i);
            if (i2 == -1) {
                break;
            }
            i += i2;
        }
        return i;
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(in, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] toByteArray(InputStream in, int expectedSize) throws IOException {
        byte[] bArr = new byte[expectedSize];
        int i = expectedSize;
        while (i > 0) {
            int i2 = expectedSize - i;
            int i3 = in.read(bArr, i2, i);
            if (i3 == -1) {
                return Arrays.copyOf(bArr, i2);
            }
            i -= i3;
        }
        int i4 = in.read();
        if (i4 == -1) {
            return bArr;
        }
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        fastByteArrayOutputStream.write(i4);
        copy(in, fastByteArrayOutputStream);
        byte[] bArr2 = new byte[fastByteArrayOutputStream.size() + expectedSize];
        System.arraycopy(bArr, 0, bArr2, 0, expectedSize);
        fastByteArrayOutputStream.writeTo(bArr2, expectedSize);
        return bArr2;
    }

    private static final class FastByteArrayOutputStream extends ByteArrayOutputStream {
        private FastByteArrayOutputStream() {
        }

        void writeTo(byte[] b, int off) {
            System.arraycopy(this.buf, 0, b, off, this.count);
        }
    }

    public static void readFully(InputStream in, byte[] b, int off, int len) throws IOException {
        int i = read(in, b, off, len);
        if (i == len) {
            return;
        }
        throw new EOFException("reached end of stream after reading " + i + " bytes; " + len + " bytes expected");
    }
}
