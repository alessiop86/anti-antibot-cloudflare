package com.github.alessiop86.antiantibotcloudflare.exceptions;

public class ParseException extends Exception {

    private static final String DEFAULT_MESSAGE = "The returned html has changed, please create a related issue (if there is not already one) at https://github.com/alessiop86/anti-antibot-cloudflare/issues";

    public ParseException() {
        this(DEFAULT_MESSAGE);
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(Exception e) {
        super(e);
    }
}
