package com.github.alessiop86.antiantibotcloudflare.http.adapters;

public class BaseHttpClientAdapter {


    private static final String SERVER_HEADER_CHALLENGE_VALUE = "cloudflare-nginx";
    private static final int HTTP_STATUS_CODE_CHALLENGE = 503;

    protected static final String SERVER_HEADER = "Server";
    protected  static final String USER_AGENT_HEADER = "User-Agent";

    protected boolean isChallenge(String serverHeader, int httpResponseStatus) {
        return expectedServerHeader(serverHeader) && expectedHttpStatusCode(httpResponseStatus);
    }

    private boolean expectedServerHeader(String serverHeader) {
        return (serverHeader != null && serverHeader.equals(SERVER_HEADER_CHALLENGE_VALUE));
    }

    private boolean expectedHttpStatusCode(int httpStatusCode) {
        return httpStatusCode == HTTP_STATUS_CODE_CHALLENGE;
    }
}
