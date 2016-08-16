package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.internals;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class ApacheHttpTextClient extends ApacheHttpBaseClient {

    public ApacheHttpTextClient(HttpClientContext httpClientContext, CloseableHttpClient httpclient) {
        super(httpClientContext, httpclient);
    }

    public HttpResponse executeRequest(HttpRequest request) throws HttpException {
        try {
            CloseableHttpResponse response = getApacheHttpResponse(request);
            return new HttpResponse(isChallenge(response), extractContentBody(response), request.getUrl());
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
}
