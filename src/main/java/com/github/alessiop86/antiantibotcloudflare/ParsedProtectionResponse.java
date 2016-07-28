package com.github.alessiop86.antiantibotcloudflare;

public class ParsedProtectionResponse {


    private final String field1;
    private final String field2;
    private final String jsChallenge;


    public ParsedProtectionResponse(String field1, String field2, String jsChallenge) {
        this.jsChallenge = jsChallenge;
        this.field2 = field2;
        this.field1 = field1;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getJsChallenge() {
        return jsChallenge;
    }
}
