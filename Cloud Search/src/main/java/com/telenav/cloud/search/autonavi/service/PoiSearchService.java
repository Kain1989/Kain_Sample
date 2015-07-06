package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.poi_generate.AutonaviResponse;
import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.exception.URLBuildException;
import com.telenav.cloud.search.autonavi.model.type.DataType;
import com.telenav.cloud.search.autonavi.model.type.QueryType;
import com.telenav.cloud.search.autonavi.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.request.Rectangle;
import com.telenav.cloud.search.autonavi.request.RequestKeyConstants;
import com.telenav.cloud.search.autonavi.request.RequestValueConstants;
import com.telenav.cloud.search.autonavi.test.WebConstKeys;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import com.telenav.cloud.search.autonavi.utils.http.TelenavHttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class PoiSearchService extends SearchService<AutonaviResponse> {

    private static Logger logger = Logger.getLogger(PoiSearchService.class);

    private String language;

    public PoiSearchService(){};

    public PoiSearchService(String language) {
        this.language = language;
    }

    public void searchById(List<DataType> dateTypes, String poiId, String city, AutonaviSearchRequest request) {
//        AutonaviSearchRequest param = this.getSearchParam(request);
//        param.setDataTypes(dateTypes);
//        param.setQueryType(QueryType.ID);
//        param.setId(poiId);
//        param.setCity(city);

    }

    public void searchByKeywords(AutonaviSearchRequest request) {
        request.setQueryType(QueryType.KEYWORDS);

    }

    public void searchByLatLon(AutonaviSearchRequest request) {
        request.setQueryType(QueryType.LATLON);

    }

    public void searchByRentangle(AutonaviSearchRequest request) {
        request.setQueryType(QueryType.RECTANGLE);

    }

    public void searchByCenter(AutonaviSearchRequest request) {
        request.setQueryType(QueryType.CENTER);

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
    protected Map<String, Object> generateQueryParameters(AutonaviSearchRequest request) throws AuthenticationBuildException {

        Map<String, Object> params = super.generateCommonParameters(request);
        if (request.getQueryType().equals(QueryType.KEYWORDS) && StringUtils.isBlank(request.getKeywords()) && StringUtils.isBlank(request.getCategory())) {
            logger.warn("Keywords and Category were both empty, could not execute search");
            return null;
        }

        params.put(RequestKeyConstants.QUERY_TYPE, request.getQueryType().getCode());

        if (StringUtils.isNotBlank(request.getId())) {
            params.put(RequestKeyConstants.ID, request.getId());
        }
        if (StringUtils.isNotBlank(request.getCity())) {
            params.put(RequestKeyConstants.CITY, request.getCity());
        }
        if (StringUtils.isNotBlank(request.getKeywords())) {
            params.put(RequestKeyConstants.KEYWORDS, request.getKeywords());
        }
        if (StringUtils.isNotBlank(request.getCategory())) {
            params.put(RequestKeyConstants.CATEGORY, request.getCategory());
        }
        if (request.getLongitude() != null) {
            params.put(RequestKeyConstants.LONGITUDE, request.getLongitude());
        }
        if (request.getLatitude() != null) {
            params.put(RequestKeyConstants.LATITUDE, request.getLatitude());
        }
        if (request.getRectangle() != null) {
            params.put(RequestKeyConstants.RECTANGLE, this.generateRectangleString(request.getRectangle()));
        }
        if (StringUtils.isNotBlank(request.getCenter())) {
            params.put(RequestKeyConstants.CENTER, request.getCenter());
        }
        if (request.getMergeAddressPoi() != null) {
            params.put(RequestKeyConstants.MERGE_ADDR_POI, request.getMergeAddressPoi());
        }
        params.put(RequestKeyConstants.SIGN, this.getSignatureString(request));
        return params;
    }

    public static void main(String[] args) throws AuthenticationBuildException, URLBuildException {
//        ApplicationContext context = new ClassPathXmlApplicationContext(
//                new String[]{"spring/appContext.xml"});
//        PoiSearchService poiSearch = (PoiSearchService) context.getBean("poiSearch");

        AutonaviSearchRequest request = new AutonaviSearchRequest();

        PoiSearchService poiSearch = new PoiSearchService("zh_CN");
        poiSearch.setHttpClient(TelenavHttpClient.getInstance());
        poiSearch.setUrlPrefix(TelenavConfiguration.getInstance().getPoiUrlPrefix());

        poiSearch.Keywords = "滴水湖";
//        poiSearch.City = "苏州";
        AutonaviResponse result = poiSearch.search(request);
        System.out.println(result.getAts().getPoiList().getPois());

    }

    @Override
    protected String getAuthenticationString(AutonaviSearchRequest request) throws AuthenticationBuildException {
        StringBuilder authentication = new StringBuilder();
        if (StringUtils.isNotBlank(request.getId())) {
            authentication.append(request.getId());
        }
        if (request.getLongitude() != null) {
            authentication.append(request.getLongitude());
        }
        if (request.getLatitude() != null) {
            authentication.append(request.getLatitude());
        }
        if (request.getKeywords() != null) {
            authentication.append(request.getKeywords());
        }
        if (request.getCategory() != null) {
            authentication.append(request.getCategory());
        }
        if (StringUtils.isNotBlank(request.getCenter())) {
            authentication.append(request.getCenter());
        }
        if (request.getRectangle() != null) {
            authentication.append(this.generateRectangleString(request.getRectangle()));
        }

        authentication.append(RequestValueConstants.AUTHENCATION_SEPERATOR);
        authentication.append(WebConstKeys.Customer_Key_Telenav);
        logger.info("Authentication string is : " + authentication);
        return authentication.toString();
    }

    private String generateRectangleString(Rectangle rectangle) {
        StringBuilder builder = new StringBuilder();
        builder.append(rectangle.getTopLeft().getLongitude());
        builder.append(";");
        builder.append(rectangle.getTopLeft().getLatitude());
        builder.append(";");
        builder.append(rectangle.getBottomRight().getLongitude());
        builder.append(";");
        builder.append(rectangle.getBottomRight().getLatitude());
        return builder.toString();
    }

}
