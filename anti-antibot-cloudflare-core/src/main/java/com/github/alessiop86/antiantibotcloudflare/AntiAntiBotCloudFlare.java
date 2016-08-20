package com.github.alessiop86.antiantibotcloudflare;

import com.github.alessiop86.antiantibotcloudflare.exceptions.AntiAntibotException;

public interface AntiAntiBotCloudFlare extends AutoCloseable {

    /**
     * Returns a String with the real (not the protection page content) content found at the given url,
     * either it is actually protected or not.
     */
    String getUrl(String url) throws AntiAntibotException;

    /**
     * Returns a byte array with the real (not the protection page content) content found at the given url,
     * either it is actually protected or not.
     */
    byte[] getByteArrayFromUrl(String url) throws AntiAntibotException;

    @Override
    void close() throws Exception;
}
