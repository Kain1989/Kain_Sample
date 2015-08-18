package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.response.AutonaviResponse;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.generator.AutonaviSearchRequestGenerator;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by zfshi on 7/24/2015.
 */
public class GeoCodeSearchServiceTest {

    private static Logger logger = Logger.getLogger(GeoCodeSearchServiceTest.class);

    private GeoCodeSearchService searchService = GeoCodeSearchService.getInstance();

    @BeforeClass
    public void setupBefore() {
    }

    @Test
    public void test() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generateGeoCodeRequest();
        request.setAddress("江杨南路1290号");
        request.setOneRow(false);
        AutonaviResponse response = null;
        try {
            response = searchService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        System.out.println(response);
    }
}