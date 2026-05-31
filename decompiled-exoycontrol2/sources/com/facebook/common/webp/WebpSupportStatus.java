package com.facebook.common.webp;

import java.io.UnsupportedEncodingException;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class WebpSupportStatus {
    private static final int EXTENDED_WEBP_HEADER_LENGTH = 21;
    private static final int SIMPLE_WEBP_HEADER_LENGTH = 20;
    private static final String VP8X_WEBP_BASE64 = "UklGRkoAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAwAAAARBxAR/Q9ERP8DAABWUDggGAAAABQBAJ0BKgEAAQAAAP4AAA3AAP7mtQAAAA==";
    public static final boolean sIsWebpSupportRequired = false;
    public static final boolean sIsSimpleWebpSupported = true;
    public static final boolean sIsExtendedWebpSupported = isExtendedWebpSupported();

    @Nullable
    public static WebpBitmapFactory sWebpBitmapFactory = null;
    private static boolean sWebpLibraryChecked = false;
    private static final byte[] WEBP_RIFF_BYTES = asciiBytes("RIFF");
    private static final byte[] WEBP_NAME_BYTES = asciiBytes("WEBP");
    private static final byte[] WEBP_VP8_BYTES = asciiBytes("VP8 ");
    private static final byte[] WEBP_VP8L_BYTES = asciiBytes("VP8L");
    private static final byte[] WEBP_VP8X_BYTES = asciiBytes("VP8X");

    private static boolean isExtendedWebpSupported() {
        return true;
    }

    @Nullable
    public static WebpBitmapFactory loadWebpBitmapFactoryIfExists() {
        WebpBitmapFactory webpBitmapFactory;
        if (sWebpLibraryChecked) {
            return sWebpBitmapFactory;
        }
        try {
            webpBitmapFactory = (WebpBitmapFactory) Class.forName("com.facebook.webpsupport.WebpBitmapFactoryImpl").newInstance();
        } catch (Throwable unused) {
            webpBitmapFactory = null;
        }
        sWebpLibraryChecked = true;
        return webpBitmapFactory;
    }

    private static byte[] asciiBytes(String value) {
        try {
            return value.getBytes("ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("ASCII not found!", e);
        }
    }

    public static boolean isWebpSupportedByPlatform(final byte[] imageHeaderBytes, final int offset, final int headerSize) {
        if (isSimpleWebpHeader(imageHeaderBytes, offset)) {
            return sIsSimpleWebpSupported;
        }
        if (isLosslessWebpHeader(imageHeaderBytes, offset)) {
            return sIsExtendedWebpSupported;
        }
        if (!isExtendedWebpHeader(imageHeaderBytes, offset, headerSize) || isAnimatedWebpHeader(imageHeaderBytes, offset)) {
            return false;
        }
        return sIsExtendedWebpSupported;
    }

    public static boolean isAnimatedWebpHeader(final byte[] imageHeaderBytes, final int offset) {
        return matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8X_BYTES) && ((imageHeaderBytes[offset + 20] & 2) == 2);
    }

    public static boolean isSimpleWebpHeader(final byte[] imageHeaderBytes, final int offset) {
        return matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8_BYTES);
    }

    public static boolean isLosslessWebpHeader(final byte[] imageHeaderBytes, final int offset) {
        return matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8L_BYTES);
    }

    public static boolean isExtendedWebpHeader(final byte[] imageHeaderBytes, final int offset, final int headerSize) {
        return headerSize >= 21 && matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8X_BYTES);
    }

    public static boolean isExtendedWebpHeaderWithAlpha(final byte[] imageHeaderBytes, final int offset) {
        return matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8X_BYTES) && ((imageHeaderBytes[offset + 20] & 16) == 16);
    }

    public static boolean isWebpHeader(final byte[] imageHeaderBytes, final int offset, final int headerSize) {
        return headerSize >= 20 && matchBytePattern(imageHeaderBytes, offset, WEBP_RIFF_BYTES) && matchBytePattern(imageHeaderBytes, offset + 8, WEBP_NAME_BYTES);
    }

    private static boolean matchBytePattern(final byte[] byteArray, final int offset, final byte[] pattern) {
        if (pattern == null || byteArray == null || pattern.length + offset > byteArray.length) {
            return false;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (byteArray[i + offset] != pattern[i]) {
                return false;
            }
        }
        return true;
    }
}
