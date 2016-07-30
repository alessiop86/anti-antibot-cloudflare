package com.github.alessiop86.antiantibotcloudflare.http.adapters.okhttp;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.UserAgents;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class OkHttpHttpClientAdapter implements HttpClientAdapter {

    private static final String SERVER_HEADER = "Server";
    private static final String SERVER_HEADER_CHALLENGE_VALUE = "cloudflare-nginx";
    private static final int HTTP_STATUS_CODE_CHALLENGE = 503;

    private OkHttpClient okHttpClient;


    public OkHttpHttpClientAdapter() {
        okHttpClient = getOkHttpClient();
    }

    public HttpResponse getUrl(String url) throws HttpException {
        HttpRequest request = HttpRequest.Builder.withUrl(url)
                .build();
        return executeRequest(request);
    }

    public HttpResponse executeRequest(HttpRequest requestAbstraction) throws HttpException {
        try {
            HttpUrl.Builder httpUrlBuilder = getHttpUrlBuilder(requestAbstraction);
            addParams(httpUrlBuilder,requestAbstraction.getParams()); //only for GET

            Request.Builder requestBuilder = new Request.Builder();
            addHeaders(requestBuilder,requestAbstraction.getHeaders());
            requestBuilder.url(httpUrlBuilder.build());

            Response response = okHttpClient.newCall(requestBuilder.build()).execute();

            return new HttpResponse(isChallenge(response), response.body().string(),
                    response.request().url().toString());
        }
        catch(IOException e) {
            throw new HttpException(e);
        }
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder().cookieJar(new CookieJarImpl()).followRedirects(true).followSslRedirects(true).build();
    }

    private HttpUrl.Builder getHttpUrlBuilder(HttpRequest requestAbstraction) {
        HttpUrl url = HttpUrl.parse(requestAbstraction.getUrl());
        return url.newBuilder();
    }

    private void addParams(HttpUrl.Builder builder, Map<String, String> params) throws UnsupportedEncodingException {
        for (Map.Entry<String,String> param : params.entrySet()) {
            //HTTP DECODE REQUIRED FOR GET ONLY
            builder.addEncodedQueryParameter(param.getKey(), URLEncoder.encode(param.getValue(),"UTF-8"));
            //builder.addQueryParameter(param.getKey(), param.getValue());
        }
    }

    private void addHeaders(Request.Builder builder, Map<String, String> headers) {
        for (Map.Entry<String,String> header : headers.entrySet()) {
            builder.addHeader(header.getKey(), header.getValue());
        }
        builder.addHeader("Connection","keep-alive");
        builder.addHeader("Accept","*/*");
        builder.addHeader("Accept-Encoding","gzip, deflate");
    }

    private boolean isChallenge(Response response) {
        return expectedServerHeader(response.header(SERVER_HEADER))
                && expectedHttpStatusCode(response.code());
    }

    private boolean expectedServerHeader(String serverHeader) {
        return (serverHeader != null && serverHeader.equals(SERVER_HEADER_CHALLENGE_VALUE));
    }

    private boolean expectedHttpStatusCode(int httpStatusCode) {
        return httpStatusCode == HTTP_STATUS_CODE_CHALLENGE;
    }
}
