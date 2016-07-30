package com.github.alessiop86.antiantibotcloudflare.http;

public class HttpResponse {

    private final boolean challenge;
    private final String content;
    private final String requestUrl;

    public HttpResponse(boolean challenge, String content, String requestUrl) {
        this.challenge = challenge;
        this.content = content;
        this.requestUrl = requestUrl;
    }

    public boolean isChallenge() {
        return challenge;
    }

    public String getContent() {
        return content;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

}
