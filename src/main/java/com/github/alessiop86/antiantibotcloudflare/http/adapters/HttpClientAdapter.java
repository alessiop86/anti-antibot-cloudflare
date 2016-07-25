package com.github.alessiop86.antiantibotcloudflare.http.adapters;

import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;

public interface HttpClientAdapter {

    public HttpResponseAdapter getUrl(String url) throws HttpException;
}
