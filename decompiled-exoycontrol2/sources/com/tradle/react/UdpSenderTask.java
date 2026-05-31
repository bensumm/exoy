package com.tradle.react;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

/* JADX INFO: loaded from: classes2.dex */
public class UdpSenderTask implements Runnable {
    private static final String TAG = "UdpSenderTask";
    private byte[] mData;
    private final WeakReference<OnDataSentListener> mListener;
    private final DatagramSocket mSocket;
    private SocketAddress mSocketAddress;

    public interface OnDataSentListener {
        void onDataSent(UdpSenderTask udpSenderTask);

        void onDataSentError(UdpSenderTask udpSenderTask, String str);

        void onDataSentRuntimeException(UdpSenderTask udpSenderTask, RuntimeException runtimeException);
    }

    public UdpSenderTask(DatagramSocket datagramSocket, OnDataSentListener onDataSentListener, SocketAddress socketAddress, byte[] bArr) {
        this.mSocket = datagramSocket;
        this.mListener = new WeakReference<>(onDataSentListener);
        this.mSocketAddress = socketAddress;
        this.mData = bArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        OnDataSentListener onDataSentListener = this.mListener.get();
        try {
            DatagramSocket datagramSocket = this.mSocket;
            if (datagramSocket == null) {
                return;
            }
            byte[] bArr = this.mData;
            datagramSocket.send(new DatagramPacket(bArr, bArr.length, this.mSocketAddress));
            if (onDataSentListener != null) {
                onDataSentListener.onDataSent(this);
            }
        } catch (IOException e) {
            if (onDataSentListener != null) {
                onDataSentListener.onDataSentError(this, e.getMessage());
            }
        } catch (RuntimeException e2) {
            if (onDataSentListener != null) {
                onDataSentListener.onDataSentRuntimeException(this, e2);
            }
        }
    }
}
