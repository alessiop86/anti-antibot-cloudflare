package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;

public class AntiAntiBotCloudFlare {

    HttpClientAdapter httpClient;


    public AntiAntiBotCloudFlare(HttpClientAdapter httpClient) {
        this.httpClient = httpClient;
    }

    public String getUrl(String url) throws AntiAntibotException {
        try {
            return httpClient.getUrl(url);
        } catch (HttpException e) {
            throw new AntiAntibotException("Operation failed",e);
        }
    }
}
