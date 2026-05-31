package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteBufferOutputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.TriState;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.JobScheduler;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.transcoder.ImageTranscodeResult;
import com.facebook.imagepipeline.transcoder.ImageTranscoder;
import com.facebook.imagepipeline.transcoder.ImageTranscoderFactory;
import com.facebook.imagepipeline.transcoder.JpegTranscoderUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class ResizeAndRotateProducer implements Producer<EncodedImage> {
    private static final String INPUT_IMAGE_FORMAT = "Image format";
    static final int MIN_TRANSFORM_INTERVAL_MS = 100;
    private static final String ORIGINAL_SIZE_KEY = "Original size";
    private static final String PRODUCER_NAME = "ResizeAndRotateProducer";
    private static final String REQUESTED_SIZE_KEY = "Requested size";
    private static final String TRANSCODER_ID = "Transcoder id";
    private static final String TRANSCODING_RESULT = "Transcoding result";
    private final Executor mExecutor;
    private final ImageTranscoderFactory mImageTranscoderFactory;
    private final Producer<EncodedImage> mInputProducer;
    private final boolean mIsResizingEnabled;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public ResizeAndRotateProducer(final Executor executor, final PooledByteBufferFactory pooledByteBufferFactory, final Producer<EncodedImage> inputProducer, final boolean isResizingEnabled, final ImageTranscoderFactory imageTranscoderFactory) {
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
        this.mPooledByteBufferFactory = (PooledByteBufferFactory) Preconditions.checkNotNull(pooledByteBufferFactory);
        this.mInputProducer = (Producer) Preconditions.checkNotNull(inputProducer);
        this.mImageTranscoderFactory = (ImageTranscoderFactory) Preconditions.checkNotNull(imageTranscoderFactory);
        this.mIsResizingEnabled = isResizingEnabled;
    }

    @Override // com.facebook.imagepipeline.producers.Producer
    public void produceResults(final Consumer<EncodedImage> consumer, final ProducerContext context) {
        this.mInputProducer.produceResults(new TransformingConsumer(consumer, context, this.mIsResizingEnabled, this.mImageTranscoderFactory), context);
    }

    private class TransformingConsumer extends DelegatingConsumer<EncodedImage, EncodedImage> {
        private final ImageTranscoderFactory mImageTranscoderFactory;
        private boolean mIsCancelled;
        private final boolean mIsResizingEnabled;
        private final JobScheduler mJobScheduler;
        private final ProducerContext mProducerContext;

        TransformingConsumer(final Consumer<EncodedImage> consumer, final ProducerContext producerContext, final boolean isResizingEnabled, final ImageTranscoderFactory imageTranscoderFactory) {
            super(consumer);
            this.mIsCancelled = false;
            this.mProducerContext = producerContext;
            Boolean resizingAllowedOverride = producerContext.getImageRequest().getResizingAllowedOverride();
            this.mIsResizingEnabled = resizingAllowedOverride != null ? resizingAllowedOverride.booleanValue() : isResizingEnabled;
            this.mImageTranscoderFactory = imageTranscoderFactory;
            this.mJobScheduler = new JobScheduler(ResizeAndRotateProducer.this.mExecutor, new JobScheduler.JobRunnable() { // from class: com.facebook.imagepipeline.producers.ResizeAndRotateProducer.TransformingConsumer.1
                @Override // com.facebook.imagepipeline.producers.JobScheduler.JobRunnable
                public void run(EncodedImage encodedImage, int status) throws Throwable {
                    TransformingConsumer transformingConsumer = TransformingConsumer.this;
                    transformingConsumer.doTransform(encodedImage, status, (ImageTranscoder) Preconditions.checkNotNull(transformingConsumer.mImageTranscoderFactory.createImageTranscoder(encodedImage.getImageFormat(), TransformingConsumer.this.mIsResizingEnabled)));
                }
            }, 100);
            producerContext.addCallbacks(new BaseProducerContextCallbacks() { // from class: com.facebook.imagepipeline.producers.ResizeAndRotateProducer.TransformingConsumer.2
                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onIsIntermediateResultExpectedChanged() {
                    if (TransformingConsumer.this.mProducerContext.isIntermediateResultExpected()) {
                        TransformingConsumer.this.mJobScheduler.scheduleJob();
                    }
                }

                @Override // com.facebook.imagepipeline.producers.BaseProducerContextCallbacks, com.facebook.imagepipeline.producers.ProducerContextCallbacks
                public void onCancellationRequested() {
                    TransformingConsumer.this.mJobScheduler.clearJob();
                    TransformingConsumer.this.mIsCancelled = true;
                    consumer.onCancellation();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.imagepipeline.producers.BaseConsumer
        public void onNewResultImpl(@Nullable EncodedImage newResult, int status) {
            if (this.mIsCancelled) {
                return;
            }
            boolean zIsLast = isLast(status);
            if (newResult == null) {
                if (zIsLast) {
                    getConsumer().onNewResult(null, 1);
                    return;
                }
                return;
            }
            ImageFormat imageFormat = newResult.getImageFormat();
            TriState triStateShouldTransform = ResizeAndRotateProducer.shouldTransform(this.mProducerContext.getImageRequest(), newResult, (ImageTranscoder) Preconditions.checkNotNull(this.mImageTranscoderFactory.createImageTranscoder(imageFormat, this.mIsResizingEnabled)));
            if (zIsLast || triStateShouldTransform != TriState.UNSET) {
                if (triStateShouldTransform != TriState.YES) {
                    forwardNewResult(newResult, status, imageFormat);
                } else if (this.mJobScheduler.updateJob(newResult, status)) {
                    if (zIsLast || this.mProducerContext.isIntermediateResultExpected()) {
                        this.mJobScheduler.scheduleJob();
                    }
                }
            }
        }

        private void forwardNewResult(EncodedImage newResult, int status, ImageFormat imageFormat) {
            EncodedImage newResultsForJpegOrHeif;
            if (imageFormat == DefaultImageFormats.JPEG || imageFormat == DefaultImageFormats.HEIF) {
                newResultsForJpegOrHeif = getNewResultsForJpegOrHeif(newResult);
            } else {
                newResultsForJpegOrHeif = getNewResultForImagesWithoutExifData(newResult);
            }
            getConsumer().onNewResult(newResultsForJpegOrHeif, status);
        }

        @Nullable
        private EncodedImage getNewResultForImagesWithoutExifData(EncodedImage encodedImage) {
            RotationOptions rotationOptions = this.mProducerContext.getImageRequest().getRotationOptions();
            return (rotationOptions.useImageMetadata() || !rotationOptions.rotationEnabled()) ? encodedImage : getCloneWithRotationApplied(encodedImage, rotationOptions.getForcedAngle());
        }

        @Nullable
        private EncodedImage getNewResultsForJpegOrHeif(EncodedImage encodedImage) {
            return (this.mProducerContext.getImageRequest().getRotationOptions().canDeferUntilRendered() || encodedImage.getRotationAngle() == 0 || encodedImage.getRotationAngle() == -1) ? encodedImage : getCloneWithRotationApplied(encodedImage, 0);
        }

        @Nullable
        private EncodedImage getCloneWithRotationApplied(EncodedImage encodedImage, int angle) {
            EncodedImage encodedImageCloneOrNull = EncodedImage.cloneOrNull(encodedImage);
            if (encodedImageCloneOrNull != null) {
                encodedImageCloneOrNull.setRotationAngle(angle);
            }
            return encodedImageCloneOrNull;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void doTransform(EncodedImage encodedImage, int status, ImageTranscoder imageTranscoder) throws Throwable {
            this.mProducerContext.getProducerListener().onProducerStart(this.mProducerContext, ResizeAndRotateProducer.PRODUCER_NAME);
            ImageRequest imageRequest = this.mProducerContext.getImageRequest();
            PooledByteBufferOutputStream pooledByteBufferOutputStreamNewOutputStream = ResizeAndRotateProducer.this.mPooledByteBufferFactory.newOutputStream();
            try {
                ImageTranscodeResult imageTranscodeResultTranscode = imageTranscoder.transcode(encodedImage, pooledByteBufferOutputStreamNewOutputStream, imageRequest.getRotationOptions(), imageRequest.getResizeOptions(), null, 85);
                if (imageTranscodeResultTranscode.getTranscodeStatus() == 2) {
                    throw new RuntimeException("Error while transcoding the image");
                }
                Map<String, String> extraMap = getExtraMap(encodedImage, imageRequest.getResizeOptions(), imageTranscodeResultTranscode, imageTranscoder.getIdentifier());
                CloseableReference closeableReferenceOf = CloseableReference.of(pooledByteBufferOutputStreamNewOutputStream.toByteBuffer());
                try {
                    EncodedImage encodedImage2 = new EncodedImage((CloseableReference<PooledByteBuffer>) closeableReferenceOf);
                    encodedImage2.setImageFormat(DefaultImageFormats.JPEG);
                    try {
                        encodedImage2.parseMetaData();
                        this.mProducerContext.getProducerListener().onProducerFinishWithSuccess(this.mProducerContext, ResizeAndRotateProducer.PRODUCER_NAME, extraMap);
                        if (imageTranscodeResultTranscode.getTranscodeStatus() != 1) {
                            status |= 16;
                        }
                        getConsumer().onNewResult(encodedImage2, status);
                    } finally {
                        EncodedImage.closeSafely(encodedImage2);
                    }
                } finally {
                    CloseableReference.closeSafely((CloseableReference<?>) closeableReferenceOf);
                }
            } catch (Exception e) {
                this.mProducerContext.getProducerListener().onProducerFinishWithFailure(this.mProducerContext, ResizeAndRotateProducer.PRODUCER_NAME, e, null);
                if (isLast(status)) {
                    getConsumer().onFailure(e);
                }
            } finally {
                pooledByteBufferOutputStreamNewOutputStream.close();
            }
        }

        @Nullable
        private Map<String, String> getExtraMap(EncodedImage encodedImage, @Nullable ResizeOptions resizeOptions, @Nullable ImageTranscodeResult transcodeResult, @Nullable String transcoderId) {
            String str;
            if (!this.mProducerContext.getProducerListener().requiresExtraMap(this.mProducerContext, ResizeAndRotateProducer.PRODUCER_NAME)) {
                return null;
            }
            String str2 = encodedImage.getWidth() + "x" + encodedImage.getHeight();
            if (resizeOptions != null) {
                str = resizeOptions.width + "x" + resizeOptions.height;
            } else {
                str = "Unspecified";
            }
            HashMap map = new HashMap();
            map.put(ResizeAndRotateProducer.INPUT_IMAGE_FORMAT, String.valueOf(encodedImage.getImageFormat()));
            map.put(ResizeAndRotateProducer.ORIGINAL_SIZE_KEY, str2);
            map.put(ResizeAndRotateProducer.REQUESTED_SIZE_KEY, str);
            map.put("queueTime", String.valueOf(this.mJobScheduler.getQueuedTime()));
            map.put(ResizeAndRotateProducer.TRANSCODER_ID, transcoderId);
            map.put(ResizeAndRotateProducer.TRANSCODING_RESULT, String.valueOf(transcodeResult));
            return ImmutableMap.copyOf((Map) map);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TriState shouldTransform(ImageRequest request, EncodedImage encodedImage, ImageTranscoder imageTranscoder) {
        if (encodedImage == null || encodedImage.getImageFormat() == ImageFormat.UNKNOWN) {
            return TriState.UNSET;
        }
        if (!imageTranscoder.canTranscode(encodedImage.getImageFormat())) {
            return TriState.NO;
        }
        return TriState.valueOf(shouldRotate(request.getRotationOptions(), encodedImage) || imageTranscoder.canResize(encodedImage, request.getRotationOptions(), request.getResizeOptions()));
    }

    private static boolean shouldRotate(RotationOptions rotationOptions, EncodedImage encodedImage) {
        return !rotationOptions.canDeferUntilRendered() && (JpegTranscoderUtils.getRotationAngle(rotationOptions, encodedImage) != 0 || shouldRotateUsingExifOrientation(rotationOptions, encodedImage));
    }

    private static boolean shouldRotateUsingExifOrientation(RotationOptions rotationOptions, EncodedImage encodedImage) {
        if (!rotationOptions.rotationEnabled() || rotationOptions.canDeferUntilRendered()) {
            encodedImage.setExifOrientation(0);
            return false;
        }
        return JpegTranscoderUtils.INVERTED_EXIF_ORIENTATIONS.contains(Integer.valueOf(encodedImage.getExifOrientation()));
    }
}
