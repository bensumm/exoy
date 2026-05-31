package com.facebook.react.modules.network;

import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import com.facebook.common.logging.FLog;
import com.facebook.fbreact.specs.NativeNetworkingAndroidSpec;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.StandardCharsets;
import com.facebook.react.common.network.OkHttpCallUtil;
import com.facebook.react.module.annotations.ReactModule;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.ByteString;
import okio.GzipSource;
import okio.Okio;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(name = "Networking")
public final class NetworkingModule extends NativeNetworkingAndroidSpec {
    private static final int CHUNK_TIMEOUT_NS = 100000000;
    private static final String CONTENT_ENCODING_HEADER_NAME = "content-encoding";
    private static final String CONTENT_TYPE_HEADER_NAME = "content-type";
    private static final int MAX_CHUNK_SIZE_BETWEEN_FLUSHES = 8192;
    private static final String REQUEST_BODY_KEY_BASE64 = "base64";
    private static final String REQUEST_BODY_KEY_FORMDATA = "formData";
    private static final String REQUEST_BODY_KEY_STRING = "string";
    private static final String REQUEST_BODY_KEY_URI = "uri";
    private static final String TAG = "Networking";
    private static final String USER_AGENT_HEADER_NAME = "user-agent";
    private static CustomClientBuilder customClientBuilder;
    private final OkHttpClient mClient;
    private final ForwardingCookieHandler mCookieHandler;
    private final CookieJarContainer mCookieJarContainer;
    private final String mDefaultUserAgent;
    private final List<RequestBodyHandler> mRequestBodyHandlers;
    private final Set<Integer> mRequestIds;
    private final List<ResponseHandler> mResponseHandlers;
    private boolean mShuttingDown;
    private final List<UriHandler> mUriHandlers;

    public interface CustomClientBuilder {
        void apply(OkHttpClient.Builder builder);
    }

    public interface RequestBodyHandler {
        boolean supports(ReadableMap readableMap);

        RequestBody toRequestBody(ReadableMap readableMap, String str);
    }

    public interface ResponseHandler {
        boolean supports(String str);

        WritableMap toResponseData(ResponseBody responseBody) throws IOException;
    }

    public interface UriHandler {
        WritableMap fetch(Uri uri) throws IOException;

        boolean supports(Uri uri, String str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean shouldDispatch(long j, long j2) {
        return j2 + 100000000 < j;
    }

    @Override // com.facebook.fbreact.specs.NativeNetworkingAndroidSpec
    public void addListener(String str) {
    }

    @Override // com.facebook.fbreact.specs.NativeNetworkingAndroidSpec
    public void removeListeners(double d) {
    }

    public NetworkingModule(ReactApplicationContext reactApplicationContext, String str, OkHttpClient okHttpClient, List<NetworkInterceptorCreator> list) {
        super(reactApplicationContext);
        this.mRequestBodyHandlers = new ArrayList();
        this.mUriHandlers = new ArrayList();
        this.mResponseHandlers = new ArrayList();
        if (list != null) {
            OkHttpClient.Builder builderNewBuilder = okHttpClient.newBuilder();
            Iterator<NetworkInterceptorCreator> it = list.iterator();
            while (it.hasNext()) {
                builderNewBuilder.addNetworkInterceptor(it.next().create());
            }
            okHttpClient = builderNewBuilder.build();
        }
        this.mClient = okHttpClient;
        this.mCookieHandler = new ForwardingCookieHandler(reactApplicationContext);
        this.mCookieJarContainer = (CookieJarContainer) okHttpClient.cookieJar();
        this.mShuttingDown = false;
        this.mDefaultUserAgent = str;
        this.mRequestIds = new HashSet();
    }

    NetworkingModule(ReactApplicationContext reactApplicationContext, String str, OkHttpClient okHttpClient) {
        this(reactApplicationContext, str, okHttpClient, null);
    }

    public NetworkingModule(ReactApplicationContext reactApplicationContext) {
        this(reactApplicationContext, null, OkHttpClientProvider.createClient(reactApplicationContext), null);
    }

    public NetworkingModule(ReactApplicationContext reactApplicationContext, List<NetworkInterceptorCreator> list) {
        this(reactApplicationContext, null, OkHttpClientProvider.createClient(reactApplicationContext), list);
    }

    public NetworkingModule(ReactApplicationContext reactApplicationContext, String str) {
        this(reactApplicationContext, str, OkHttpClientProvider.createClient(reactApplicationContext), null);
    }

    public static void setCustomClientBuilder(CustomClientBuilder customClientBuilder2) {
        customClientBuilder = customClientBuilder2;
    }

    private static void applyCustomBuilder(OkHttpClient.Builder builder) {
        CustomClientBuilder customClientBuilder2 = customClientBuilder;
        if (customClientBuilder2 != null) {
            customClientBuilder2.apply(builder);
        }
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule, com.facebook.react.turbomodule.core.interfaces.TurboModule
    public void initialize() {
        this.mCookieJarContainer.setCookieJar(new JavaNetCookieJar(this.mCookieHandler));
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule, com.facebook.react.turbomodule.core.interfaces.TurboModule
    public void invalidate() {
        this.mShuttingDown = true;
        cancelAllRequests();
        this.mCookieHandler.destroy();
        this.mCookieJarContainer.removeCookieJar();
        this.mRequestBodyHandlers.clear();
        this.mResponseHandlers.clear();
        this.mUriHandlers.clear();
    }

    public void addUriHandler(UriHandler uriHandler) {
        this.mUriHandlers.add(uriHandler);
    }

    public void addRequestBodyHandler(RequestBodyHandler requestBodyHandler) {
        this.mRequestBodyHandlers.add(requestBodyHandler);
    }

    public void addResponseHandler(ResponseHandler responseHandler) {
        this.mResponseHandlers.add(responseHandler);
    }

    public void removeUriHandler(UriHandler uriHandler) {
        this.mUriHandlers.remove(uriHandler);
    }

    public void removeRequestBodyHandler(RequestBodyHandler requestBodyHandler) {
        this.mRequestBodyHandlers.remove(requestBodyHandler);
    }

    public void removeResponseHandler(ResponseHandler responseHandler) {
        this.mResponseHandlers.remove(responseHandler);
    }

    @Override // com.facebook.fbreact.specs.NativeNetworkingAndroidSpec
    public void sendRequest(String str, String str2, double d, ReadableArray readableArray, ReadableMap readableMap, String str3, boolean z, double d2, boolean z2) {
        int i = (int) d;
        try {
            sendRequestInternal(str, str2, i, readableArray, readableMap, str3, z, (int) d2, z2);
        } catch (Throwable th) {
            FLog.e("Networking", "Failed to send url request: " + str2, th);
            ResponseUtil.onRequestError(getReactApplicationContextIfActiveOrWarn(), i, th.getMessage(), th);
        }
    }

    public void sendRequestInternal(String str, String str2, final int i, ReadableArray readableArray, ReadableMap readableMap, final String str3, final boolean z, int i2, boolean z2) {
        RequestBodyHandler next;
        RequestBody emptyBody;
        Charset charset;
        final ReactApplicationContext reactApplicationContextIfActiveOrWarn = getReactApplicationContextIfActiveOrWarn();
        try {
            Uri uri = Uri.parse(str2);
            for (UriHandler uriHandler : this.mUriHandlers) {
                if (uriHandler.supports(uri, str3)) {
                    ResponseUtil.onDataReceived(reactApplicationContextIfActiveOrWarn, i, uriHandler.fetch(uri));
                    ResponseUtil.onRequestSuccess(reactApplicationContextIfActiveOrWarn, i);
                    return;
                }
            }
            try {
                Request.Builder builderUrl = new Request.Builder().url(str2);
                if (i != 0) {
                    builderUrl.tag(Integer.valueOf(i));
                }
                OkHttpClient.Builder builderNewBuilder = this.mClient.newBuilder();
                applyCustomBuilder(builderNewBuilder);
                if (!z2) {
                    builderNewBuilder.cookieJar(CookieJar.NO_COOKIES);
                }
                if (z) {
                    builderNewBuilder.addNetworkInterceptor(new Interceptor() { // from class: com.facebook.react.modules.network.NetworkingModule.1
                        @Override // okhttp3.Interceptor
                        public Response intercept(Interceptor.Chain chain) throws IOException {
                            Response responseProceed = chain.proceed(chain.request());
                            return responseProceed.newBuilder().body(new ProgressResponseBody(responseProceed.body(), new ProgressListener() { // from class: com.facebook.react.modules.network.NetworkingModule.1.1
                                long last = System.nanoTime();

                                @Override // com.facebook.react.modules.network.ProgressListener
                                public void onProgress(long j, long j2, boolean z3) {
                                    long jNanoTime = System.nanoTime();
                                    if ((z3 || NetworkingModule.shouldDispatch(jNanoTime, this.last)) && !str3.equals("text")) {
                                        ResponseUtil.onDataReceivedProgress(reactApplicationContextIfActiveOrWarn, i, j, j2);
                                        this.last = jNanoTime;
                                    }
                                }
                            })).build();
                        }
                    });
                }
                if (i2 != this.mClient.connectTimeoutMillis()) {
                    builderNewBuilder.connectTimeout(i2, TimeUnit.MILLISECONDS);
                }
                OkHttpClient okHttpClientBuild = builderNewBuilder.build();
                Headers headersExtractHeaders = extractHeaders(readableArray, readableMap);
                if (headersExtractHeaders == null) {
                    ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, "Unrecognized headers format", null);
                    return;
                }
                String str4 = headersExtractHeaders.get(CONTENT_TYPE_HEADER_NAME);
                String str5 = headersExtractHeaders.get(CONTENT_ENCODING_HEADER_NAME);
                builderUrl.headers(headersExtractHeaders);
                if (readableMap != null) {
                    Iterator<RequestBodyHandler> it = this.mRequestBodyHandlers.iterator();
                    while (it.hasNext()) {
                        next = it.next();
                        if (next.supports(readableMap)) {
                            break;
                        }
                    }
                    next = null;
                } else {
                    next = null;
                }
                if (readableMap == null || str.toLowerCase(Locale.ROOT).equals("get") || str.toLowerCase(Locale.ROOT).equals("head")) {
                    emptyBody = RequestBodyUtil.getEmptyBody(str);
                } else if (next != null) {
                    emptyBody = next.toRequestBody(readableMap, str4);
                } else if (readableMap.hasKey(REQUEST_BODY_KEY_STRING)) {
                    if (str4 == null) {
                        ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, "Payload is set but no content-type header specified", null);
                        return;
                    }
                    String string = readableMap.getString(REQUEST_BODY_KEY_STRING);
                    MediaType mediaType = MediaType.parse(str4);
                    if (RequestBodyUtil.isGzipEncoding(str5)) {
                        emptyBody = RequestBodyUtil.createGzip(mediaType, string);
                        if (emptyBody == null) {
                            ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, "Failed to gzip request body", null);
                            return;
                        }
                    } else {
                        if (mediaType == null) {
                            charset = StandardCharsets.UTF_8;
                        } else {
                            charset = mediaType.charset(StandardCharsets.UTF_8);
                        }
                        emptyBody = RequestBody.create(mediaType, string.getBytes(charset));
                    }
                } else if (readableMap.hasKey(REQUEST_BODY_KEY_BASE64)) {
                    if (str4 == null) {
                        ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, "Payload is set but no content-type header specified", null);
                        return;
                    }
                    emptyBody = RequestBody.create(MediaType.parse(str4), ByteString.decodeBase64(readableMap.getString(REQUEST_BODY_KEY_BASE64)));
                } else if (readableMap.hasKey(REQUEST_BODY_KEY_URI)) {
                    if (str4 == null) {
                        ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, "Payload is set but no content-type header specified", null);
                        return;
                    }
                    String string2 = readableMap.getString(REQUEST_BODY_KEY_URI);
                    InputStream fileInputStream = RequestBodyUtil.getFileInputStream(getReactApplicationContext(), string2);
                    if (fileInputStream == null) {
                        ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, "Could not retrieve file for uri " + string2, null);
                        return;
                    }
                    emptyBody = RequestBodyUtil.create(MediaType.parse(str4), fileInputStream);
                } else if (readableMap.hasKey(REQUEST_BODY_KEY_FORMDATA)) {
                    if (str4 == null) {
                        str4 = "multipart/form-data";
                    }
                    MultipartBody.Builder builderConstructMultipartBody = constructMultipartBody(readableMap.getArray(REQUEST_BODY_KEY_FORMDATA), str4, i);
                    if (builderConstructMultipartBody == null) {
                        return;
                    } else {
                        emptyBody = builderConstructMultipartBody.build();
                    }
                } else {
                    emptyBody = RequestBodyUtil.getEmptyBody(str);
                }
                builderUrl.method(str, wrapRequestBodyWithProgressEmitter(emptyBody, i));
                addRequest(i);
                okHttpClientBuild.newCall(builderUrl.build()).enqueue(new Callback() { // from class: com.facebook.react.modules.network.NetworkingModule.2
                    @Override // okhttp3.Callback
                    public void onFailure(Call call, IOException iOException) {
                        String message;
                        if (NetworkingModule.this.mShuttingDown) {
                            return;
                        }
                        NetworkingModule.this.removeRequest(i);
                        if (iOException.getMessage() != null) {
                            message = iOException.getMessage();
                        } else {
                            message = "Error while executing request: " + iOException.getClass().getSimpleName();
                        }
                        ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, message, iOException);
                    }

                    @Override // okhttp3.Callback
                    public void onResponse(Call call, Response response) throws IOException {
                        if (NetworkingModule.this.mShuttingDown) {
                            return;
                        }
                        NetworkingModule.this.removeRequest(i);
                        ResponseUtil.onResponseReceived(reactApplicationContextIfActiveOrWarn, i, response.code(), NetworkingModule.translateHeaders(response.headers()), response.request().url().getUrl());
                        try {
                            ResponseBody responseBodyBody = response.body();
                            if ("gzip".equalsIgnoreCase(response.header("Content-Encoding")) && responseBodyBody != null) {
                                GzipSource gzipSource = new GzipSource(responseBodyBody.getBodySource());
                                String strHeader = response.header("Content-Type");
                                responseBodyBody = ResponseBody.create(strHeader != null ? MediaType.parse(strHeader) : null, -1L, Okio.buffer(gzipSource));
                            }
                            for (ResponseHandler responseHandler : NetworkingModule.this.mResponseHandlers) {
                                if (responseHandler.supports(str3)) {
                                    ResponseUtil.onDataReceived(reactApplicationContextIfActiveOrWarn, i, responseHandler.toResponseData(responseBodyBody));
                                    ResponseUtil.onRequestSuccess(reactApplicationContextIfActiveOrWarn, i);
                                    return;
                                }
                            }
                            if (z && str3.equals("text")) {
                                NetworkingModule.this.readWithProgress(i, responseBodyBody);
                                ResponseUtil.onRequestSuccess(reactApplicationContextIfActiveOrWarn, i);
                                return;
                            }
                            String strString = "";
                            if (str3.equals("text")) {
                                try {
                                    strString = responseBodyBody.string();
                                } catch (IOException e) {
                                    if (!response.request().method().equalsIgnoreCase("HEAD")) {
                                        ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, e.getMessage(), e);
                                    }
                                }
                            } else if (str3.equals(NetworkingModule.REQUEST_BODY_KEY_BASE64)) {
                                strString = Base64.encodeToString(responseBodyBody.bytes(), 2);
                            }
                            ResponseUtil.onDataReceived(reactApplicationContextIfActiveOrWarn, i, strString);
                            ResponseUtil.onRequestSuccess(reactApplicationContextIfActiveOrWarn, i);
                        } catch (IOException e2) {
                            ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, e2.getMessage(), e2);
                        }
                    }
                });
            } catch (Exception e) {
                ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, e.getMessage(), null);
            }
        } catch (IOException e2) {
            ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, e2.getMessage(), e2);
        }
    }

    private RequestBody wrapRequestBodyWithProgressEmitter(RequestBody requestBody, final int i) {
        if (requestBody == null) {
            return null;
        }
        final ReactApplicationContext reactApplicationContextIfActiveOrWarn = getReactApplicationContextIfActiveOrWarn();
        return RequestBodyUtil.createProgressRequest(requestBody, new ProgressListener() { // from class: com.facebook.react.modules.network.NetworkingModule.3
            long last = System.nanoTime();

            @Override // com.facebook.react.modules.network.ProgressListener
            public void onProgress(long j, long j2, boolean z) {
                long jNanoTime = System.nanoTime();
                if (z || NetworkingModule.shouldDispatch(jNanoTime, this.last)) {
                    ResponseUtil.onDataSend(reactApplicationContextIfActiveOrWarn, i, j, j2);
                    this.last = jNanoTime;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readWithProgress(int i, ResponseBody responseBody) throws IOException {
        long j;
        Charset charset;
        long contentLength = -1;
        try {
            ProgressResponseBody progressResponseBody = (ProgressResponseBody) responseBody;
            j = progressResponseBody.totalBytesRead();
            try {
                contentLength = progressResponseBody.getContentLength();
            } catch (ClassCastException unused) {
            }
        } catch (ClassCastException unused2) {
            j = -1;
        }
        if (responseBody.get$contentType() == null) {
            charset = StandardCharsets.UTF_8;
        } else {
            charset = responseBody.get$contentType().charset(StandardCharsets.UTF_8);
        }
        ProgressiveStringDecoder progressiveStringDecoder = new ProgressiveStringDecoder(charset);
        InputStream inputStreamByteStream = responseBody.byteStream();
        try {
            byte[] bArr = new byte[8192];
            ReactApplicationContext reactApplicationContextIfActiveOrWarn = getReactApplicationContextIfActiveOrWarn();
            while (true) {
                int i2 = inputStreamByteStream.read(bArr);
                if (i2 == -1) {
                    return;
                } else {
                    ResponseUtil.onIncrementalDataReceived(reactApplicationContextIfActiveOrWarn, i, progressiveStringDecoder.decodeNext(bArr, i2), j, contentLength);
                }
            }
        } finally {
            inputStreamByteStream.close();
        }
    }

    private synchronized void addRequest(int i) {
        this.mRequestIds.add(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void removeRequest(int i) {
        this.mRequestIds.remove(Integer.valueOf(i));
    }

    private synchronized void cancelAllRequests() {
        Iterator<Integer> it = this.mRequestIds.iterator();
        while (it.hasNext()) {
            cancelRequest(it.next().intValue());
        }
        this.mRequestIds.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static WritableMap translateHeaders(Headers headers) {
        Bundle bundle = new Bundle();
        for (int i = 0; i < headers.size(); i++) {
            String strName = headers.name(i);
            if (bundle.containsKey(strName)) {
                bundle.putString(strName, bundle.getString(strName) + ", " + headers.value(i));
            } else {
                bundle.putString(strName, headers.value(i));
            }
        }
        return Arguments.fromBundle(bundle);
    }

    @Override // com.facebook.fbreact.specs.NativeNetworkingAndroidSpec
    public void abortRequest(double d) {
        int i = (int) d;
        cancelRequest(i);
        removeRequest(i);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.modules.network.NetworkingModule$4] */
    private void cancelRequest(final int i) {
        new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) { // from class: com.facebook.react.modules.network.NetworkingModule.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.react.bridge.GuardedAsyncTask
            public void doInBackgroundGuarded(Void... voidArr) {
                OkHttpCallUtil.cancelTag(NetworkingModule.this.mClient, Integer.valueOf(i));
            }
        }.execute(new Void[0]);
    }

    @Override // com.facebook.fbreact.specs.NativeNetworkingAndroidSpec
    @ReactMethod
    public void clearCookies(com.facebook.react.bridge.Callback callback) {
        this.mCookieHandler.clearCookies(callback);
    }

    private MultipartBody.Builder constructMultipartBody(ReadableArray readableArray, String str, int i) {
        MediaType mediaType;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MediaType.parse(str));
        ReactApplicationContext reactApplicationContextIfActiveOrWarn = getReactApplicationContextIfActiveOrWarn();
        int size = readableArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            ReadableMap map = readableArray.getMap(i2);
            Headers headersExtractHeaders = extractHeaders(map.getArray("headers"), null);
            if (headersExtractHeaders == null) {
                ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, "Missing or invalid header format for FormData part.", null);
                return null;
            }
            String str2 = headersExtractHeaders.get(CONTENT_TYPE_HEADER_NAME);
            if (str2 != null) {
                mediaType = MediaType.parse(str2);
                headersExtractHeaders = headersExtractHeaders.newBuilder().removeAll(CONTENT_TYPE_HEADER_NAME).build();
            } else {
                mediaType = null;
            }
            if (map.hasKey(REQUEST_BODY_KEY_STRING)) {
                builder.addPart(headersExtractHeaders, RequestBody.create(mediaType, map.getString(REQUEST_BODY_KEY_STRING)));
            } else if (map.hasKey(REQUEST_BODY_KEY_URI)) {
                if (mediaType == null) {
                    ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, "Binary FormData part needs a content-type header.", null);
                    return null;
                }
                String string = map.getString(REQUEST_BODY_KEY_URI);
                InputStream fileInputStream = RequestBodyUtil.getFileInputStream(getReactApplicationContext(), string);
                if (fileInputStream == null) {
                    ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, "Could not retrieve file for uri " + string, null);
                    return null;
                }
                builder.addPart(headersExtractHeaders, RequestBodyUtil.create(mediaType, fileInputStream));
            } else {
                ResponseUtil.onRequestError(reactApplicationContextIfActiveOrWarn, i, "Unrecognized FormData part.", null);
            }
        }
        return builder;
    }

    private Headers extractHeaders(ReadableArray readableArray, ReadableMap readableMap) {
        String str;
        if (readableArray == null) {
            return null;
        }
        Headers.Builder builder = new Headers.Builder();
        int size = readableArray.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            ReadableArray array = readableArray.getArray(i);
            if (array != null && array.size() == 2) {
                String strStripHeaderName = HeaderUtil.stripHeaderName(array.getString(0));
                String strStripHeaderValue = HeaderUtil.stripHeaderValue(array.getString(1));
                if (strStripHeaderName != null && strStripHeaderValue != null) {
                    builder.add(strStripHeaderName, strStripHeaderValue);
                }
            }
            return null;
        }
        if (builder.get(USER_AGENT_HEADER_NAME) == null && (str = this.mDefaultUserAgent) != null) {
            builder.add(USER_AGENT_HEADER_NAME, str);
        }
        if (readableMap != null && readableMap.hasKey(REQUEST_BODY_KEY_STRING)) {
            z = true;
        }
        if (!z) {
            builder.removeAll(CONTENT_ENCODING_HEADER_NAME);
        }
        return builder.build();
    }
}
