package com.github.alessiop86.antiantibotcloudflare.http.adapters;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class HttpResponseAdapter {

//    private HashMap<String,String> headers;
    private final boolean challenge;
    private final String content;
    private final String requestUrl;

    public HttpResponseAdapter(boolean challenge, String content, String requestUrl) {
        this.challenge = challenge;
        this.content = content;
        this.requestUrl = requestUrl;
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

    public String getRequestUrl() {
        return requestUrl;
    }

    public  String getDomainName() throws AntiAntibotException {
        try {
            URI uri = new URI(requestUrl);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        }
        catch (URISyntaxException e) {
            throw new AntiAntibotException(String.format("Unable to parse the domain name for the url %s. "
                    + " Please check that you are using latest anti-antibot version available at " +
                    "https://github.com/alessiop86/anti-antibot-cloudflare. If you are using latest version, " +
                    "lease submit a bug report at https://github.com/alessiop86/anti-antibot-cloudflare/issues", requestUrl), e);
        }
    }
}
