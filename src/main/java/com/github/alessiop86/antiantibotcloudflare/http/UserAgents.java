package com.github.alessiop86.antiantibotcloudflare.http;

import java.util.Random;

public final class UserAgents {

    private static final String[] USER_AGENTS = {"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"
            ,
//            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36",
//            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36",
//            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0",
//            "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:41.0) Gecko/20100101 Firefox/41.0"
 };
    private static final Random random = new Random();

    private UserAgents() { }

    public static final String getRandom() {
        return USER_AGENTS[random.nextInt(USER_AGENTS.length)];
    }

}
