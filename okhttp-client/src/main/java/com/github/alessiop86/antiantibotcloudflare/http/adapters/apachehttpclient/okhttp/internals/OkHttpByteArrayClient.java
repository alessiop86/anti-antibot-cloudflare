package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.internals;

import com.github.alessiop86.antiantibotcloudflare.http.ByteArrayHttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.CookieJarImpl;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpByteArrayClient extends OkHttpBaseClient  {

    public OkHttpByteArrayClient(OkHttpClient okHttpClient, CookieJarImpl cookieJar) {
        super(okHttpClient, cookieJar);
    }

    public ByteArrayHttpResponse executeByteArrayRequest(HttpRequest request) throws HttpException {
        try {
            Response response = executeOkHttpRequest(request);
            return buildAppropriateResponseType(response);
        }
        catch(IOException e) {
            throw new HttpException(e);
        }
    }


    private ByteArrayHttpResponse buildAppropriateResponseType(Response response) throws IOException {
        if (isChallenge(response)) {
            return buildChallengeHttpResponse(response);
        }
        else {
            return buildByteArrayHttpResponse(response);
        }
    }

    private ByteArrayHttpResponse buildChallengeHttpResponse(Response response) throws IOException {
        return ByteArrayHttpResponse.buildChallengeHttpResponse(response.body().string(),
                getUrlFromResponse(response));
    }

    private ByteArrayHttpResponse buildByteArrayHttpResponse(Response response) throws IOException {
        return new ByteArrayHttpResponse(response.body().bytes(), getUrlFromResponse(response));
    }
}
