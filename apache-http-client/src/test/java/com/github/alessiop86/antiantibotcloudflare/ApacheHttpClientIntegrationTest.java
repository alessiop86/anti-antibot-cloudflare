package com.github.alessiop86.antiantibotcloudflare;

public class ApacheHttpClientIntegrationTest extends AbstractIntegrationTest {

    @Override
    protected ApacheHttpAntiAntibotCloudFlareFactory getAntiAntiBotCloudFlareFactory() {
        return new ApacheHttpAntiAntibotCloudFlareFactory();
    }
}
