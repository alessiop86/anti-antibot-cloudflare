package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.http.adapters.apachehttpclient.ApacheHttpClientHttpClientAdapter;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.okhttp.OkHttpHttpClientAdapter;

public class TestHttp {

    static  String site = "https://wuxiaworld.com";

    public static void main(String[] args) throws Exception {
        //site = "http://localhost/input2.txt";
        AntiAntiBotCloudFlare main = new AntiAntiBotCloudFlare(new OkHttpHttpClientAdapter());
        //AntiAntiBotCloudFlare main = new AntiAntiBotCloudFlare(new ApacheHttpClientHttpClientAdapter());
        String output = main.getUrl(site);
        System.out.println(output);
    }
}
