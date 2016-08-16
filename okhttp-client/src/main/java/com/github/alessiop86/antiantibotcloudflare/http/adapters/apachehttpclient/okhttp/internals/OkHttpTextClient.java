package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.internals;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.CookieJarImpl;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpTextClient extends OkHttpBaseClient {

    public OkHttpTextClient(OkHttpClient okHttpClient, CookieJarImpl cookieJar) {
        super(okHttpClient, cookieJar);
    }

    public HttpResponse executeRequest(HttpRequest requestAbstraction) throws HttpException {
        try {
            Response response = executeOkHttpRequest(requestAbstraction);
            return buildTextHttpResponse(response);
        }
        catch(IOException e) {
            throw new HttpException(e);
        }
    }

    private HttpResponse buildTextHttpResponse(Response response) throws IOException {
        return new HttpResponse(isChallenge(response), response.body().string(),
                getUrlFromResponse(response));
    }
}
