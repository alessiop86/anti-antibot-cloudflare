package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpResponseAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;

public class AntiAntiBotCloudFlare {

    private final HttpClientAdapter httpClient;
    private final ChallengeSolver challengeSolver = new ChallengeSolver();

    public AntiAntiBotCloudFlare(HttpClientAdapter httpClient) {
        this.httpClient = httpClient;
    }

    public String getUrl(String url) throws AntiAntibotException {
        try {
            HttpResponseAdapter firstReturnedPage = httpClient.getUrl(url);
            if (!firstReturnedPage.isChallenge()) {
                return  firstReturnedPage.getContent();
            }
            else {
                return proceedWithAntiAntibot(firstReturnedPage.getContent());
            }
        } catch (HttpException e) {
            throw new AntiAntibotException("Operation failed",e);
        }
    }

    private String proceedWithAntiAntibot(String firstReturnedPage) {
        String challengeResult = challengeSolver.solve(firstReturnedPage);
        return null;
    }
}
