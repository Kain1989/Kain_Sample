package com.telenav.cloud.search.autonavi.generator;

import com.telenav.cloud.search.autonavi.model.type.DataType;
import com.telenav.cloud.search.autonavi.model.type.QueryType;
import com.telenav.cloud.search.autonavi.request.AutonaviSearchRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfshi on 7/2/2015.
 */
public class AutonaviSearchRequestGenerator {

    private static AutonaviSearchRequest generateBaseRequest() {
        AutonaviSearchRequest request = new AutonaviSearchRequest();
        request.setFrom("iPhone_5_.1");
        List<DataType> dataTypeList = new ArrayList<DataType>();
        dataTypeList.add(DataType.POI);
        dataTypeList.add(DataType.ROAD);
        dataTypeList.add(DataType.ROADINTER);
        request.setDataTypes(dataTypeList);

        return request;
    }

    public static AutonaviSearchRequest generatePoiKeywordsRequest() {
        AutonaviSearchRequest request = generateBaseRequest();
        request.setQueryType(QueryType.KEYWORDS);


        return request;
    }

    public static AutonaviSearchRequest generatePoiLatlonRequest() {
        AutonaviSearchRequest request = generateBaseRequest();
        request.setQueryType(QueryType.LATLON);
        request.setKeywords("东方明珠");

        request.setLatitude(31.239305);
        request.setLongitude(121.521646);


        return request;
    }

    public static AutonaviSearchRequest generateReverseCodeRequest() {
        AutonaviSearchRequest request = generateBaseRequest();
        request.setDataTypes(new ArrayList<DataType>());

        request.setLatitude(31.239305);
        request.setLongitude(121.521646);

        return request;
    }
}

