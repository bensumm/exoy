package com.facebook.soloader;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.soloader.SysUtil;
import com.facebook.soloader.nativeloader.NativeLoader;
import com.facebook.soloader.nativeloader.SystemDelegate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class SoLoader {
    static final boolean DEBUG = false;
    public static final int SOLOADER_ALLOW_ASYNC_INIT = 2;
    public static final int SOLOADER_DISABLE_BACKUP_SOSOURCE = 8;
    public static final int SOLOADER_DISABLE_FS_SYNC_JOB = 256;
    public static final int SOLOADER_DONT_TREAT_AS_SYSTEMAPP = 32;
    public static final int SOLOADER_ENABLE_DIRECT_SOSOURCE = 64;
    public static final int SOLOADER_ENABLE_EXOPACKAGE = 1;
    public static final int SOLOADER_EXPLICITLY_ENABLE_BACKUP_SOSOURCE = 128;
    public static final int SOLOADER_LOOK_IN_ZIP = 4;
    public static final int SOLOADER_SKIP_MERGED_JNI_ONLOAD = 16;
    private static final String SO_STORE_NAME_MAIN = "lib-main";
    private static final String SO_STORE_NAME_SPLIT = "lib-";
    static final String TAG = "SoLoader";
    public static String VERSION = "0.10.5";

    @Nullable
    private static ApplicationSoSource sApplicationSoSource;

    @Nullable
    private static UnpackingSoSource[] sBackupSoSources;
    private static int sFlags;

    @Nullable
    static SoFileLoader sSoFileLoader;
    private static final ReentrantReadWriteLock sSoSourcesLock = new ReentrantReadWriteLock();

    @Nullable
    private static volatile SoSource[] sSoSources = null;
    private static final AtomicInteger sSoSourcesVersion = new AtomicInteger(0);
    private static final HashSet<String> sLoadedLibraries = new HashSet<>();
    private static final Map<String, Object> sLoadingLibraries = new HashMap();
    private static final Set<String> sLoadedAndMergedLibraries = Collections.newSetFromMap(new ConcurrentHashMap());

    @Nullable
    private static SystemLoadLibraryWrapper sSystemLoadLibraryWrapper = null;
    private static boolean isEnabled = true;
    private static final String[] DEFAULT_DENY_LIST = {System.mapLibraryName("breakpad")};
    private static int sAppType = 0;
    static final boolean SYSTRACE_LIBRARY_LOADING = true;

    interface AppType {
        public static final int SYSTEM_APP = 2;
        public static final int THIRD_PARTY_APP = 1;
        public static final int UNSET = 0;
        public static final int UPDATED_SYSTEM_APP = 3;
    }

    public static void init(Context context, int i) throws IOException {
        init(context, i, null, DEFAULT_DENY_LIST);
    }

    public static void init(Context context, int i, @Nullable SoFileLoader soFileLoader) throws IOException {
        init(context, i, soFileLoader, DEFAULT_DENY_LIST);
    }

    public static void init(Context context, int i, @Nullable SoFileLoader soFileLoader, String[] strArr) throws IOException {
        if (isInitialized()) {
            return;
        }
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskWrites = StrictMode.allowThreadDiskWrites();
        try {
            boolean zInitEnableConfig = initEnableConfig(context);
            isEnabled = zInitEnableConfig;
            if (zInitEnableConfig) {
                int appType = getAppType(context, i);
                sAppType = appType;
                if ((i & 128) == 0 && SysUtil.isSupportedDirectLoad(context, appType)) {
                    i |= 72;
                }
                initSoLoader(soFileLoader);
                initSoSources(context, i, strArr);
                Log.v(TAG, "Init SoLoader delegate");
                NativeLoader.initIfUninitialized(new NativeLoaderToSoLoaderDelegate());
            } else {
                initDummySoSource();
                Log.v(TAG, "Init System Loader delegate");
                NativeLoader.initIfUninitialized(new SystemDelegate());
            }
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskWrites);
        }
    }

    public static void init(Context context, boolean z) {
        try {
            init(context, z ? 1 : 0, null, DEFAULT_DENY_LIST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean initEnableConfig(Context context) {
        String packageName;
        Bundle bundle = null;
        try {
            packageName = context.getPackageName();
            try {
                bundle = context.getPackageManager().getApplicationInfo(packageName, 128).metaData;
            } catch (Exception e) {
                e = e;
                Log.w(TAG, "Unexpected issue with package manager (" + packageName + ")", e);
            }
        } catch (Exception e2) {
            e = e2;
            packageName = null;
        }
        return bundle == null || bundle.getBoolean("com.facebook.soloader.enabled", true);
    }

    private static void initSoSources(Context context, int i, String[] strArr) throws IOException {
        if (sSoSources != null) {
            return;
        }
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.writeLock().lock();
        try {
            if (sSoSources == null) {
                sFlags = i;
                ArrayList arrayList = new ArrayList();
                AddSystemLibSoSource(arrayList, strArr);
                if (context != null) {
                    if ((i & 1) != 0) {
                        addApplicationSoSource(context, arrayList, getApplicationSoSourceFlags());
                        sBackupSoSources = null;
                        if (Log.isLoggable(TAG, 3)) {
                            Log.d(TAG, "adding exo package source: lib-main");
                        }
                        arrayList.add(0, new ExoSoSource(context, SO_STORE_NAME_MAIN));
                    } else {
                        if ((i & 64) != 0) {
                            addDirectApkSoSource(context, arrayList);
                        }
                        addApplicationSoSource(context, arrayList, getApplicationSoSourceFlags());
                        AddBackupSoSource(context, arrayList, 1);
                    }
                }
                SoSource[] soSourceArr = (SoSource[]) arrayList.toArray(new SoSource[arrayList.size()]);
                int iMakePrepareFlags = makePrepareFlags();
                int length = soSourceArr.length;
                while (true) {
                    int i2 = length - 1;
                    if (length <= 0) {
                        break;
                    }
                    if (Log.isLoggable(TAG, 3)) {
                        Log.d(TAG, "Preparing SO source: " + soSourceArr[i2]);
                    }
                    boolean z = SYSTRACE_LIBRARY_LOADING;
                    if (z) {
                        Api18TraceUtils.beginTraceSection(TAG, "_", soSourceArr[i2].getClass().getSimpleName());
                    }
                    soSourceArr[i2].prepare(iMakePrepareFlags);
                    if (z) {
                        Api18TraceUtils.endSection();
                    }
                    length = i2;
                }
                sSoSources = soSourceArr;
                sSoSourcesVersion.getAndIncrement();
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "init finish: " + sSoSources.length + " SO sources prepared");
                }
                return;
            }
            reentrantReadWriteLock.writeLock().unlock();
        } finally {
            sSoSourcesLock.writeLock().unlock();
        }
    }

    private static void initDummySoSource() {
        if (sSoSources != null) {
            return;
        }
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.writeLock().lock();
        try {
            if (sSoSources == null) {
                sSoSources = new SoSource[0];
                reentrantReadWriteLock.writeLock().unlock();
            } else {
                reentrantReadWriteLock.writeLock().unlock();
            }
        } catch (Throwable th) {
            sSoSourcesLock.writeLock().unlock();
            throw th;
        }
    }

    private static int getApplicationSoSourceFlags() {
        int i = sAppType;
        if (i == 1) {
            return 0;
        }
        if (i == 2 || i == 3) {
            return 1;
        }
        throw new RuntimeException("Unsupported app type, we should not reach here");
    }

    private static void addDirectApkSoSource(Context context, ArrayList<SoSource> arrayList) {
        if (context.getApplicationInfo().splitSourceDirs != null) {
            for (String str : context.getApplicationInfo().splitSourceDirs) {
                DirectApkSoSource directApkSoSource = new DirectApkSoSource(new File(str));
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "validating/adding directApk source from splitApk: " + directApkSoSource.toString());
                }
                if (directApkSoSource.isValid()) {
                    arrayList.add(0, directApkSoSource);
                }
            }
        }
        DirectApkSoSource directApkSoSource2 = new DirectApkSoSource(context);
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "validating/adding directApk source: " + directApkSoSource2.toString());
        }
        if (directApkSoSource2.isValid()) {
            arrayList.add(0, directApkSoSource2);
        }
    }

    private static void addApplicationSoSource(Context context, ArrayList<SoSource> arrayList, int i) {
        sApplicationSoSource = new ApplicationSoSource(context, i);
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "adding application source: " + sApplicationSoSource.toString());
        }
        arrayList.add(0, sApplicationSoSource);
    }

    private static void AddBackupSoSource(Context context, ArrayList<SoSource> arrayList, int i) throws IOException {
        if ((sFlags & 8) != 0) {
            sBackupSoSources = null;
            File soStorePath = UnpackingSoSource.getSoStorePath(context, SO_STORE_NAME_MAIN);
            try {
                SysUtil.dumbDeleteRecursive(soStorePath);
                return;
            } catch (IOException e) {
                Log.w(TAG, "Failed to delete " + soStorePath.getCanonicalPath(), e);
                return;
            }
        }
        File file = new File(context.getApplicationInfo().sourceDir);
        ArrayList arrayList2 = new ArrayList();
        ApkSoSource apkSoSource = new ApkSoSource(context, file, SO_STORE_NAME_MAIN, i);
        arrayList2.add(apkSoSource);
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "adding backup source from : " + apkSoSource.toString());
        }
        addBackupSoSourceFromSplitApk(context, i, arrayList2);
        sBackupSoSources = (UnpackingSoSource[]) arrayList2.toArray(new UnpackingSoSource[arrayList2.size()]);
        arrayList.addAll(0, arrayList2);
    }

    private static void addBackupSoSourceFromSplitApk(Context context, int i, ArrayList<UnpackingSoSource> arrayList) {
        if (context.getApplicationInfo().splitSourceDirs != null) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "adding backup sources from split apks");
            }
            String[] strArr = context.getApplicationInfo().splitSourceDirs;
            int length = strArr.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                File file = new File(strArr[i2]);
                StringBuilder sb = new StringBuilder(SO_STORE_NAME_SPLIT);
                int i4 = i3 + 1;
                sb.append(i3);
                ApkSoSource apkSoSource = new ApkSoSource(context, file, sb.toString(), i);
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "adding backup source: " + apkSoSource.toString());
                }
                arrayList.add(apkSoSource);
                i2++;
                i3 = i4;
            }
        }
    }

    private static void AddSystemLibSoSource(ArrayList<SoSource> arrayList, String[] strArr) {
        String str = SysUtil.is64Bit() ? "/system/lib64:/vendor/lib64" : "/system/lib:/vendor/lib";
        String str2 = System.getenv("LD_LIBRARY_PATH");
        if (str2 != null && !str2.equals("")) {
            str = str + ":" + str2;
        }
        for (String str3 : new HashSet(Arrays.asList(str.split(":")))) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "adding system library source: " + str3);
            }
            arrayList.add(new DirectorySoSource(new File(str3), 2, strArr));
        }
    }

    private static int makePrepareFlags() {
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.writeLock().lock();
        try {
            int i = sFlags;
            int i2 = (i & 2) != 0 ? 1 : 0;
            if ((i & 256) != 0) {
                i2 |= 4;
            }
            reentrantReadWriteLock.writeLock().unlock();
            return i2;
        } catch (Throwable th) {
            sSoSourcesLock.writeLock().unlock();
            throw th;
        }
    }

    private static synchronized void initSoLoader(@Nullable SoFileLoader soFileLoader) {
        if (soFileLoader == null) {
            if (sSoFileLoader != null) {
                return;
            }
        }
        if (soFileLoader != null) {
            sSoFileLoader = soFileLoader;
            return;
        }
        final Runtime runtime = Runtime.getRuntime();
        final Method nativeLoadRuntimeMethod = getNativeLoadRuntimeMethod();
        final boolean z = nativeLoadRuntimeMethod != null;
        final String classLoaderLdLoadLibrary = z ? SysUtil.Api14Utils.getClassLoaderLdLoadLibrary() : null;
        final String strMakeNonZipPath = makeNonZipPath(classLoaderLdLoadLibrary);
        sSoFileLoader = new SoFileLoader() { // from class: com.facebook.soloader.SoLoader.1
            @Override // com.facebook.soloader.SoFileLoader
            public void loadBytes(String str, ElfByteChannel elfByteChannel, int i) {
                throw new UnsupportedOperationException();
            }

            /* JADX WARN: Code restructure failed: missing block: B:17:0x0035, code lost:
            
                if (r1 == null) goto L56;
             */
            /* JADX WARN: Code restructure failed: missing block: B:18:0x0037, code lost:
            
                android.util.Log.e(com.facebook.soloader.SoLoader.TAG, "Error when loading lib: " + r1 + " lib hash: " + getLibHash(r9) + " search path is " + r10);
             */
            /* JADX WARN: Code restructure failed: missing block: B:56:?, code lost:
            
                return;
             */
            /* JADX WARN: Code restructure failed: missing block: B:57:?, code lost:
            
                return;
             */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:45:0x009b  */
            /* JADX WARN: Type inference failed for: r1v0 */
            /* JADX WARN: Type inference failed for: r1v2 */
            @Override // com.facebook.soloader.SoFileLoader
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void load(java.lang.String r9, int r10) throws java.lang.Throwable {
                /*
                    r8 = this;
                    boolean r0 = r1
                    if (r0 == 0) goto Lc3
                    r0 = 4
                    r10 = r10 & r0
                    r1 = 1
                    r2 = 0
                    if (r10 != r0) goto Lc
                    r10 = r1
                    goto Ld
                Lc:
                    r10 = r2
                Ld:
                    if (r10 == 0) goto L12
                    java.lang.String r10 = r2
                    goto L14
                L12:
                    java.lang.String r10 = r3
                L14:
                    r0 = 0
                    java.lang.Runtime r3 = r4     // Catch: java.lang.Throwable -> L74 java.lang.reflect.InvocationTargetException -> L79 java.lang.IllegalArgumentException -> L7b java.lang.IllegalAccessException -> L7d
                    monitor-enter(r3)     // Catch: java.lang.Throwable -> L74 java.lang.reflect.InvocationTargetException -> L79 java.lang.IllegalArgumentException -> L7b java.lang.IllegalAccessException -> L7d
                    java.lang.reflect.Method r4 = r5     // Catch: java.lang.Throwable -> L66
                    java.lang.Runtime r5 = r4     // Catch: java.lang.Throwable -> L66
                    r6 = 3
                    java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch: java.lang.Throwable -> L66
                    r6[r2] = r9     // Catch: java.lang.Throwable -> L66
                    java.lang.Class<com.facebook.soloader.SoLoader> r2 = com.facebook.soloader.SoLoader.class
                    java.lang.ClassLoader r2 = r2.getClassLoader()     // Catch: java.lang.Throwable -> L66
                    r6[r1] = r2     // Catch: java.lang.Throwable -> L66
                    r1 = 2
                    r6[r1] = r10     // Catch: java.lang.Throwable -> L66
                    java.lang.Object r1 = r4.invoke(r5, r6)     // Catch: java.lang.Throwable -> L66
                    java.lang.String r1 = (java.lang.String) r1     // Catch: java.lang.Throwable -> L66
                    if (r1 != 0) goto L60
                    monitor-exit(r3)     // Catch: java.lang.Throwable -> L72
                    if (r1 == 0) goto Lc6
                    java.lang.String r0 = "SoLoader"
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder
                    java.lang.String r3 = "Error when loading lib: "
                    r2.<init>(r3)
                    r2.append(r1)
                    java.lang.String r1 = " lib hash: "
                    r2.append(r1)
                    java.lang.String r9 = r8.getLibHash(r9)
                    r2.append(r9)
                    java.lang.String r9 = " search path is "
                    r2.append(r9)
                    r2.append(r10)
                    java.lang.String r9 = r2.toString()
                    android.util.Log.e(r0, r9)
                    goto Lc6
                L60:
                    java.lang.UnsatisfiedLinkError r0 = new java.lang.UnsatisfiedLinkError     // Catch: java.lang.Throwable -> L72
                    r0.<init>(r1)     // Catch: java.lang.Throwable -> L72
                    throw r0     // Catch: java.lang.Throwable -> L72
                L66:
                    r1 = move-exception
                    r7 = r1
                    r1 = r0
                    r0 = r7
                L6a:
                    monitor-exit(r3)     // Catch: java.lang.Throwable -> L72
                    throw r0     // Catch: java.lang.reflect.InvocationTargetException -> L6c java.lang.IllegalArgumentException -> L6e java.lang.IllegalAccessException -> L70 java.lang.Throwable -> L98
                L6c:
                    r0 = move-exception
                    goto L81
                L6e:
                    r0 = move-exception
                    goto L81
                L70:
                    r0 = move-exception
                    goto L81
                L72:
                    r0 = move-exception
                    goto L6a
                L74:
                    r1 = move-exception
                    r7 = r1
                    r1 = r0
                    r0 = r7
                    goto L99
                L79:
                    r1 = move-exception
                    goto L7e
                L7b:
                    r1 = move-exception
                    goto L7e
                L7d:
                    r1 = move-exception
                L7e:
                    r7 = r1
                    r1 = r0
                    r0 = r7
                L81:
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L98
                    r2.<init>()     // Catch: java.lang.Throwable -> L98
                    java.lang.String r3 = "Error: Cannot load "
                    r2.append(r3)     // Catch: java.lang.Throwable -> L98
                    r2.append(r9)     // Catch: java.lang.Throwable -> L98
                    java.lang.String r1 = r2.toString()     // Catch: java.lang.Throwable -> L98
                    java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L98
                    r2.<init>(r1, r0)     // Catch: java.lang.Throwable -> L98
                    throw r2     // Catch: java.lang.Throwable -> L98
                L98:
                    r0 = move-exception
                L99:
                    if (r1 == 0) goto Lc2
                    java.lang.String r2 = "SoLoader"
                    java.lang.StringBuilder r3 = new java.lang.StringBuilder
                    java.lang.String r4 = "Error when loading lib: "
                    r3.<init>(r4)
                    r3.append(r1)
                    java.lang.String r1 = " lib hash: "
                    r3.append(r1)
                    java.lang.String r9 = r8.getLibHash(r9)
                    r3.append(r9)
                    java.lang.String r9 = " search path is "
                    r3.append(r9)
                    r3.append(r10)
                    java.lang.String r9 = r3.toString()
                    android.util.Log.e(r2, r9)
                Lc2:
                    throw r0
                Lc3:
                    java.lang.System.load(r9)
                Lc6:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.AnonymousClass1.load(java.lang.String, int):void");
            }

            private String getLibHash(String str) {
                try {
                    File file = new File(str);
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int i = fileInputStream.read(bArr);
                            if (i > 0) {
                                messageDigest.update(bArr, 0, i);
                            } else {
                                String str2 = String.format("%32x", new BigInteger(1, messageDigest.digest()));
                                fileInputStream.close();
                                return str2;
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException | SecurityException | NoSuchAlgorithmException e) {
                    return e.toString();
                }
            }
        };
    }

    @Nullable
    private static Method getNativeLoadRuntimeMethod() {
        if (Build.VERSION.SDK_INT > 27) {
            return null;
        }
        try {
            Method declaredMethod = Runtime.class.getDeclaredMethod("nativeLoad", String.class, ClassLoader.class, String.class);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (NoSuchMethodException | SecurityException e) {
            Log.w(TAG, "Cannot get nativeLoad method", e);
            return null;
        }
    }

    private static int getAppType(Context context, int i) {
        int i2 = sAppType;
        if (i2 != 0) {
            return i2;
        }
        int i3 = 1;
        if ((i & 32) == 0 && context != null) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if ((applicationInfo.flags & 1) != 0) {
                i3 = (applicationInfo.flags & 128) != 0 ? 3 : 2;
            }
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "ApplicationInfo.flags is: " + applicationInfo.flags + " appType is: " + i3);
            }
        }
        return i3;
    }

    public static void setInTestMode() {
        TestOnlyUtils.setSoSources(new SoSource[]{new NoopSoSource()});
    }

    public static void deinitForTest() {
        TestOnlyUtils.setSoSources(null);
    }

    static class TestOnlyUtils {
        TestOnlyUtils() {
        }

        static void setSoSources(SoSource[] soSourceArr) {
            SoLoader.sSoSourcesLock.writeLock().lock();
            try {
                SoSource[] unused = SoLoader.sSoSources = soSourceArr;
                SoLoader.sSoSourcesVersion.getAndIncrement();
            } finally {
                SoLoader.sSoSourcesLock.writeLock().unlock();
            }
        }

        static void setSoFileLoader(SoFileLoader soFileLoader) {
            SoLoader.sSoFileLoader = soFileLoader;
        }

        static void resetStatus() {
            synchronized (SoLoader.class) {
                SoLoader.sLoadedLibraries.clear();
                SoLoader.sLoadingLibraries.clear();
                SoLoader.sSoFileLoader = null;
            }
            setSoSources(null);
        }
    }

    public static void setSystemLoadLibraryWrapper(SystemLoadLibraryWrapper systemLoadLibraryWrapper) {
        sSystemLoadLibraryWrapper = systemLoadLibraryWrapper;
    }

    public static final class WrongAbiError extends UnsatisfiedLinkError {
        WrongAbiError(Throwable th, String str) {
            super("APK was built for a different platform. Supported ABIs: " + Arrays.toString(SysUtil.getSupportedAbis()) + " error: " + str);
            initCause(th);
        }
    }

    @Nullable
    public static String getLibraryPath(String str) throws IOException {
        sSoSourcesLock.readLock().lock();
        try {
            String libraryPath = null;
            if (sSoSources != null) {
                int i = 0;
                while (libraryPath == null) {
                    if (i >= sSoSources.length) {
                        break;
                    }
                    libraryPath = sSoSources[i].getLibraryPath(str);
                    i++;
                }
            }
            return libraryPath;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    @Nullable
    public static String[] getLibraryDependencies(String str) throws IOException {
        sSoSourcesLock.readLock().lock();
        try {
            String[] libraryDependencies = null;
            if (sSoSources != null) {
                int i = 0;
                while (libraryDependencies == null) {
                    if (i >= sSoSources.length) {
                        break;
                    }
                    libraryDependencies = sSoSources[i].getLibraryDependencies(str);
                    i++;
                }
            }
            return libraryDependencies;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    @Nullable
    public static File getSoFile(String str) {
        File soFileByName;
        String strMapLibName = MergedSoMapping.mapLibName(str);
        if (strMapLibName != null) {
            str = strMapLibName;
        }
        String strMapLibraryName = System.mapLibraryName(str);
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources != null) {
                for (int i = 0; i < sSoSources.length; i++) {
                    try {
                        soFileByName = sSoSources[i].getSoFileByName(strMapLibraryName);
                    } catch (IOException unused) {
                    }
                    if (soFileByName != null) {
                        return soFileByName;
                    }
                }
            }
            sSoSourcesLock.readLock().unlock();
            return null;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    public static boolean loadLibrary(String str) {
        return isEnabled ? loadLibrary(str, 0) : NativeLoader.loadLibrary(str);
    }

    public static boolean loadLibrary(String str, int i) throws UnsatisfiedLinkError {
        SystemLoadLibraryWrapper systemLoadLibraryWrapper;
        Boolean boolLoadLibraryOnNonAndroid = loadLibraryOnNonAndroid(str);
        if (boolLoadLibraryOnNonAndroid != null) {
            return boolLoadLibraryOnNonAndroid.booleanValue();
        }
        if (!isEnabled) {
            return NativeLoader.loadLibrary(str);
        }
        int i2 = sAppType;
        if ((i2 == 2 || i2 == 3) && (systemLoadLibraryWrapper = sSystemLoadLibraryWrapper) != null) {
            systemLoadLibraryWrapper.loadLibrary(str);
            return true;
        }
        String strMapLibName = MergedSoMapping.mapLibName(str);
        return loadLibraryBySoName(System.mapLibraryName(strMapLibName != null ? strMapLibName : str), str, strMapLibName, i, null);
    }

    @Nullable
    private static Boolean loadLibraryOnNonAndroid(String str) {
        Boolean boolValueOf;
        if (sSoSources != null) {
            return null;
        }
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.readLock().lock();
        try {
            if (sSoSources == null) {
                if ("http://www.android.com/".equals(System.getProperty("java.vendor.url"))) {
                    assertInitialized();
                } else {
                    synchronized (SoLoader.class) {
                        boolean z = !sLoadedLibraries.contains(str);
                        if (z) {
                            SystemLoadLibraryWrapper systemLoadLibraryWrapper = sSystemLoadLibraryWrapper;
                            if (systemLoadLibraryWrapper != null) {
                                systemLoadLibraryWrapper.loadLibrary(str);
                            } else {
                                System.loadLibrary(str);
                            }
                        }
                        boolValueOf = Boolean.valueOf(z);
                    }
                    reentrantReadWriteLock.readLock().unlock();
                    return boolValueOf;
                }
            }
            reentrantReadWriteLock.readLock().unlock();
            return null;
        } catch (Throwable th) {
            sSoSourcesLock.readLock().unlock();
            throw th;
        }
    }

    static void loadLibraryBySoName(String str, int i, StrictMode.ThreadPolicy threadPolicy) {
        loadLibraryBySoNameImpl(str, null, null, i, threadPolicy);
    }

    private static boolean loadLibraryBySoName(String str, @Nullable String str2, @Nullable String str3, int i, @Nullable StrictMode.ThreadPolicy threadPolicy) {
        boolean z;
        boolean zLoadLibraryBySoNameImpl = false;
        do {
            try {
                zLoadLibraryBySoNameImpl = loadLibraryBySoNameImpl(str, str2, str3, i, threadPolicy);
                z = false;
            } catch (UnsatisfiedLinkError e) {
                int i2 = sSoSourcesVersion.get();
                sSoSourcesLock.writeLock().lock();
                try {
                    try {
                        if (sApplicationSoSource == null || !sApplicationSoSource.checkAndMaybeUpdate()) {
                            z = false;
                        } else {
                            Log.w(TAG, "sApplicationSoSource updated during load: " + str + ", attempting load again.");
                            sSoSourcesVersion.getAndIncrement();
                            z = true;
                        }
                        sSoSourcesLock.writeLock().unlock();
                        if (sSoSourcesVersion.get() == i2) {
                            throw e;
                        }
                    } catch (IOException e2) {
                        throw new RuntimeException(e2);
                    }
                } catch (Throwable th) {
                    sSoSourcesLock.writeLock().unlock();
                    throw th;
                }
            }
        } while (z);
        return zLoadLibraryBySoNameImpl;
    }

    private static boolean loadLibraryBySoNameImpl(String str, @Nullable String str2, @Nullable String str3, int i, @Nullable StrictMode.ThreadPolicy threadPolicy) {
        boolean z;
        Object obj;
        boolean z2 = false;
        if (!TextUtils.isEmpty(str2) && sLoadedAndMergedLibraries.contains(str2)) {
            return false;
        }
        synchronized (SoLoader.class) {
            HashSet<String> hashSet = sLoadedLibraries;
            if (!hashSet.contains(str)) {
                z = false;
            } else {
                if (str3 == null) {
                    return false;
                }
                z = true;
            }
            Map<String, Object> map = sLoadingLibraries;
            if (map.containsKey(str)) {
                obj = map.get(str);
            } else {
                Object obj2 = new Object();
                map.put(str, obj2);
                obj = obj2;
            }
            ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
            reentrantReadWriteLock.readLock().lock();
            try {
                synchronized (obj) {
                    if (!z) {
                        try {
                            synchronized (SoLoader.class) {
                                if (hashSet.contains(str)) {
                                    if (str3 == null) {
                                        reentrantReadWriteLock.readLock().unlock();
                                        return false;
                                    }
                                    z = true;
                                }
                                if (!z) {
                                    try {
                                        if (Log.isLoggable(TAG, 3)) {
                                            Log.d(TAG, "About to load: " + str);
                                        }
                                        doLoadLibraryBySoName(str, i, threadPolicy);
                                        if (Log.isLoggable(TAG, 3)) {
                                            Log.d(TAG, "Loaded: " + str);
                                        }
                                        synchronized (SoLoader.class) {
                                            hashSet.add(str);
                                        }
                                    } catch (UnsatisfiedLinkError e) {
                                        String message = e.getMessage();
                                        if (message != null && message.contains("unexpected e_machine:")) {
                                            throw new WrongAbiError(e, message.substring(message.lastIndexOf("unexpected e_machine:")));
                                        }
                                        throw e;
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                    if ((i & 16) == 0) {
                        if (!TextUtils.isEmpty(str2) && sLoadedAndMergedLibraries.contains(str2)) {
                            z2 = true;
                        }
                        if (str3 != null && !z2) {
                            boolean z3 = SYSTRACE_LIBRARY_LOADING;
                            if (z3) {
                                Api18TraceUtils.beginTraceSection("MergedSoMapping.invokeJniOnload[", str2, "]");
                            }
                            try {
                                try {
                                    if (Log.isLoggable(TAG, 3)) {
                                        Log.d(TAG, "About to merge: " + str2 + " / " + str);
                                    }
                                    MergedSoMapping.invokeJniOnload(str2);
                                    sLoadedAndMergedLibraries.add(str2);
                                    if (z3) {
                                        Api18TraceUtils.endSection();
                                    }
                                } catch (UnsatisfiedLinkError e2) {
                                    throw new RuntimeException("Failed to call JNI_OnLoad from '" + str2 + "', which has been merged into '" + str + "'.  See comment for details.", e2);
                                }
                            } catch (Throwable th) {
                                if (SYSTRACE_LIBRARY_LOADING) {
                                    Api18TraceUtils.endSection();
                                }
                                throw th;
                            }
                        }
                    }
                    reentrantReadWriteLock.readLock().unlock();
                    return !z;
                }
            } catch (Throwable th2) {
                sSoSourcesLock.readLock().unlock();
                throw th2;
            }
        }
    }

    public static File unpackLibraryAndDependencies(String str) throws UnsatisfiedLinkError {
        assertInitialized();
        try {
            return unpackLibraryBySoName(System.mapLibraryName(str));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0055, code lost:
    
        if (android.util.Log.isLoggable(com.facebook.soloader.SoLoader.TAG, 3) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0057, code lost:
    
        android.util.Log.d(com.facebook.soloader.SoLoader.TAG, "Trying backup SoSource for " + r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006b, code lost:
    
        r8 = com.facebook.soloader.SoLoader.sBackupSoSources;
        r9 = r8.length;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006f, code lost:
    
        if (r10 >= r9) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0071, code lost:
    
        r11 = r8[r10];
        r11.prepare(r12);
        r11 = r11.loadLibrary(r12, r13, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x007a, code lost:
    
        if (r11 != 1) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x007c, code lost:
    
        r2 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007e, code lost:
    
        r10 = r10 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void doLoadLibraryBySoName(java.lang.String r12, int r13, @javax.annotation.Nullable android.os.StrictMode.ThreadPolicy r14) throws java.lang.UnsatisfiedLinkError {
        /*
            Method dump skipped, instruction units count: 395
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.doLoadLibraryBySoName(java.lang.String, int, android.os.StrictMode$ThreadPolicy):void");
    }

    @Nullable
    public static String makeNonZipPath(String str) {
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split(":");
        ArrayList arrayList = new ArrayList(strArrSplit.length);
        for (String str2 : strArrSplit) {
            if (!str2.contains("!")) {
                arrayList.add(str2);
            }
        }
        return TextUtils.join(":", arrayList);
    }

    static File unpackLibraryBySoName(String str) throws IOException {
        sSoSourcesLock.readLock().lock();
        try {
            for (SoSource soSource : sSoSources) {
                File fileUnpackLibrary = soSource.unpackLibrary(str);
                if (fileUnpackLibrary != null) {
                    return fileUnpackLibrary;
                }
            }
            sSoSourcesLock.readLock().unlock();
            throw new FileNotFoundException(str);
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    private static void assertInitialized() {
        if (!isInitialized()) {
            throw new IllegalStateException("SoLoader.init() not yet called");
        }
    }

    public static boolean isInitialized() {
        if (sSoSources != null) {
            return true;
        }
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.readLock().lock();
        try {
            boolean z = sSoSources != null;
            reentrantReadWriteLock.readLock().unlock();
            return z;
        } catch (Throwable th) {
            sSoSourcesLock.readLock().unlock();
            throw th;
        }
    }

    public static int getSoSourcesVersion() {
        return sSoSourcesVersion.get();
    }

    public static void prependSoSource(SoSource soSource) throws IOException {
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.writeLock().lock();
        try {
            assertInitialized();
            soSource.prepare(makePrepareFlags());
            SoSource[] soSourceArr = new SoSource[sSoSources.length + 1];
            soSourceArr[0] = soSource;
            System.arraycopy(sSoSources, 0, soSourceArr, 1, sSoSources.length);
            sSoSources = soSourceArr;
            sSoSourcesVersion.getAndIncrement();
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Prepended to SO sources: " + soSource);
            }
            reentrantReadWriteLock.writeLock().unlock();
        } catch (Throwable th) {
            sSoSourcesLock.writeLock().unlock();
            throw th;
        }
    }

    public static String makeLdLibraryPath() {
        sSoSourcesLock.readLock().lock();
        try {
            assertInitialized();
            ArrayList arrayList = new ArrayList();
            SoSource[] soSourceArr = sSoSources;
            if (soSourceArr != null) {
                for (SoSource soSource : soSourceArr) {
                    soSource.addToLdLibraryPath(arrayList);
                }
            }
            String strJoin = TextUtils.join(":", arrayList);
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "makeLdLibraryPath final path: " + strJoin);
            }
            return strJoin;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    public static boolean areSoSourcesAbisSupported() {
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.readLock().lock();
        try {
            if (sSoSources != null) {
                String[] supportedAbis = SysUtil.getSupportedAbis();
                for (SoSource soSource : sSoSources) {
                    for (String str : soSource.getSoSourceAbis()) {
                        boolean zEquals = false;
                        for (int i = 0; i < supportedAbis.length && !zEquals; i++) {
                            zEquals = str.equals(supportedAbis[i]);
                        }
                        if (!zEquals) {
                            Log.e(TAG, "abi not supported: " + str);
                            reentrantReadWriteLock = sSoSourcesLock;
                        }
                    }
                }
                sSoSourcesLock.readLock().unlock();
                return true;
            }
            reentrantReadWriteLock.readLock().unlock();
            return false;
        } catch (Throwable th) {
            sSoSourcesLock.readLock().unlock();
            throw th;
        }
    }

    public static boolean useDepsFile(Context context, boolean z, boolean z2) {
        return NativeDeps.useDepsFile(context, z, z2);
    }

    public static int getLoadedLibrariesCount() {
        return sLoadedLibraries.size();
    }
}
