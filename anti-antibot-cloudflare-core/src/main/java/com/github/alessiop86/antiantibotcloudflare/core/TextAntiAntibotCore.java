package com.github.alessiop86.antiantibotcloudflare.core;

import com.github.alessiop86.antiantibotcloudflare.challenge.ChallengeSolver;
import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;

public class TextAntiAntibotCore extends BaseAntiAntibotCore {

    public TextAntiAntibotCore(HttpClientAdapter httpClient, ChallengeSolver challengeSolver) {
        super(challengeSolver, httpClient);
    }

    public String proceed(HttpResponse firstReturnedPage) throws AntiAntibotException {
        HttpRequest request = prepareSecondRequest(firstReturnedPage);
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

}
