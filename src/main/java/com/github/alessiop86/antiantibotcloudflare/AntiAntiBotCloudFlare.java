package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.exceptions.ParseException;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpResponseAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.OkHttpHttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;

public class AntiAntiBotCloudFlare {

    private static final int REQUIRED_DELAY = 000;//TODO 5000

    private final HttpClientAdapter httpClient;
    private final ChallengeSolver challengeSolver;

    public AntiAntiBotCloudFlare(HttpClientAdapter httpClient) {
        this(httpClient, false);
    }

    public AntiAntiBotCloudFlare(HttpClientAdapter httpClient, boolean isAndroid) {
        this.httpClient = httpClient;
        challengeSolver = new ChallengeSolver(new JavascriptEngine(isAndroid));
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
                return firstReturnedPage.getContent();
            } else {
                return proceedWithAntiAntibot(firstReturnedPage);
            }
        } catch (HttpException e) {
            throw new AntiAntibotException("Operation failed", e);
        }
    }

    private String proceedWithAntiAntibot(HttpResponseAdapter firstReturnedPage) throws AntiAntibotException {
        String challenge = extractChallenge(firstReturnedPage.getContent());
        Integer challengeResult = challengeSolver.solve(challenge, firstReturnedPage);
        requiredDelay();
        return executeCallWithChallengeResult(challengeResult,firstReturnedPage);
    }

    private String executeCallWithChallengeResult(Integer challengeResult, HttpResponseAdapter firstReturnedPage) {
        String submitUrl = getSubmitUrl(firstReturnedPage.getRequestUrl());
        return null;
    }

    private String extractChallenge(String httpResponseBody) throws AntiAntibotException {
        try {
            Parser p = new Parser(httpResponseBody);
            return p.getJsChallenge();
        } catch (ParseException e) {
            throw new AntiAntibotException("Unable to parse Cloudflare anti-bots page. " +
                    "If the anti-bots protection is the captcha one, you are out of luck. " +
                    "If you are not using the latest version please submit a bug report " +
                    "at https://github.com/alessiop86/anti-antibot-cloudflare/issues", e);
        }
    }

    private void requiredDelay() throws AntiAntibotException {
        try {
            Thread.sleep(REQUIRED_DELAY);
        }
        catch (InterruptedException e) {
            throw new AntiAntibotException(e);
        }
    }

    private String getSubmitUrl(String baseUrl) {
        return baseUrl + "cdn-cgi/l/chk_jschl";
    }
}
