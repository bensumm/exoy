package com.swmansion.reanimated;

/* JADX INFO: loaded from: classes2.dex */
public class ReanimatedMessageQueueThread extends ReanimatedMessageQueueThreadBase {
    @Override // com.facebook.react.bridge.queue.MessageQueueThread
    public boolean runOnQueue(Runnable runnable) {
        return this.messageQueueThread.runOnQueue(runnable);
    }
}
