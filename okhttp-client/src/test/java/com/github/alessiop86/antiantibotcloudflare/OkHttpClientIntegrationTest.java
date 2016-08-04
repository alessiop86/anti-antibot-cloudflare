package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.HttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.OkHttpHttpClientAdapter;

public class OkHttpClientIntegrationTest extends  AbstractIntegrationTest {

    @Override
    protected AntiAntiBotCloudFlareFactory getAntiAntiBotCloudFlareFactory() {
        return new OkHttpAntiAntibotCloudFlareFactory();
    }
}
