package com.telenav.cloud.search.autonavi.utils.config;

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

    private static final String REVERSE_CODE_URL_PREFIX = "url.web.search.reverse";

    private static final String GEO_CODE_URL_PREFIX = "url.web.search.geocode";

    private static final String DEEP_INFO_URL_PREFIX = "url.web.search.deepinfo";

    private static final String CUSTOMER_NAME = "customer.name";

    private static final String CUSTOMER_KEY = "customer.key";

    private static Object obj = new Object();

    private static TelenavConfiguration instance;

    private Properties props = new Properties();

    public static TelenavConfiguration getInstance() {
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

    public String getReverseCodePrefix() {
        return props.getProperty(REVERSE_CODE_URL_PREFIX);
    }

    public String getSuggestUrlPrefix() {
        return props.getProperty(SUGGEST_URL_PREFIX);
    }

    public String getCustomerName() {
        return props.getProperty(CUSTOMER_NAME);
    }

    public String getGeoCodeUrlPrefix() {
        return props.getProperty(GEO_CODE_URL_PREFIX);
    }

    public String getDeepInfoUrlPrefix() {
        return props.getProperty(DEEP_INFO_URL_PREFIX);
    }

    public String getCustomerKey() {
        return props.getProperty(CUSTOMER_KEY);
    }
}
