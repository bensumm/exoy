package com.facebook.react.modules.camera;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64OutputStream;
import com.facebook.fbreact.specs.NativeImageStoreAndroidSpec;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.module.annotations.ReactModule;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(name = NativeImageStoreAndroidSpec.NAME)
public class ImageStoreManager extends NativeImageStoreAndroidSpec {
    private static final int BUFFER_SIZE = 8192;

    public ImageStoreManager(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    @Override // com.facebook.fbreact.specs.NativeImageStoreAndroidSpec
    public void getBase64ForTag(String str, Callback callback, Callback callback2) {
        new GetBase64Task(getReactApplicationContext(), str, callback, callback2).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private class GetBase64Task extends GuardedAsyncTask<Void, Void> {
        private final Callback mError;
        private final Callback mSuccess;
        private final String mUri;

        private GetBase64Task(ReactContext reactContext, String str, Callback callback, Callback callback2) {
            super(reactContext);
            this.mUri = str;
            this.mSuccess = callback;
            this.mError = callback2;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.facebook.react.bridge.GuardedAsyncTask
        public void doInBackgroundGuarded(Void... voidArr) {
            try {
                InputStream inputStreamOpenInputStream = ImageStoreManager.this.getReactApplicationContext().getContentResolver().openInputStream(Uri.parse(this.mUri));
                try {
                    try {
                        this.mSuccess.invoke(ImageStoreManager.this.convertInputStreamToBase64OutputStream(inputStreamOpenInputStream));
                    } catch (IOException e) {
                        this.mError.invoke(e.getMessage());
                    }
                } finally {
                    ImageStoreManager.closeQuietly(inputStreamOpenInputStream);
                }
            } catch (FileNotFoundException e2) {
                this.mError.invoke(e2.getMessage());
            }
        }
    }

    String convertInputStreamToBase64OutputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Base64OutputStream base64OutputStream = new Base64OutputStream(byteArrayOutputStream, 2);
        byte[] bArr = new byte[8192];
        while (true) {
            try {
                int i = inputStream.read(bArr);
                if (i > -1) {
                    base64OutputStream.write(bArr, 0, i);
                } else {
                    closeQuietly(base64OutputStream);
                    return byteArrayOutputStream.toString();
                }
            } catch (Throwable th) {
                closeQuietly(base64OutputStream);
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException unused) {
        }
    }
}
