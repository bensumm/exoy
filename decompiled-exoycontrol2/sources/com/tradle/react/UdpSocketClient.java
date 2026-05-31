package com.tradle.react;

import android.util.Base64;
import com.facebook.react.bridge.Callback;
import com.tradle.react.UdpReceiverTask;
import com.tradle.react.UdpSenderTask;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public final class UdpSocketClient implements UdpReceiverTask.OnDataReceivedListener, UdpSenderTask.OnDataSentListener {
    private final OnRuntimeExceptionListener mExceptionListener;
    private final OnDataReceivedListener mReceiverListener;
    private UdpReceiverTask mReceiverTask;
    private DatagramSocket mSocket;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean mIsMulticastSocket = false;
    private final Map<UdpSenderTask, Callback> mPendingSends = new ConcurrentHashMap();

    public interface OnDataReceivedListener {
        void didReceiveData(UdpSocketClient udpSocketClient, String str, String str2, int i);

        void didReceiveError(UdpSocketClient udpSocketClient, String str);
    }

    public interface OnRuntimeExceptionListener {
        void didReceiveException(RuntimeException runtimeException);
    }

    public UdpSocketClient(OnDataReceivedListener onDataReceivedListener, OnRuntimeExceptionListener onRuntimeExceptionListener) {
        this.mReceiverListener = onDataReceivedListener;
        this.mExceptionListener = onRuntimeExceptionListener;
    }

    public boolean isMulticast() {
        return this.mIsMulticastSocket;
    }

    public void bind(Integer num, @Nullable String str) throws IOException {
        InetSocketAddress inetSocketAddress;
        if (this.mSocket != null || this.mReceiverTask != null) {
            throw new IllegalStateException("Socket is already bound");
        }
        if (str != null) {
            inetSocketAddress = new InetSocketAddress(InetAddress.getByName(str), num.intValue());
        } else {
            inetSocketAddress = new InetSocketAddress(num.intValue());
        }
        MulticastSocket multicastSocket = new MulticastSocket(inetSocketAddress);
        this.mSocket = multicastSocket;
        multicastSocket.setReuseAddress(true);
        this.mReceiverTask = new UdpReceiverTask(this.mSocket, this);
        new Thread(this.mReceiverTask).start();
    }

    public void addMembership(String str) throws IllegalStateException, IOException {
        DatagramSocket datagramSocket = this.mSocket;
        if (datagramSocket == null || !datagramSocket.isBound()) {
            throw new IllegalStateException("Socket is not bound.");
        }
        ((MulticastSocket) this.mSocket).joinGroup(InetAddress.getByName(str));
        this.mIsMulticastSocket = true;
    }

    public void dropMembership(String str) throws IOException {
        ((MulticastSocket) this.mSocket).leaveGroup(InetAddress.getByName(str));
        this.mIsMulticastSocket = false;
    }

    public void send(String str, Integer num, String str2, @Nullable Callback callback) throws IllegalStateException, IOException {
        DatagramSocket datagramSocket = this.mSocket;
        if (datagramSocket == null || !datagramSocket.isBound()) {
            throw new IllegalStateException("Socket is not bound.");
        }
        UdpSenderTask udpSenderTask = new UdpSenderTask(this.mSocket, this, new InetSocketAddress(InetAddress.getByName(str2), num.intValue()), Base64.decode(str, 2));
        if (callback != null) {
            synchronized (this.mPendingSends) {
                this.mPendingSends.put(udpSenderTask, callback);
            }
        }
        this.executor.submit(udpSenderTask);
    }

    public void setBroadcast(boolean z) throws SocketException {
        DatagramSocket datagramSocket = this.mSocket;
        if (datagramSocket != null) {
            datagramSocket.setBroadcast(z);
        }
    }

    public void close() {
        UdpReceiverTask udpReceiverTask = this.mReceiverTask;
        if (udpReceiverTask != null && udpReceiverTask.isRunning()) {
            this.mReceiverTask.terminate();
        }
        this.executor.shutdownNow();
        DatagramSocket datagramSocket = this.mSocket;
        if (datagramSocket != null && !datagramSocket.isClosed()) {
            this.mSocket.close();
        }
        this.mSocket = null;
        this.mReceiverTask = null;
    }

    @Override // com.tradle.react.UdpReceiverTask.OnDataReceivedListener
    public void didReceiveData(String str, String str2, int i) {
        this.mReceiverListener.didReceiveData(this, str, str2, i);
    }

    @Override // com.tradle.react.UdpReceiverTask.OnDataReceivedListener
    public void didReceiveError(String str) {
        this.mReceiverListener.didReceiveError(this, str);
    }

    @Override // com.tradle.react.UdpReceiverTask.OnDataReceivedListener
    public void didReceiveRuntimeException(RuntimeException runtimeException) {
        this.mExceptionListener.didReceiveException(runtimeException);
    }

    @Override // com.tradle.react.UdpSenderTask.OnDataSentListener
    public void onDataSent(UdpSenderTask udpSenderTask) {
        Callback callback;
        synchronized (this.mPendingSends) {
            callback = this.mPendingSends.get(udpSenderTask);
            this.mPendingSends.remove(udpSenderTask);
        }
        if (callback != null) {
            callback.invoke(new Object[0]);
        }
    }

    @Override // com.tradle.react.UdpSenderTask.OnDataSentListener
    public void onDataSentError(UdpSenderTask udpSenderTask, String str) {
        Callback callback;
        synchronized (this.mPendingSends) {
            callback = this.mPendingSends.get(udpSenderTask);
            this.mPendingSends.remove(udpSenderTask);
        }
        if (callback != null) {
            callback.invoke(UdpErrorUtil.getError(UdpErrorCodes.sendError.name(), str));
        }
    }

    @Override // com.tradle.react.UdpSenderTask.OnDataSentListener
    public void onDataSentRuntimeException(UdpSenderTask udpSenderTask, RuntimeException runtimeException) {
        this.mExceptionListener.didReceiveException(runtimeException);
        synchronized (this.mPendingSends) {
            this.mPendingSends.remove(udpSenderTask);
        }
    }
}
