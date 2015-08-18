package com.telenav.cloud.search.autonavi.generator;

import com.telenav.cloud.search.autonavi.model.type.DataType;
import com.telenav.cloud.search.autonavi.model.type.QueryType;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.Point;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfshi on 7/2/2015.
 */
public class AutonaviSearchRequestGenerator {

    public static void main(String[] args) {
        AutonaviSearchRequest request = generateBaseRequest();
        System.out.println(StringUtils.join(request.getDataTypes().toArray(), "+"));
    }

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

        return request;
    }

    public static AutonaviSearchRequest generatePoiLatlonRequest() {
        AutonaviSearchRequest request = generateBaseRequest();
        request.setQueryType(QueryType.LATLON);
        request.setKeywords("东方明珠");

        request.setLocation(new Point(121.521646, 31.239305));


        return request;
    }

    public static AutonaviSearchRequest generateGeoCodeRequest() {
        AutonaviSearchRequest request = generateBaseRequest();
        request.setDataTypes(new ArrayList<DataType>());

        request.setLocation(new Point(121.521646, 31.239305));

        return request;
    }

    public static AutonaviSearchRequest generateReverseCodeRequest() {
        AutonaviSearchRequest request = generateBaseRequest();
        request.setDataTypes(new ArrayList<DataType>());


        return request;
    }

    public static AutonaviSearchRequest generateInputSuggestionRequest() {
        AutonaviSearchRequest request = generateBaseRequest();

        return request;
    }

    public static AutonaviSearchRequest generateAlongRouteRequest() {
        AutonaviSearchRequest request = generateBaseRequest();
        request.setDataTypes(new ArrayList<DataType>());


        return request;
    }
}

