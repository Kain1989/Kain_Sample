package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.request.Point;
import com.telenav.cloud.search.autonavi.entity.response.AutonaviResponse;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.generator.AutonaviSearchRequestGenerator;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by zfshi on 7/2/2015.
 */
public class ReverseGeoCodeSearchServiceTest {

    private static Logger logger = Logger.getLogger(ReverseGeocodingSearchService.class);

    private ReverseGeocodingSearchService searchService = ReverseGeocodingSearchService.getInstance();

    @BeforeClass
    public void setupBefore() {
    }

    @Test
    public void test() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generateReverseCodeRequest();
        request.setLocation(new Point(121.39796,31.205883));
        AutonaviResponse response = null;
        try {
            response = searchService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        System.out.println(response.toString());
    }
}
