package com.facebook.react.modules.image;

import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.fbreact.specs.NativeImageLoaderAndroidSpec;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.fresco.ReactNetworkImageRequest;
import com.facebook.react.views.image.ReactCallerContextFactory;
import com.facebook.react.views.imagehelper.ImageSource;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(name = NativeImageLoaderAndroidSpec.NAME)
public class ImageLoaderModule extends NativeImageLoaderAndroidSpec implements LifecycleEventListener {
    private static final String ERROR_GET_SIZE_FAILURE = "E_GET_SIZE_FAILURE";
    private static final String ERROR_INVALID_URI = "E_INVALID_URI";
    private static final String ERROR_PREFETCH_FAILURE = "E_PREFETCH_FAILURE";
    private final Object mCallerContext;
    private ReactCallerContextFactory mCallerContextFactory;
    private final Object mEnqueuedRequestMonitor;
    private final SparseArray<DataSource<Void>> mEnqueuedRequests;
    private ImagePipeline mImagePipeline;

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
    }

    public ImageLoaderModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mEnqueuedRequestMonitor = new Object();
        this.mEnqueuedRequests = new SparseArray<>();
        this.mImagePipeline = null;
        this.mCallerContext = this;
    }

    public ImageLoaderModule(ReactApplicationContext reactApplicationContext, Object obj) {
        super(reactApplicationContext);
        this.mEnqueuedRequestMonitor = new Object();
        this.mEnqueuedRequests = new SparseArray<>();
        this.mImagePipeline = null;
        this.mCallerContext = obj;
    }

    public ImageLoaderModule(ReactApplicationContext reactApplicationContext, ImagePipeline imagePipeline, ReactCallerContextFactory reactCallerContextFactory) {
        super(reactApplicationContext);
        this.mEnqueuedRequestMonitor = new Object();
        this.mEnqueuedRequests = new SparseArray<>();
        this.mCallerContextFactory = reactCallerContextFactory;
        this.mImagePipeline = imagePipeline;
        this.mCallerContext = null;
    }

    private Object getCallerContext() {
        ReactCallerContextFactory reactCallerContextFactory = this.mCallerContextFactory;
        if (reactCallerContextFactory != null) {
            return reactCallerContextFactory.getOrCreateCallerContext("", "");
        }
        return this.mCallerContext;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ImagePipeline getImagePipeline() {
        ImagePipeline imagePipeline = this.mImagePipeline;
        return imagePipeline != null ? imagePipeline : Fresco.getImagePipeline();
    }

    @Override // com.facebook.fbreact.specs.NativeImageLoaderAndroidSpec
    @ReactMethod
    public void getSize(String str, final Promise promise) {
        if (str == null || str.isEmpty()) {
            promise.reject(ERROR_INVALID_URI, "Cannot get the size of an image for an empty URI");
        } else {
            getImagePipeline().fetchDecodedImage(ImageRequestBuilder.newBuilderWithSource(new ImageSource(getReactApplicationContext(), str).getUri()).build(), getCallerContext()).subscribe(new BaseDataSubscriber<CloseableReference<CloseableImage>>() { // from class: com.facebook.react.modules.image.ImageLoaderModule.1
                @Override // com.facebook.datasource.BaseDataSubscriber
                protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    if (dataSource.isFinished()) {
                        CloseableReference<CloseableImage> result = dataSource.getResult();
                        try {
                            if (result != null) {
                                try {
                                    CloseableImage closeableImage = result.get();
                                    WritableMap writableMapCreateMap = Arguments.createMap();
                                    writableMapCreateMap.putInt("width", closeableImage.getWidth());
                                    writableMapCreateMap.putInt("height", closeableImage.getHeight());
                                    promise.resolve(writableMapCreateMap);
                                } catch (Exception e) {
                                    promise.reject(ImageLoaderModule.ERROR_GET_SIZE_FAILURE, e);
                                }
                                return;
                            }
                            promise.reject(ImageLoaderModule.ERROR_GET_SIZE_FAILURE);
                        } finally {
                            CloseableReference.closeSafely(result);
                        }
                    }
                }

                @Override // com.facebook.datasource.BaseDataSubscriber
                protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    promise.reject(ImageLoaderModule.ERROR_GET_SIZE_FAILURE, dataSource.getFailureCause());
                }
            }, CallerThreadExecutor.getInstance());
        }
    }

    @Override // com.facebook.fbreact.specs.NativeImageLoaderAndroidSpec
    @ReactMethod
    public void getSizeWithHeaders(String str, ReadableMap readableMap, final Promise promise) {
        if (str == null || str.isEmpty()) {
            promise.reject(ERROR_INVALID_URI, "Cannot get the size of an image for an empty URI");
        } else {
            getImagePipeline().fetchDecodedImage(ReactNetworkImageRequest.fromBuilderWithHeaders(ImageRequestBuilder.newBuilderWithSource(new ImageSource(getReactApplicationContext(), str).getUri()), readableMap), getCallerContext()).subscribe(new BaseDataSubscriber<CloseableReference<CloseableImage>>() { // from class: com.facebook.react.modules.image.ImageLoaderModule.2
                @Override // com.facebook.datasource.BaseDataSubscriber
                protected void onNewResultImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    if (dataSource.isFinished()) {
                        CloseableReference<CloseableImage> result = dataSource.getResult();
                        try {
                            if (result != null) {
                                try {
                                    CloseableImage closeableImage = result.get();
                                    WritableMap writableMapCreateMap = Arguments.createMap();
                                    writableMapCreateMap.putInt("width", closeableImage.getWidth());
                                    writableMapCreateMap.putInt("height", closeableImage.getHeight());
                                    promise.resolve(writableMapCreateMap);
                                } catch (Exception e) {
                                    promise.reject(ImageLoaderModule.ERROR_GET_SIZE_FAILURE, e);
                                }
                                return;
                            }
                            promise.reject(ImageLoaderModule.ERROR_GET_SIZE_FAILURE);
                        } finally {
                            CloseableReference.closeSafely(result);
                        }
                    }
                }

                @Override // com.facebook.datasource.BaseDataSubscriber
                protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    promise.reject(ImageLoaderModule.ERROR_GET_SIZE_FAILURE, dataSource.getFailureCause());
                }
            }, CallerThreadExecutor.getInstance());
        }
    }

    @Override // com.facebook.fbreact.specs.NativeImageLoaderAndroidSpec
    public void prefetchImage(String str, double d, final Promise promise) {
        final int i = (int) d;
        if (str == null || str.isEmpty()) {
            promise.reject(ERROR_INVALID_URI, "Cannot prefetch an image for an empty URI");
            return;
        }
        DataSource<Void> dataSourcePrefetchToDiskCache = getImagePipeline().prefetchToDiskCache(ImageRequestBuilder.newBuilderWithSource(Uri.parse(str)).build(), getCallerContext());
        BaseDataSubscriber<Void> baseDataSubscriber = new BaseDataSubscriber<Void>() { // from class: com.facebook.react.modules.image.ImageLoaderModule.3
            @Override // com.facebook.datasource.BaseDataSubscriber
            protected void onNewResultImpl(DataSource<Void> dataSource) {
                if (dataSource.isFinished()) {
                    try {
                        try {
                            ImageLoaderModule.this.removeRequest(i);
                            promise.resolve(true);
                        } catch (Exception e) {
                            promise.reject(ImageLoaderModule.ERROR_PREFETCH_FAILURE, e);
                        }
                    } finally {
                        dataSource.close();
                    }
                }
            }

            @Override // com.facebook.datasource.BaseDataSubscriber
            protected void onFailureImpl(DataSource<Void> dataSource) {
                try {
                    ImageLoaderModule.this.removeRequest(i);
                    promise.reject(ImageLoaderModule.ERROR_PREFETCH_FAILURE, dataSource.getFailureCause());
                } finally {
                    dataSource.close();
                }
            }
        };
        registerRequest(i, dataSourcePrefetchToDiskCache);
        dataSourcePrefetchToDiskCache.subscribe(baseDataSubscriber, CallerThreadExecutor.getInstance());
    }

    @Override // com.facebook.fbreact.specs.NativeImageLoaderAndroidSpec
    public void abortRequest(double d) {
        DataSource<Void> dataSourceRemoveRequest = removeRequest((int) d);
        if (dataSourceRemoveRequest != null) {
            dataSourceRemoveRequest.close();
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.modules.image.ImageLoaderModule$4] */
    @Override // com.facebook.fbreact.specs.NativeImageLoaderAndroidSpec
    @ReactMethod
    public void queryCache(final ReadableArray readableArray, final Promise promise) {
        new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) { // from class: com.facebook.react.modules.image.ImageLoaderModule.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.react.bridge.GuardedAsyncTask
            public void doInBackgroundGuarded(Void... voidArr) {
                WritableMap writableMapCreateMap = Arguments.createMap();
                ImagePipeline imagePipeline = ImageLoaderModule.this.getImagePipeline();
                for (int i = 0; i < readableArray.size(); i++) {
                    String string = readableArray.getString(i);
                    if (!TextUtils.isEmpty(string)) {
                        Uri uri = Uri.parse(string);
                        if (imagePipeline.isInBitmapMemoryCache(uri)) {
                            writableMapCreateMap.putString(string, "memory");
                        } else if (imagePipeline.isInDiskCacheSync(uri)) {
                            writableMapCreateMap.putString(string, "disk");
                        }
                    }
                }
                promise.resolve(writableMapCreateMap);
            }
        }.executeOnExecutor(GuardedAsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private void registerRequest(int i, DataSource<Void> dataSource) {
        synchronized (this.mEnqueuedRequestMonitor) {
            this.mEnqueuedRequests.put(i, dataSource);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DataSource<Void> removeRequest(int i) {
        DataSource<Void> dataSource;
        synchronized (this.mEnqueuedRequestMonitor) {
            dataSource = this.mEnqueuedRequests.get(i);
            this.mEnqueuedRequests.remove(i);
        }
        return dataSource;
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
        synchronized (this.mEnqueuedRequestMonitor) {
            int size = this.mEnqueuedRequests.size();
            for (int i = 0; i < size; i++) {
                DataSource<Void> dataSourceValueAt = this.mEnqueuedRequests.valueAt(i);
                if (dataSourceValueAt != null) {
                    dataSourceValueAt.close();
                }
            }
            this.mEnqueuedRequests.clear();
        }
    }
}
