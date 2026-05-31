package com.facebook.common.media;

import com.facebook.common.internal.ImmutableMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class MediaUtils {
    public static final Map<String, String> ADDITIONAL_ALLOWED_MIME_TYPES = ImmutableMap.of("mkv", "video/x-matroska", "glb", "model/gltf-binary");

    public static boolean isPhoto(@Nullable String mimeType) {
        return mimeType != null && mimeType.startsWith("image/");
    }

    public static boolean isVideo(@Nullable String mimeType) {
        return mimeType != null && mimeType.startsWith("video/");
    }

    public static boolean isThreeD(@Nullable String mimeType) {
        return mimeType != null && mimeType.equals("model/gltf-binary");
    }

    @Nullable
    public static String extractMime(String path) {
        String strExtractExtension = extractExtension(path);
        if (strExtractExtension == null) {
            return null;
        }
        String lowerCase = strExtractExtension.toLowerCase(Locale.US);
        String mimeTypeFromExtension = MimeTypeMapWrapper.getMimeTypeFromExtension(lowerCase);
        return mimeTypeFromExtension == null ? ADDITIONAL_ALLOWED_MIME_TYPES.get(lowerCase) : mimeTypeFromExtension;
    }

    @Nullable
    private static String extractExtension(String path) {
        int iLastIndexOf = path.lastIndexOf(46);
        if (iLastIndexOf < 0 || iLastIndexOf == path.length() - 1) {
            return null;
        }
        return path.substring(iLastIndexOf + 1);
    }

    public static boolean isNonNativeSupportedMimeType(String mimeType) {
        return ADDITIONAL_ALLOWED_MIME_TYPES.containsValue(mimeType);
    }
}
