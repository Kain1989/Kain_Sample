package com.telenav.cloud.search.service;

import com.telenav.cloud.search.entity.poi_generate.AutonaviResponse;
import com.telenav.cloud.search.model.type.DataType;
import com.telenav.cloud.search.model.type.QueryType;
import com.telenav.cloud.search.param.poi.PoiSearchParam;
import com.telenav.cloud.search.test.WebConstKeys;
import com.telenav.cloud.search.utils.config.TelenavConfiguration;
import com.telenav.cloud.search.utils.http.TelenavHttpClient;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PoiSearch extends SearchService<AutonaviResponse> {

    private static Logger logger = Logger.getLogger(PoiSearch.class);

    private String language;

    public PoiSearch(String language) {
        this.language = language;
    }

    public void searchById(List<DataType> dateTypes, String poiId, String city) {
        PoiSearchParam param = this.getSearchParam();
        param.setDataTypes(dateTypes);
        param.setQueryType(QueryType.ID);
        param.setId(poiId);
        param.setCity(city);

    }

    public void searchByKeywords(PoiSearchParam param) {
        param.setQueryType(QueryType.KEYWORDS);

    }

    public void searchByLatLon(PoiSearchParam param) {
        param.setQueryType(QueryType.LATLON);

    }

    public void searchByRentangle(PoiSearchParam param) {
        param.setQueryType(QueryType.RECTANGLE);

    }

    public void searchByCenter(PoiSearchParam param) {
        param.setQueryType(QueryType.CENTER);

    }

    private PoiSearchParam getSearchParam() {
        PoiSearchParam param = new PoiSearchParam();
        param.setChanel(WebConstKeys.Customer_Channel_Telenav);
        param.setFrom("iPhone_5.1");
        param.setOutput("json");
        param.setAnonymous(false);
        param.setCompress(false);
        param.setLanguage(language);
        return param;
    }

    public String Query_type = "TQUERY";
    public String Data_type = "POI+ROAD+ROADINTER";
    //public String Data_type = new String("POI");
    public String City = "";
    public String Keywords = "";
    public String Category = "";
    public long Page_size = 20;
    public long Page_number = 1;
    public boolean addr_poi_merge = false;

    public double latitude = 0;

    public double longitude = 0;

    @Override
    protected Map<String, Object> generateQueryParameters() {

        Map<String, Object> params = new LinkedHashMap<>();
        if (Keywords.isEmpty() && Category.isEmpty())
            return null;

        params.put("from", "iPhone_5.1");
        params.put("output", "json");
        params.put("channel", WebConstKeys.Customer_Channel_Telenav);
//        params.put("query_type", Query_type);
        params.put("query_type", "RQBXY");
        params.put("data_type", Data_type);


        // 121.243045,31.397778 嘉定北
//        latitude = 31.397778;
//        longitude = 121.243045;

        // 121.521646,31.239305  东昌路
        latitude = 31.239305;
        longitude = 121.521646;

//       121.16845,31.294162   安亭
//        latitude = 31.294162;
//        longitude = 121.16845;
        params.put("latitude", latitude);
        params.put("longitude", longitude);

        if (!City.isEmpty()) {
            params.put("city", City);
        }
        if (!Keywords.isEmpty()) {
            params.put("keywords", Keywords);
        }
        if (!Category.isEmpty()) {
            params.put("category", Category);
        }
        params.put("pagesize", Page_size);
        params.put("pagenum", Page_number);
        if (addr_poi_merge) {
            params.put("addr_poi_merge", true);
        } else {
            params.put("addr_poi_merge", false);
        }
        params.put("sign", getSignatureString());
        return params;
    }

    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext(
//                new String[]{"spring/appContext.xml"});
//        PoiSearch poiSearch = (PoiSearch) context.getBean("poiSearch");

        PoiSearch poiSearch = new PoiSearch("zh_CN");
        poiSearch.setHttpClient(TelenavHttpClient.getInstance());
        poiSearch.setUrlPrefix(TelenavConfiguration.getInstance().getPoiUrlPrefix());

        poiSearch.Keywords = "滴水湖";
//        poiSearch.City = "苏州";
        AutonaviResponse result = poiSearch.search();
        System.out.println(result.getAts().getPoiList().getPoi());

//        for (Poi poi : result.getAts().getPoiList().getPoi()) {
//            System.out.println(poi.toString());
//        }
//        poiSearch.City = "上海";
//        poiSearch.search("仙霞");
//
//        poiSearch.City = "上海";
//        poiSearch.search("dfmz");

    }

    @Override
    protected String getAuthenticationString() {
        StringBuilder authentication = new StringBuilder();
        // TODO id
        if (longitude != 0) {
            authentication.append(longitude);
        }
        if (latitude != 0) {
            authentication.append(latitude);
        }
        if (!Keywords.isEmpty()) {
            authentication.append(Keywords);
        }
        if (!Category.isEmpty()) {
            authentication.append(Category);
        }

        authentication.append("@");
        authentication.append(WebConstKeys.Customer_Key_Telenav);
        System.out.println(authentication);
        return authentication.toString();
    }

}
