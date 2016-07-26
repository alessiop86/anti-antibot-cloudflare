package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpResponseAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.OkHttpHttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;

public class AntiAntiBotCloudFlare {

    private final HttpClientAdapter httpClient;
    private final ChallengeSolver challengeSolver;

    public AntiAntiBotCloudFlare(HttpClientAdapter httpClient) {
        this(httpClient,false);
    }

    public AntiAntiBotCloudFlare(HttpClientAdapter httpClient, boolean isAndroid) {
        this.httpClient = httpClient;
        challengeSolver = new ChallengeSolver(isAndroid);
    }

    public static AntiAntiBotCloudFlare createAntiAntiBotCloudFlareWithDefaultConfig() {
        return new AntiAntiBotCloudFlare(new OkHttpHttpClientAdapter());
    }

    public static AntiAntiBotCloudFlare createAntiAntiBotCloudFlareWithDefaultAndroidConfig() {
        return new AntiAntiBotCloudFlare(new OkHttpHttpClientAdapter(), true);
    }


    public String getUrl(String url) throws AntiAntibotException {
        try {
            HttpResponseAdapter firstReturnedPage = httpClient.getUrl(url);
            if (!firstReturnedPage.isChallenge()) {
                return  firstReturnedPage.getContent();
            }
            else {
                return proceedWithAntiAntibot(firstReturnedPage);
            }
        } catch (HttpException e) {
            throw new AntiAntibotException("Operation failed",e);
        }
    }

    private String proceedWithAntiAntibot(HttpResponseAdapter firstReturnedPage) throws AntiAntibotException {
        String challengeResult = challengeSolver.solve(firstReturnedPage);
        return null;
    }
}
