package com.facebook.react.bridge;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import androidx.core.content.res.ResourcesCompat;
import com.facebook.common.logging.FLog;
import com.facebook.react.common.ReactConstants;

/* JADX INFO: loaded from: classes.dex */
public class ColorPropConverter {
    private static final String ATTR = "attr";
    private static final String ATTR_SEGMENT = "attr/";
    private static final String JSON_KEY = "resource_paths";
    private static final String PACKAGE_DELIMITER = ":";
    private static final String PATH_DELIMITER = "/";
    private static final String PREFIX_ATTR = "?";
    private static final String PREFIX_RESOURCE = "@";

    public static Integer getColor(Object obj, Context context) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Double) {
            return Integer.valueOf(((Double) obj).intValue());
        }
        if (context == null) {
            throw new RuntimeException("Context may not be null.");
        }
        if (obj instanceof ReadableMap) {
            ReadableArray array = ((ReadableMap) obj).getArray(JSON_KEY);
            if (array == null) {
                throw new JSApplicationCausedNativeException("ColorValue: The `resource_paths` must be an array of color resource path strings.");
            }
            for (int i = 0; i < array.size(); i++) {
                Integer numResolveResourcePath = resolveResourcePath(context, array.getString(i));
                if (numResolveResourcePath != null) {
                    return numResolveResourcePath;
                }
            }
            throw new JSApplicationCausedNativeException("ColorValue: None of the paths in the `resource_paths` array resolved to a color resource.");
        }
        throw new JSApplicationCausedNativeException("ColorValue: the value must be a number or Object.");
    }

    public static Integer getColor(Object obj, Context context, int i) {
        try {
            return getColor(obj, context);
        } catch (JSApplicationCausedNativeException e) {
            FLog.w(ReactConstants.TAG, e, "Error converting ColorValue", new Object[0]);
            return Integer.valueOf(i);
        }
    }

    public static Integer resolveResourcePath(Context context, String str) {
        if (str != null && !str.isEmpty()) {
            boolean zStartsWith = str.startsWith(PREFIX_RESOURCE);
            boolean zStartsWith2 = str.startsWith(PREFIX_ATTR);
            String strSubstring = str.substring(1);
            try {
                if (zStartsWith) {
                    return Integer.valueOf(resolveResource(context, strSubstring));
                }
                if (zStartsWith2) {
                    return Integer.valueOf(resolveThemeAttribute(context, strSubstring));
                }
            } catch (Resources.NotFoundException unused) {
            }
        }
        return null;
    }

    private static int resolveResource(Context context, String str) {
        String[] strArrSplit = str.split(PACKAGE_DELIMITER);
        String packageName = context.getPackageName();
        if (strArrSplit.length > 1) {
            packageName = strArrSplit[0];
            str = strArrSplit[1];
        }
        String[] strArrSplit2 = str.split(PATH_DELIMITER);
        String str2 = strArrSplit2[0];
        return ResourcesCompat.getColor(context.getResources(), context.getResources().getIdentifier(strArrSplit2[1], str2, packageName), context.getTheme());
    }

    private static int resolveThemeAttribute(Context context, String str) {
        String strReplaceAll = str.replaceAll(ATTR_SEGMENT, "");
        String[] strArrSplit = strReplaceAll.split(PACKAGE_DELIMITER);
        String packageName = context.getPackageName();
        if (strArrSplit.length > 1) {
            packageName = strArrSplit[0];
            strReplaceAll = strArrSplit[1];
        }
        int identifier = context.getResources().getIdentifier(strReplaceAll, ATTR, packageName);
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(identifier, typedValue, true)) {
            return typedValue.data;
        }
        throw new Resources.NotFoundException();
    }
}
