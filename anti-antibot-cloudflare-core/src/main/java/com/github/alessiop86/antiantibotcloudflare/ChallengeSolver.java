package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;
import com.github.alessiop86.antiantibotcloudflare.util.UrlUtils;

public class ChallengeSolver {

    private final JavascriptEngine javascriptEngine;

    public ChallengeSolver(JavascriptEngine javascriptEngine) {
        this.javascriptEngine = javascriptEngine;
    }

    public Integer solve(String challenge, HttpResponse firstReturnedPage) throws AntiAntibotException {
        Integer jsChallengeResult = javascriptEngine.solveJavascript(challenge);
        String domain = UrlUtils.extractDomainName(firstReturnedPage.getRequestUrl());
        System.out.println(String.format("challenge=%s, domain=%s, length=%s", jsChallengeResult,domain, domain.length()));
        return jsChallengeResult + domain.length();
    }

}
