package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.generator.AutonaviSearchRequestGenerator;
import com.telenav.cloud.search.autonavi.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import com.telenav.cloud.search.autonavi.utils.http.TelenavHttpClient;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by zfshi on 6/12/2015.
 */
public class PoiSearchTest {

    PoiSearchService poiService = new PoiSearchService();

    @BeforeClass
    public void setupBefore() {
        poiService.setHttpClient(TelenavHttpClient.getInstance());
        poiService.setUrlPrefix(TelenavConfiguration.getInstance().getPoiUrlPrefix());
    }

    @Test
    public void testKeywordsSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setKeywords("东方明珠");

        // 121.243045,31.397778 嘉定北
//        latitude = 31.397778;
//        longitude = 121.243045;

        // 121.521646,31.239305  东昌路
        request.setLatitude(31.239305);
        request.setLongitude(121.521646);

//       121.16845,31.294162   安亭
//        latitude = 31.294162;
//        longitude = 121.16845;

        try {
            poiService.search(request);
        } catch (AuthenticationBuildException e) {
            e.printStackTrace();
        }

    }

//    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp="NullPoint")
//    public void Test() {
//
//    }

    @AfterClass
    public void afterClass() {
        System.out.println("this is after class");
    }
}
