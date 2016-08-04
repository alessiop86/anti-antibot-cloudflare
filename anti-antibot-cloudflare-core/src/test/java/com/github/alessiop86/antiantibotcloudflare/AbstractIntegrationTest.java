package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class AbstractIntegrationTest {

    //As today this is a site that has the DDOS protection active 24/7
    private static final String SITE = "https://wuxiaworld.com";
    private static final String EXPECTED_PORTION = "Chinese fantasy novels and light novels";
    private static final String UNEXPECTED_PORTION = "Your browser will redirect to your requested content shortly.";


    private AntiAntiBotCloudFlare antiantibot;

    @Before
    public void setUp() {
        antiantibot = getAntiAntiBotCloudFlareFactory().createInstance();
    }

    @Test
    public void getUrlAgainstWebsite() throws AntiAntibotException {
        String content = antiantibot.getUrl(SITE);

        boolean containsExpectedText = content.contains(EXPECTED_PORTION);
        String doesNotContainExpectedTextErrorMessage = String.format("The content does not contain '%s'.\nContent=", EXPECTED_PORTION, content);
        assertTrue(doesNotContainExpectedTextErrorMessage, containsExpectedText);

        boolean containsUnexpectedText = content.contains(UNEXPECTED_PORTION);
        String containsUnExpectedTextErrorMessage = String.format("The content contain '%s'.\nContent=", UNEXPECTED_PORTION, content);
        assertFalse(containsUnExpectedTextErrorMessage, containsUnexpectedText);
    }

    protected abstract AntiAntiBotCloudFlareFactory getAntiAntiBotCloudFlareFactory();

}
