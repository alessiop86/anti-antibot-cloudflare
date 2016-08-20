package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.challenge.JavascriptEngine.IsAndroid;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.okhttp.OkHttpHttpClientAdapter;

public class OkHttpAntiAntibotCloudFlareFactory implements AntiAntiBotCloudFlareFactory {

    @Override
    public AntiAntiBotCloudFlare createInstance() {
        return new AntiAntiBotCloudFlareImpl(new OkHttpHttpClientAdapter(), IsAndroid.ANDROID);
    }

    /**
     * Additional custom factory method in case you decide to use OkHttp client outside Android.
     */
    public AntiAntiBotCloudFlare createInstanceNonAndroid() {
        return new AntiAntiBotCloudFlareImpl(new OkHttpHttpClientAdapter());
    }
}
