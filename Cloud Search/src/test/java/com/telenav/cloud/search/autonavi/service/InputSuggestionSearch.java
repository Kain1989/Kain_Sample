package com.telenav.cloud.search.autonavi.service;

import org.testng.annotations.AfterClass;

/**
 * Created by zfshi on 6/12/2015.
 */
public class InputSuggestionSearch {

//    @BeforeClass
//    public void beforeClass() {
//        System.out.println("this is before class");
//    }
//
//    @Test
//    public void TestSearch() {
//        ApplicationContext context = new ClassPathXmlApplicationContext(
//                new String[]{"spring/appContext.xml"});
//
//
//        InputSuggestion suggestion = (InputSuggestion) context.getBean("inputSuggestion");
//        suggestion.Words = "东方明珠";
//        CloudResult result = suggestion.search();
//        System.out.println(result.getAts().getTip_list());
//
//        suggestion.Words = "仙霞";
//        suggestion.City = "上海";
//        System.out.println(suggestion.search().getAts().getTip_list());
//    }

//    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp="NullPoint")
//    public void Test() {
//
//    }

    @AfterClass
    public void afterClass() {
        System.out.println("this is after class");
    }

}
