package com.telenav.cloud.search.utils.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by zfshi on 6/11/2015.
 */
public class TelenavConfiguration {

    private static Logger logger = Logger.getLogger(TelenavConfiguration.class);

    private static final String PROPERTIES_FILE = "config/config.properties";

    private static final String POI_URL_PREFIX = "url.web.search.poi";

    private static final String SUGGEST_URL_PREFIX = "url.web.search.suggest";

    private static TelenavConfiguration instance;

    private Properties props = new Properties();

    public static TelenavConfiguration getInstance() {
        Object obj = new Object();
        synchronized (obj) {
            if (instance == null) {
                instance = new TelenavConfiguration();
            }
        }
        return instance;
    }

    private TelenavConfiguration() {
        logger.info("Reading " + PROPERTIES_FILE);
        try {
            props.load(getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {

            logger.error(e.getMessage(), e);
        }
    }

    public String getPoiUrlPrefix() {
        return props.getProperty(POI_URL_PREFIX);
    }

    public String getSuggestUrlPrefix() {
        return props.getProperty(SUGGEST_URL_PREFIX);
    }
}
