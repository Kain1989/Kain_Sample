package com.telenav.cloud.search.service;

import com.telenav.cloud.search.entity.keyword.KeywordResult;
import com.telenav.cloud.search.entity.keyword.Poi;
import com.telenav.cloud.search.test.WebConstKeys;
import com.telenav.cloud.search.utils.coder.MD5Encoder;
import com.telenav.cloud.search.utils.http.TelenavHttpClient;
import com.telenav.cloud.search.utils.http.TelenavHttpResponse;
import com.telenav.cloud.search.utils.json.JsonUtils;
import com.telenav.cloud.search.utils.uri.TelenavUriBuilder;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KeywordSearch {

    private static Logger logger = Logger.getLogger(KeywordSearch.class);

    public String Query_type = "TQUERY";
    public String Data_type = "POI+ROAD+ROADINTER";
    //public String Data_type = new String("POI");
    public String City = "";
    public String Keywords = "";
    public String Category = "";
    public long Page_size = 20;
    public long Page_number = 1;
    public boolean addr_poi_merge = false;

    private TelenavHttpClient httpClient;

    private String urlPrefix;

    public void setHttpClient(TelenavHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    private Map<String, Object> generateQueryParameters(String keywords) {

        Map<String, Object> params = new HashMap<String, Object>();
        this.Keywords = keywords;
        if (keywords.isEmpty() && Category.isEmpty())
            return null;

        params.put("from", "iPhone_5.1");
        params.put("output", "json");
        params.put("channel", WebConstKeys.Customer_Channel_Telenav);
        params.put("query_type", Query_type);
        params.put("data_type", Data_type);
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

    public KeywordResult search(String keywords) {
        KeywordResult result = null;
        TelenavHttpResponse response = null;
        Map<String, Object> params = this.generateQueryParameters(keywords);
        if (params == null) {
            return result;
        }
        try {
            response = httpClient.doGet(TelenavUriBuilder.build(this.urlPrefix, params).toString());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (response.getStatus() == HttpStatus.SC_OK) {
            System.out.println("Search success : " + response.getBody());
            result = JsonUtils.fromJson(response.getBody(), KeywordResult.class);
            return result;
        } else {
            return result;
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"spring/appContext.xml"});
        KeywordSearch keywordSearch = (KeywordSearch) context.getBean("keywordSearch");

//        KeywordResult result = keywordSearch.search("东方明珠");
//        System.out.println(result.toString());
//
//        for (Poi poi : result.getAts().getPoi_list().getPoi()) {
//            System.out.println(poi.toString());
//        }

        KeywordResult result = keywordSearch.search("上海仙霞");
        System.out.println(result.toString());

        for (Poi poi : result.getAts().getPoi_list()) {
            System.out.println(poi.toString());
        }
//        keywordSearch.City = "上海";
//        keywordSearch.search("仙霞");
//
//        keywordSearch.City = "上海";
//        keywordSearch.search("dfmz");

    }


    private String getAuthenticationString() {
        return Keywords + Category + "@" + WebConstKeys.Customer_Key_Telenav;
    }

    private String getSignatureString() {
        String str = getAuthenticationString();
        return MD5Encoder.getMD5(str.getBytes());
    }
}
