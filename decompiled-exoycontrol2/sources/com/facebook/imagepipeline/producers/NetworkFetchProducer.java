package com.facebook.imagepipeline.producers;

import android.os.SystemClock;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.common.BytesRange;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.EncodedImageOrigin;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.io.InputStream;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class NetworkFetchProducer implements Producer<EncodedImage> {
    public static final String INTERMEDIATE_RESULT_PRODUCER_EVENT = "intermediate_result";
    public static final String PRODUCER_NAME = "NetworkFetchProducer";
    private static final int READ_SIZE = 16384;
    static final long TIME_BETWEEN_PARTIAL_RESULTS_MS = 100;
    private final ByteArrayPool mByteArrayPool;
    private final NetworkFetcher mNetworkFetcher;
    protected final PooledByteBufferFactory mPooledByteBufferFactory;

    public NetworkFetchProducer(PooledByteBufferFactory pooledByteBufferFactory, ByteArrayPool byteArrayPool, NetworkFetcher networkFetcher) {
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mByteArrayPool = byteArrayPool;
        this.mNetworkFetcher = networkFetcher;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext context) {
        context.getProducerListener().onProducerStart(context, PRODUCER_NAME);
        final FetchState fetchStateCreateFetchState = this.mNetworkFetcher.createFetchState(consumer, context);
        this.mNetworkFetcher.fetch(fetchStateCreateFetchState, new NetworkFetcher.Callback() { // from class: com.facebook.imagepipeline.producers.NetworkFetchProducer.1
            @Override // com.facebook.imagepipeline.producers.NetworkFetcher.Callback
            public void onResponse(InputStream response, int responseLength) throws Throwable {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.beginSection("NetworkFetcher->onResponse");
                }
                NetworkFetchProducer.this.onResponse(fetchStateCreateFetchState, response, responseLength);
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            }

            @Override // com.facebook.imagepipeline.producers.NetworkFetcher.Callback
            public void onFailure(Throwable throwable) {
                NetworkFetchProducer.this.onFailure(fetchStateCreateFetchState, throwable);
            }

            @Override // com.facebook.imagepipeline.producers.NetworkFetcher.Callback
            public void onCancellation() {
                NetworkFetchProducer.this.onCancellation(fetchStateCreateFetchState);
            }
        });
    }

    protected void onResponse(FetchState fetchState, InputStream responseData, int responseContentLength) throws Throwable {
        PooledByteBufferOutputStream pooledByteBufferOutputStreamNewOutputStream;
        if (responseContentLength > 0) {
            pooledByteBufferOutputStreamNewOutputStream = this.mPooledByteBufferFactory.newOutputStream(responseContentLength);
        } else {
            pooledByteBufferOutputStreamNewOutputStream = this.mPooledByteBufferFactory.newOutputStream();
        }
        byte[] bArr = this.mByteArrayPool.get(16384);
        while (true) {
            try {
                int i = responseData.read(bArr);
                if (i < 0) {
                    this.mNetworkFetcher.onFetchCompletion(fetchState, pooledByteBufferOutputStreamNewOutputStream.size());
                    handleFinalResult(pooledByteBufferOutputStreamNewOutputStream, fetchState);
                    return;
                } else if (i > 0) {
                    pooledByteBufferOutputStreamNewOutputStream.write(bArr, 0, i);
                    maybeHandleIntermediateResult(pooledByteBufferOutputStreamNewOutputStream, fetchState);
                    fetchState.getConsumer().onProgressUpdate(calculateProgress(pooledByteBufferOutputStreamNewOutputStream.size(), responseContentLength));
                }
            } finally {
                this.mByteArrayPool.release(bArr);
                pooledByteBufferOutputStreamNewOutputStream.close();
            }
        }
    }

    protected static float calculateProgress(int downloaded, int total) {
        return total > 0 ? downloaded / total : 1.0f - ((float) Math.exp(((double) (-downloaded)) / 50000.0d));
    }

    protected void maybeHandleIntermediateResult(PooledByteBufferOutputStream pooledOutputStream, FetchState fetchState) throws Throwable {
        long systemUptime = getSystemUptime();
        if (!shouldPropagateIntermediateResults(fetchState) || systemUptime - fetchState.getLastIntermediateResultTimeMs() < TIME_BETWEEN_PARTIAL_RESULTS_MS) {
            return;
        }
        fetchState.setLastIntermediateResultTimeMs(systemUptime);
        fetchState.getListener().onProducerEvent(fetchState.getContext(), PRODUCER_NAME, INTERMEDIATE_RESULT_PRODUCER_EVENT);
        notifyConsumer(pooledOutputStream, fetchState.getOnNewResultStatusFlags(), fetchState.getResponseBytesRange(), fetchState.getConsumer(), fetchState.getContext());
    }

    protected void handleFinalResult(PooledByteBufferOutputStream pooledOutputStream, FetchState fetchState) throws Throwable {
        Map<String, String> extraMap = getExtraMap(fetchState, pooledOutputStream.size());
        ProducerListener2 listener = fetchState.getListener();
        listener.onProducerFinishWithSuccess(fetchState.getContext(), PRODUCER_NAME, extraMap);
        listener.onUltimateProducerReached(fetchState.getContext(), PRODUCER_NAME, true);
        fetchState.getContext().putOriginExtra("network");
        notifyConsumer(pooledOutputStream, fetchState.getOnNewResultStatusFlags() | 1, fetchState.getResponseBytesRange(), fetchState.getConsumer(), fetchState.getContext());
    }

    protected static void notifyConsumer(PooledByteBufferOutputStream pooledOutputStream, int status, @Nullable BytesRange responseBytesRange, Consumer<EncodedImage> consumer, ProducerContext context) throws Throwable {
        EncodedImage encodedImage;
        CloseableReference closeableReferenceOf = CloseableReference.of(pooledOutputStream.toByteBuffer());
        EncodedImage encodedImage2 = null;
        try {
            encodedImage = new EncodedImage((CloseableReference<PooledByteBuffer>) closeableReferenceOf);
        } catch (Throwable th) {
            th = th;
        }
        try {
            encodedImage.setBytesRange(responseBytesRange);
            encodedImage.parseMetaData();
            context.setEncodedImageOrigin(EncodedImageOrigin.NETWORK);
            consumer.onNewResult(encodedImage, status);
            EncodedImage.closeSafely(encodedImage);
            CloseableReference.closeSafely((CloseableReference<?>) closeableReferenceOf);
        } catch (Throwable th2) {
            th = th2;
            encodedImage2 = encodedImage;
            EncodedImage.closeSafely(encodedImage2);
            CloseableReference.closeSafely((CloseableReference<?>) closeableReferenceOf);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFailure(FetchState fetchState, Throwable e) {
        fetchState.getListener().onProducerFinishWithFailure(fetchState.getContext(), PRODUCER_NAME, e, null);
        fetchState.getListener().onUltimateProducerReached(fetchState.getContext(), PRODUCER_NAME, false);
        fetchState.getContext().putOriginExtra("network");
        fetchState.getConsumer().onFailure(e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCancellation(FetchState fetchState) {
        fetchState.getListener().onProducerFinishWithCancellation(fetchState.getContext(), PRODUCER_NAME, null);
        fetchState.getConsumer().onCancellation();
    }

    private boolean shouldPropagateIntermediateResults(FetchState fetchState) {
        if (fetchState.getContext().isIntermediateResultExpected()) {
            return this.mNetworkFetcher.shouldPropagate(fetchState);
        }
        return false;
    }

    @Nullable
    private Map<String, String> getExtraMap(FetchState fetchState, int byteSize) {
        if (fetchState.getListener().requiresExtraMap(fetchState.getContext(), PRODUCER_NAME)) {
            return this.mNetworkFetcher.getExtraMap(fetchState, byteSize);
        }
        return null;
    }

    protected long getSystemUptime() {
        return SystemClock.uptimeMillis();
    }
}
