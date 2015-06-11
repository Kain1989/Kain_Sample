package com.telenav.search.index.test;

import com.telenav.entity.bindings.android.EntityServiceConfig;
import com.telenav.entity.bindings.android.embedded.EmbeddedEntityService;
import com.telenav.entity.service.model.common.GeoPoint;
import com.telenav.entity.service.model.v4.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfshi on 6/8/2015.
 */
public class Tester {
    private EmbeddedEntityService service = null;
    private static String apiKey = "60c498cd-b202-4bf5-bb08-1265320245a3";
    private static String apiSecret = "06c81aa5-feb3-4816-b70e-cb5fab8877ab";

    public boolean loadData(String dataPath) {
        try {
            EntityServiceConfig config = EntityServiceConfig.createEmbeddedEntityServiceConfig(apiKey, apiSecret, dataPath);
            service = new EmbeddedEntityService(config);
        }
        catch(Exception e) {
            return false;
        }

        return true;
    }

    public void testOneBoxSearch(String strQuery, String strLocation) {
        testOneBoxSearch(strQuery, strLocation, "zh-CN" );
    }
    public void testOneBoxSearch(String strQuery, String strLocation, String strLocale) {
        EntitySearchRequest request = new EntitySearchRequest();
        request.setLocale(strLocale);
        request.setLocation(convertToGeoPoint(strLocation));
        request.setQuery(strQuery);
        //request.setTndebug(true);
        request.setLimit(20);
        EntitySearchResponse response = service.search(request, null);
        String strResponse = JsonConverter.toPrettyJson(response);
        System.out.println("OneBox Search case: query[" + strQuery + "] location[" + strLocation + "]");
        System.out.println(JsonConverter.toPrettyJson(request));
        System.out.println(strResponse);
        System.out.println();
    }

    public void testCategorySearch(String strCategory, String strLocation) {
        testCategorySearch(strCategory, strLocation, "zh-CN");
    }
    public void testCategorySearch(String strCategory, String strLocation, String strLocale) {
        EntitySearchRequest request = new EntitySearchRequest();
        request.setLocale(strLocale);
        request.setLocation(convertToGeoPoint(strLocation));
        String[] catIdArray = strCategory.split(",");
        List<String> cats = new ArrayList<String>();
        for (String str: catIdArray)
            cats.add(str);
        request.setCategories(cats);
        request.setTndebug(true);
        request.setLimit(20);
        EntitySearchResponse response = service.search(request, null);
        String strResponse = JsonConverter.toJson(response);
        System.out.println("OneBox Search case: category[" + strCategory + "] location[" + strLocation + "]");
        System.out.println(JsonConverter.toJson(request));
        System.out.println(strResponse);
        System.out.println();
    }


    public void testOneBoxSuggestion(String strQuery, String strLocation) {
        testOneBoxSuggestion(strQuery, strLocation, "zh-CN");
    }
    public void testOneBoxSuggestion(String strQuery, String strLocation, String strLocale) {
        EntitySuggestionRequest request = new EntitySuggestionRequest();
        request.setLocale(strLocale);
        request.setLocation(convertToGeoPoint(strLocation));
        request.setQuery(strQuery);
        request.setLimit(20);
        EntitySuggestionResponse response =  service.suggestions(request, null);
        String strResponse = JsonConverter.toJson(response);
        System.out.println("OneBox Suggestion case: query[" + strQuery + "] location[" + strLocation + "]");
        System.out.println(JsonConverter.toJson(request));
        System.out.println(strResponse);
        System.out.println();
    }
    public void testTwoBoxSearch(String strAdmin, String strQuery, String strLocation) {
        testTwoBoxSearch(strAdmin, strQuery, strLocation, "zh-CN");
    }
    public void testTwoBoxSearch(String strAdmin, String strQuery, String strLocation, String strLocale) {
        EntitySearchRequest request = new EntitySearchRequest();
        request.setLocale(strLocale);
        request.setLocation(convertToGeoPoint(strLocation));
        String strTwoBoxQuery = "BRAND,DOOR,STREET,XSTREET=" + strQuery;
        strTwoBoxQuery += ";ADMIN=" + strAdmin;
        request.setQuery(strTwoBoxQuery);
        //request.setTndebug(true);
        request.setLimit(20);
        EntitySearchResponse response = service.search(request, null);

        String strResponse = JsonConverter.toPrettyJson(response);
        System.out.println("TwoBox Search case: admin[" + strAdmin + "] query[" + strQuery + "] location[" + strLocation + "]");
        System.out.println(JsonConverter.toPrettyJson(request));
        System.out.println(strResponse);
        System.out.println();
    }

    public void testCategoryTree() {
        testCategoryTree("zh-CN");
    }

    public void testCategoryTree(String strLocal) {
        EntityCategoryRequest categoryReq= new EntityCategoryRequest();
        categoryReq.setLocale(strLocal);
        EntityCategoryResponse response= service.categories(categoryReq, null);
        String strResponse = JsonConverter.toJson(response);
        System.out.println("Category tree case: local[" + strLocal + "]");
        System.out.println(JsonConverter.toJson(categoryReq));
        System.out.println(strResponse);
        System.out.println();
    }

    public void testTwoBoxSuggestion(String strAdmin, String strQuery, String strLocation) {
        testTwoBoxSuggestion(strAdmin, strQuery, strLocation, "zh-CN");
    }
    public void testTwoBoxSuggestion(String strAdmin, String strQuery, String strLocation, String strLocale) {
        EntitySuggestionRequest request = new EntitySuggestionRequest();
        request.setLocale(strLocale);
        request.setLocation(convertToGeoPoint(strLocation));
        String strTwoBoxQuery = "BRAND,DOOR,STREET,XSTREET=" + strQuery;
        strTwoBoxQuery += ";ADMIN=" + strAdmin;
        request.setQuery(strTwoBoxQuery);
        //request.setTndebug(true);
        request.setLimit(20);

        EntitySuggestionResponse response =  service.suggestions(request, null);

        String strResponse = JsonConverter.toJson(response);
        System.out.println("TwoBox Suggestion case: admin[" + strAdmin + "] query[" + strQuery + "] location[" + strLocation + "]");
        System.out.println(JsonConverter.toJson(request));
        System.out.println(strResponse);
        System.out.println();
    }

    public void testDetail(String id) {
        testDetail(id, "zh-CN");
    }

    public void testDetail(String id, String strLocale) {
        EntityDetailRequest request = new EntityDetailRequest();
        request.setLocale(strLocale);
        List<String> entityIds = new ArrayList<String>();
        entityIds.add(id);
        request.setEntityIds(entityIds);
        EntityDetailResponse response = service.detail(request, null);
        String strResponse = JsonConverter.toJson(response);
        System.out.println("Detail case: entity id[" + id + "]");
        System.out.println(JsonConverter.toJson(request));
        System.out.println(strResponse);
        System.out.println();
    }

    public void testRGC(String strLocation) {
        testRGC(strLocation, "zh-CN");
    }
    public void testRGC(String strLocation, String strLocale) {
        EntityRgcRequest request = new EntityRgcRequest();
        request.setLocale(strLocale);
        request.setLocation(convertToGeoPoint(strLocation));
        request.setTndebug(true);
        EntityDetailResponse response = service.rgc(request, null);

        String strResponse = JsonConverter.toJson(response);
        System.out.println("Rgc case: location[" + strLocation + "]");
        System.out.println(JsonConverter.toJson(request));
        System.out.println(strResponse);
        System.out.println();
    }

    public void testGetLowerLevelAdmin(String strCountry, String strState, String strCity) {
        testGetLowerLevelAdmin(strCountry, strState, strCity, "zh-CN");
    }

    public void testGetLowerLevelAdmin(String strCountry, String strState, String strCity, String strLocale) {
        EntitySearchRequest request = new EntitySearchRequest();
        request.setLocale(strLocale);
        request.setLimit(100);
        request.setIntent(Intent.lower_level_admin);
        request.setLocation(convertToGeoPoint("31.21219,121.43386"));

        Filters filter = new Filters();
        if (strCountry != null && !strCountry.isEmpty())
            filter.setCountry(strCountry);
        if (strState != null && !strState.isEmpty())
            filter.setState(strState);
        if (strCity != null && !strCity.isEmpty())
            filter.setCity(strCity);

        request.setFilters(filter);
        request.setTndebug(true);

        EntitySearchResponse response = service.search(request, null);
        String strResponse = JsonConverter.toPrettyJson(response);
        System.out.println("Get lower level admin case: country[" + strCountry + "] state[" + strState + "] city[" + strCity + "]");
        System.out.println(JsonConverter.toPrettyJson(request));
        System.out.println(strResponse);
        System.out.println();
    }

    public GeoPoint convertToGeoPoint(String strLocation) {
        String[] array = strLocation.split(",");
        if (array.length != 2) {
            return null;
        }
        GeoPoint pt = new GeoPoint();
        pt.setLatitude(Double.parseDouble(array[0]));
        pt.setLongitude(Double.parseDouble(array[1]));
        return pt;
    }

    public static void RunOneBoxSearchCasesCHI(Tester test) {

    }

    public static void RunOneBoxSearchCasesENG(Tester test) {

    }

    public static void RunOneBoxSearchCasesSZM(Tester test) {

    }


    public static void RunTwoBoxSearchCasesSZM(Tester test) {
        test.testTwoBoxSearch("上海", "xxl@gbl", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "xxl333", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "WJDS", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "NHGJ", "31.206170,121.395652");
        test.testTwoBoxSearch("北京", "DFMZT", "32.691859,120.361652");

        test.testTwoBoxSearch("", "xxl@gbl", "31.206170,121.395652");
        test.testTwoBoxSearch("", "xxl333", "31.206170,121.395652");
        test.testTwoBoxSearch("", "DFWJ", "31.206170,121.395652");
        test.testTwoBoxSearch("", "NHGJ", "31.206170,121.395652");
        test.testTwoBoxSearch("", "DFMZT", "32.691859,120.361652");
    }


    public static void RunTwoBoxSearchCasesENG(Tester test) {
        test.testTwoBoxSearch("shanghai", "xianxia rd & gubei rd", "31.206170,121.395652", "en_US");
        test.testTwoBoxSearch("shanghai", "333 xianxia rd", "31.206170,121.395652", "en_US");
        test.testTwoBoxSearch("shanghai", "east", "31.206170,121.395652", "en_US");
        test.testTwoBoxSearch("shanghai", "xianxia rd", "31.206170,121.395652", "en_US");
        test.testTwoBoxSearch("shanghai", "gas station", "31.206170,121.395652", "en_US");
        //test.testTwoBoxSearch("guangzhou", "东方明珠塔", "32.691859,120.361652", "en_US");

        test.testTwoBoxSearch("", "xianxia rd & gubei rd", "31.206170,121.395652", "en_US");
        test.testTwoBoxSearch("", "333 xianxia rd", "31.206170,121.395652", "en_US");
        test.testTwoBoxSearch("", "east", "31.206170,121.395652", "en_US");
        test.testTwoBoxSearch("", "xianxia rd", "31.206170,121.395652", "en_US");
        test.testTwoBoxSearch("", "gas station", "31.206170,121.395652", "en_US");
        //test.testTwoBoxSearch("", "东方明珠塔", "32.691859,120.361652", "en_US");
    }

    public static void RunTwoBoxSearchCasesCHI(Tester test) {

        test.testTwoBoxSearch("上海", "仙霞路古北路", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "仙霞路333号", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "东方维京", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "内环高架", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "加油站", "31.206170,121.395652");
        test.testTwoBoxSearch("北京", "东方明珠塔", "32.691859,120.361652");

        test.testTwoBoxSearch("", "仙霞路古北路", "31.206170,121.395652");
        test.testTwoBoxSearch("", "仙霞路333号", "31.206170,121.395652");
        test.testTwoBoxSearch("", "东方维京", "31.206170,121.395652");
        test.testTwoBoxSearch("", "内环高架", "31.206170,121.395652");
        test.testTwoBoxSearch("", "加油站", "31.206170,121.395652");
        test.testTwoBoxSearch("", "东方明珠塔", "32.691859,120.361652");
    }



    public static void RunOneBoxSuggestionCasesCHI(Tester test) {

    }

    public static void RunOneBoxSuggestionCasesENG(Tester test) {

    }

    public static void RunOneBoxSuggestionCasesSZM(Tester test) {

    }

    public static void RunTwoBoxSuggestionCasesCHI(Tester test) {
        test.testTwoBoxSearch("上海", "仙霞路古北", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "仙霞路333", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "东方维", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "内环高", "31.206170,121.395652");
        test.testTwoBoxSearch("上海", "加油", "31.206170,121.395652");
        test.testTwoBoxSearch("北京", "东方明珠", "32.691859,120.361652");

        test.testTwoBoxSearch("", "仙霞路古北", "31.206170,121.395652");
        test.testTwoBoxSearch("", "仙霞路333", "31.206170,121.395652");
        test.testTwoBoxSearch("", "东方维", "31.206170,121.395652");
        test.testTwoBoxSearch("", "内环高", "31.206170,121.395652");
        test.testTwoBoxSearch("", "加油", "31.206170,121.395652");
        test.testTwoBoxSearch("", "东方明珠", "32.691859,120.361652");
    }

    public static void RunTwoBoxSuggestionCasesENG(Tester test) {

    }

    public static void RunTwoBoxSuggestionCasesSZM(Tester test) {
        test.testTwoBoxSuggestion("上海", "xxl@gb", "31.206170,121.395652");
        test.testTwoBoxSuggestion("上海", "xxl333", "31.206170,121.395652");
        test.testTwoBoxSuggestion("上海", "DFW", "31.206170,121.395652");
        test.testTwoBoxSuggestion("上海", "NHG", "31.206170,121.395652");
        test.testTwoBoxSuggestion("北京", "DFMZ", "32.691859,120.361652");

        test.testTwoBoxSuggestion("", "xxl@gb", "31.206170,121.395652");
        test.testTwoBoxSuggestion("", "xxl333", "31.206170,121.395652");
        test.testTwoBoxSuggestion("", "DFW", "31.206170,121.395652");
        test.testTwoBoxSuggestion("", "NHG", "31.206170,121.395652");
        test.testTwoBoxSuggestion("", "DFMZ", "32.691859,120.361652");
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Tester test = new Tester();
        test.loadData("D:/test/yangzi_sprint16/index-cn");
        //test.loadData("D:/test/sprint-16/index-cn");
		/*
		RunTwoBoxSearchCasesCHI(test);
		RunTwoBoxSearchCasesENG(test);
		RunTwoBoxSearchCasesSZM(test);
		RunTwoBoxSuggestionCasesCHI(test);
		RunTwoBoxSuggestionCasesSZM(test);
		test.testCategoryTree();
		test.testCategoryTree("en_US");



		test.testOneBoxSearch("上海长宁区", "31.206170,121.395652");
		test.testOneBoxSearch("南京公交车站", "31.206170,121.395652");
		test.testOneBoxSearch("上海仙霞路古北路", "31.206170,121.395652");
		test.testOneBoxSearch("上海仙霞路古北路", "39.907749,116.395141");
		test.testOneBoxSearch("仙霞路", "31.206170,121.395652");
		test.testOneBoxSearch("仙霞路333", "31.206170,121.395652");
		test.testOneBoxSuggestion("肯德基", "31.206170,121.395652");
		test.testOneBoxSearch("平塘路100", "31.206170,121.395652");
		test.testOneBoxSearch("仙霞", "31.206170,121.395652");


		test.testOneBoxSearch("西直门外大街", "39.938728,116.339521");

		test.testTwoBoxSearch("", "仙霞路", "31.206257,121.398615");
		test.testOneBoxSearch("仙霞路;", "31.206257,121.398615");

		test.testTwoBoxSearch("海淀区", "", "39.959160,116.298470");
		test.testOneBoxSearch("海淀区", "39.959160,116.298470");

		test.testOneBoxSearch("杰尔曼尼欧陆家具", "31.21219,121.43386");
		test.testCategorySearch("333", "31.21219,121.43386");
		test.testRGC("31.21219,121.43386");
		test.testRGC("31.238630,121.528150");



		test.testOneBoxSearch("南京夫子庙", "31.21219,121.43386");


		test.testOneBoxSuggestion("DFMZT", "31.21219,121.43386");
		test.testTwoBoxSuggestion("上海", "DFMZT", "31.21219,121.43386");


		test.testOneBoxSearch("南京咖啡馆", "31.21219,121.43386");
		test.testTwoBoxSearch("江苏南京", "咖啡馆", "31.23237,121.47560");

		test.testTwoBoxSearch("上海", "", "32.691859,120.361652");
		test.testOneBoxSearch("上海", "32.691859,120.361652");
		test.testOneBoxSuggestion("上海", "32.691859,120.361652");

		test.testTwoBoxSearch("上海", "仙霞路", "32.691859,120.361652");
		test.testOneBoxSearch("仙霞路", "31.21219,121.43386");
		test.testOneBoxSuggestion("仙霞路", "31.21219,121.43386");
		test.testTwoBoxSuggestion("上海", "仙霞路", "31.21219,121.43386");

		test.testOneBoxSearch("天山路762", "31.21219,121.43386");
		test.testTwoBoxSearch("上海", "天山路762", "32.691859,120.361652");
		test.testTwoBoxSuggestion("上海", "天山路762", "31.21219,121.43386");



		test.testTwoBoxSearch("Shanghai", "Changhai Hospital", "32.044575,118.788949", "en_US");
		test.testTwoBoxSuggestion("Shanghai", "Yan'an Rd", "39.946341,116.195004", "en_US");


		test.testOneBoxSearch("上海宁波银行", "31.490950,120.312370");


		test.testOneBoxSuggestion("仙霞路@古北路", "31.21219,121.43386");
		test.testOneBoxSuggestion("城隍庙", "31.232202,121.473877");
		*/
        //test.testOneBoxSuggestion("XXL@GBL", "31.20598,121.39888");
        //test.testTwoBoxSearch("changning", "Hongqiao Airport T2", "31.20598,121.39888", "en_US");
        //test.testOneBoxSearch("XXL @ GBL", "31.20598,121.39888");
        //
        //test.testCategoryTree();
        //test.testGetLowerLevelAdmin("CHN", "上海市", "");
        //test.testTwoBoxSuggestion("上海", "东方", "31.21219,121.43386");

        //test.testDetail("P-2631717");
        //test.testTwoBoxSuggestion("上海", "仙霞", "39.946341, 116.195004");
        //test.testTwoBoxSearch("上海长宁区", "家乐福", "31.23237,121.47554");

        test.testOneBoxSearch("仙霞", "31.20598,121.39888");
    }
}
