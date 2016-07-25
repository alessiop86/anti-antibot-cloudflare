package com.github.alessiop86.antiantibotcloudflare.http.adapters;

import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpHttpClientAdapter implements HttpClientAdapter {

    public String getUrl(String url) throws HttpException {

        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(IOException e) {
            throw new HttpException(e);
        }
    }
}
