package com.downloader.animedownloader.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uriSchemeHandler.CouldNotOpenUriSchemeHandler;
import uriSchemeHandler.URISchemeHandler;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class MagnetLinkHandler {
    private static final Logger logger = LoggerFactory.getLogger(MagnetLinkHandler.class);

    public void open(String magnetLink) throws CouldNotOpenUriSchemeHandler {
        URI uri = null;
        try {
            uri = new URI(magnetLink);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
        URISchemeHandler uriSchemeHandler = new URISchemeHandler();
        uriSchemeHandler.open(uri);
    }
}
