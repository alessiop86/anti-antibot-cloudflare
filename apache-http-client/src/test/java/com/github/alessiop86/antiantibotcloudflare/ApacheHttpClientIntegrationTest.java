package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.ApacheHttpClientHttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.HttpClientAdapter;

public class ApacheHttpClientIntegrationTest extends  AbstractIntegrationTest {

    @Override
    protected HttpClientAdapter getHttpClientAdapterUnderTest() {
        return new ApacheHttpClientHttpClientAdapter();
    }
}
