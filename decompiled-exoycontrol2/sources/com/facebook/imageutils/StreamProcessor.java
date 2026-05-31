package com.facebook.imageutils;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
class StreamProcessor {
    StreamProcessor() {
    }

    public static int readPackedInt(InputStream is, int numBytes, boolean isLittleEndian) throws IOException {
        int i;
        int i2 = 0;
        for (int i3 = 0; i3 < numBytes; i3++) {
            int i4 = is.read();
            if (i4 == -1) {
                throw new IOException("no more bytes");
            }
            if (isLittleEndian) {
                i = (i4 & 255) << (i3 * 8);
            } else {
                i2 <<= 8;
                i = i4 & 255;
            }
            i2 |= i;
        }
        return i2;
    }
}
