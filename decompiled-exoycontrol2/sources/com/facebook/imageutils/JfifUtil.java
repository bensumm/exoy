package com.facebook.imageutils;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.facebook.common.internal.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class JfifUtil {
    public static final int APP1_EXIF_MAGIC = 1165519206;
    public static final int MARKER_APP1 = 225;
    public static final int MARKER_EOI = 217;
    public static final int MARKER_ESCAPE_BYTE = 0;
    public static final int MARKER_FIRST_BYTE = 255;
    public static final int MARKER_RST0 = 208;
    public static final int MARKER_RST7 = 215;
    public static final int MARKER_SOFn = 192;
    public static final int MARKER_SOI = 216;
    public static final int MARKER_SOS = 218;
    public static final int MARKER_TEM = 1;

    private static boolean isSOFn(int marker) {
        switch (marker) {
            case MARKER_SOFn /* 192 */:
            case 193:
            case 194:
            case 195:
            case 197:
            case 198:
            case 199:
            case 201:
            case 202:
            case 203:
            case 205:
            case 206:
            case 207:
                return true;
            case 196:
            case ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION /* 200 */:
            case 204:
            default:
                return false;
        }
    }

    private JfifUtil() {
    }

    public static int getAutoRotateAngleFromOrientation(int orientation) {
        return TiffUtil.getAutoRotateAngleFromOrientation(orientation);
    }

    public static int getOrientation(byte[] jpeg) {
        return getOrientation(new ByteArrayInputStream(jpeg));
    }

    public static int getOrientation(InputStream is) {
        try {
            int iMoveToAPP1EXIF = moveToAPP1EXIF(is);
            if (iMoveToAPP1EXIF == 0) {
                return 0;
            }
            return TiffUtil.readOrientationFromTIFF(is, iMoveToAPP1EXIF);
        } catch (IOException unused) {
            return 0;
        }
    }

    public static boolean moveToMarker(InputStream is, int markerToFind) throws IOException {
        Preconditions.checkNotNull(is);
        while (StreamProcessor.readPackedInt(is, 1, false) == 255) {
            int packedInt = 255;
            while (packedInt == 255) {
                packedInt = StreamProcessor.readPackedInt(is, 1, false);
            }
            if ((markerToFind == 192 && isSOFn(packedInt)) || packedInt == markerToFind) {
                return true;
            }
            if (packedInt != 216 && packedInt != 1) {
                if (packedInt == 217 || packedInt == 218) {
                    break;
                }
                is.skip(StreamProcessor.readPackedInt(is, 2, false) - 2);
            }
        }
        return false;
    }

    private static int moveToAPP1EXIF(InputStream is) throws IOException {
        int packedInt;
        if (moveToMarker(is, MARKER_APP1) && (packedInt = StreamProcessor.readPackedInt(is, 2, false) - 2) > 6) {
            int packedInt2 = StreamProcessor.readPackedInt(is, 4, false);
            int packedInt3 = StreamProcessor.readPackedInt(is, 2, false);
            int i = (packedInt - 4) - 2;
            if (packedInt2 == 1165519206 && packedInt3 == 0) {
                return i;
            }
        }
        return 0;
    }
}
