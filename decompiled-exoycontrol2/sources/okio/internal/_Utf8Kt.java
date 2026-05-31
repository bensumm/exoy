package okio.internal;

import com.facebook.imageutils.JfifUtil;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import okio.Utf8;

/* JADX INFO: compiled from: -Utf8.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u001e\u0010\u0003\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "beginIndex", "", "endIndex", "okio"}, k = 2, mv = {1, 4, 0})
public final class _Utf8Kt {
    public static /* synthetic */ String commonToUtf8String$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = bArr.length;
        }
        return commonToUtf8String(bArr, i, i2);
    }

    public static final String commonToUtf8String(byte[] commonToUtf8String, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = i;
        Intrinsics.checkNotNullParameter(commonToUtf8String, "$this$commonToUtf8String");
        if (i7 < 0 || i2 > commonToUtf8String.length || i7 > i2) {
            throw new ArrayIndexOutOfBoundsException("size=" + commonToUtf8String.length + " beginIndex=" + i7 + " endIndex=" + i2);
        }
        char[] cArr = new char[i2 - i7];
        int i8 = 0;
        while (i7 < i2) {
            byte b = commonToUtf8String[i7];
            if (b >= 0) {
                i3 = i8 + 1;
                cArr[i8] = (char) b;
                i7++;
                while (i7 < i2) {
                    byte b2 = commonToUtf8String[i7];
                    if (b2 < 0) {
                        break;
                    }
                    i7++;
                    cArr[i3] = (char) b2;
                    i3++;
                }
            } else {
                if ((b >> 5) == -2) {
                    int i9 = i7 + 1;
                    if (i2 <= i9) {
                        i3 = i8 + 1;
                        cArr[i8] = (char) Utf8.REPLACEMENT_CODE_POINT;
                    } else {
                        byte b3 = commonToUtf8String[i9];
                        if ((b3 & 192) == 128) {
                            int i10 = (b << 6) ^ (b3 ^ ByteCompanionObject.MIN_VALUE);
                            if (i10 < 128) {
                                i3 = i8 + 1;
                                cArr[i8] = (char) Utf8.REPLACEMENT_CODE_POINT;
                            } else {
                                i3 = i8 + 1;
                                cArr[i8] = (char) i10;
                            }
                            Unit unit = Unit.INSTANCE;
                            i4 = 2;
                        } else {
                            i3 = i8 + 1;
                            cArr[i8] = (char) Utf8.REPLACEMENT_CODE_POINT;
                        }
                    }
                    Unit unit2 = Unit.INSTANCE;
                    i4 = 1;
                } else if ((b >> 4) == -2) {
                    int i11 = i7 + 2;
                    if (i2 <= i11) {
                        i3 = i8 + 1;
                        cArr[i8] = (char) Utf8.REPLACEMENT_CODE_POINT;
                        Unit unit3 = Unit.INSTANCE;
                        int i12 = i7 + 1;
                        if (i2 > i12) {
                            if ((commonToUtf8String[i12] & 192) == 128) {
                                i4 = 2;
                            }
                        }
                        i4 = 1;
                    } else {
                        byte b4 = commonToUtf8String[i7 + 1];
                        if ((b4 & 192) == 128) {
                            byte b5 = commonToUtf8String[i11];
                            if ((b5 & 192) == 128) {
                                int i13 = (b << 12) ^ ((b5 ^ ByteCompanionObject.MIN_VALUE) ^ (b4 << 6));
                                if (i13 < 2048) {
                                    i3 = i8 + 1;
                                    cArr[i8] = (char) Utf8.REPLACEMENT_CODE_POINT;
                                } else if (55296 <= i13 && 57343 >= i13) {
                                    i3 = i8 + 1;
                                    cArr[i8] = (char) Utf8.REPLACEMENT_CODE_POINT;
                                } else {
                                    i3 = i8 + 1;
                                    cArr[i8] = (char) i13;
                                }
                                Unit unit4 = Unit.INSTANCE;
                                i4 = 3;
                            } else {
                                i3 = i8 + 1;
                                cArr[i8] = (char) Utf8.REPLACEMENT_CODE_POINT;
                                Unit unit5 = Unit.INSTANCE;
                                i4 = 2;
                            }
                        } else {
                            i3 = i8 + 1;
                            cArr[i8] = (char) Utf8.REPLACEMENT_CODE_POINT;
                            Unit unit6 = Unit.INSTANCE;
                            i4 = 1;
                        }
                    }
                } else {
                    if ((b >> 3) == -2) {
                        int i14 = i7 + 3;
                        if (i2 <= i14) {
                            i5 = i8 + 1;
                            cArr[i8] = Utf8.REPLACEMENT_CHARACTER;
                            Unit unit7 = Unit.INSTANCE;
                            int i15 = i7 + 1;
                            if (i2 > i15) {
                                if ((commonToUtf8String[i15] & 192) == 128) {
                                    int i16 = i7 + 2;
                                    if (i2 > i16) {
                                        if ((commonToUtf8String[i16] & 192) == 128) {
                                            i6 = 3;
                                        }
                                    }
                                    i6 = 2;
                                }
                            }
                            i6 = 1;
                        } else {
                            byte b6 = commonToUtf8String[i7 + 1];
                            if ((b6 & 192) == 128) {
                                byte b7 = commonToUtf8String[i7 + 2];
                                if ((b7 & 192) == 128) {
                                    byte b8 = commonToUtf8String[i14];
                                    if ((b8 & 192) == 128) {
                                        int i17 = (b << 18) ^ (((b8 ^ ByteCompanionObject.MIN_VALUE) ^ (b7 << 6)) ^ (b6 << 12));
                                        if (i17 > 1114111) {
                                            i5 = i8 + 1;
                                            cArr[i8] = Utf8.REPLACEMENT_CHARACTER;
                                        } else if ((55296 <= i17 && 57343 >= i17) || i17 < 65536 || i17 == 65533) {
                                            i5 = i8 + 1;
                                            cArr[i8] = Utf8.REPLACEMENT_CHARACTER;
                                        } else {
                                            int i18 = i8 + 1;
                                            cArr[i8] = (char) ((i17 >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                            char c = (char) ((i17 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                            i5 = i18 + 1;
                                            cArr[i18] = c;
                                        }
                                        Unit unit8 = Unit.INSTANCE;
                                        i6 = 4;
                                    } else {
                                        i5 = i8 + 1;
                                        cArr[i8] = Utf8.REPLACEMENT_CHARACTER;
                                        Unit unit9 = Unit.INSTANCE;
                                        i6 = 3;
                                    }
                                } else {
                                    i5 = i8 + 1;
                                    cArr[i8] = Utf8.REPLACEMENT_CHARACTER;
                                    Unit unit10 = Unit.INSTANCE;
                                    i6 = 2;
                                }
                            } else {
                                i5 = i8 + 1;
                                cArr[i8] = Utf8.REPLACEMENT_CHARACTER;
                                Unit unit11 = Unit.INSTANCE;
                                i6 = 1;
                            }
                        }
                        i7 += i6;
                    } else {
                        i5 = i8 + 1;
                        cArr[i8] = Utf8.REPLACEMENT_CHARACTER;
                        i7++;
                    }
                    i8 = i5;
                }
                i7 += i4;
            }
            i8 = i3;
        }
        return new String(cArr, 0, i8);
    }

    public static final byte[] commonAsUtf8ToByteArray(String commonAsUtf8ToByteArray) {
        int i;
        int i2;
        char cCharAt;
        Intrinsics.checkNotNullParameter(commonAsUtf8ToByteArray, "$this$commonAsUtf8ToByteArray");
        byte[] bArr = new byte[commonAsUtf8ToByteArray.length() * 4];
        int length = commonAsUtf8ToByteArray.length();
        int i3 = 0;
        while (i3 < length) {
            char cCharAt2 = commonAsUtf8ToByteArray.charAt(i3);
            if (Intrinsics.compare((int) cCharAt2, 128) >= 0) {
                int length2 = commonAsUtf8ToByteArray.length();
                int i4 = i3;
                while (i3 < length2) {
                    char cCharAt3 = commonAsUtf8ToByteArray.charAt(i3);
                    if (Intrinsics.compare((int) cCharAt3, 128) < 0) {
                        int i5 = i4 + 1;
                        bArr[i4] = (byte) cCharAt3;
                        i3++;
                        while (i3 < length2 && Intrinsics.compare((int) commonAsUtf8ToByteArray.charAt(i3), 128) < 0) {
                            bArr[i5] = (byte) commonAsUtf8ToByteArray.charAt(i3);
                            i3++;
                            i5++;
                        }
                        i4 = i5;
                    } else {
                        if (Intrinsics.compare((int) cCharAt3, 2048) < 0) {
                            int i6 = i4 + 1;
                            bArr[i4] = (byte) ((cCharAt3 >> 6) | JfifUtil.MARKER_SOFn);
                            byte b = (byte) ((cCharAt3 & '?') | 128);
                            i = i6 + 1;
                            bArr[i6] = b;
                        } else if (55296 > cCharAt3 || 57343 < cCharAt3) {
                            int i7 = i4 + 1;
                            bArr[i4] = (byte) ((cCharAt3 >> '\f') | 224);
                            int i8 = i7 + 1;
                            bArr[i7] = (byte) (((cCharAt3 >> 6) & 63) | 128);
                            byte b2 = (byte) ((cCharAt3 & '?') | 128);
                            i = i8 + 1;
                            bArr[i8] = b2;
                        } else if (Intrinsics.compare((int) cCharAt3, 56319) > 0 || length2 <= (i2 = i3 + 1) || 56320 > (cCharAt = commonAsUtf8ToByteArray.charAt(i2)) || 57343 < cCharAt) {
                            i = i4 + 1;
                            bArr[i4] = Utf8.REPLACEMENT_BYTE;
                        } else {
                            int iCharAt = ((cCharAt3 << '\n') + commonAsUtf8ToByteArray.charAt(i2)) - 56613888;
                            int i9 = i4 + 1;
                            bArr[i4] = (byte) ((iCharAt >> 18) | 240);
                            int i10 = i9 + 1;
                            bArr[i9] = (byte) (((iCharAt >> 12) & 63) | 128);
                            int i11 = i10 + 1;
                            bArr[i10] = (byte) (((iCharAt >> 6) & 63) | 128);
                            byte b3 = (byte) ((iCharAt & 63) | 128);
                            i = i11 + 1;
                            bArr[i11] = b3;
                            i3 += 2;
                            i4 = i;
                        }
                        i3++;
                        i4 = i;
                    }
                }
                byte[] bArrCopyOf = Arrays.copyOf(bArr, i4);
                Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
                return bArrCopyOf;
            }
            bArr[i3] = (byte) cCharAt2;
            i3++;
        }
        byte[] bArrCopyOf2 = Arrays.copyOf(bArr, commonAsUtf8ToByteArray.length());
        Intrinsics.checkNotNullExpressionValue(bArrCopyOf2, "java.util.Arrays.copyOf(this, newSize)");
        return bArrCopyOf2;
    }
}
