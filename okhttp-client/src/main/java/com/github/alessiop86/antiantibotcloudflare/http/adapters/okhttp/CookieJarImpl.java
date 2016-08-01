package com.github.alessiop86.antiantibotcloudflare.http.adapters.okhttp;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CookieJarImpl implements CookieJar {

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

        //TODO TEMPORARY IMPL
        List<Cookie> cookiesForUrl = cookieStore.get(url.host());
        if (cookiesForUrl != null) {
            cookiesForUrl = new ArrayList<Cookie>(cookiesForUrl); //unmodifiable
            cookiesForUrl.addAll(cookies);
            cookieStore.put(url.host(), cookiesForUrl);
        }
        else {
            cookieStore.put(url.host(), cookies);
        }




        //OLD IMPL
        //cookieStore.put(url.host(), cookies);


    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url.host());
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }



}
