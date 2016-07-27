package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.ParseException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


import static org.junit.Assert.assertEquals;


public class ParserTest {

    private static final String INPUT_FILE = "input.txt";
    private static final String input = readTestInput();

    private static String readTestInput() {

        String result = "";

        ClassLoader classLoader = ParserTest.class.getClassLoader();
        try {
            return IOUtils.toString(classLoader.getResourceAsStream(INPUT_FILE));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Parser parser;

    @Before
    public void setUp() throws ParseException {
        parser = new Parser(input);
    }


    @Test
    public void getJsChallenge() {
        //TODO: can I remove empty spaces???
        assertEquals("var s,t,o,p,b,r,e,a,k,i,n,g,f, deXyqUX={\"QLH\":+((+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]))};        ;deXyqUX.QLH*=+((+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]));deXyqUX.QLH-=+((!+[]+!![]+!![]+!![]+[])+(!+[]+!![]+!![]+!![]+!![]+!![]));deXyqUX.QLH*=!+[]+!![]+!![];deXyqUX.QLH*=+((+!![]+[])+(!+[]+!![]+!![]+!![]+!![]));deXyqUX.QLH+=+((!+[]+!![]+!![]+!![]+!![]+[])+(+[]));deXyqUX.QLH+=+((!+[]+!![]+!![]+!![]+[])+(+!![]));deXyqUX.QLH*=!+[]+!![]+!![];deXyqUX.QLH*=+((!+[]+!![]+[])+(!+[]+!![]+!![]+!![]));parseInt(deXyqUX.QLH, 10)",
                parser.getJsChallenge());
    }

}

