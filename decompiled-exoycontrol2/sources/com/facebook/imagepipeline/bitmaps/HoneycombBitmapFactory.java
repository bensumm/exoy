package com.facebook.imagepipeline.bitmaps;

import android.graphics.Bitmap;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imagepipeline.core.CloseableReferenceFactory;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.platform.PlatformDecoder;

/* JADX INFO: loaded from: classes.dex */
public class HoneycombBitmapFactory extends PlatformBitmapFactory {
    private static final String TAG = "HoneycombBitmapFactory";
    private final CloseableReferenceFactory mCloseableReferenceFactory;
    private boolean mImmutableBitmapFallback;
    private final EmptyJpegGenerator mJpegGenerator;
    private final PlatformDecoder mPurgeableDecoder;

    public HoneycombBitmapFactory(EmptyJpegGenerator jpegGenerator, PlatformDecoder purgeableDecoder, CloseableReferenceFactory closeableReferenceFactory) {
        this.mJpegGenerator = jpegGenerator;
        this.mPurgeableDecoder = purgeableDecoder;
        this.mCloseableReferenceFactory = closeableReferenceFactory;
    }

    @Override // com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory
    public CloseableReference<Bitmap> createBitmapInternal(int width, int height, Bitmap.Config bitmapConfig) throws Throwable {
        if (this.mImmutableBitmapFallback) {
            return createFallbackBitmap(width, height, bitmapConfig);
        }
        CloseableReference<PooledByteBuffer> closeableReferenceGenerate = this.mJpegGenerator.generate((short) width, (short) height);
        try {
            EncodedImage encodedImage = new EncodedImage(closeableReferenceGenerate);
            encodedImage.setImageFormat(DefaultImageFormats.JPEG);
            try {
                CloseableReference<Bitmap> closeableReferenceDecodeJPEGFromEncodedImage = this.mPurgeableDecoder.decodeJPEGFromEncodedImage(encodedImage, bitmapConfig, null, closeableReferenceGenerate.get().size());
                if (!closeableReferenceDecodeJPEGFromEncodedImage.get().isMutable()) {
                    CloseableReference.closeSafely(closeableReferenceDecodeJPEGFromEncodedImage);
                    this.mImmutableBitmapFallback = true;
                    FLog.wtf(TAG, "Immutable bitmap returned by decoder");
                    return createFallbackBitmap(width, height, bitmapConfig);
                }
                closeableReferenceDecodeJPEGFromEncodedImage.get().setHasAlpha(true);
                closeableReferenceDecodeJPEGFromEncodedImage.get().eraseColor(0);
                return closeableReferenceDecodeJPEGFromEncodedImage;
            } finally {
                EncodedImage.closeSafely(encodedImage);
            }
        } finally {
            closeableReferenceGenerate.close();
        }
    }

    private CloseableReference<Bitmap> createFallbackBitmap(int width, int height, Bitmap.Config bitmapConfig) {
        return this.mCloseableReferenceFactory.create(Bitmap.createBitmap(width, height, bitmapConfig), SimpleBitmapReleaser.getInstance());
    }
}
