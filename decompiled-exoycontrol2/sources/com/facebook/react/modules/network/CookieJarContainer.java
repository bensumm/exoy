package com.facebook.react.modules.network;

import okhttp3.CookieJar;

/* JADX INFO: loaded from: classes.dex */
public interface CookieJarContainer extends CookieJar {
    void removeCookieJar();

    void setCookieJar(CookieJar cookieJar);
}
