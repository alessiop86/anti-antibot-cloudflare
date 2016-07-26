package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JavascriptEngineTest {

    private JavascriptEngine engine = new JavascriptEngine(true);

    @Test
    public void easyTest() throws AntiAntibotException {
        assertEquals(new Integer(3),engine.solveJavascript("1+2;"));
    }

    @Test
    public void realTest() throws AntiAntibotException {
        assertEquals(new Integer(5332),engine.solveJavascript("var s,t,o,p,b,r,e,a,k,i,n,g,f, RIAxDXb={\"dJvhbZlZfYj\":!+[]+!![]+!![]+!![]+!![]+!![]+!![]};        ;RIAxDXb.dJvhbZlZfYj*=+((!+[]+!![]+[])+(!+[]+!![]+!![]));RIAxDXb.dJvhbZlZfYj*=+((!+[]+!![]+!![]+[])+(!+[]+!![]+!![]));RIAxDXb.dJvhbZlZfYj-=!+[]+!![]+!![]+!![]+!![];RIAxDXb.dJvhbZlZfYj+=+((!+[]+!![]+[])+(!+[]+!![]+!![]+!![]));parseInt(RIAxDXb.dJvhbZlZfYj, 10)"));
    }
}
