package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.poi_generate.AutonaviResponse;
import com.telenav.cloud.search.autonavi.generator.AutonaviSearchRequestGenerator;
import com.telenav.cloud.search.autonavi.model.type.QueryType;
import com.telenav.cloud.search.autonavi.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.request.Point;
import com.telenav.cloud.search.autonavi.request.Rectangle;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import com.telenav.cloud.search.autonavi.utils.http.TelenavHttpClient;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by zfshi on 6/12/2015.
 */
public class PoiSearchTest {

    private static Logger logger = Logger.getLogger(PoiSearchTest.class);

    PoiSearchService poiService = new PoiSearchService();

    @BeforeClass
    public void setupBefore() {
        logger.info("Initialize Poi search service successful");
        poiService.setHttpClient(TelenavHttpClient.getInstance());
        poiService.setUrlPrefix(TelenavConfiguration.getInstance().getPoiUrlPrefix());
    }

    @Test
    public void testIdSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.ID);
        request.setId("B00150F6D6");
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testKeywordsSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.KEYWORDS);
        request.setKeywords("东方明珠");
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testLatlonSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.LATLON);
        request.setKeywords("东方明珠");
        // 121.243045,31.397778 嘉定北
        // 121.521646,31.239305  东昌路
        // 121.16845,31.294162   安亭
        request.setLatitude(31.239305);
        request.setLongitude(121.521646);
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testRectangleSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.RECTANGLE);
        Point topLeft = new Point(121.488858,31.24964);// 天潼路
        Point bottomRight = new Point(121.522957,31.236179);// 商城路
        request.setRectangle(new Rectangle(topLeft, bottomRight));
//        request.setKeywords("麦当劳");
        request.setCategory("050302");
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testCenterSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.CENTER);
        request.setCenter("东方明珠");
        request.setKeywords("酒店");
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
