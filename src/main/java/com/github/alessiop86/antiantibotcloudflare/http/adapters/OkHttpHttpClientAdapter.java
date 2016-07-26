package com.github.alessiop86.antiantibotcloudflare.http.adapters;

import com.github.alessiop86.antiantibotcloudflare.http.UserAgents;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpHttpClientAdapter implements HttpClientAdapter {

    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String SERVER_HEADER = "Server";
    private static final String SERVER_HEADER_CHALLENGE_VALUE = "cloudflare-nginx";
    private static final int HTTP_STATUS_CODE_CHALLENGE = 503;

    public HttpResponseAdapter getUrl(String url) throws HttpException {

        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(USER_AGENT_HEADER, UserAgents.getRandom())
                    .build();
            Response response = client.newCall(request).execute();
            return new HttpResponseAdapter(isChallenge(response), response.body().string(), response.request().url().toString());
        }
        catch(IOException e) {
            throw new HttpException(e);
        }
    }

    private boolean isChallenge(Response response) {
        return expectedServerHeader(response.header(SERVER_HEADER))
                && expectedHttpStatusCode(response.code());
    }

    private boolean expectedServerHeader(String serverHeader) {
        return (serverHeader != null && serverHeader.equals(SERVER_HEADER_CHALLENGE_VALUE));
    }

    private boolean expectedHttpStatusCode(int httpStatusCode) {
        return httpStatusCode == HTTP_STATUS_CODE_CHALLENGE;
    }
}
