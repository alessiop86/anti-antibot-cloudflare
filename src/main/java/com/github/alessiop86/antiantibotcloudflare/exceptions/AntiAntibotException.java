package com.github.alessiop86.antiantibotcloudflare.exceptions;

public class AntiAntibotException extends Exception {

    public AntiAntibotException(Exception e) {
        super(e);
    }

    public AntiAntibotException(String message, Exception e) {
        super(message,e);
    }
}
