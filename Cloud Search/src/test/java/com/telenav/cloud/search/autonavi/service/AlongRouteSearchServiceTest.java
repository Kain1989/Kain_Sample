package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.Point;
import com.telenav.cloud.search.autonavi.entity.response.AutonaviResponse;
import com.telenav.cloud.search.autonavi.generator.AutonaviSearchRequestGenerator;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfshi on 7/28/2015.
 */
public class AlongRouteSearchServiceTest {

    private static Logger logger = Logger.getLogger(GeoCodeSearchServiceTest.class);

    private AlongRouteSearchService searchService = AlongRouteSearchService.getInstance();

    @BeforeClass
    public void setupBefore() {
    }

    @Test
    public void test() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generateAlongRouteRequest();
        List<Point> pointList = new ArrayList<Point>();
        pointList.add(new Point(116.34864583333334, 39.870559722222225));
        pointList.add(new Point(116.34858083333333, 39.86936194444444));
        pointList.add(new Point(116.34850722222222, 39.86864166666667));
        pointList.add(new Point(116.3484075, 39.86756111111111));
        pointList.add(new Point(116.34836, 39.86708388888889));
        pointList.add(new Point(116.34822555555556, 39.865903333333335));
        pointList.add(new Point(116.34813027777778, 39.865161388888886));
        pointList.add(new Point(116.34806527777778, 39.864493055555556));
        pointList.add(new Point(116.3479263888889, 39.8634125));
        pointList.add(new Point(116.3475663888889, 39.86019638888889));
        pointList.add(new Point(116.34734083333333, 39.858382222222225));
        pointList.add(new Point(116.34730194444444, 39.858087222222224));
        pointList.add(new Point(116.34706333333334, 39.85613861111111));
        pointList.add(new Point(116.34703305555556, 39.85583916666667));
        pointList.add(new Point(116.34664694444444, 39.852545));
        pointList.add(new Point(116.34661666666666, 39.85227166666667));
        pointList.add(new Point(116.34639972222222, 39.85042277777778));
        pointList.add(new Point(116.34622611111111, 39.84897333333333));
        pointList.add(new Point(116.3460525, 39.84750638888889));
        pointList.add(new Point(116.34593555555556, 39.846443055555554));
        pointList.add(new Point(116.34581416666667, 39.84514111111111));
        pointList.add(new Point(116.34576222222222, 39.843136111111114));
        pointList.add(new Point(116.34576222222222, 39.84179944444445));
        pointList.add(new Point(116.34576222222222, 39.84110944444444));
        pointList.add(new Point(116.34576222222222, 39.83850527777778));
        pointList.add(new Point(116.34576222222222, 39.838093055555554));
        request.setGeoLine(pointList);

        List<String> categoryList = new ArrayList<String>();
        categoryList.add("160300");
//        categoryList.add("010100");
        request.setCategoryList(categoryList);
        AutonaviResponse response = null;
        try {
            response = searchService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        System.out.println(response);
    }
}

