package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.response.AutonaviResponse;
import com.telenav.cloud.search.autonavi.entity.response.Poi;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.Point;
import com.telenav.cloud.search.autonavi.entity.request.Rectangle;
import com.telenav.cloud.search.autonavi.generator.AutonaviSearchRequestGenerator;
import com.telenav.cloud.search.autonavi.model.type.QueryType;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfshi on 6/12/2015.
 */
public class PoiSearchTest {

    private static Logger logger = Logger.getLogger(PoiSearchTest.class);

    PoiSearchService poiService = PoiSearchService.getInstance();

    @BeforeClass
    public void setupBefore() {
        logger.info("Initialize Poi search service successful");
//        poiService.setHttpClient(TelenavHttpClient.getInstance());
//        poiService.setUrlPrefix(TelenavConfiguration.getInstance().getPoiUrlPrefix());
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
        this.printResult(response);
    }

    @Test
    public void testIdSearch_City() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.ID);
        request.setId("B00150F6D6");
        request.setCity("上海");
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.printResult(response);
    }

    @Test
    public void testKeywordsSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.KEYWORDS);
        request.setKeywords("xianxialu333");
        request.setLocation(new Point(121.521646, 31.239305));
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.printResult(response);
    }

    @Test
    public void testKeywordsSearch_Category() {
        // Seems need specify city
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.KEYWORDS);
        List<String> categoryList = new ArrayList<String>();
        categoryList.add("050302");
        request.setCategoryList(categoryList);
        request.setCity("上海");
//        request.setKeywords("东方明珠");
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.printResult(response);
    }

    @Test
    public void testKeywordsSearch_Latlon() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.KEYWORDS);
//        request.setCategory("050302");
        request.setLocation(new Point(121.521646, 31.239305));
        request.setKeywords("停车场");
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.printResult(response);
    }

    @Test
    public void testLatlonSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.LATLON);
        request.setKeywords("东方明珠");
        // 121.243045,31.397778 嘉定北
        // 121.521646,31.239305  东昌路
        // 121.16845,31.294162   安亭
        request.setLocation(new Point(121.521646, 31.239305));
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.printResult(response);
    }

    @Test
    public void testLatlonSearch_Category() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.LATLON);
        // 121.243045,31.397778 嘉定北
        // 121.521646,31.239305  东昌路
        // 121.16845,31.294162   安亭
        request.setLocation(new Point(121.16845, 31.294162));
        request.setRange(50000L);
        request.setKeywords("希尔顿");
//        List<String> categoryList = new ArrayList<String>();
//        categoryList.add("050302");
//        request.setCategoryList(categoryList);
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.printResult(response);
    }

    @Test
    public void testRectangleSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.RECTANGLE);
        Point topLeft = new Point(121.488858,31.24964);// 天潼路
        Point bottomRight = new Point(121.522957,31.236179);// 商城路
        request.setRectangle(new Rectangle(topLeft, bottomRight));
//        request.setKeywords("麦当劳");
        List<String> categoryList = new ArrayList<String>();
        categoryList.add("050302");
        request.setCategoryList(categoryList);
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.printResult(response);
    }

    @Test
    public void testCenterSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generatePoiKeywordsRequest();
        request.setQueryType(QueryType.CENTER);
        request.setCenter("东方明珠");
        request.setKeywords("apple");
        AutonaviResponse response = null;
        try {
            response = poiService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.printResult(response);
    }

//    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp="NullPoint")
//    public void Test() {
//
//    }

    private void printResult(AutonaviResponse response) {
        if (response != null && response.getAts().getPoiList() != null) {
            for (Poi poi : response.getAts().getPoiList().getPois()) {
                System.out.println(poi.toString());
            }
        }
    }

    @AfterClass
    public void afterClass() {
    }
}
