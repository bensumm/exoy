package com.facebook.imagepipeline.cache;

import bolts.Task;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.FileCache;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteStreams;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.instrumentation.FrescoInstrumenter;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class BufferedDiskCache {
    private static final Class<?> TAG = BufferedDiskCache.class;
    private final FileCache mFileCache;
    private final ImageCacheStatsTracker mImageCacheStatsTracker;
    private final PooledByteBufferFactory mPooledByteBufferFactory;
    private final PooledByteStreams mPooledByteStreams;
    private final Executor mReadExecutor;
    private final StagingArea mStagingArea = StagingArea.getInstance();
    private final Executor mWriteExecutor;

    public BufferedDiskCache(FileCache fileCache, PooledByteBufferFactory pooledByteBufferFactory, PooledByteStreams pooledByteStreams, Executor readExecutor, Executor writeExecutor, ImageCacheStatsTracker imageCacheStatsTracker) {
        this.mFileCache = fileCache;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mPooledByteStreams = pooledByteStreams;
        this.mReadExecutor = readExecutor;
        this.mWriteExecutor = writeExecutor;
        this.mImageCacheStatsTracker = imageCacheStatsTracker;
    }

    public boolean containsSync(CacheKey key) {
        return this.mStagingArea.containsKey(key) || this.mFileCache.hasKeySync(key);
    }

    public Task<Boolean> contains(final CacheKey key) {
        if (containsSync(key)) {
            return Task.forResult(true);
        }
        return containsAsync(key);
    }

    private Task<Boolean> containsAsync(final CacheKey key) {
        try {
            final Object objOnBeforeSubmitWork = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_containsAsync");
            return Task.call(new Callable<Boolean>() { // from class: com.facebook.imagepipeline.cache.BufferedDiskCache.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                public Boolean call() throws Exception {
                    Object objOnBeginWork = FrescoInstrumenter.onBeginWork(objOnBeforeSubmitWork, null);
                    try {
                        return Boolean.valueOf(BufferedDiskCache.this.checkInStagingAreaAndFileCache(key));
                    } finally {
                    }
                }
            }, this.mReadExecutor);
        } catch (Exception e) {
            FLog.w(TAG, e, "Failed to schedule disk-cache read for %s", key.getUriString());
            return Task.forError(e);
        }
    }

    public boolean diskCheckSync(final CacheKey key) {
        if (containsSync(key)) {
            return true;
        }
        return checkInStagingAreaAndFileCache(key);
    }

    public Task<EncodedImage> get(CacheKey key, AtomicBoolean isCancelled) {
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("BufferedDiskCache#get");
            }
            EncodedImage encodedImage = this.mStagingArea.get(key);
            if (encodedImage != null) {
                return foundPinnedImage(key, encodedImage);
            }
            Task<EncodedImage> async = getAsync(key, isCancelled);
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            return async;
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    public Task<Void> probe(final CacheKey key) {
        Preconditions.checkNotNull(key);
        try {
            final Object objOnBeforeSubmitWork = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_probe");
            return Task.call(new Callable<Void>() { // from class: com.facebook.imagepipeline.cache.BufferedDiskCache.2
                @Override // java.util.concurrent.Callable
                public Void call() throws Exception {
                    Object objOnBeginWork = FrescoInstrumenter.onBeginWork(objOnBeforeSubmitWork, null);
                    try {
                        BufferedDiskCache.this.mFileCache.probe(key);
                        return null;
                    } finally {
                        FrescoInstrumenter.onEndWork(objOnBeginWork);
                    }
                }
            }, this.mWriteExecutor);
        } catch (Exception e) {
            FLog.w(TAG, e, "Failed to schedule disk-cache probe for %s", key.getUriString());
            return Task.forError(e);
        }
    }

    public void addKeyForAsyncProbing(final CacheKey key) {
        Preconditions.checkNotNull(key);
        this.mFileCache.probe(key);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkInStagingAreaAndFileCache(final CacheKey key) {
        EncodedImage encodedImage = this.mStagingArea.get(key);
        if (encodedImage != null) {
            encodedImage.close();
            FLog.v(TAG, "Found image for %s in staging area", key.getUriString());
            this.mImageCacheStatsTracker.onStagingAreaHit(key);
            return true;
        }
        FLog.v(TAG, "Did not find image for %s in staging area", key.getUriString());
        this.mImageCacheStatsTracker.onStagingAreaMiss(key);
        try {
            return this.mFileCache.hasKey(key);
        } catch (Exception unused) {
            return false;
        }
    }

    private Task<EncodedImage> getAsync(final CacheKey key, final AtomicBoolean isCancelled) {
        try {
            final Object objOnBeforeSubmitWork = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_getAsync");
            return Task.call(new Callable<EncodedImage>() { // from class: com.facebook.imagepipeline.cache.BufferedDiskCache.3
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.util.concurrent.Callable
                @Nullable
                public EncodedImage call() throws Exception {
                    Object objOnBeginWork = FrescoInstrumenter.onBeginWork(objOnBeforeSubmitWork, null);
                    try {
                        if (isCancelled.get()) {
                            throw new CancellationException();
                        }
                        EncodedImage encodedImage = BufferedDiskCache.this.mStagingArea.get(key);
                        if (encodedImage != null) {
                            FLog.v((Class<?>) BufferedDiskCache.TAG, "Found image for %s in staging area", key.getUriString());
                            BufferedDiskCache.this.mImageCacheStatsTracker.onStagingAreaHit(key);
                        } else {
                            FLog.v((Class<?>) BufferedDiskCache.TAG, "Did not find image for %s in staging area", key.getUriString());
                            BufferedDiskCache.this.mImageCacheStatsTracker.onStagingAreaMiss(key);
                            try {
                                PooledByteBuffer fromDiskCache = BufferedDiskCache.this.readFromDiskCache(key);
                                if (fromDiskCache == null) {
                                    return null;
                                }
                                CloseableReference closeableReferenceOf = CloseableReference.of(fromDiskCache);
                                try {
                                    encodedImage = new EncodedImage((CloseableReference<PooledByteBuffer>) closeableReferenceOf);
                                } finally {
                                    CloseableReference.closeSafely((CloseableReference<?>) closeableReferenceOf);
                                }
                            } catch (Exception unused) {
                                return null;
                            }
                        }
                        if (!Thread.interrupted()) {
                            return encodedImage;
                        }
                        FLog.v((Class<?>) BufferedDiskCache.TAG, "Host thread was interrupted, decreasing reference count");
                        if (encodedImage != null) {
                            encodedImage.close();
                        }
                        throw new InterruptedException();
                    } catch (Throwable th) {
                        try {
                            FrescoInstrumenter.markFailure(objOnBeforeSubmitWork, th);
                            throw th;
                        } finally {
                            FrescoInstrumenter.onEndWork(objOnBeginWork);
                        }
                    }
                }
            }, this.mReadExecutor);
        } catch (Exception e) {
            FLog.w(TAG, e, "Failed to schedule disk-cache read for %s", key.getUriString());
            return Task.forError(e);
        }
    }

    public void put(final CacheKey key, EncodedImage encodedImage) {
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("BufferedDiskCache#put");
            }
            Preconditions.checkNotNull(key);
            Preconditions.checkArgument(Boolean.valueOf(EncodedImage.isValid(encodedImage)));
            this.mStagingArea.put(key, encodedImage);
            final EncodedImage encodedImageCloneOrNull = EncodedImage.cloneOrNull(encodedImage);
            try {
                final Object objOnBeforeSubmitWork = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_putAsync");
                this.mWriteExecutor.execute(new Runnable() { // from class: com.facebook.imagepipeline.cache.BufferedDiskCache.4
                    @Override // java.lang.Runnable
                    public void run() {
                        Object objOnBeginWork = FrescoInstrumenter.onBeginWork(objOnBeforeSubmitWork, null);
                        try {
                            BufferedDiskCache.this.writeToDiskCache(key, encodedImageCloneOrNull);
                        } finally {
                        }
                    }
                });
            } catch (Exception e) {
                FLog.w(TAG, e, "Failed to schedule disk-cache write for %s", key.getUriString());
                this.mStagingArea.remove(key, encodedImage);
                EncodedImage.closeSafely(encodedImageCloneOrNull);
            }
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    public Task<Void> remove(final CacheKey key) {
        Preconditions.checkNotNull(key);
        this.mStagingArea.remove(key);
        try {
            final Object objOnBeforeSubmitWork = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_remove");
            return Task.call(new Callable<Void>() { // from class: com.facebook.imagepipeline.cache.BufferedDiskCache.5
                @Override // java.util.concurrent.Callable
                public Void call() throws Exception {
                    Object objOnBeginWork = FrescoInstrumenter.onBeginWork(objOnBeforeSubmitWork, null);
                    try {
                        BufferedDiskCache.this.mStagingArea.remove(key);
                        BufferedDiskCache.this.mFileCache.remove(key);
                        return null;
                    } finally {
                    }
                }
            }, this.mWriteExecutor);
        } catch (Exception e) {
            FLog.w(TAG, e, "Failed to schedule disk-cache remove for %s", key.getUriString());
            return Task.forError(e);
        }
    }

    public Task<Void> clearAll() {
        this.mStagingArea.clearAll();
        final Object objOnBeforeSubmitWork = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_clearAll");
        try {
            return Task.call(new Callable<Void>() { // from class: com.facebook.imagepipeline.cache.BufferedDiskCache.6
                @Override // java.util.concurrent.Callable
                public Void call() throws Exception {
                    Object objOnBeginWork = FrescoInstrumenter.onBeginWork(objOnBeforeSubmitWork, null);
                    try {
                        BufferedDiskCache.this.mStagingArea.clearAll();
                        BufferedDiskCache.this.mFileCache.clearAll();
                        return null;
                    } finally {
                    }
                }
            }, this.mWriteExecutor);
        } catch (Exception e) {
            FLog.w(TAG, e, "Failed to schedule disk-cache clear", new Object[0]);
            return Task.forError(e);
        }
    }

    public long getSize() {
        return this.mFileCache.getSize();
    }

    private Task<EncodedImage> foundPinnedImage(CacheKey key, EncodedImage pinnedImage) {
        FLog.v(TAG, "Found image for %s in staging area", key.getUriString());
        this.mImageCacheStatsTracker.onStagingAreaHit(key);
        return Task.forResult(pinnedImage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    public PooledByteBuffer readFromDiskCache(final CacheKey key) throws IOException {
        try {
            Class<?> cls = TAG;
            FLog.v(cls, "Disk cache read for %s", key.getUriString());
            BinaryResource resource = this.mFileCache.getResource(key);
            if (resource == null) {
                FLog.v(cls, "Disk cache miss for %s", key.getUriString());
                this.mImageCacheStatsTracker.onDiskCacheMiss(key);
                return null;
            }
            FLog.v(cls, "Found entry in disk cache for %s", key.getUriString());
            this.mImageCacheStatsTracker.onDiskCacheHit(key);
            InputStream inputStreamOpenStream = resource.openStream();
            try {
                PooledByteBuffer pooledByteBufferNewByteBuffer = this.mPooledByteBufferFactory.newByteBuffer(inputStreamOpenStream, (int) resource.size());
                inputStreamOpenStream.close();
                FLog.v(cls, "Successful read from disk cache for %s", key.getUriString());
                return pooledByteBufferNewByteBuffer;
            } catch (Throwable th) {
                inputStreamOpenStream.close();
                throw th;
            }
        } catch (IOException e) {
            FLog.w(TAG, e, "Exception reading from cache for %s", key.getUriString());
            this.mImageCacheStatsTracker.onDiskCacheGetFail(key);
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeToDiskCache(final CacheKey key, final EncodedImage encodedImage) {
        Class<?> cls = TAG;
        FLog.v(cls, "About to write to disk-cache for key %s", key.getUriString());
        try {
            this.mFileCache.insert(key, new WriterCallback() { // from class: com.facebook.imagepipeline.cache.BufferedDiskCache.7
                @Override // com.facebook.cache.common.WriterCallback
                public void write(OutputStream os) throws IOException {
                    InputStream inputStream = encodedImage.getInputStream();
                    Preconditions.checkNotNull(inputStream);
                    BufferedDiskCache.this.mPooledByteStreams.copy(inputStream, os);
                }
            });
            this.mImageCacheStatsTracker.onDiskCachePut(key);
            FLog.v(cls, "Successful disk-cache write for key %s", key.getUriString());
        } catch (IOException e) {
            FLog.w(TAG, e, "Failed to write to disk-cache for key %s", key.getUriString());
        }
    }
}
