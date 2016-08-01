package com.github.alessiop86.antiantibotcloudflare.http.adapters.okhttp;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.UserAgents;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.BaseHttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class OkHttpHttpClientAdapter extends BaseHttpClientAdapter implements HttpClientAdapter {

    private OkHttpClient okHttpClient;

    public OkHttpHttpClientAdapter() {
        okHttpClient = getOkHttpClient();
    }

    public HttpResponse getUrl(String url) throws HttpException {
        HttpRequest request = HttpRequest.Builder.withUrl(url).build();
        return executeRequest(request);
    }

    public HttpResponse executeRequest(HttpRequest requestAbstraction) throws HttpException {
        try {
            HttpUrl.Builder httpUrlBuilder = getHttpUrlBuilder(requestAbstraction);
            addParams(httpUrlBuilder,requestAbstraction.getParams());

            Request.Builder requestBuilder = new Request.Builder();
            addHeaders(requestBuilder,requestAbstraction.getHeaders());
            HttpUrl httpUrl = httpUrlBuilder.build();
            requestBuilder.url(httpUrl);
//MOVE THE GOAL POST
            System.out.println(String.format("curl -v \"%s\" %s %s",
                    httpUrl ,
                    toCurlHeaders(requestAbstraction) ,
                    getCookieCurlParam(httpUrl)
                )
            );
            if (requestAbstraction.getParams().size() > 0)
                throw new RuntimeException();
//WE CAN DO BETTER
            Call call = okHttpClient.newCall(requestBuilder.build());
            Response response = call.execute();
            return buildHttpResponseBean(response);
        }
        catch(IOException e) {
            throw new HttpException(e);
        }
    }

    private String getCookieCurlParam(HttpUrl httpUrl) {
        List<Cookie> cookies = okHttpClient.cookieJar().loadForRequest(httpUrl);
        if (cookies.size() == 0) {
            return "";
        }
        if (cookies.size() == 1) {
            return String.format(" --cookie \"%s=%s\"", cookies.get(0).name(),cookies.get(0).value());
        }
        throw new RuntimeException("wtf");
    }


    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl())

//                .followRedirects(true)
//                .followSslRedirects(true)
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
        if (urlFromResponse.equals("https://wuxiaworld.com/"))
            urlFromResponse = "https://wuxiaworld.com";

        return new HttpResponse(isChallenge(response), response.body().string(),
                urlFromResponse);
    }
}
