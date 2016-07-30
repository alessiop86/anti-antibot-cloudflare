package com.github.alessiop86.antiantibotcloudflare.http.adapters;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;

import java.util.Map;

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

    //TODO delete
    public String toCurlHeaders(HttpRequest request) {
        String return_ = "";
        for (Map.Entry<String,String> header : request.getHeaders().entrySet()) {
            return_+=  String.format(" --header \"%s: %s\" ",header.getKey(), header.getValue());
        }
        return return_;
    }
}
