package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.exceptions.ParseException;
import com.github.alessiop86.antiantibotcloudflare.http.adapters.HttpResponseAdapter;
import org.jsoup.Jsoup;

import java.io.IOException;

public class ChallengeSolver {

    private JavascriptEngine javascriptEngine;

    public ChallengeSolver(JavascriptEngine javascriptEngine) {
        javascriptEngine = javascriptEngine;
    }

    public Integer solve(String challenge, HttpResponseAdapter firstReturnedPage) throws AntiAntibotException {
        Integer jsChallengeResult = javascriptEngine.solveJavascript(challenge);
        Integer domainNameLength = firstReturnedPage.getDomainName().length();
        return jsChallengeResult + domainNameLength;
    }

}
