package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.poi_generate.AutonaviResponse;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.Rectangle;
import com.telenav.cloud.search.autonavi.entity.request.RequestKeyConstants;
import com.telenav.cloud.search.autonavi.entity.request.RequestValueConstants;
import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.exception.URLBuildException;
import com.telenav.cloud.search.autonavi.model.type.DataType;
import com.telenav.cloud.search.autonavi.model.type.QueryType;
import com.telenav.cloud.search.autonavi.test.WebConstKeys;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import com.telenav.cloud.search.autonavi.utils.http.TelenavHttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class PoiSearchService extends SearchService<AutonaviResponse> {

    private static Logger logger = Logger.getLogger(PoiSearchService.class);

    private static Object obj = new Object();

    private static PoiSearchService instance;

    public static PoiSearchService getInstance() {
        synchronized (obj) {
            if (instance == null) {
                instance = new PoiSearchService();
            }
        }
        return instance;
    }

    private PoiSearchService() {
        super();
        this.urlPrefix = TelenavConfiguration.getInstance().getPoiUrlPrefix();
    }

    ;

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

    @Override
    protected Map<String, Object> generateQueryParameters(AutonaviSearchRequest request) throws AuthenticationBuildException {

        Map<String, Object> params = super.generateCommonParameters(request);
        if (request.getQueryType().equals(QueryType.KEYWORDS) && StringUtils.isBlank(request.getKeywords())
                && (request.getCategoryList() == null || request.getCategoryList().size() <= 0)) {
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
        if (request.getCategoryList() != null && request.getCategoryList().size() > 0) {
            params.put(RequestKeyConstants.CATEGORY, StringUtils.join(request.getCategoryList(), "|"));
        }
        if (request.getLocation() != null) {
            params.put(RequestKeyConstants.LONGITUDE, request.getLocation().getLongitude());
            params.put(RequestKeyConstants.LATITUDE, request.getLocation().getLatitude());
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
        return params;
    }

    @Override
    protected String getAuthenticationString(AutonaviSearchRequest request) throws AuthenticationBuildException {
        StringBuilder authentication = new StringBuilder();
        if (StringUtils.isNotBlank(request.getId())) {
            authentication.append(request.getId());
        }
        if (request.getLocation() != null) {
            authentication.append(request.getLocation().getLongitude());
            authentication.append(request.getLocation().getLatitude());
        }
        if (request.getKeywords() != null) {
            authentication.append(request.getKeywords());
        }
        if (request.getCategoryList() != null && request.getCategoryList().size() > 0) {
            authentication.append(StringUtils.join(request.getCategoryList(), "|"));
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

    public static void main(String[] args) throws AuthenticationBuildException, URLBuildException {
//        ApplicationContext context = new ClassPathXmlApplicationContext(
//                new String[]{"spring/appContext.xml"});
//        PoiSearchService poiSearch = (PoiSearchService) context.getBean("poiSearch");

        AutonaviSearchRequest request = new AutonaviSearchRequest();

        PoiSearchService poiSearch = new PoiSearchService();
        poiSearch.setHttpClient(TelenavHttpClient.getInstance());
        poiSearch.setUrlPrefix(TelenavConfiguration.getInstance().getPoiUrlPrefix());

//        poiSearch.Keywords = "滴水湖";
//        poiSearch.City = "苏州";
        AutonaviResponse result = poiSearch.search(request);
        System.out.println(result.getAts().getPoiList().getPois());

    }
}
