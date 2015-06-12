package com.telenav.cloud.search.service;

import com.telenav.cloud.search.entity.CloudResult;
import com.telenav.cloud.search.test.WebConstKeys;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public class PoiSearch extends SearchService<CloudResult> {

    private static Logger logger = Logger.getLogger(PoiSearch.class);

    public String Query_type = "TQUERY";
    public String Data_type = "POI+ROAD+ROADINTER";
    //public String Data_type = new String("POI");
    public String City = "";
    public String Keywords = "";
    public String Category = "";
    public long Page_size = 20;
    public long Page_number = 1;
    public boolean addr_poi_merge = false;

    @Override
    protected Map<String, Object> generateQueryParameters() {

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        if (Keywords.isEmpty() && Category.isEmpty())
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

    @Override
    protected String getAuthenticationString() {
        return Keywords + Category + "@" + WebConstKeys.Customer_Key_Telenav;
    }

}
