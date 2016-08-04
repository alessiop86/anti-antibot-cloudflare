package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.BaseHttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class OkHttpHttpClientAdapter extends BaseHttpClientAdapter implements HttpClientAdapter {

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
        try {
            HttpUrl.Builder httpUrlBuilder = getHttpUrlBuilder(requestAbstraction);
            addParams(httpUrlBuilder,requestAbstraction.getParams());

            Request.Builder requestBuilder = new Request.Builder();
            addHeaders(requestBuilder,requestAbstraction.getHeaders());
            requestBuilder.url(httpUrlBuilder.build());

            Call call = okHttpClient.newCall(requestBuilder.build());
            Response response = call.execute();

            return buildHttpResponseBean(response);
        }
        catch(IOException e) {
            throw new HttpException(e);
        }
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();
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

    private boolean isChallenge(Response response) {
        return isChallenge(response.header(SERVER_HEADER), response.code());
    }

    private HttpResponse buildHttpResponseBean(Response response) throws IOException {
        String urlFromResponse = response.request().url().toString();
        return new HttpResponse(isChallenge(response), response.body().string(),
                urlFromResponse);
    }


}
