package com.github.alessiop86.antiantibotcloudflare.core;

import com.github.alessiop86.antiantibotcloudflare.http.ByteArrayHttpResponse;
import com.github.alessiop86.antiantibotcloudflare.challenge.ChallengeSolver;
import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;

public class ByteArrayAntiAntibotCore extends BaseAntiAntibotCore {

    public ByteArrayAntiAntibotCore(HttpClientAdapter httpClient, ChallengeSolver challengeSolver) {
        super(challengeSolver, httpClient);
    }

    public byte[] proceed(HttpResponse firstReturnedPage) throws AntiAntibotException {
        HttpRequest request = prepareSecondRequest(firstReturnedPage);
        return executeFinalHttpRequest(request);
    }

    private byte[] executeFinalHttpRequest(HttpRequest request) throws AntiAntibotException {
        try {
            ByteArrayHttpResponse desiredPage = httpClient.executeByteArrayRequest(request);
            return desiredPage.getByteArrayContent();
        } catch (HttpException e) {
            throw new AntiAntibotException("Error executing the second Http call",e);
        }
    }
}
