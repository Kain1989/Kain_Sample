package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.poi_generate.AutonaviResponse;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.generator.AutonaviSearchRequestGenerator;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by zfshi on 6/12/2015.
 */
public class SuggestionSearchTest {

    private static Logger logger = Logger.getLogger(SuggestionSearchTest.class);

    SuggestionSearchService suggestionService = SuggestionSearchService.getInstance();

    @BeforeClass
    public void beforeClass() {
    }

    @Test
    public void TestSearch() {
        AutonaviSearchRequest request = AutonaviSearchRequestGenerator.generateInputSuggestionRequest();
//        request.setQueryType(QueryType.ID);
        request.setWords("sh");
//        request.setLanguage("zh");
        AutonaviResponse response = null;
        try {
            response = suggestionService.search(request);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        this.printResult(response);
    }

    private void printResult(AutonaviResponse response) {
        if (response != null && response.getAts().getTipList() != null) {
            for (String suggestion : response.getAts().getTipList().getTips()) {
                System.out.println(suggestion);
            }
        }
    }

    @AfterClass
    public void afterClass() {
        System.out.println("this is after class");
    }

}
