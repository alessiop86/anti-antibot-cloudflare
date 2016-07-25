package com.github.alessiop86.antiantibotcloudflare.http.exceptions;

public class HttpException extends Exception {

    public HttpException(Exception e) {
        super(e);
    }
}
