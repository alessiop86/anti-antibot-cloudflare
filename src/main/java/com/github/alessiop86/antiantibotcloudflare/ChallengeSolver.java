package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import com.github.alessiop86.antiantibotcloudflare.http.HttpResponse;

public class ChallengeSolver {

    private JavascriptEngine javascriptEngine;

    public ChallengeSolver(JavascriptEngine javascriptEngine) {
        javascriptEngine = javascriptEngine;
    }

    public Integer solve(String challenge, HttpResponse firstReturnedPage) throws AntiAntibotException {
        Integer jsChallengeResult = javascriptEngine.solveJavascript(challenge);
        Integer domainNameLength = firstReturnedPage.getDomainName().length();
        return jsChallengeResult + domainNameLength;
    }

}
