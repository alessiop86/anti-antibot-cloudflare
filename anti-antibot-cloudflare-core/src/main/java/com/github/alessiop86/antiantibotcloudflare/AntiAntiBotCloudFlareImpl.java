package com.github.alessiop86.antiantibotcloudflare;


import com.github.alessiop86.antiantibotcloudflare.challenge.JavascriptEngine.IsAndroid;
import com.github.alessiop86.antiantibotcloudflare.challenge.ChallengeSolver;
import com.github.alessiop86.antiantibotcloudflare.challenge.JavascriptEngine;
import com.github.alessiop86.antiantibotcloudflare.internals.HttpRequestsManager;
import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.http.ByteArrayHttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;

public class AntiAntiBotCloudFlareImpl implements AntiAntiBotCloudFlare {

    private final HttpClientAdapter httpClient;
    private final HttpRequestsManager antiAntibotCore;

    public AntiAntiBotCloudFlareImpl(HttpClientAdapter httpClient) {
        this(httpClient, IsAndroid.NOT_ANDROID);
    }

    public AntiAntiBotCloudFlareImpl(HttpClientAdapter httpClient, IsAndroid isAndroid) {
        this.httpClient = httpClient;
        antiAntibotCore = new HttpRequestsManager(httpClient, buildChallengeSolver(isAndroid));
    }

    private ChallengeSolver buildChallengeSolver(IsAndroid isAndroid) {
        return new ChallengeSolver(new JavascriptEngine(isAndroid));
    }

    @Override
    public String getUrl(String url) throws AntiAntibotException {
        try {
            HttpResponse response = httpClient.getUrl(url);
            return returnContentOrExecuteSecondRequest(response);
        } catch (HttpException e) {
            throw new AntiAntibotException("Operation failed", e);
        }
    }

    private String returnContentOrExecuteSecondRequest(HttpResponse firstReturnedPage) throws AntiAntibotException {
        if (!firstReturnedPage.isChallenge()) {
            return firstReturnedPage.getContent();
        } else {
            return antiAntibotCore.getString(firstReturnedPage);
        }
    }

    @Override
    public byte[] getByteArrayFromUrl(String url) throws AntiAntibotException {
        try {
            HttpRequest request = requestUrl(url);
            ByteArrayHttpResponse response = httpClient.executeByteArrayRequest(request);
            return returnContentOrExecuteSecondRequest(response);
        } catch (HttpException e) {
            throw new AntiAntibotException("Operation failed", e);
        }
    }

    private HttpRequest requestUrl(String url) {
        return HttpRequest.Builder.withUrl(url).build();
    }

    private byte[] returnContentOrExecuteSecondRequest(ByteArrayHttpResponse firstReturnedPage) throws AntiAntibotException {
        if (!firstReturnedPage.isChallenge()) {
            return firstReturnedPage.getByteArrayContent();
        } else {
            return antiAntibotCore.getByteArray(firstReturnedPage);
        }
    }

    @Override
    public void close() throws Exception {
        httpClient.close();
    }
}
