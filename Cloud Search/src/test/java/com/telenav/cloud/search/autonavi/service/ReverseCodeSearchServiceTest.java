package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.generator.AutonaviSearchRequestGenerator;
import com.telenav.cloud.search.autonavi.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import com.telenav.cloud.search.autonavi.utils.http.TelenavHttpClient;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by zfshi on 7/2/2015.
 */
public class ReverseCodeSearchServiceTest {

    private static Logger logger = Logger.getLogger(ReverseCodeSearchService.class);

    private ReverseCodeSearchService searchService = new ReverseCodeSearchService();

    @BeforeClass
    public void setupBefore() {
        searchService.setHttpClient(TelenavHttpClient.getInstance());
        searchService.setUrlPrefix(TelenavConfiguration.getInstance().getReverseCodePrefix());
    }

    @Test
    public void test() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generateReverseCodeRequest();
        try {
            searchService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
