package com.github.alessiop86.antiantibotcloudflare.http;

import java.util.HashMap;

public class HttpRequest {

    private final String url;
    private final HashMap<String,String> headers;
    private final HashMap<String,String> params;

    private HttpRequest(String url, HashMap<String, String> headers, HashMap<String, String> params) {
        this.url = url;
        this.headers = headers;
        this.params = params;
    }

    /**
     * Builder used to construct the HttpRequest
     */
    public static class Builder {

        private String url;
        private HashMap<String,String> headers = new HashMap<String,String>();
        private HashMap<String,String> params = new HashMap<String,String>();

        private Builder() {
        }

        public static Builder withUrl(String url) {
            Builder builder = new Builder();
            builder.url = url;
            return builder;
        }

        public Builder addHeader(String headerName, String headerValue) {
            headers.put(headerName,headerValue);
            return this;
        }

        public Builder addParam(String paramName, String paramValue) {
            params.put(paramName,paramValue);
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(url,headers,params);
        }
    }

    /**
     * Getter for the requested url
     * @return url in string format
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter for the request headers
     * @return headers inside an HashMap<String,String> format
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * Getter for the request parameters
     * @return request parameters inside an HashMap<String,String> format
     */
    public HashMap<String, String> getParams() {
        return params;
    }
}
