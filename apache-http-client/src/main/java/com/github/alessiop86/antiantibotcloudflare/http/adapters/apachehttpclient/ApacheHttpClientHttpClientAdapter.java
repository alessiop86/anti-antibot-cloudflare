package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient;

import com.github.alessiop86.antiantibotcloudflare.http.ByteArrayHttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.internals.ApacheHttpByteArrayClient;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.internals.ApacheHttpTextClient;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static com.github.alessiop86.antiantibotcloudflare.http.ByteArrayHttpResponse.buildChallengeHttpResponse;

public class ApacheHttpClientHttpClientAdapter implements HttpClientAdapter {

    private final CloseableHttpClient httpclient;
    private final HttpClientContext httpClientContext;

    public ApacheHttpClientHttpClientAdapter() {
        httpclient = HttpClients.createDefault();
        httpClientContext = HttpClientContext.create();
    }

    @Override
    public void close() throws Exception {
        httpclient.close();
    }

    @Override
    public HttpResponse getUrl(String url) throws HttpException {
        return executeRequest(HttpRequest.Builder.withUrl(url).build());
    }

    @Override
    public HttpResponse executeRequest(HttpRequest request) throws HttpException {
       return new ApacheHttpTextClient(httpClientContext, httpclient).executeRequest(request);
    }

    @Override
    public ByteArrayHttpResponse executeByteArrayRequest(HttpRequest request) throws HttpException {
        return new ApacheHttpByteArrayClient(httpClientContext, httpclient).executeByteArrayRequest(request);
    }

}
