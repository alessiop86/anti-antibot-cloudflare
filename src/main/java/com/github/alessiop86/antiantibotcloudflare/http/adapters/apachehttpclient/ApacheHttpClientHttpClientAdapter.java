package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class ApacheHttpClientHttpClientAdapter implements HttpClientAdapter {

    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String SERVER_HEADER = "Server";
    private static final String SERVER_HEADER_CHALLENGE_VALUE = "cloudflare-nginx";
    private static final int HTTP_STATUS_CODE_CHALLENGE = 503;

    private final CloseableHttpClient httpclient;

    public ApacheHttpClientHttpClientAdapter() {
        httpclient = HttpClients.createDefault();
    }

    @Override
    public HttpResponse getUrl(String url) throws HttpException {
        return executeRequest(HttpRequest.Builder.withUrl(url).build());
    }

    @Override
    public HttpResponse executeRequest(HttpRequest request) throws HttpException {
        URI uri = buildUri(request);
        HttpGet httpGet = new HttpGet(uri);
        try {
            setHeaders(request, httpGet);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            return new HttpResponse(isChallenge(response), EntityUtils.toString(response.getEntity()),
                    request.getUrl());

        } catch (IOException e) {
            throw new HttpException(e);
        }
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

    private boolean isChallenge(CloseableHttpResponse response) {
        return expectedServerHeader(response.getFirstHeader(SERVER_HEADER).getValue())
                && expectedHttpStatusCode(response.getStatusLine().getStatusCode());
    }


    private boolean expectedServerHeader(String serverHeader) {
        return (serverHeader != null && serverHeader.equals(SERVER_HEADER_CHALLENGE_VALUE));
    }

    private boolean expectedHttpStatusCode(int httpStatusCode) {
        return httpStatusCode == HTTP_STATUS_CODE_CHALLENGE;
    }
}
