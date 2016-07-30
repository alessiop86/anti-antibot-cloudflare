package com.github.alessiop86.antiantibotcloudflare.util;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtils {

    public static String extractDomainName(String requestUrl) throws AntiAntibotException {
        URI uri = createUriObjectAndManageException(requestUrl);
        String domain = uri.getHost();
        return stripWwwIfPresent(domain);
    }

    public static String getSubmitUrl(String requestUrl) throws AntiAntibotException {
        URI uri = createUriObjectAndManageException(requestUrl);
        //Note: as it is, it works only on default ports (443 for ssl, 80 for http.
        return String.format("%s://%s/cdn-cgi/l/chk_jschl",uri.getScheme(),stripWwwIfPresent(uri.getHost()));
    }

    private static String stripWwwIfPresent(String domain) {
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    private static URI createUriObjectAndManageException(String uri) throws AntiAntibotException {
        try {
            return new URI(uri);
        }
        catch (URISyntaxException e) {
            throw new AntiAntibotException(String.format("Unable to parse the domain name for the url %s. "
                    + " Please check that you are using latest anti-antibot version available at " +
                    "https://github.com/alessiop86/anti-antibot-cloudflare. If you are using latest version, " +
                    "Please submit a bug report at https://github.com/alessiop86/anti-antibot-cloudflare/issues", uri), e);
        }
    }
}
