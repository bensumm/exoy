package okhttp3;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Cookie;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;

/* JADX INFO: compiled from: JavaNetCookieJar.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u001e\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lokhttp3/JavaNetCookieJar;", "Lokhttp3/CookieJar;", "cookieHandler", "Ljava/net/CookieHandler;", "(Ljava/net/CookieHandler;)V", "decodeHeaderAsJavaNetCookies", "", "Lokhttp3/Cookie;", "url", "Lokhttp3/HttpUrl;", "header", "", "loadForRequest", "saveFromResponse", "", "cookies", "okhttp-urlconnection"}, k = 1, mv = {1, 4, 0})
public final class JavaNetCookieJar implements CookieJar {
    private final CookieHandler cookieHandler;

    public JavaNetCookieJar(CookieHandler cookieHandler) {
        Intrinsics.checkNotNullParameter(cookieHandler, "cookieHandler");
        this.cookieHandler = cookieHandler;
    }

    @Override // okhttp3.CookieJar
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(cookies, "cookies");
        ArrayList arrayList = new ArrayList();
        Iterator<Cookie> it = cookies.iterator();
        while (it.hasNext()) {
            arrayList.add(Internal.cookieToString(it.next(), true));
        }
        try {
            this.cookieHandler.put(url.uri(), MapsKt.mapOf(TuplesKt.to("Set-Cookie", arrayList)));
        } catch (IOException e) {
            Platform platform = Platform.INSTANCE.get();
            StringBuilder sb = new StringBuilder("Saving cookies failed for ");
            HttpUrl httpUrlResolve = url.resolve("/...");
            Intrinsics.checkNotNull(httpUrlResolve);
            sb.append(httpUrlResolve);
            platform.log(sb.toString(), 5, e);
        }
    }

    @Override // okhttp3.CookieJar
    public List<Cookie> loadForRequest(HttpUrl url) {
        Intrinsics.checkNotNullParameter(url, "url");
        try {
            Map<String, List<String>> cookieHeaders = this.cookieHandler.get(url.uri(), MapsKt.emptyMap());
            ArrayList arrayList = null;
            Intrinsics.checkNotNullExpressionValue(cookieHeaders, "cookieHeaders");
            for (Map.Entry<String, List<String>> entry : cookieHeaders.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                if (StringsKt.equals("Cookie", key, true) || StringsKt.equals("Cookie2", key, true)) {
                    Intrinsics.checkNotNullExpressionValue(value, "value");
                    if (!value.isEmpty()) {
                        for (String header : value) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            Intrinsics.checkNotNullExpressionValue(header, "header");
                            arrayList.addAll(decodeHeaderAsJavaNetCookies(url, header));
                        }
                    }
                }
            }
            if (arrayList != null) {
                List<Cookie> listUnmodifiableList = Collections.unmodifiableList(arrayList);
                Intrinsics.checkNotNullExpressionValue(listUnmodifiableList, "Collections.unmodifiableList(cookies)");
                return listUnmodifiableList;
            }
            return CollectionsKt.emptyList();
        } catch (IOException e) {
            Platform platform = Platform.INSTANCE.get();
            StringBuilder sb = new StringBuilder("Loading cookies failed for ");
            HttpUrl httpUrlResolve = url.resolve("/...");
            Intrinsics.checkNotNull(httpUrlResolve);
            sb.append(httpUrlResolve);
            platform.log(sb.toString(), 5, e);
            return CollectionsKt.emptyList();
        }
    }

    private final List<Cookie> decodeHeaderAsJavaNetCookies(HttpUrl url, String header) {
        ArrayList arrayList = new ArrayList();
        int length = header.length();
        int i = 0;
        while (i < length) {
            int iDelimiterOffset = Util.delimiterOffset(header, ";,", i, length);
            int iDelimiterOffset2 = Util.delimiterOffset(header, '=', i, iDelimiterOffset);
            String strTrimSubstring = Util.trimSubstring(header, i, iDelimiterOffset2);
            if (!StringsKt.startsWith$default(strTrimSubstring, "$", false, 2, (Object) null)) {
                String strTrimSubstring2 = iDelimiterOffset2 < iDelimiterOffset ? Util.trimSubstring(header, iDelimiterOffset2 + 1, iDelimiterOffset) : "";
                if (StringsKt.startsWith$default(strTrimSubstring2, "\"", false, 2, (Object) null) && StringsKt.endsWith$default(strTrimSubstring2, "\"", false, 2, (Object) null)) {
                    int length2 = strTrimSubstring2.length() - 1;
                    if (strTrimSubstring2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    strTrimSubstring2 = strTrimSubstring2.substring(1, length2);
                    Intrinsics.checkNotNullExpressionValue(strTrimSubstring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                }
                arrayList.add(new Cookie.Builder().name(strTrimSubstring).value(strTrimSubstring2).domain(url.host()).build());
            }
            i = iDelimiterOffset + 1;
        }
        return arrayList;
    }
}
