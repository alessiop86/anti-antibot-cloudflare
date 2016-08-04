package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.OkHttpHttpClientAdapter;

public class OkHttpAntiAntibotCloudFlareFactory implements AntiAntiBotCloudFlareFactory {

    @Override
    public AntiAntiBotCloudFlare createInstance() {
        return new AntiAntiBotCloudFlare(new OkHttpHttpClientAdapter(),true);
    }

    public AntiAntiBotCloudFlare createInstanceNonAndroid() {
        return new AntiAntiBotCloudFlare(new OkHttpHttpClientAdapter());
    }
}
