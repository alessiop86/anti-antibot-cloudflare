package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.BaseHttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class ApacheHttpClientHttpClientAdapter extends BaseHttpClientAdapter implements HttpClientAdapter {

    private final CloseableHttpClient httpclient;
    private final HttpClientContext httpClientContext;

    public ApacheHttpClientHttpClientAdapter() {
        httpclient = HttpClients.createDefault();
        httpClientContext = HttpClientContext.create();
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
            //MOVE THE GOAL POST
            System.out.println("curl -L -v \"" +
                    uri + "\"" +
                    toCurlHeaders(request) +
                    getCookieCurlParam()
            );
//            if (request.getParams().size() > 0)
//                throw new RuntimeException();
//HOW CAN WE SOLVE THAT DIFFERENTLY
            CloseableHttpResponse response = httpclient.execute(httpGet,httpClientContext);
            return new HttpResponse(isChallenge(response), extractContentBody(response), request.getUrl());
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    //TODO TMP
    private String getCookieCurlParam() {
        CookieStore cookieStore = httpClientContext.getCookieStore();
        if (cookieStore != null) {
            List<Cookie> cookies =cookieStore.getCookies();
            if (cookies.size() == 0) {
                return "";
            }
            if (cookies.size() == 1) {
                return String.format(" --cookie \"%s\"", cookies.get(0).getName() + "=" + cookies.get(0).getValue());
            }
            throw new RuntimeException("wtf");
        }
        return "";
    }

    private String extractContentBody(CloseableHttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
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
        return isChallenge(getServerHeader(response), getStatusCode(response));
    }

    private int getStatusCode(CloseableHttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    private String getServerHeader(CloseableHttpResponse response) {
        return response.getFirstHeader(SERVER_HEADER).getValue();
    }

}
