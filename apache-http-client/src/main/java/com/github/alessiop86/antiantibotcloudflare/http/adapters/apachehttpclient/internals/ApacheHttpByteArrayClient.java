package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.internals;

import com.github.alessiop86.antiantibotcloudflare.http.ByteArrayHttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static com.github.alessiop86.antiantibotcloudflare.http.ByteArrayHttpResponse.buildChallengeHttpResponse;

public class ApacheHttpByteArrayClient extends ApacheHttpBaseClient {

    public ApacheHttpByteArrayClient(HttpClientContext httpClientContext, CloseableHttpClient httpclient) {
        super(httpClientContext, httpclient);
    }

    public ByteArrayHttpResponse executeByteArrayRequest(HttpRequest request) throws HttpException {
        try {
            CloseableHttpResponse response = getApacheHttpResponse(request);
            if (isChallenge(response)) {
                return buildChallengeHttpResponse(extractContentBody(response), request.getUrl());
            }
            else {
                return buildByteArrayHttpResponse(request, response);
            }
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    private ByteArrayHttpResponse buildByteArrayHttpResponse(HttpRequest request, CloseableHttpResponse response) throws IOException {
        return new ByteArrayHttpResponse(extractByteArrayContentBody(response), request.getUrl());
    }

    private byte[] extractByteArrayContentBody(CloseableHttpResponse response) throws IOException {
        return EntityUtils.toByteArray(response.getEntity());
    }
}
