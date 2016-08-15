package com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient;

import com.github.alessiop86.antiantibotcloudflare.http.ByteArrayHttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.HttpRequest;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.http.exceptions.HttpException;


public interface HttpClientAdapter extends AutoCloseable {

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
     * defined inside the HttpRequest parameter. It will return the content
     * as a string.
     *
     * This is supposed to store cookies between one call and the following one.
     *
     * @param request bean containing the parameters for the HTTP request
     * @return HttpResponse with the requested content inside the content field
     * @throws HttpException
     */
    HttpResponse executeRequest(HttpRequest request) throws HttpException;

    /**
     * Executes a more articulated HTTP request based on the params
     * defined inside the HttpRequest parameter. It will read the content
     * as bytes (that is without encoding).
     *
     * This is supposed to store cookies between one call and the following one.
     *
     * @param request bean containing the parameters for the HTTP request
     * @return HttpResponse with the requested content inside the byteArrayContent field
     * @throws HttpException
     */
    ByteArrayHttpResponse executeByteArrayRequest(HttpRequest request) throws HttpException;
}
