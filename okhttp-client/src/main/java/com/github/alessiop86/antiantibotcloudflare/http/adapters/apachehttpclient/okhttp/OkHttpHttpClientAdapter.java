package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp;

import com.github.alessiop86.antiantibotcloudflare.http.ByteArrayHttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.BaseHttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.internals.OkHttpByteArrayClient;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.internals.OkHttpTextClient;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class OkHttpHttpClientAdapter implements HttpClientAdapter {

    private OkHttpClient okHttpClient;
    private CookieJarImpl cookieJar;

    public OkHttpHttpClientAdapter() {
        cookieJar = new CookieJarImpl();
        okHttpClient = getOkHttpClient();
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public HttpResponse getUrl(String url) throws HttpException {
        HttpRequest request = HttpRequest.Builder.withUrl(url).build();
        return executeRequest(request);
    }

    @Override
    public HttpResponse executeRequest(HttpRequest requestAbstraction) throws HttpException {
        return new OkHttpTextClient(okHttpClient, cookieJar).executeRequest(requestAbstraction);
    }

    @Override
    public ByteArrayHttpResponse executeByteArrayRequest(HttpRequest request) throws HttpException {
        return new OkHttpByteArrayClient(okHttpClient, cookieJar).executeByteArrayRequest(request);
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
    }
}
