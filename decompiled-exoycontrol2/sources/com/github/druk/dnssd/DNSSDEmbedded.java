package com.github.druk.dnssd;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.facebook.react.uimanager.ViewProps;

/* JADX INFO: loaded from: classes.dex */
public class DNSSDEmbedded extends DNSSD {
    public static final int DEFAULT_STOP_TIMER_DELAY = 5000;
    private static final String TAG = "DNSSDEmbedded";
    private final Handler handler;
    private volatile boolean isStarted;
    private final long mStopTimerDelay;
    private Thread mThread;
    private int serviceCount;

    static native void nativeExit();

    static native int nativeInit();

    static native int nativeLoop();

    public DNSSDEmbedded(Context context) {
        this(context, 5000L);
    }

    public DNSSDEmbedded(Context context, long stopTimerDelay) {
        super(context, "jdns_sd_embedded");
        this.handler = new Handler(Looper.getMainLooper());
        this.isStarted = false;
        this.serviceCount = 0;
        this.mStopTimerDelay = stopTimerDelay;
    }

    public void init() {
        this.handler.removeCallbacks(new DNSSDEmbedded$$ExternalSyntheticLambda0());
        Thread thread = this.mThread;
        if (thread != null && thread.isAlive()) {
            Log.i(TAG, "already started");
            waitUntilStarted();
            return;
        }
        this.isStarted = false;
        InternalDNSSD.getInstance();
        Thread thread2 = new Thread() { // from class: com.github.druk.dnssd.DNSSDEmbedded.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Log.i(DNSSDEmbedded.TAG, "init");
                int iNativeInit = DNSSDEmbedded.nativeInit();
                synchronized (DNSSDEmbedded.class) {
                    DNSSDEmbedded.this.isStarted = true;
                    DNSSDEmbedded.class.notifyAll();
                }
                if (iNativeInit != 0) {
                    Log.e(DNSSDEmbedded.TAG, "error: " + iNativeInit);
                } else {
                    Log.i(DNSSDEmbedded.TAG, ViewProps.START);
                    int iNativeLoop = DNSSDEmbedded.nativeLoop();
                    DNSSDEmbedded.this.isStarted = false;
                    Log.i(DNSSDEmbedded.TAG, "finish with code: " + iNativeLoop);
                }
            }
        };
        this.mThread = thread2;
        thread2.setPriority(10);
        this.mThread.setName("DNS-SDEmbedded");
        this.mThread.start();
        waitUntilStarted();
    }

    public void exit() {
        synchronized (DNSSDEmbedded.class) {
            Log.i(TAG, "post exit");
            this.handler.postDelayed(new DNSSDEmbedded$$ExternalSyntheticLambda0(), this.mStopTimerDelay);
        }
    }

    private void waitUntilStarted() {
        synchronized (DNSSDEmbedded.class) {
            while (!this.isStarted) {
                try {
                    DNSSDEmbedded.class.wait();
                } catch (InterruptedException e) {
                    Log.e(TAG, "waitUntilStarted exception: ", e);
                }
            }
        }
    }

    @Override // com.github.druk.dnssd.DNSSD, com.github.druk.dnssd.InternalDNSSDService.DnssdServiceListener
    public void onServiceStarting() {
        super.onServiceStarting();
        init();
        this.serviceCount++;
    }

    @Override // com.github.druk.dnssd.DNSSD, com.github.druk.dnssd.InternalDNSSDService.DnssdServiceListener
    public void onServiceStopped() {
        super.onServiceStopped();
        int i = this.serviceCount - 1;
        this.serviceCount = i;
        if (i == 0) {
            exit();
        }
    }
}
