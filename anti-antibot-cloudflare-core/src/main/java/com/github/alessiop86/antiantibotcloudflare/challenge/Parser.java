package com.github.alessiop86.antiantibotcloudflare.challenge;

import com.github.alessiop86.antiantibotcloudflare.exceptions.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private static final String BASE_REGEX =	"setTimeout\\(function\\(\\)\\{\\s+(var s,t,o,p,b,r,e,a,k,i,n,g,f.+?\\r?\\n[\\s\\S]+?a\\.value =.+?)\\r?\\n";
    private static final Pattern patternRegex = Pattern.compile(BASE_REGEX);

    private static final String CHALLENGE_FORM_SELECTOR = "#challenge-form";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String VALUE_ATTRIBUTE = "value";
    public static final String PASS_FIELD = "pass";
    public static final String JSCHL_VC_FIELD = "jschl_vc";

    private final ParsedChallengePage parseResult;

    public Parser(String document) throws ParseException {

        try {
            Element form = findForm(document);
            Elements elementsMatchingField1 = form.getElementsByAttributeValue(NAME_ATTRIBUTE, PASS_FIELD);
            Elements elementsMatchingField2 = form.getElementsByAttributeValue(NAME_ATTRIBUTE, JSCHL_VC_FIELD);
            if (elementsMatchingField1.size() != 1 || elementsMatchingField2.size() != 1) {
                throw new ParseException("The challenge form format has changed. New format:" + form.html());
            }

            String field1 = elementsMatchingField1.attr(VALUE_ATTRIBUTE);
            String field2 = elementsMatchingField2.attr(VALUE_ATTRIBUTE);
            String jsChallenge = extractJsChallenge(document);

            parseResult = new ParsedChallengePage(field1,field2,jsChallenge);
        }
        catch(ParseException e) {
            throw e;
        }
        catch(Exception e) {
            throw new ParseException(e);
        }
    }

    private String extractJsChallenge(String document) {
        Matcher matcher = patternRegex.matcher(document);
        matcher.find();
        String step1 =  matcher.group(1);
        String step2 = step1.replaceFirst("a\\.value = (parseInt\\(.+?\\)).+","$1");
        String step3 = step2.replaceAll("\\s{3,}[a-z](?: = |\\.).+","");
        return step3.replaceFirst("[\\n\\\\']","");
    }

    private Element findForm(String document) throws ParseException {
        Elements forms = Jsoup.parse(document).select(CHALLENGE_FORM_SELECTOR);
        if (forms.size() == 0)
            throw new ParseException("The challenge format has changed. New format:" + document);
        return forms.get(0);
    }

    public ParsedChallengePage getParsedProtectionResponse() {
        return parseResult;
    }



    public class ParsedChallengePage {

        private final String pass1;
        private final String jschl_vc;
        private final String jsChallenge;


        public ParsedChallengePage(String pass1, String jschl_vc, String jsChallenge) {
            this.jsChallenge = jsChallenge;
            this.pass1 = pass1;
            this.jschl_vc = jschl_vc;
        }

        public String getPass1() {
            return pass1;
        }

        public String getJschl_vc() {
            return jschl_vc;
        }

        public String getJsChallenge() {
            return jsChallenge;
        }
    }

}
