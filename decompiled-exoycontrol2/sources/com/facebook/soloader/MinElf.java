package com.facebook.soloader;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedByInterruptException;
import kotlin.UByte;
import kotlin.UShort;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: classes.dex */
public final class MinElf {
    public static final int DT_NEEDED = 1;
    public static final int DT_NULL = 0;
    public static final int DT_STRTAB = 5;
    public static final int ELF_MAGIC = 1179403647;
    public static final int PN_XNUM = 65535;
    public static final int PT_DYNAMIC = 2;
    public static final int PT_LOAD = 1;
    private static final String TAG = "MinElf";

    public enum ISA {
        NOT_SO("not_so"),
        X86("x86"),
        ARM("armeabi-v7a"),
        X86_64("x86_64"),
        AARCH64("arm64-v8a"),
        OTHERS("others");

        private final String value;

        ISA(String str) {
            this.value = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.value;
        }
    }

    public static String[] extract_DT_NEEDED(File file) throws IOException {
        ElfFileChannel elfFileChannel = new ElfFileChannel(file);
        try {
            String[] strArrExtract_DT_NEEDED = extract_DT_NEEDED(elfFileChannel);
            elfFileChannel.close();
            return strArrExtract_DT_NEEDED;
        } catch (Throwable th) {
            try {
                elfFileChannel.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static String[] extract_DT_NEEDED_with_retries(ElfFileChannel elfFileChannel) throws IOException {
        int i = 0;
        while (true) {
            try {
                return extract_DT_NEEDED_no_retries(elfFileChannel);
            } catch (ClosedByInterruptException e) {
                i++;
                if (i > 4) {
                    throw e;
                }
                Thread.interrupted();
                Log.e(TAG, "retrying extract_DT_NEEDED due to ClosedByInterruptException", e);
                elfFileChannel.openChannel();
            }
        }
    }

    public static String[] extract_DT_NEEDED(ElfByteChannel elfByteChannel) throws IOException {
        if (elfByteChannel instanceof ElfFileChannel) {
            return extract_DT_NEEDED_with_retries((ElfFileChannel) elfByteChannel);
        }
        return extract_DT_NEEDED_no_retries(elfByteChannel);
    }

    private static String[] extract_DT_NEEDED_no_retries(ElfByteChannel elfByteChannel) throws IOException {
        long j;
        long j2;
        String str;
        long j3;
        String str2;
        long j4;
        long j5;
        long j6;
        long j7;
        long j8;
        long j9;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        long j10 = getu32(elfByteChannel, byteBufferAllocate, 0L);
        if (j10 != 1179403647) {
            throw new ElfError("file is not ELF: 0x" + Long.toHexString(j10));
        }
        boolean z = getu8(elfByteChannel, byteBufferAllocate, 4L) == 1;
        if (getu8(elfByteChannel, byteBufferAllocate, 5L) == 2) {
            byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
        }
        long j11 = z ? getu32(elfByteChannel, byteBufferAllocate, 28L) : get64(elfByteChannel, byteBufferAllocate, 32L);
        long j12 = z ? getu16(elfByteChannel, byteBufferAllocate, 44L) : getu16(elfByteChannel, byteBufferAllocate, 56L);
        int i = getu16(elfByteChannel, byteBufferAllocate, z ? 42L : 54L);
        if (j12 == WebSocketProtocol.PAYLOAD_SHORT_MAX) {
            long j13 = z ? getu32(elfByteChannel, byteBufferAllocate, 32L) : get64(elfByteChannel, byteBufferAllocate, 40L);
            if (z) {
                j9 = getu32(elfByteChannel, byteBufferAllocate, j13 + 28);
            } else {
                j9 = getu32(elfByteChannel, byteBufferAllocate, j13 + 44);
            }
            j12 = j9;
        }
        long j14 = j11;
        long j15 = 0;
        while (true) {
            if (j15 >= j12) {
                j = 0;
                break;
            }
            if (z) {
                j8 = getu32(elfByteChannel, byteBufferAllocate, j14 + 0);
            } else {
                j8 = getu32(elfByteChannel, byteBufferAllocate, j14 + 0);
            }
            if (j8 != 2) {
                j14 += (long) i;
                j15++;
            } else if (z) {
                j = getu32(elfByteChannel, byteBufferAllocate, j14 + 4);
            } else {
                j = get64(elfByteChannel, byteBufferAllocate, j14 + 8);
            }
        }
        long j16 = 0;
        if (j == 0) {
            throw new ElfError("ELF file does not contain dynamic linking information");
        }
        long j17 = j;
        long j18 = 0;
        int i2 = 0;
        while (true) {
            boolean z2 = z;
            long j19 = z ? getu32(elfByteChannel, byteBufferAllocate, j17 + j16) : get64(elfByteChannel, byteBufferAllocate, j17 + j16);
            long j20 = j;
            String str3 = "malformed DT_NEEDED section";
            if (j19 != 1) {
                j2 = j19;
                if (j19 == 5) {
                    j18 = z2 ? getu32(elfByteChannel, byteBufferAllocate, j17 + 4) : get64(elfByteChannel, byteBufferAllocate, j17 + 8);
                }
            } else {
                if (i2 == Integer.MAX_VALUE) {
                    throw new ElfError("malformed DT_NEEDED section");
                }
                i2++;
                j2 = j19;
            }
            long j21 = 16;
            j17 += z2 ? 8L : 16L;
            j16 = 0;
            if (j2 != 0) {
                z = z2;
                j = j20;
            } else {
                if (j18 == 0) {
                    throw new ElfError("Dynamic section string-table not found");
                }
                int i3 = 0;
                while (true) {
                    if (i3 >= j12) {
                        str = str3;
                        j3 = 0;
                        break;
                    }
                    if (z2) {
                        j4 = getu32(elfByteChannel, byteBufferAllocate, j11 + j16);
                    } else {
                        j4 = getu32(elfByteChannel, byteBufferAllocate, j11 + j16);
                    }
                    if (j4 == 1) {
                        if (z2) {
                            j5 = getu32(elfByteChannel, byteBufferAllocate, j11 + 8);
                        } else {
                            j5 = get64(elfByteChannel, byteBufferAllocate, j11 + j21);
                        }
                        if (z2) {
                            str = str3;
                            j6 = getu32(elfByteChannel, byteBufferAllocate, j11 + 20);
                        } else {
                            str = str3;
                            j6 = get64(elfByteChannel, byteBufferAllocate, j11 + 40);
                        }
                        if (j5 <= j18 && j18 < j6 + j5) {
                            if (z2) {
                                j7 = getu32(elfByteChannel, byteBufferAllocate, j11 + 4);
                            } else {
                                j7 = get64(elfByteChannel, byteBufferAllocate, j11 + 8);
                            }
                            j3 = j7 + (j18 - j5);
                        }
                    } else {
                        str = str3;
                    }
                    j11 += (long) i;
                    i3++;
                    str3 = str;
                    j21 = 16;
                    j16 = 0;
                }
                long j22 = 0;
                if (j3 == 0) {
                    throw new ElfError("did not find file offset of DT_STRTAB table");
                }
                String[] strArr = new String[i2];
                long j23 = j20;
                int i4 = 0;
                while (true) {
                    long j24 = j23 + j22;
                    long j25 = z2 ? getu32(elfByteChannel, byteBufferAllocate, j24) : get64(elfByteChannel, byteBufferAllocate, j24);
                    if (j25 == 1) {
                        strArr[i4] = getSz(elfByteChannel, byteBufferAllocate, (z2 ? getu32(elfByteChannel, byteBufferAllocate, j23 + 4) : get64(elfByteChannel, byteBufferAllocate, j23 + 8)) + j3);
                        if (i4 == Integer.MAX_VALUE) {
                            throw new ElfError(str);
                        }
                        i4++;
                        str2 = str;
                    } else {
                        str2 = str;
                    }
                    j23 += z2 ? 8L : 16L;
                    if (j25 == 0) {
                        if (i4 == i2) {
                            return strArr;
                        }
                        throw new ElfError(str2);
                    }
                    str = str2;
                    j22 = 0;
                }
            }
        }
    }

    private static String getSz(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            long j2 = 1 + j;
            short u8Var = getu8(elfByteChannel, byteBuffer, j);
            if (u8Var != 0) {
                sb.append((char) u8Var);
                j = j2;
            } else {
                return sb.toString();
            }
        }
    }

    private static void read(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, int i, long j) throws IOException {
        int i2;
        byteBuffer.position(0);
        byteBuffer.limit(i);
        while (byteBuffer.remaining() > 0 && (i2 = elfByteChannel.read(byteBuffer, j)) != -1) {
            j += (long) i2;
        }
        if (byteBuffer.remaining() > 0) {
            throw new ElfError("ELF file truncated");
        }
        byteBuffer.position(0);
    }

    private static long get64(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 8, j);
        return byteBuffer.getLong();
    }

    private static long getu32(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 4, j);
        return ((long) byteBuffer.getInt()) & 4294967295L;
    }

    private static int getu16(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 2, j);
        return byteBuffer.getShort() & UShort.MAX_VALUE;
    }

    private static short getu8(ElfByteChannel elfByteChannel, ByteBuffer byteBuffer, long j) throws IOException {
        read(elfByteChannel, byteBuffer, 1, j);
        return (short) (byteBuffer.get() & UByte.MAX_VALUE);
    }

    private static class ElfError extends RuntimeException {
        ElfError(String str) {
            super(str);
        }
    }
}
