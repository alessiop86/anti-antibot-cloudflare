package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.exceptions.ParseException;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpResponseAdapter;
import org.jsoup.Jsoup;

import java.io.IOException;

public class ChallengeSolver {

    private static final int REQUIRED_DELAY = 000;//TODO 5000
    private Parser parser;
    private JavascriptEngine javascriptEngine;

    public ChallengeSolver(boolean isAndroid) {
        javascriptEngine = new JavascriptEngine(isAndroid);
    }

    public String solve(HttpResponseAdapter firstReturnedPage) throws AntiAntibotException {
        try {
            requiredDelay();
            parser = new Parser(firstReturnedPage.getContent());

            String submitUrl = getSubmitUrl(firstReturnedPage.getRequestUrl());
            System.out.println(submitUrl);
            return null;
        }
        catch(ParseException e) {
            throw new AntiAntibotException(e.getMessage(),e);
        }
    }

    private void requiredDelay() throws AntiAntibotException {
        try {
            Thread.sleep(REQUIRED_DELAY);
        }
        catch (InterruptedException e) {
            throw new AntiAntibotException(e);
        }
    }
    private String getSubmitUrl(String baseUrl) {
        return baseUrl + "cdn-cgi/l/chk_jschl";
    }


}
