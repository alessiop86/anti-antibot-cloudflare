package com.github.alessiop86.antiantibotcloudflare.http.adapters;

import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;


public interface HttpClientAdapter {

    /**
     * Executes an HTTP GET requests to the desired url
     *
     * @param url  contains the url of the desired content
     * @return HttpResponse with the requested content
     * @throws HttpException
     */
    HttpResponse getUrl(String url) throws HttpException;

    /**
     * Executes a more articulated HTTP request based on the params
     * defined inside the HttpRequest parameter.
     *
     * This is supposed to store cookies between one call and the following one.
     *
     * @param request bean containing the parameters for the HTTP request
     * @return HttpResponse with the requested content
     * @throws HttpException
     */
    HttpResponse executeRequest(HttpRequest request) throws HttpException;
}
