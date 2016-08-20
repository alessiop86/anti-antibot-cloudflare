package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.ApacheHttpClientHttpClientAdapter;

public class ApacheHttpAntiAntibotCloudFlareFactory implements AntiAntiBotCloudFlareFactory {

    @Override
    public AntiAntiBotCloudFlare createInstance() {
        return new AntiAntiBotCloudFlareImpl(new ApacheHttpClientHttpClientAdapter());
    }
}
