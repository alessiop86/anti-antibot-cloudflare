package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.exceptions.ParseException;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.UserAgents;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import com.github.alessiop86.antiantibotcloudflare.util.UrlUtils;

public class AntiAntiBotCloudFlare {

    private static final int REQUIRED_DELAY = 5000;//TODO 5000

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
            //if (!firstReturnedPage.isChallenge()) {
            if (false) { //TODO testing purposes with python SimpleHTTPServer returning not 503
                return firstReturnedPage.getContent();
            } else {
                return proceedWithAntiAntibot(firstReturnedPage);
            }
        } catch (HttpException e) {
            throw new AntiAntibotException("Operation failed", e);
        }
    }

    private String proceedWithAntiAntibot(HttpResponse firstReturnedPage) throws AntiAntibotException {
        long beginMillis = System.currentTimeMillis();
        ParsedProtectionResponse parsedResponse = parseResponse(firstReturnedPage.getContent());
        Integer challengeResult = challengeSolver.solve(parsedResponse.getJsChallenge(), firstReturnedPage);
        String requestUrl = firstReturnedPage.getRequestUrl();
        String submitUrl = UrlUtils.getSubmitUrl(requestUrl);
        HttpRequest request = HttpRequest.Builder.withUrl(submitUrl)
                .addHeader("Referer",requestUrl)
                .addHeader("User-Agent", UserAgents.getRandom())
                .addParam(Parser.INPUT_FIELD_1,parsedResponse.getField1())
                .addParam(Parser.INPUT_FIELD_2,parsedResponse.getField2())
                .addParam("jschl_answer", "" + challengeResult)
                .build();
        requiredDelay(beginMillis);
        return executeFinalHttpRequest(request);
    }

    private String executeFinalHttpRequest(HttpRequest request) throws AntiAntibotException {
        try {
            HttpResponse desiredPage = httpClient.executeRequest(request);
            return desiredPage.getContent();
        } catch (HttpException e) {
            throw new AntiAntibotException("Error executing the second Http call",e);
        }
    }


    private ParsedProtectionResponse parseResponse(String httpResponseBody) throws AntiAntibotException {
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

    private String buildSubmitUrl(String baseUrl) {
        return baseUrl + "cdn-cgi/l/chk_jschl";
    }
}
