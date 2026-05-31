package com.facebook.imagepipeline.memory;

import android.os.SharedMemory;
import android.system.ErrnoException;
import android.util.Log;
import com.facebook.common.internal.Preconditions;
import java.io.Closeable;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class AshmemMemoryChunk implements MemoryChunk, Closeable {
    private static final String TAG = "AshmemMemoryChunk";

    @Nullable
    private ByteBuffer mByteBuffer;
    private final long mId;

    @Nullable
    private SharedMemory mSharedMemory;

    public AshmemMemoryChunk(final int size) {
        Preconditions.checkArgument(Boolean.valueOf(size > 0));
        try {
            SharedMemory sharedMemoryCreate = SharedMemory.create(TAG, size);
            this.mSharedMemory = sharedMemoryCreate;
            this.mByteBuffer = sharedMemoryCreate.mapReadWrite();
            this.mId = System.identityHashCode(this);
        } catch (ErrnoException e) {
            throw new RuntimeException("Fail to create AshmemMemory", e);
        }
    }

    public AshmemMemoryChunk() {
        this.mSharedMemory = null;
        this.mByteBuffer = null;
        this.mId = System.identityHashCode(this);
    }

    @Override // com.facebook.imagepipeline.memory.MemoryChunk, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        if (!isClosed()) {
            SharedMemory.unmap(this.mByteBuffer);
            this.mSharedMemory.close();
            this.mByteBuffer = null;
            this.mSharedMemory = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x000c  */
    @Override // com.facebook.imagepipeline.memory.MemoryChunk
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized boolean isClosed() {
        /*
            r1 = this;
            monitor-enter(r1)
            java.nio.ByteBuffer r0 = r1.mByteBuffer     // Catch: java.lang.Throwable -> Lf
            if (r0 == 0) goto Lc
            android.os.SharedMemory r0 = r1.mSharedMemory     // Catch: java.lang.Throwable -> Lf
            if (r0 != 0) goto La
            goto Lc
        La:
            r0 = 0
            goto Ld
        Lc:
            r0 = 1
        Ld:
            monitor-exit(r1)
            return r0
        Lf:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.memory.AshmemMemoryChunk.isClosed():boolean");
    }

    @Override // com.facebook.imagepipeline.memory.MemoryChunk
    public int getSize() {
        Preconditions.checkState(!isClosed());
        return this.mSharedMemory.getSize();
    }

    @Override // com.facebook.imagepipeline.memory.MemoryChunk
    public synchronized int write(final int memoryOffset, final byte[] byteArray, final int byteArrayOffset, final int count) {
        int iAdjustByteCount;
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkState(!isClosed());
        iAdjustByteCount = MemoryChunkUtil.adjustByteCount(memoryOffset, count, getSize());
        MemoryChunkUtil.checkBounds(memoryOffset, byteArray.length, byteArrayOffset, iAdjustByteCount, getSize());
        this.mByteBuffer.position(memoryOffset);
        this.mByteBuffer.put(byteArray, byteArrayOffset, iAdjustByteCount);
        return iAdjustByteCount;
    }

    @Override // com.facebook.imagepipeline.memory.MemoryChunk
    public synchronized int read(final int memoryOffset, final byte[] byteArray, final int byteArrayOffset, final int count) {
        int iAdjustByteCount;
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkState(!isClosed());
        iAdjustByteCount = MemoryChunkUtil.adjustByteCount(memoryOffset, count, getSize());
        MemoryChunkUtil.checkBounds(memoryOffset, byteArray.length, byteArrayOffset, iAdjustByteCount, getSize());
        this.mByteBuffer.position(memoryOffset);
        this.mByteBuffer.get(byteArray, byteArrayOffset, iAdjustByteCount);
        return iAdjustByteCount;
    }

    @Override // com.facebook.imagepipeline.memory.MemoryChunk
    public synchronized byte read(final int offset) {
        boolean z = true;
        Preconditions.checkState(!isClosed());
        Preconditions.checkArgument(Boolean.valueOf(offset >= 0));
        if (offset >= getSize()) {
            z = false;
        }
        Preconditions.checkArgument(Boolean.valueOf(z));
        return this.mByteBuffer.get(offset);
    }

    @Override // com.facebook.imagepipeline.memory.MemoryChunk
    public long getNativePtr() {
        throw new UnsupportedOperationException("Cannot get the pointer of an  AshmemMemoryChunk");
    }

    @Override // com.facebook.imagepipeline.memory.MemoryChunk
    @Nullable
    public ByteBuffer getByteBuffer() {
        return this.mByteBuffer;
    }

    @Override // com.facebook.imagepipeline.memory.MemoryChunk
    public long getUniqueId() {
        return this.mId;
    }

    @Override // com.facebook.imagepipeline.memory.MemoryChunk
    public void copy(final int offset, final MemoryChunk other, final int otherOffset, final int count) {
        Preconditions.checkNotNull(other);
        if (other.getUniqueId() == getUniqueId()) {
            Log.w(TAG, "Copying from AshmemMemoryChunk " + Long.toHexString(getUniqueId()) + " to AshmemMemoryChunk " + Long.toHexString(other.getUniqueId()) + " which are the same ");
            Preconditions.checkArgument(false);
        }
        if (other.getUniqueId() < getUniqueId()) {
            synchronized (other) {
                synchronized (this) {
                    doCopy(offset, other, otherOffset, count);
                }
            }
        } else {
            synchronized (this) {
                synchronized (other) {
                    doCopy(offset, other, otherOffset, count);
                }
            }
        }
    }

    private void doCopy(final int offset, final MemoryChunk other, final int otherOffset, final int count) {
        if (!(other instanceof AshmemMemoryChunk)) {
            throw new IllegalArgumentException("Cannot copy two incompatible MemoryChunks");
        }
        Preconditions.checkState(!isClosed());
        Preconditions.checkState(!other.isClosed());
        MemoryChunkUtil.checkBounds(offset, other.getSize(), otherOffset, count, getSize());
        this.mByteBuffer.position(offset);
        other.getByteBuffer().position(otherOffset);
        byte[] bArr = new byte[count];
        this.mByteBuffer.get(bArr, 0, count);
        other.getByteBuffer().put(bArr, 0, count);
    }
}
