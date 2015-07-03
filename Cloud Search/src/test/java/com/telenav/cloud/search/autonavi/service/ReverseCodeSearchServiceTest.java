package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.generator.AutonaviSearchRequestGenerator;
import com.telenav.cloud.search.autonavi.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import com.telenav.cloud.search.autonavi.utils.http.TelenavHttpClient;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by zfshi on 7/2/2015.
 */
public class ReverseCodeSearchServiceTest {

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
        } catch (AuthenticationBuildException e) {
            e.printStackTrace();
        }
    }
}
