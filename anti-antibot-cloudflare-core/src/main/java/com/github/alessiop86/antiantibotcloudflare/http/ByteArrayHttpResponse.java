package com.github.alessiop86.antiantibotcloudflare.http;

/**
 * Extending HttpResponse because if the content is protected the object will contain
 * a string content (the challenge to solve). On the other hand, if the content is
 * unprotected or the challenge has already been solved, the object will contain a
 * null string content and the byte content in the byteArrayContent field
 */
public class ByteArrayHttpResponse extends HttpResponse {

    private final byte[] byteArrayContent;

    //non-challenge constructor
    public ByteArrayHttpResponse( byte[] byteArrayContent, String requestUrl) {
        super(false, null, requestUrl);
        this.byteArrayContent = byteArrayContent;
    }

    //devious constructor for challenge. Private in order to avoid misuses
    private ByteArrayHttpResponse(String content, String requestUrl) {
        super(true, content,requestUrl);
        byteArrayContent = null;
    }

    public static ByteArrayHttpResponse buildChallengeHttpResponse(String content, String requestUri) {
        return new ByteArrayHttpResponse(content,requestUri);
    }

    public byte[] getByteArrayContent() {
        return byteArrayContent;
    }
}
