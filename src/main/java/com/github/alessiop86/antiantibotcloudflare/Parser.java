package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

    private static final String CHALLENGE_FORM_SELECTOR = "#challenge-form";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String INPUT_FIELD_1 = "pass";
    private static final String INPUT_FIELD_2 = "jschl_vc";

    private final String field1;
    private final String field2;

    public String getField2() {
        return field2;
    }

    public String getField1() {
        return field1;
    }


    public Parser(String document) throws ParseException {
        Element form = findForm(document);
        Elements elementsMatchingField1 = form.getElementsByAttributeValue(NAME_ATTRIBUTE,INPUT_FIELD_1);
        Elements elementsMatchingField2 = form.getElementsByAttributeValue(NAME_ATTRIBUTE,INPUT_FIELD_2);
        if (elementsMatchingField1.size() != 1 || elementsMatchingField2.size() != 1) {
            throw new ParseException("The challenge form format has changed. New format:" + form.html());
        }
        field1 = elementsMatchingField1.attr(VALUE_ATTRIBUTE);
        field2 = elementsMatchingField2.attr(VALUE_ATTRIBUTE);
    }

    private Element findForm(String document) throws ParseException {
        Elements forms = Jsoup.parse(document).select(CHALLENGE_FORM_SELECTOR);
        if (forms.size() == 0)
            throw new ParseException("The challenge format has changed. New format:" + document);
        return forms.get(0);
    }


    public String getJsChallenge() {
        return null;
    }
}
