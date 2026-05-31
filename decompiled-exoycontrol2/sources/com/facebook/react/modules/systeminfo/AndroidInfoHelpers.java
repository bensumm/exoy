package com.facebook.react.modules.systeminfo;

import android.content.Context;
import android.os.Build;
import com.facebook.react.R;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class AndroidInfoHelpers {
    public static final String DEVICE_LOCALHOST = "localhost";
    public static final String EMULATOR_LOCALHOST = "10.0.2.2";
    public static final String GENYMOTION_LOCALHOST = "10.0.3.2";
    public static final String METRO_HOST_PROP_NAME = "metro.host";
    private static final String TAG = "AndroidInfoHelpers";
    private static String metroHostPropValue;

    private static boolean isRunningOnGenymotion() {
        return Build.FINGERPRINT.contains("vbox");
    }

    private static boolean isRunningOnStockEmulator() {
        return Build.FINGERPRINT.contains("generic") || Build.FINGERPRINT.startsWith("google/sdk_gphone");
    }

    public static String getServerHost(Integer num) {
        return getServerIpAddress(num.intValue());
    }

    public static String getServerHost(Context context) {
        return getServerIpAddress(getDevServerPort(context).intValue());
    }

    public static String getAdbReverseTcpCommand(Integer num) {
        return "adb reverse tcp:" + num + " tcp:" + num;
    }

    public static String getAdbReverseTcpCommand(Context context) {
        return getAdbReverseTcpCommand(getDevServerPort(context));
    }

    public static String getInspectorProxyHost(Context context) {
        return getServerIpAddress(getInspectorProxyPort(context).intValue());
    }

    public static String getFriendlyDeviceName() {
        if (isRunningOnGenymotion()) {
            return Build.MODEL;
        }
        return Build.MODEL + " - " + Build.VERSION.RELEASE + " - API " + Build.VERSION.SDK_INT;
    }

    private static Integer getDevServerPort(Context context) {
        return Integer.valueOf(context.getResources().getInteger(R.integer.react_native_dev_server_port));
    }

    private static Integer getInspectorProxyPort(Context context) {
        return Integer.valueOf(context.getResources().getInteger(R.integer.react_native_dev_server_port));
    }

    private static String getServerIpAddress(int i) {
        String metroHostPropValue2 = getMetroHostPropValue();
        if (metroHostPropValue2.equals("")) {
            if (isRunningOnGenymotion()) {
                metroHostPropValue2 = GENYMOTION_LOCALHOST;
            } else {
                metroHostPropValue2 = isRunningOnStockEmulator() ? EMULATOR_LOCALHOST : DEVICE_LOCALHOST;
            }
        }
        return String.format(Locale.US, "%s:%d", metroHostPropValue2, Integer.valueOf(i));
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:8|62|9|60|10|11|(7:57|12|(1:14)(1:64)|18|35|36|37)|15|50|16|(1:18)|35|36|37) */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0077 A[Catch: all -> 0x007b, TRY_ENTER, TryCatch #5 {, blocks: (B:4:0x0003, B:16:0x003a, B:18:0x003f, B:35:0x006b, B:32:0x0065, B:40:0x0072, B:42:0x0077, B:43:0x007a), top: B:56:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0072 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:? A[Catch: all -> 0x007b, SYNTHETIC, TRY_LEAVE, TryCatch #5 {, blocks: (B:4:0x0003, B:16:0x003a, B:18:0x003f, B:35:0x006b, B:32:0x0065, B:40:0x0072, B:42:0x0077, B:43:0x007a), top: B:56:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static synchronized java.lang.String getMetroHostPropValue() {
        /*
            java.lang.Class<com.facebook.react.modules.systeminfo.AndroidInfoHelpers> r0 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.class
            monitor-enter(r0)
            java.lang.String r1 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.metroHostPropValue     // Catch: java.lang.Throwable -> L7b
            if (r1 == 0) goto L9
            monitor-exit(r0)
            return r1
        L9:
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L54
            java.lang.String r3 = "/system/bin/getprop"
            java.lang.String r4 = "metro.host"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4}     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L54
            java.lang.Process r2 = r2.exec(r3)     // Catch: java.lang.Throwable -> L4f java.lang.Exception -> L54
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4a
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4a
            java.io.InputStream r5 = r2.getInputStream()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4a
            java.lang.String r6 = "UTF-8"
            java.nio.charset.Charset r6 = java.nio.charset.Charset.forName(r6)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4a
            r4.<init>(r5, r6)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4a
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L4a
            java.lang.String r1 = ""
        L30:
            java.lang.String r4 = r3.readLine()     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L6f
            if (r4 == 0) goto L38
            r1 = r4
            goto L30
        L38:
            com.facebook.react.modules.systeminfo.AndroidInfoHelpers.metroHostPropValue = r1     // Catch: java.lang.Exception -> L43 java.lang.Throwable -> L6f
            r3.close()     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L7b
        L3d:
            if (r2 == 0) goto L6b
        L3f:
            r2.destroy()     // Catch: java.lang.Throwable -> L7b
            goto L6b
        L43:
            r1 = move-exception
            goto L58
        L45:
            r3 = move-exception
            r7 = r3
            r3 = r1
            r1 = r7
            goto L70
        L4a:
            r3 = move-exception
            r7 = r3
            r3 = r1
            r1 = r7
            goto L58
        L4f:
            r2 = move-exception
            r3 = r1
            r1 = r2
            r2 = r3
            goto L70
        L54:
            r2 = move-exception
            r3 = r1
            r1 = r2
            r2 = r3
        L58:
            java.lang.String r4 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.TAG     // Catch: java.lang.Throwable -> L6f
            java.lang.String r5 = "Failed to query for metro.host prop:"
            com.facebook.common.logging.FLog.w(r4, r5, r1)     // Catch: java.lang.Throwable -> L6f
            java.lang.String r1 = ""
            com.facebook.react.modules.systeminfo.AndroidInfoHelpers.metroHostPropValue = r1     // Catch: java.lang.Throwable -> L6f
            if (r3 == 0) goto L68
            r3.close()     // Catch: java.lang.Exception -> L68 java.lang.Throwable -> L7b
        L68:
            if (r2 == 0) goto L6b
            goto L3f
        L6b:
            java.lang.String r1 = com.facebook.react.modules.systeminfo.AndroidInfoHelpers.metroHostPropValue     // Catch: java.lang.Throwable -> L7b
            monitor-exit(r0)
            return r1
        L6f:
            r1 = move-exception
        L70:
            if (r3 == 0) goto L75
            r3.close()     // Catch: java.lang.Exception -> L75 java.lang.Throwable -> L7b
        L75:
            if (r2 == 0) goto L7a
            r2.destroy()     // Catch: java.lang.Throwable -> L7b
        L7a:
            throw r1     // Catch: java.lang.Throwable -> L7b
        L7b:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.systeminfo.AndroidInfoHelpers.getMetroHostPropValue():java.lang.String");
    }
}
