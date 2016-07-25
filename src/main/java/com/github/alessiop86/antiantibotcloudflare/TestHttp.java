package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.http.adapters.OkHttpHttpClientAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TestHttp {

    final static String site = "https://www.pokevision.com";
    
    public static void main(String[] args) throws Exception {
        AntiAntiBotCloudFlare main = new AntiAntiBotCloudFlare(new OkHttpHttpClientAdapter());
        String output = main.getUrl(site);
        System.out.println(output);
    }
}
