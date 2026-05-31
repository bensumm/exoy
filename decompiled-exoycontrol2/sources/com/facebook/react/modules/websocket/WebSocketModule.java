package com.facebook.react.modules.websocket;

import com.facebook.common.logging.FLog;
import com.facebook.common.util.UriUtil;
import com.facebook.fbreact.specs.NativeWebSocketModuleSpec;
import com.facebook.imagepipeline.producers.ProducerContext;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.network.ForwardingCookieHandler;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/* JADX INFO: loaded from: classes.dex */
@ReactModule(hasConstants = false, name = NativeWebSocketModuleSpec.NAME)
public final class WebSocketModule extends NativeWebSocketModuleSpec {
    private final Map<Integer, ContentHandler> mContentHandlers;
    private ForwardingCookieHandler mCookieHandler;
    private final Map<Integer, WebSocket> mWebSocketConnections;

    public interface ContentHandler {
        void onMessage(String str, WritableMap writableMap);

        void onMessage(ByteString byteString, WritableMap writableMap);
    }

    @Override // com.facebook.fbreact.specs.NativeWebSocketModuleSpec
    public void addListener(String str) {
    }

    @Override // com.facebook.fbreact.specs.NativeWebSocketModuleSpec
    public void removeListeners(double d) {
    }

    public WebSocketModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.mWebSocketConnections = new ConcurrentHashMap();
        this.mContentHandlers = new ConcurrentHashMap();
        this.mCookieHandler = new ForwardingCookieHandler(reactApplicationContext);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule, com.facebook.react.turbomodule.core.interfaces.TurboModule
    public void invalidate() {
        Iterator<WebSocket> it = this.mWebSocketConnections.values().iterator();
        while (it.hasNext()) {
            it.next().close(1001, null);
        }
        this.mWebSocketConnections.clear();
        this.mContentHandlers.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent(String str, WritableMap writableMap) {
        ReactApplicationContext reactApplicationContext = getReactApplicationContext();
        if (reactApplicationContext.hasActiveReactInstance()) {
            reactApplicationContext.emitDeviceEvent(str, writableMap);
        }
    }

    public void setContentHandler(int i, ContentHandler contentHandler) {
        if (contentHandler != null) {
            this.mContentHandlers.put(Integer.valueOf(i), contentHandler);
        } else {
            this.mContentHandlers.remove(Integer.valueOf(i));
        }
    }

    @Override // com.facebook.fbreact.specs.NativeWebSocketModuleSpec
    public void connect(String str, ReadableArray readableArray, ReadableMap readableMap, double d) {
        boolean z;
        final int i = (int) d;
        OkHttpClient okHttpClientBuild = new OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS).readTimeout(0L, TimeUnit.MINUTES).build();
        Request.Builder builderUrl = new Request.Builder().tag(Integer.valueOf(i)).url(str);
        String cookie = getCookie(str);
        if (cookie != null) {
            builderUrl.addHeader("Cookie", cookie);
        }
        if (readableMap != null && readableMap.hasKey("headers") && readableMap.getType("headers").equals(ReadableType.Map)) {
            ReadableMap map = readableMap.getMap("headers");
            ReadableMapKeySetIterator readableMapKeySetIteratorKeySetIterator = map.keySetIterator();
            z = false;
            while (readableMapKeySetIteratorKeySetIterator.hasNextKey()) {
                String strNextKey = readableMapKeySetIteratorKeySetIterator.nextKey();
                if (ReadableType.String.equals(map.getType(strNextKey))) {
                    if (strNextKey.equalsIgnoreCase(ProducerContext.ExtraKeys.ORIGIN)) {
                        z = true;
                    }
                    builderUrl.addHeader(strNextKey, map.getString(strNextKey));
                } else {
                    FLog.w(ReactConstants.TAG, "Ignoring: requested " + strNextKey + ", value not a string");
                }
            }
        } else {
            z = false;
        }
        if (!z) {
            builderUrl.addHeader(ProducerContext.ExtraKeys.ORIGIN, getDefaultOrigin(str));
        }
        if (readableArray != null && readableArray.size() > 0) {
            StringBuilder sb = new StringBuilder("");
            for (int i2 = 0; i2 < readableArray.size(); i2++) {
                String strTrim = readableArray.getString(i2).trim();
                if (!strTrim.isEmpty() && !strTrim.contains(",")) {
                    sb.append(strTrim);
                    sb.append(",");
                }
            }
            if (sb.length() > 0) {
                sb.replace(sb.length() - 1, sb.length(), "");
                builderUrl.addHeader("Sec-WebSocket-Protocol", sb.toString());
            }
        }
        okHttpClientBuild.newWebSocket(builderUrl.build(), new WebSocketListener() { // from class: com.facebook.react.modules.websocket.WebSocketModule.1
            @Override // okhttp3.WebSocketListener
            public void onOpen(WebSocket webSocket, Response response) {
                WebSocketModule.this.mWebSocketConnections.put(Integer.valueOf(i), webSocket);
                WritableMap writableMapCreateMap = Arguments.createMap();
                writableMapCreateMap.putInt("id", i);
                writableMapCreateMap.putString("protocol", response.header("Sec-WebSocket-Protocol", ""));
                WebSocketModule.this.sendEvent("websocketOpen", writableMapCreateMap);
            }

            @Override // okhttp3.WebSocketListener
            public void onClosing(WebSocket webSocket, int i3, String str2) {
                webSocket.close(i3, str2);
            }

            @Override // okhttp3.WebSocketListener
            public void onClosed(WebSocket webSocket, int i3, String str2) {
                WritableMap writableMapCreateMap = Arguments.createMap();
                writableMapCreateMap.putInt("id", i);
                writableMapCreateMap.putInt("code", i3);
                writableMapCreateMap.putString("reason", str2);
                WebSocketModule.this.sendEvent("websocketClosed", writableMapCreateMap);
            }

            @Override // okhttp3.WebSocketListener
            public void onFailure(WebSocket webSocket, Throwable th, Response response) {
                WebSocketModule.this.notifyWebSocketFailed(i, th.getMessage());
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, String str2) {
                WritableMap writableMapCreateMap = Arguments.createMap();
                writableMapCreateMap.putInt("id", i);
                writableMapCreateMap.putString("type", "text");
                ContentHandler contentHandler = (ContentHandler) WebSocketModule.this.mContentHandlers.get(Integer.valueOf(i));
                if (contentHandler != null) {
                    contentHandler.onMessage(str2, writableMapCreateMap);
                } else {
                    writableMapCreateMap.putString(UriUtil.DATA_SCHEME, str2);
                }
                WebSocketModule.this.sendEvent("websocketMessage", writableMapCreateMap);
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, ByteString byteString) {
                WritableMap writableMapCreateMap = Arguments.createMap();
                writableMapCreateMap.putInt("id", i);
                writableMapCreateMap.putString("type", "binary");
                ContentHandler contentHandler = (ContentHandler) WebSocketModule.this.mContentHandlers.get(Integer.valueOf(i));
                if (contentHandler != null) {
                    contentHandler.onMessage(byteString, writableMapCreateMap);
                } else {
                    writableMapCreateMap.putString(UriUtil.DATA_SCHEME, byteString.base64());
                }
                WebSocketModule.this.sendEvent("websocketMessage", writableMapCreateMap);
            }
        });
        okHttpClientBuild.dispatcher().executorService().shutdown();
    }

    @Override // com.facebook.fbreact.specs.NativeWebSocketModuleSpec
    public void close(double d, String str, double d2) {
        int i = (int) d2;
        WebSocket webSocket = this.mWebSocketConnections.get(Integer.valueOf(i));
        if (webSocket == null) {
            return;
        }
        try {
            webSocket.close((int) d, str);
            this.mWebSocketConnections.remove(Integer.valueOf(i));
            this.mContentHandlers.remove(Integer.valueOf(i));
        } catch (Exception e) {
            FLog.e(ReactConstants.TAG, "Could not close WebSocket connection for id " + i, e);
        }
    }

    @Override // com.facebook.fbreact.specs.NativeWebSocketModuleSpec
    public void send(String str, double d) {
        int i = (int) d;
        WebSocket webSocket = this.mWebSocketConnections.get(Integer.valueOf(i));
        if (webSocket == null) {
            WritableMap writableMapCreateMap = Arguments.createMap();
            writableMapCreateMap.putInt("id", i);
            writableMapCreateMap.putString("message", "client is null");
            sendEvent("websocketFailed", writableMapCreateMap);
            WritableMap writableMapCreateMap2 = Arguments.createMap();
            writableMapCreateMap2.putInt("id", i);
            writableMapCreateMap2.putInt("code", 0);
            writableMapCreateMap2.putString("reason", "client is null");
            sendEvent("websocketClosed", writableMapCreateMap2);
            this.mWebSocketConnections.remove(Integer.valueOf(i));
            this.mContentHandlers.remove(Integer.valueOf(i));
            return;
        }
        try {
            webSocket.send(str);
        } catch (Exception e) {
            notifyWebSocketFailed(i, e.getMessage());
        }
    }

    @Override // com.facebook.fbreact.specs.NativeWebSocketModuleSpec
    public void sendBinary(String str, double d) {
        int i = (int) d;
        WebSocket webSocket = this.mWebSocketConnections.get(Integer.valueOf(i));
        if (webSocket == null) {
            WritableMap writableMapCreateMap = Arguments.createMap();
            writableMapCreateMap.putInt("id", i);
            writableMapCreateMap.putString("message", "client is null");
            sendEvent("websocketFailed", writableMapCreateMap);
            WritableMap writableMapCreateMap2 = Arguments.createMap();
            writableMapCreateMap2.putInt("id", i);
            writableMapCreateMap2.putInt("code", 0);
            writableMapCreateMap2.putString("reason", "client is null");
            sendEvent("websocketClosed", writableMapCreateMap2);
            this.mWebSocketConnections.remove(Integer.valueOf(i));
            this.mContentHandlers.remove(Integer.valueOf(i));
            return;
        }
        try {
            webSocket.send(ByteString.decodeBase64(str));
        } catch (Exception e) {
            notifyWebSocketFailed(i, e.getMessage());
        }
    }

    public void sendBinary(ByteString byteString, int i) {
        WebSocket webSocket = this.mWebSocketConnections.get(Integer.valueOf(i));
        if (webSocket == null) {
            WritableMap writableMapCreateMap = Arguments.createMap();
            writableMapCreateMap.putInt("id", i);
            writableMapCreateMap.putString("message", "client is null");
            sendEvent("websocketFailed", writableMapCreateMap);
            WritableMap writableMapCreateMap2 = Arguments.createMap();
            writableMapCreateMap2.putInt("id", i);
            writableMapCreateMap2.putInt("code", 0);
            writableMapCreateMap2.putString("reason", "client is null");
            sendEvent("websocketClosed", writableMapCreateMap2);
            this.mWebSocketConnections.remove(Integer.valueOf(i));
            this.mContentHandlers.remove(Integer.valueOf(i));
            return;
        }
        try {
            webSocket.send(byteString);
        } catch (Exception e) {
            notifyWebSocketFailed(i, e.getMessage());
        }
    }

    @Override // com.facebook.fbreact.specs.NativeWebSocketModuleSpec
    public void ping(double d) {
        int i = (int) d;
        WebSocket webSocket = this.mWebSocketConnections.get(Integer.valueOf(i));
        if (webSocket == null) {
            WritableMap writableMapCreateMap = Arguments.createMap();
            writableMapCreateMap.putInt("id", i);
            writableMapCreateMap.putString("message", "client is null");
            sendEvent("websocketFailed", writableMapCreateMap);
            WritableMap writableMapCreateMap2 = Arguments.createMap();
            writableMapCreateMap2.putInt("id", i);
            writableMapCreateMap2.putInt("code", 0);
            writableMapCreateMap2.putString("reason", "client is null");
            sendEvent("websocketClosed", writableMapCreateMap2);
            this.mWebSocketConnections.remove(Integer.valueOf(i));
            this.mContentHandlers.remove(Integer.valueOf(i));
            return;
        }
        try {
            webSocket.send(ByteString.EMPTY);
        } catch (Exception e) {
            notifyWebSocketFailed(i, e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyWebSocketFailed(int i, String str) {
        WritableMap writableMapCreateMap = Arguments.createMap();
        writableMapCreateMap.putInt("id", i);
        writableMapCreateMap.putString("message", str);
        sendEvent("websocketFailed", writableMapCreateMap);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getDefaultOrigin(java.lang.String r11) {
        /*
            java.net.URI r0 = new java.net.URI     // Catch: java.net.URISyntaxException -> La0
            r0.<init>(r11)     // Catch: java.net.URISyntaxException -> La0
            java.lang.String r1 = r0.getScheme()     // Catch: java.net.URISyntaxException -> La0
            int r2 = r1.hashCode()     // Catch: java.net.URISyntaxException -> La0
            r3 = 3804(0xedc, float:5.33E-42)
            java.lang.String r4 = "https"
            java.lang.String r5 = "http"
            r6 = -1
            r7 = 0
            r8 = 3
            r9 = 2
            r10 = 1
            if (r2 == r3) goto L45
            r3 = 118039(0x1cd17, float:1.65408E-40)
            if (r2 == r3) goto L3a
            r3 = 3213448(0x310888, float:4.503E-39)
            if (r2 == r3) goto L32
            r3 = 99617003(0x5f008eb, float:2.2572767E-35)
            if (r2 == r3) goto L2a
            goto L50
        L2a:
            boolean r1 = r1.equals(r4)     // Catch: java.net.URISyntaxException -> La0
            if (r1 == 0) goto L50
            r1 = r8
            goto L51
        L32:
            boolean r1 = r1.equals(r5)     // Catch: java.net.URISyntaxException -> La0
            if (r1 == 0) goto L50
            r1 = r9
            goto L51
        L3a:
            java.lang.String r2 = "wss"
            boolean r1 = r1.equals(r2)     // Catch: java.net.URISyntaxException -> La0
            if (r1 == 0) goto L50
            r1 = r7
            goto L51
        L45:
            java.lang.String r2 = "ws"
            boolean r1 = r1.equals(r2)     // Catch: java.net.URISyntaxException -> La0
            if (r1 == 0) goto L50
            r1 = r10
            goto L51
        L50:
            r1 = r6
        L51:
            if (r1 == 0) goto L6e
            if (r1 == r10) goto L6d
            java.lang.String r4 = ""
            if (r1 == r9) goto L5c
            if (r1 == r8) goto L5c
            goto L6e
        L5c:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.net.URISyntaxException -> La0
            r1.<init>(r4)     // Catch: java.net.URISyntaxException -> La0
            java.lang.String r2 = r0.getScheme()     // Catch: java.net.URISyntaxException -> La0
            r1.append(r2)     // Catch: java.net.URISyntaxException -> La0
            java.lang.String r4 = r1.toString()     // Catch: java.net.URISyntaxException -> La0
            goto L6e
        L6d:
            r4 = r5
        L6e:
            int r1 = r0.getPort()     // Catch: java.net.URISyntaxException -> La0
            if (r1 == r6) goto L8f
            java.lang.String r1 = "%s://%s:%s"
            java.lang.Object[] r2 = new java.lang.Object[r8]     // Catch: java.net.URISyntaxException -> La0
            r2[r7] = r4     // Catch: java.net.URISyntaxException -> La0
            java.lang.String r3 = r0.getHost()     // Catch: java.net.URISyntaxException -> La0
            r2[r10] = r3     // Catch: java.net.URISyntaxException -> La0
            int r0 = r0.getPort()     // Catch: java.net.URISyntaxException -> La0
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: java.net.URISyntaxException -> La0
            r2[r9] = r0     // Catch: java.net.URISyntaxException -> La0
            java.lang.String r11 = java.lang.String.format(r1, r2)     // Catch: java.net.URISyntaxException -> La0
            goto L9f
        L8f:
            java.lang.String r1 = "%s://%s"
            java.lang.Object[] r2 = new java.lang.Object[r9]     // Catch: java.net.URISyntaxException -> La0
            r2[r7] = r4     // Catch: java.net.URISyntaxException -> La0
            java.lang.String r0 = r0.getHost()     // Catch: java.net.URISyntaxException -> La0
            r2[r10] = r0     // Catch: java.net.URISyntaxException -> La0
            java.lang.String r11 = java.lang.String.format(r1, r2)     // Catch: java.net.URISyntaxException -> La0
        L9f:
            return r11
        La0:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Unable to set "
            r1.<init>(r2)
            r1.append(r11)
            java.lang.String r11 = " as default origin header"
            r1.append(r11)
            java.lang.String r11 = r1.toString()
            r0.<init>(r11)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.websocket.WebSocketModule.getDefaultOrigin(java.lang.String):java.lang.String");
    }

    private String getCookie(String str) {
        try {
            List<String> list = this.mCookieHandler.get(new URI(getDefaultOrigin(str)), new HashMap()).get("Cookie");
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
            return null;
        } catch (IOException | URISyntaxException unused) {
            throw new IllegalArgumentException("Unable to get cookie from " + str);
        }
    }
}
