package com.telenav.cloud.search.service;

import com.telenav.cloud.search.entity.CloudResult;
import com.telenav.cloud.search.entity.Poi;
import com.telenav.cloud.search.utils.config.TelenavConfiguration;
import com.telenav.cloud.search.utils.http.TelenavHttpClient;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by zfshi on 6/12/2015.
 */
public class PoiSearchTest {

    @BeforeClass
    public void beforeClass() {
        System.out.println("this is before class");
    }

    @Test
    public void TestSearch() {
        PoiSearch poiSearch = new PoiSearch();
        poiSearch.setHttpClient(TelenavHttpClient.getInstance());
        poiSearch.setUrlPrefix(TelenavConfiguration.getInstance().getPoiUrlPrefix());

        System.out.println("东方明珠==================================================");
        poiSearch.Keywords = "东方明珠";
        CloudResult result = poiSearch.search();

        if (result != null) {
            for (Poi poi : result.getAts().getPoi_list().getPoi()) {
                System.out.println(poi);
            }
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
