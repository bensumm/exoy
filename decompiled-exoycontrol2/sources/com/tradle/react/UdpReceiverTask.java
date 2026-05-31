package com.tradle.react;

import android.util.Base64;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/* JADX INFO: loaded from: classes2.dex */
public class UdpReceiverTask implements Runnable {
    private static final int MAX_UDP_DATAGRAM_LEN = 65535;
    private boolean isRunning = false;
    private final OnDataReceivedListener receiverListener;
    private final DatagramSocket socket;

    public interface OnDataReceivedListener {
        void didReceiveData(String str, String str2, int i);

        void didReceiveError(String str);

        void didReceiveRuntimeException(RuntimeException runtimeException);
    }

    public UdpReceiverTask(DatagramSocket datagramSocket, OnDataReceivedListener onDataReceivedListener) {
        this.socket = datagramSocket;
        this.receiverListener = onDataReceivedListener;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void terminate() {
        this.isRunning = false;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.isRunning = true;
        DatagramPacket datagramPacket = new DatagramPacket(new byte[65535], 65535);
        while (this.isRunning) {
            try {
                this.socket.receive(datagramPacket);
                InetAddress address = datagramPacket.getAddress();
                this.receiverListener.didReceiveData(Base64.encodeToString(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength(), 2), address.getHostAddress(), datagramPacket.getPort());
            } catch (IOException e) {
                OnDataReceivedListener onDataReceivedListener = this.receiverListener;
                if (onDataReceivedListener != null) {
                    onDataReceivedListener.didReceiveError(e.getMessage());
                }
                this.isRunning = false;
            } catch (RuntimeException e2) {
                OnDataReceivedListener onDataReceivedListener2 = this.receiverListener;
                if (onDataReceivedListener2 != null) {
                    onDataReceivedListener2.didReceiveRuntimeException(e2);
                }
                this.isRunning = false;
            }
        }
    }
}
