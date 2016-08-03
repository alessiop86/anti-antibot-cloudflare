package com.github.alessiop86.antiantibotcloudflare.http.adapters.okhttp;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CookieJarImpl implements CookieJar {

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    //TODO handle cookie overwriting (now cookie is just duplicated)
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        String host = getHost(url);
        List<Cookie> cookiesForUrl = cookieStore.get(host);
        if (cookiesForUrl != null) {
            cookiesForUrl = new ArrayList<Cookie>(cookiesForUrl); //unmodifiable
            cookiesForUrl.addAll(cookies);
            cookieStore.put(host, cookiesForUrl);
        }
        else {
            cookieStore.put(host, cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String host = getHost(url);
        List<Cookie> cookies = cookieStore.get(host);
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    private String getHost(HttpUrl url) {
        String host = url.host();
        if (host.startsWith("www.")) {
            host = host.substring(4);
        }
        return host;
    }


}
