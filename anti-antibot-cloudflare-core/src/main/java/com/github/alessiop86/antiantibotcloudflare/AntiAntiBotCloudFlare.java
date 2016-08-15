package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.challenge.ChallengeSolver;
import com.github.alessiop86.antiantibotcloudflare.challenge.JavascriptEngine;
import com.github.alessiop86.antiantibotcloudflare.core.ByteArrayAntiAntibotCore;
import com.github.alessiop86.antiantibotcloudflare.core.TextAntiAntibotCore;
import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.http.ByteArrayHttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;

public class AntiAntiBotCloudFlare implements AutoCloseable {

    private final HttpClientAdapter httpClient;
    private final ChallengeSolver challengeSolver;

    public AntiAntiBotCloudFlare(HttpClientAdapter httpClient) {
        this(httpClient, false);
    }

    public AntiAntiBotCloudFlare(HttpClientAdapter httpClient, boolean isAndroid) {
        this.httpClient = httpClient;
        challengeSolver = new ChallengeSolver(new JavascriptEngine(isAndroid));
    }

    public String getUrl(String url) throws AntiAntibotException {
        try {
            HttpResponse firstReturnedPage = httpClient.getUrl(url);
            if (!firstReturnedPage.isChallenge()) {
                return firstReturnedPage.getContent();
            } else {
                TextAntiAntibotCore antiAntiBot = new TextAntiAntibotCore(httpClient, challengeSolver);
                return antiAntiBot.proceed(firstReturnedPage);
            }
        } catch (HttpException e) {
            throw new AntiAntibotException("Operation failed", e);
        }
    }


    public byte[] getByteArrayFromUrl(String url) throws AntiAntibotException {
        try {
            HttpRequest request = HttpRequest.Builder.withUrl(url).build();
            ByteArrayHttpResponse firstReturnedPage = httpClient.executeByteArrayRequest(request);
            if (!firstReturnedPage.isChallenge()) {
                return firstReturnedPage.getByteArrayContent();
            } else {
                ByteArrayAntiAntibotCore antiAntibot = new ByteArrayAntiAntibotCore(httpClient, challengeSolver);
                return antiAntibot.proceed(firstReturnedPage);
            }
        } catch (HttpException e) {
            throw new AntiAntibotException("Operation failed", e);
        }
    }

    @Override
    public void close() throws Exception {
        httpClient.close();
    }
}
