package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.http.adapters.okhttp.OkHttpHttpClientAdapter;

public class TestOkHttp {

    static  String site = "https://wuxiaworld.com";

    public static void main(String[] args) throws Exception {
        System.out.println(TestOkHttp.class);
        //site = "http://localhost/input2.txt";
        AntiAntiBotCloudFlare main = new AntiAntiBotCloudFlare(new OkHttpHttpClientAdapter());
        //AntiAntiBotCloudFlare main = new AntiAntiBotCloudFlare(new ApacheHttpClientHttpClientAdapter());
        String output = main.getUrl(site);
        System.out.println(output);
    }
}
