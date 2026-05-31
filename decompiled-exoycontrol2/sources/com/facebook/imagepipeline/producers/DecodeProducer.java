package com.facebook.imagepipeline.producers;

import android.graphics.Bitmap;
import androidx.core.os.EnvironmentCompat;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.ExceptionWithNoStacktrace;
import com.facebook.common.util.UriUtil;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.CloseableReferenceFactory;
import com.facebook.imagepipeline.decoder.DecodeException;
import com.facebook.imagepipeline.decoder.ImageDecoder;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegParser;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.producers.JobScheduler;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import com.facebook.imagepipeline.transcoder.DownsampleUtil;
import com.facebook.imageutils.BitmapUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class DecodeProducer implements Producer<CloseableReference<CloseableImage>> {
    public static final String ENCODED_IMAGE_SIZE = "encodedImageSize";
    public static final String EXTRA_BITMAP_BYTES = "byteCount";
    public static final String EXTRA_BITMAP_SIZE = "bitmapSize";
    public static final String EXTRA_HAS_GOOD_QUALITY = "hasGoodQuality";
    public static final String EXTRA_IMAGE_FORMAT_NAME = "imageFormat";
    public static final String EXTRA_IS_FINAL = "isFinal";
    private static final int MAX_BITMAP_SIZE = 104857600;
    public static final String PRODUCER_NAME = "DecodeProducer";
    public static final String REQUESTED_IMAGE_SIZE = "requestedImageSize";
    public static final String SAMPLE_SIZE = "sampleSize";
    private final ByteArrayPool mByteArrayPool;
    private final CloseableReferenceFactory mCloseableReferenceFactory;
    private final boolean mDecodeCancellationEnabled;
    private final boolean mDownsampleEnabled;
    private final boolean mDownsampleEnabledForNetwork;
    private final Executor mExecutor;
    private final ImageDecoder mImageDecoder;
    private final Producer<EncodedImage> mInputProducer;
    private final int mMaxBitmapSize;
    private final ProgressiveJpegConfig mProgressiveJpegConfig;

    @Nullable
    private final Runnable mReclaimMemoryRunnable;
    private final Supplier<Boolean> mRecoverFromDecoderOOM;

    public DecodeProducer(final ByteArrayPool byteArrayPool, final Executor executor, final ImageDecoder imageDecoder, final ProgressiveJpegConfig progressiveJpegConfig, final boolean downsampleEnabled, final boolean downsampleEnabledForNetwork, final boolean decodeCancellationEnabled, final Producer<EncodedImage> inputProducer, final int maxBitmapSize, final CloseableReferenceFactory closeableReferenceFactory, @Nullable final Runnable reclaimMemoryRunnable, Supplier<Boolean> recoverFromDecoderOOM) {
        this.mByteArrayPool = (ByteArrayPool) Preconditions.checkNotNull(byteArrayPool);
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mImageDecoder = (ImageDecoder) Preconditions.checkNotNull(imageDecoder);
        this.mProgressiveJpegConfig = (ProgressiveJpegConfig) Preconditions.checkNotNull(progressiveJpegConfig);
        this.mDownsampleEnabled = downsampleEnabled;
        this.mDownsampleEnabledForNetwork = downsampleEnabledForNetwork;
        this.mInputProducer = (Producer) Preconditions.checkNotNull(inputProducer);
        this.mDecodeCancellationEnabled = decodeCancellationEnabled;
        this.mMaxBitmapSize = maxBitmapSize;
        this.mCloseableReferenceFactory = closeableReferenceFactory;
        this.mReclaimMemoryRunnable = reclaimMemoryRunnable;
        this.mRecoverFromDecoderOOM = recoverFromDecoderOOM;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(final Consumer<CloseableReference<CloseableImage>> consumer, final ProducerContext producerContext) {
        Consumer<EncodedImage> networkImagesProgressiveDecoder;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("DecodeProducer#produceResults");
            }
            if (!UriUtil.isNetworkUri(producerContext.getImageRequest().getSourceUri())) {
                networkImagesProgressiveDecoder = new LocalImagesProgressiveDecoder(consumer, producerContext, this.mDecodeCancellationEnabled, this.mMaxBitmapSize);
            } else {
                networkImagesProgressiveDecoder = new NetworkImagesProgressiveDecoder(consumer, producerContext, new ProgressiveJpegParser(this.mByteArrayPool), this.mProgressiveJpegConfig, this.mDecodeCancellationEnabled, this.mMaxBitmapSize);
            }
            this.mInputProducer.produceResults(networkImagesProgressiveDecoder, producerContext);
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    private abstract class ProgressiveDecoder extends DelegatingConsumer<EncodedImage, CloseableReference<CloseableImage>> {
        private static final int DECODE_EXCEPTION_MESSAGE_NUM_HEADER_BYTES = 10;
        private final String TAG;
        private final ImageDecodeOptions mImageDecodeOptions;
        private boolean mIsFinished;
        private final JobScheduler mJobScheduler;
        private final ProducerContext mProducerContext;
        private final ProducerListener2 mProducerListener;

        protected abstract int getIntermediateImageEndOffset(EncodedImage encodedImage);

        protected abstract QualityInfo getQualityInfo();

        public ProgressiveDecoder(final Consumer<CloseableReference<CloseableImage>> consumer, final ProducerContext producerContext, final boolean decodeCancellationEnabled, final int maxBitmapSize) {
            super(consumer);
            this.TAG = "ProgressiveDecoder";
            this.mProducerContext = producerContext;
            this.mProducerListener = producerContext.getProducerListener();
            ImageDecodeOptions imageDecodeOptions = producerContext.getImageRequest().getImageDecodeOptions();
            this.mImageDecodeOptions = imageDecodeOptions;
            this.mIsFinished = false;
            this.mJobScheduler = new JobScheduler(DecodeProducer.this.mExecutor, new JobScheduler.JobRunnable() { // from class: com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder.1
                @Override // com.facebook.imagepipeline.producers.JobScheduler.JobRunnable
                public void run(EncodedImage encodedImage, int status) {
                    if (encodedImage != null) {
                        ProgressiveDecoder.this.mProducerContext.setExtra(ProducerContext.ExtraKeys.IMAGE_FORMAT, encodedImage.getImageFormat().getName());
                        if (DecodeProducer.this.mDownsampleEnabled || !BaseConsumer.statusHasFlag(status, 16)) {
                            ImageRequest imageRequest = producerContext.getImageRequest();
                            if (DecodeProducer.this.mDownsampleEnabledForNetwork || !UriUtil.isNetworkUri(imageRequest.getSourceUri())) {
                                encodedImage.setSampleSize(DownsampleUtil.determineSampleSize(imageRequest.getRotationOptions(), imageRequest.getResizeOptions(), encodedImage, maxBitmapSize));
                            }
                        }
                        if (producerContext.getImagePipelineConfig().getExperiments().shouldDownsampleIfLargeBitmap()) {
                            ProgressiveDecoder.this.maybeIncreaseSampleSize(encodedImage);
                        }
                        ProgressiveDecoder.this.doDecode(encodedImage, status);
                    }
                }
            }, imageDecodeOptions.minDecodeIntervalMs);
            producerContext.addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder.2
                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onIsIntermediateResultExpectedChanged() {
                    if (ProgressiveDecoder.this.mProducerContext.isIntermediateResultExpected()) {
                        ProgressiveDecoder.this.mJobScheduler.scheduleJob();
                    }
                }

                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onCancellationRequested() {
                    if (decodeCancellationEnabled) {
                        ProgressiveDecoder.this.handleCancellation();
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void maybeIncreaseSampleSize(final EncodedImage encodedImage) {
            if (encodedImage.getImageFormat() != DefaultImageFormats.JPEG) {
                return;
            }
            encodedImage.setSampleSize(DownsampleUtil.determineSampleSizeJPEG(encodedImage, BitmapUtil.getPixelSizeForBitmapConfig(this.mImageDecodeOptions.bitmapConfig), DecodeProducer.MAX_BITMAP_SIZE));
        }

        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(@Nullable EncodedImage newResult, int status) {
            boolean zIsTracing;
            try {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.beginSection("DecodeProducer#onNewResultImpl");
                }
                boolean zIsLast = isLast(status);
                if (zIsLast) {
                    if (newResult == null) {
                        handleError(new ExceptionWithNoStacktrace("Encoded image is null."));
                        if (zIsTracing) {
                            return;
                        } else {
                            return;
                        }
                    } else if (!newResult.isValid()) {
                        handleError(new ExceptionWithNoStacktrace("Encoded image is not valid."));
                        if (FrescoSystrace.isTracing()) {
                            FrescoSystrace.endSection();
                            return;
                        }
                        return;
                    }
                }
                if (!updateDecodeJob(newResult, status)) {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                        return;
                    }
                    return;
                }
                boolean zStatusHasFlag = statusHasFlag(status, 4);
                if (zIsLast || zStatusHasFlag || this.mProducerContext.isIntermediateResultExpected()) {
                    this.mJobScheduler.scheduleJob();
                }
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            } finally {
                if (FrescoSystrace.isTracing()) {
                    FrescoSystrace.endSection();
                }
            }
        }

        @Override // com.facebook.imagepipeline.producers.DelegatingConsumer, com.facebook.imagepipeline.producers.BaseConsumer
        protected void onProgressUpdateImpl(float progress) {
            super.onProgressUpdateImpl(progress * 0.99f);
        }

        @Override // com.facebook.imagepipeline.producers.DelegatingConsumer, com.facebook.imagepipeline.producers.BaseConsumer
        public void onFailureImpl(Throwable t) {
            handleError(t);
        }

        @Override // com.facebook.imagepipeline.producers.DelegatingConsumer, com.facebook.imagepipeline.producers.BaseConsumer
        public void onCancellationImpl() {
            handleCancellation();
        }

        protected boolean updateDecodeJob(@Nullable EncodedImage ref, int status) {
            return this.mJobScheduler.updateJob(ref, status);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void doDecode(EncodedImage encodedImage, int status) {
            String str;
            int size;
            QualityInfo qualityInfo;
            CloseableImage closeableImage;
            int i = status;
            if ((encodedImage.getImageFormat() != DefaultImageFormats.JPEG && isNotLast(status)) || isFinished() || !EncodedImage.isValid(encodedImage)) {
                return;
            }
            ImageFormat imageFormat = encodedImage.getImageFormat();
            String name = imageFormat != null ? imageFormat.getName() : EnvironmentCompat.MEDIA_UNKNOWN;
            String str2 = encodedImage.getWidth() + "x" + encodedImage.getHeight();
            String strValueOf = String.valueOf(encodedImage.getSampleSize());
            boolean zIsLast = isLast(status);
            boolean z = zIsLast && !statusHasFlag(i, 8);
            boolean zStatusHasFlag = statusHasFlag(i, 4);
            ResizeOptions resizeOptions = this.mProducerContext.getImageRequest().getResizeOptions();
            if (resizeOptions != null) {
                str = resizeOptions.width + "x" + resizeOptions.height;
            } else {
                str = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            try {
                long queuedTime = this.mJobScheduler.getQueuedTime();
                String strValueOf2 = String.valueOf(this.mProducerContext.getImageRequest().getSourceUri());
                if (z || zStatusHasFlag) {
                    size = encodedImage.getSize();
                } else {
                    size = getIntermediateImageEndOffset(encodedImage);
                }
                if (z || zStatusHasFlag) {
                    qualityInfo = ImmutableQualityInfo.FULL_QUALITY;
                } else {
                    qualityInfo = getQualityInfo();
                }
                QualityInfo qualityInfo2 = qualityInfo;
                this.mProducerListener.onProducerStart(this.mProducerContext, DecodeProducer.PRODUCER_NAME);
                try {
                    try {
                        CloseableImage closeableImageInternalDecode = internalDecode(encodedImage, size, qualityInfo2);
                        try {
                            if (encodedImage.getSampleSize() != 1) {
                                i |= 16;
                            }
                            this.mProducerListener.onProducerFinishWithSuccess(this.mProducerContext, DecodeProducer.PRODUCER_NAME, getExtraMap(closeableImageInternalDecode, queuedTime, qualityInfo2, zIsLast, name, str2, str, strValueOf));
                            setImageExtras(encodedImage, closeableImageInternalDecode);
                            handleResult(closeableImageInternalDecode, i);
                        } catch (Exception e) {
                            e = e;
                            closeableImage = closeableImageInternalDecode;
                            this.mProducerListener.onProducerFinishWithFailure(this.mProducerContext, DecodeProducer.PRODUCER_NAME, e, getExtraMap(closeableImage, queuedTime, qualityInfo2, zIsLast, name, str2, str, strValueOf));
                            handleError(e);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        closeableImage = null;
                    }
                } catch (DecodeException e3) {
                    EncodedImage encodedImage2 = e3.getEncodedImage();
                    FLog.w("ProgressiveDecoder", "%s, {uri: %s, firstEncodedBytes: %s, length: %d}", e3.getMessage(), strValueOf2, encodedImage2.getFirstBytesAsHexString(10), Integer.valueOf(encodedImage2.getSize()));
                    throw e3;
                }
            } finally {
                EncodedImage.closeSafely(encodedImage);
            }
        }

        private CloseableImage internalDecode(EncodedImage encodedImage, int length, QualityInfo quality) {
            boolean z = DecodeProducer.this.mReclaimMemoryRunnable != null && ((Boolean) DecodeProducer.this.mRecoverFromDecoderOOM.get()).booleanValue();
            try {
                return DecodeProducer.this.mImageDecoder.decode(encodedImage, length, quality, this.mImageDecodeOptions);
            } catch (OutOfMemoryError e) {
                if (z) {
                    DecodeProducer.this.mReclaimMemoryRunnable.run();
                    System.gc();
                    return DecodeProducer.this.mImageDecoder.decode(encodedImage, length, quality, this.mImageDecodeOptions);
                }
                throw e;
            }
        }

        private void setImageExtras(EncodedImage encodedImage, CloseableImage image) {
            this.mProducerContext.setExtra(ProducerContext.ExtraKeys.ENCODED_WIDTH, Integer.valueOf(encodedImage.getWidth()));
            this.mProducerContext.setExtra(ProducerContext.ExtraKeys.ENCODED_HEIGHT, Integer.valueOf(encodedImage.getHeight()));
            this.mProducerContext.setExtra(ProducerContext.ExtraKeys.ENCODED_SIZE, Integer.valueOf(encodedImage.getSize()));
            if (image instanceof CloseableBitmap) {
                Bitmap underlyingBitmap = ((CloseableBitmap) image).getUnderlyingBitmap();
                this.mProducerContext.setExtra("bitmap_config", String.valueOf(underlyingBitmap == null ? null : underlyingBitmap.getConfig()));
            }
            if (image != null) {
                image.setImageExtras(this.mProducerContext.getExtras());
            }
        }

        @Nullable
        private Map<String, String> getExtraMap(@Nullable CloseableImage image, long queueTime, QualityInfo quality, boolean isFinal, String imageFormatName, String encodedImageSize, String requestImageSize, String sampleSize) {
            if (!this.mProducerListener.requiresExtraMap(this.mProducerContext, DecodeProducer.PRODUCER_NAME)) {
                return null;
            }
            String strValueOf = String.valueOf(queueTime);
            String strValueOf2 = String.valueOf(quality.isOfGoodEnoughQuality());
            String strValueOf3 = String.valueOf(isFinal);
            if (image instanceof CloseableStaticBitmap) {
                Bitmap underlyingBitmap = ((CloseableStaticBitmap) image).getUnderlyingBitmap();
                Preconditions.checkNotNull(underlyingBitmap);
                String str = underlyingBitmap.getWidth() + "x" + underlyingBitmap.getHeight();
                HashMap map = new HashMap(8);
                map.put(DecodeProducer.EXTRA_BITMAP_SIZE, str);
                map.put("queueTime", strValueOf);
                map.put(DecodeProducer.EXTRA_HAS_GOOD_QUALITY, strValueOf2);
                map.put(DecodeProducer.EXTRA_IS_FINAL, strValueOf3);
                map.put("encodedImageSize", encodedImageSize);
                map.put(DecodeProducer.EXTRA_IMAGE_FORMAT_NAME, imageFormatName);
                map.put(DecodeProducer.REQUESTED_IMAGE_SIZE, requestImageSize);
                map.put(DecodeProducer.SAMPLE_SIZE, sampleSize);
                map.put(DecodeProducer.EXTRA_BITMAP_BYTES, underlyingBitmap.getByteCount() + "");
                return ImmutableMap.copyOf((Map) map);
            }
            HashMap map2 = new HashMap(7);
            map2.put("queueTime", strValueOf);
            map2.put(DecodeProducer.EXTRA_HAS_GOOD_QUALITY, strValueOf2);
            map2.put(DecodeProducer.EXTRA_IS_FINAL, strValueOf3);
            map2.put("encodedImageSize", encodedImageSize);
            map2.put(DecodeProducer.EXTRA_IMAGE_FORMAT_NAME, imageFormatName);
            map2.put(DecodeProducer.REQUESTED_IMAGE_SIZE, requestImageSize);
            map2.put(DecodeProducer.SAMPLE_SIZE, sampleSize);
            return ImmutableMap.copyOf((Map) map2);
        }

        private synchronized boolean isFinished() {
            return this.mIsFinished;
        }

        private void maybeFinish(boolean shouldFinish) {
            synchronized (this) {
                if (shouldFinish) {
                    if (!this.mIsFinished) {
                        getConsumer().onProgressUpdate(1.0f);
                        this.mIsFinished = true;
                        this.mJobScheduler.clearJob();
                    }
                }
            }
        }

        private void handleResult(final CloseableImage decodedImage, final int status) {
            CloseableReference<CloseableImage> closeableReferenceCreate = DecodeProducer.this.mCloseableReferenceFactory.create(decodedImage);
            try {
                maybeFinish(isLast(status));
                getConsumer().onNewResult(closeableReferenceCreate, status);
            } finally {
                CloseableReference.closeSafely(closeableReferenceCreate);
            }
        }

        private void handleError(Throwable t) {
            maybeFinish(true);
            getConsumer().onFailure(t);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleCancellation() {
            maybeFinish(true);
            getConsumer().onCancellation();
        }
    }

    private class LocalImagesProgressiveDecoder extends ProgressiveDecoder {
        public LocalImagesProgressiveDecoder(final Consumer<CloseableReference<CloseableImage>> consumer, final ProducerContext producerContext, final boolean decodeCancellationEnabled, final int maxBitmapSize) {
            super(consumer, producerContext, decodeCancellationEnabled, maxBitmapSize);
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected synchronized boolean updateDecodeJob(@Nullable EncodedImage encodedImage, int status) {
            if (isNotLast(status)) {
                return false;
            }
            return super.updateDecodeJob(encodedImage, status);
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return encodedImage.getSize();
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected QualityInfo getQualityInfo() {
            return ImmutableQualityInfo.of(0, false, false);
        }
    }

    private class NetworkImagesProgressiveDecoder extends ProgressiveDecoder {
        private int mLastScheduledScanNumber;
        private final ProgressiveJpegConfig mProgressiveJpegConfig;
        private final ProgressiveJpegParser mProgressiveJpegParser;

        public NetworkImagesProgressiveDecoder(final Consumer<CloseableReference<CloseableImage>> consumer, final ProducerContext producerContext, final ProgressiveJpegParser progressiveJpegParser, final ProgressiveJpegConfig progressiveJpegConfig, final boolean decodeCancellationEnabled, final int maxBitmapSize) {
            super(consumer, producerContext, decodeCancellationEnabled, maxBitmapSize);
            this.mProgressiveJpegParser = (ProgressiveJpegParser) Preconditions.checkNotNull(progressiveJpegParser);
            this.mProgressiveJpegConfig = (ProgressiveJpegConfig) Preconditions.checkNotNull(progressiveJpegConfig);
            this.mLastScheduledScanNumber = 0;
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected synchronized boolean updateDecodeJob(@Nullable EncodedImage encodedImage, int status) {
            boolean zUpdateDecodeJob = super.updateDecodeJob(encodedImage, status);
            if ((isNotLast(status) || statusHasFlag(status, 8)) && !statusHasFlag(status, 4) && EncodedImage.isValid(encodedImage) && encodedImage.getImageFormat() == DefaultImageFormats.JPEG) {
                if (!this.mProgressiveJpegParser.parseMoreData(encodedImage)) {
                    return false;
                }
                int bestScanNumber = this.mProgressiveJpegParser.getBestScanNumber();
                int i = this.mLastScheduledScanNumber;
                if (bestScanNumber <= i) {
                    return false;
                }
                if (bestScanNumber < this.mProgressiveJpegConfig.getNextScanNumberToDecode(i) && !this.mProgressiveJpegParser.isEndMarkerRead()) {
                    return false;
                }
                this.mLastScheduledScanNumber = bestScanNumber;
            }
            return zUpdateDecodeJob;
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected int getIntermediateImageEndOffset(EncodedImage encodedImage) {
            return this.mProgressiveJpegParser.getBestScanEndOffset();
        }

        @Override // com.facebook.imagepipeline.producers.DecodeProducer.ProgressiveDecoder
        protected QualityInfo getQualityInfo() {
            return this.mProgressiveJpegConfig.getQualityInfo(this.mProgressiveJpegParser.getBestScanNumber());
        }
    }
}
