package com.github.alessiop86.antiantibotcloudflare.http.adapters;

import java.util.HashMap;

public class HttpResponseAdapter {

//    private HashMap<String,String> headers;
    private boolean challenge;
    private String content;

    public HttpResponseAdapter(boolean challenge, String content) {
        this.challenge = challenge;
        this.content = content;
    }
//    public HttpResponseAdapter(HashMap<String, String> headers, String content) {
//        this.headers = headers;
//        this.content = content;
//    }
//
//    public HashMap<String, String> getHeaders() {
//        return headers;
//    }
//
//    public String getHeader(String header) {
//        return headers.get(header)
//    }

    public boolean isChallenge() {
        return challenge;
    }

    public String getContent() {
        return content;
    }
}
