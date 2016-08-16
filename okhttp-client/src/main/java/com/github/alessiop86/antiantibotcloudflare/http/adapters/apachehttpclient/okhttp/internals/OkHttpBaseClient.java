package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.internals;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.BaseHttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.CookieJarImpl;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

//TODO I don't like this inheritance, refactor BaseHttpClientAdapter
//using helpers or composition
public abstract class OkHttpBaseClient extends BaseHttpClientAdapter {

    private OkHttpClient okHttpClient;
    private CookieJarImpl cookieJar;

    public OkHttpBaseClient(OkHttpClient okHttpClient, CookieJarImpl cookieJar) {
        this.okHttpClient = okHttpClient;
        this.cookieJar = cookieJar;
    }

    protected Response executeOkHttpRequest(HttpRequest requestAbstraction) throws IOException {
        HttpUrl.Builder httpUrlBuilder = getHttpUrlBuilder(requestAbstraction);
        addParams(httpUrlBuilder,requestAbstraction.getParams());

        Request.Builder requestBuilder = new Request.Builder();
        addHeaders(requestBuilder,requestAbstraction.getHeaders());
        requestBuilder.url(httpUrlBuilder.build());

        Call call = okHttpClient.newCall(requestBuilder.build());
        return call.execute();
    }

    private HttpUrl.Builder getHttpUrlBuilder(HttpRequest requestAbstraction) {
        HttpUrl url = HttpUrl.parse(requestAbstraction.getUrl());
        return url.newBuilder();
    }

    private void addParams(HttpUrl.Builder builder, Map<String, String> params) throws UnsupportedEncodingException {
        for (Map.Entry<String,String> param : params.entrySet()) {
            builder.addEncodedQueryParameter(param.getKey(), URLEncoder.encode(param.getValue(),"UTF-8"));
        }
    }

    private void addHeaders(Request.Builder builder, Map<String, String> headers) {
        for (Map.Entry<String,String> header : headers.entrySet()) {
            builder.addHeader(header.getKey(), header.getValue());
        }
    }

    protected boolean isChallenge(Response response) {
        return isChallenge(response.header(SERVER_HEADER), response.code());
    }

    protected String getUrlFromResponse(Response response) {
        return response.request().url().toString();
    }
}
