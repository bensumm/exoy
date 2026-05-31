package com.facebook.imagepipeline.transcoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import com.facebook.common.logging.FLog;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import java.io.OutputStream;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class SimpleImageTranscoder implements ImageTranscoder {
    private static final String TAG = "SimpleImageTranscoder";
    private final int mMaxBitmapSize;
    private final boolean mResizingEnabled;

    @Override // com.facebook.imagepipeline.transcoder.ImageTranscoder
    public String getIdentifier() {
        return TAG;
    }

    public SimpleImageTranscoder(final boolean resizingEnabled, final int maxBitmapSize) {
        this.mResizingEnabled = resizingEnabled;
        this.mMaxBitmapSize = maxBitmapSize;
    }

    @Override // com.facebook.imagepipeline.transcoder.ImageTranscoder
    public ImageTranscodeResult transcode(EncodedImage encodedImage, OutputStream outputStream, @Nullable RotationOptions rotationOptions, @Nullable ResizeOptions resizeOptions, @Nullable ImageFormat outputFormat, @Nullable Integer quality) throws Throwable {
        SimpleImageTranscoder simpleImageTranscoder;
        RotationOptions rotationOptionsAutoRotate;
        Bitmap bitmapCreateBitmap;
        Throwable th;
        OutOfMemoryError e;
        Integer num = quality == null ? 85 : quality;
        if (rotationOptions == null) {
            rotationOptionsAutoRotate = RotationOptions.autoRotate();
            simpleImageTranscoder = this;
        } else {
            simpleImageTranscoder = this;
            rotationOptionsAutoRotate = rotationOptions;
        }
        int sampleSize = simpleImageTranscoder.getSampleSize(encodedImage, rotationOptionsAutoRotate, resizeOptions);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        try {
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(encodedImage.getInputStream(), null, options);
            if (bitmapDecodeStream == null) {
                FLog.e(TAG, "Couldn't decode the EncodedImage InputStream ! ");
                return new ImageTranscodeResult(2);
            }
            Matrix transformationMatrix = JpegTranscoderUtils.getTransformationMatrix(encodedImage, rotationOptionsAutoRotate);
            if (transformationMatrix != null) {
                try {
                    bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeStream, 0, 0, bitmapDecodeStream.getWidth(), bitmapDecodeStream.getHeight(), transformationMatrix, false);
                } catch (OutOfMemoryError e2) {
                    e = e2;
                    bitmapCreateBitmap = bitmapDecodeStream;
                    FLog.e(TAG, "Out-Of-Memory during transcode", e);
                    ImageTranscodeResult imageTranscodeResult = new ImageTranscodeResult(2);
                    bitmapCreateBitmap.recycle();
                    bitmapDecodeStream.recycle();
                    return imageTranscodeResult;
                } catch (Throwable th2) {
                    th = th2;
                    bitmapCreateBitmap = bitmapDecodeStream;
                    bitmapCreateBitmap.recycle();
                    bitmapDecodeStream.recycle();
                    throw th;
                }
            } else {
                bitmapCreateBitmap = bitmapDecodeStream;
            }
            try {
                try {
                    bitmapCreateBitmap.compress(getOutputFormat(outputFormat), num.intValue(), outputStream);
                    ImageTranscodeResult imageTranscodeResult2 = new ImageTranscodeResult(sampleSize > 1 ? 0 : 1);
                    bitmapCreateBitmap.recycle();
                    bitmapDecodeStream.recycle();
                    return imageTranscodeResult2;
                } catch (Throwable th3) {
                    th = th3;
                    bitmapCreateBitmap.recycle();
                    bitmapDecodeStream.recycle();
                    throw th;
                }
            } catch (OutOfMemoryError e3) {
                e = e3;
                FLog.e(TAG, "Out-Of-Memory during transcode", e);
                ImageTranscodeResult imageTranscodeResult3 = new ImageTranscodeResult(2);
                bitmapCreateBitmap.recycle();
                bitmapDecodeStream.recycle();
                return imageTranscodeResult3;
            }
        } catch (OutOfMemoryError e4) {
            FLog.e(TAG, "Out-Of-Memory during transcode", e4);
            return new ImageTranscodeResult(2);
        }
    }

    @Override // com.facebook.imagepipeline.transcoder.ImageTranscoder
    public boolean canResize(EncodedImage encodedImage, @Nullable RotationOptions rotationOptions, @Nullable ResizeOptions resizeOptions) {
        if (rotationOptions == null) {
            rotationOptions = RotationOptions.autoRotate();
        }
        return this.mResizingEnabled && DownsampleUtil.determineSampleSize(rotationOptions, resizeOptions, encodedImage, this.mMaxBitmapSize) > 1;
    }

    @Override // com.facebook.imagepipeline.transcoder.ImageTranscoder
    public boolean canTranscode(ImageFormat imageFormat) {
        return imageFormat == DefaultImageFormats.HEIF || imageFormat == DefaultImageFormats.JPEG;
    }

    private int getSampleSize(final EncodedImage encodedImage, final RotationOptions rotationOptions, @Nullable final ResizeOptions resizeOptions) {
        if (this.mResizingEnabled) {
            return DownsampleUtil.determineSampleSize(rotationOptions, resizeOptions, encodedImage, this.mMaxBitmapSize);
        }
        return 1;
    }

    private static Bitmap.CompressFormat getOutputFormat(@Nullable final ImageFormat format) {
        if (format == null) {
            return Bitmap.CompressFormat.JPEG;
        }
        if (format == DefaultImageFormats.JPEG) {
            return Bitmap.CompressFormat.JPEG;
        }
        if (format == DefaultImageFormats.PNG) {
            return Bitmap.CompressFormat.PNG;
        }
        if (DefaultImageFormats.isStaticWebpFormat(format)) {
            return Bitmap.CompressFormat.WEBP;
        }
        return Bitmap.CompressFormat.JPEG;
    }
}
