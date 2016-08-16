package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.internals;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.BaseHttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public abstract class ApacheHttpBaseClient extends BaseHttpClientAdapter {

    private final CloseableHttpClient httpclient;
    private final HttpClientContext httpClientContext;

    public ApacheHttpBaseClient(HttpClientContext httpClientContext, CloseableHttpClient httpclient) {
        this.httpClientContext = httpClientContext;
        this.httpclient = httpclient;
    }

    protected CloseableHttpResponse getApacheHttpResponse(HttpRequest request) throws HttpException, IOException {
        URI uri = buildUri(request);
        HttpGet httpGet = new HttpGet(uri);
        setHeaders(request, httpGet);
        return httpclient.execute(httpGet, httpClientContext);
    }

    private URI buildUri(HttpRequest request) throws HttpException {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(request.getUrl());
            if (request.getParams().size() > 0) {
                for (Map.Entry<String, String> param : request.getParams().entrySet()) {
                    uriBuilder.addParameter(param.getKey(), param.getValue());
                }
            }
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new HttpException("Invalid URI",e);
        }
    }

    private void setHeaders(HttpRequest request, HttpGet httpGet) {
        for (Map.Entry<String,String> header : request.getHeaders().entrySet()) {
            httpGet.addHeader(header.getKey(),header.getValue());
        }
    }

    protected String extractContentBody(CloseableHttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    protected boolean isChallenge(CloseableHttpResponse response) {
        return isChallenge(getServerHeader(response), getStatusCode(response));
    }

    private int getStatusCode(CloseableHttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    private String getServerHeader(CloseableHttpResponse response) {
        return response.getFirstHeader(SERVER_HEADER).getValue();
    }
}
