package com.github.alessiop86.antiantibotcloudflare.core;

import com.github.alessiop86.antiantibotcloudflare.challenge.ChallengeSolver;
import com.github.alessiop86.antiantibotcloudflare.challenge.Parser;
import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.exceptions.ParseException;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.UserAgents;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.util.UrlUtils;

public abstract class BaseAntiAntibotCore {

    private static final int REQUIRED_DELAY = 5000;
    private final ChallengeSolver challengeSolver;
    protected final HttpClientAdapter httpClient;

    public BaseAntiAntibotCore(ChallengeSolver challengeSolver, HttpClientAdapter httpClient) {
        this.challengeSolver = challengeSolver;
        this.httpClient = httpClient;
    }

    protected HttpRequest prepareSecondRequest(HttpResponse firstReturnedPage) throws AntiAntibotException {
        long beginMillis = System.currentTimeMillis();
        Parser.ParsedChallengePage parsedResponse = parseResponse(firstReturnedPage.getContent());
        Integer challengeResult = challengeSolver.solve(parsedResponse.getJsChallenge(), firstReturnedPage);
        String requestUrl = firstReturnedPage.getRequestUrl();
        String submitUrl = UrlUtils.getSubmitUrl(requestUrl);
        HttpRequest request = HttpRequest.Builder.withUrl(submitUrl)
                .addHeader("Referer",requestUrl)
                .addHeader("User-Agent", UserAgents.getRandom())
                .addParam(Parser.PASS_FIELD,parsedResponse.getPass1())
                .addParam(Parser.JSCHL_VC_FIELD,parsedResponse.getJschl_vc())
                .addParam("jschl_answer", "" + challengeResult)
                .build();
        requiredDelay(beginMillis);
        return request;
    }

    private Parser.ParsedChallengePage parseResponse(String httpResponseBody) throws AntiAntibotException {
        try {
            Parser parser = new Parser(httpResponseBody);
            return parser.getParsedProtectionResponse();
        } catch (ParseException e) {
            throw new AntiAntibotException("Unable to parse Cloudflare anti-bots page. " +
                    "If the anti-bots protection is the captcha one, you are out of luck. " +
                    "If you are not using the latest version please submit a bug report " +
                    "at https://github.com/alessiop86/anti-antibot-cloudflare/issues", e);
        }
    }

    private void requiredDelay(long beginMillis) throws AntiAntibotException {
        try {
            for (long currentTimeMillis = System.currentTimeMillis(); currentTimeMillis - beginMillis < REQUIRED_DELAY;) {
                Thread.sleep(REQUIRED_DELAY - (currentTimeMillis - beginMillis) + 100);
                currentTimeMillis = System.currentTimeMillis();
            }
        }
        catch (InterruptedException e) {
            throw new AntiAntibotException(e);
        }
    }
}
