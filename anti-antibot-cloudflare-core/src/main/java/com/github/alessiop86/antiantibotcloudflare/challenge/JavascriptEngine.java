package com.github.alessiop86.antiantibotcloudflare.challenge;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class JavascriptEngine {

    public enum IsAndroid { ANDROID, NOT_ANDROID }

    private final IsAndroid isAndroid;

    public JavascriptEngine(IsAndroid isAndroid) {
        this.isAndroid = isAndroid;
    }

    public Integer solveJavascript(String javascriptSource) throws AntiAntibotException {
        Context rhino = Context.enter();
        if (isAndroid()) {
            makeAndroidCompatible(rhino);
        }
        try {
            Scriptable scope = rhino.initStandardObjects();
            Object javascriptResult = rhino.evaluateString(scope, javascriptSource,"challenge", 0, null);
            return (int) Float.parseFloat(javascriptResult.toString());
        }
        catch(Exception e) {
            throw new AntiAntibotException("Error solving the javascript challenge", e);
        }
        finally {
            Context.exit();
        }
    }

    private boolean isAndroid() {
        return isAndroid == IsAndroid.ANDROID;
    }

    private void makeAndroidCompatible(Context rhino) {
        rhino.setOptimizationLevel(-1);
    }
}
