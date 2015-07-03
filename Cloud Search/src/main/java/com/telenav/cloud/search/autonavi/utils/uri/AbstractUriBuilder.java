package com.telenav.cloud.search.autonavi.utils.uri;

import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by zfshi on 6/4/2015.
 */
public class AbstractUriBuilder {

    private static Logger logger = Logger.getLogger(AbstractUriBuilder.class);

    public static URI build(String prefix, Map<String, Object> params) {
        URIBuilder builder = new URIBuilder();
        builder.setPath(prefix);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.setParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }
        try {
            return builder.build();
        } catch (URISyntaxException e) {
            logger.error("Could not build the URI.", e);
        }
        return null;
    }
}
