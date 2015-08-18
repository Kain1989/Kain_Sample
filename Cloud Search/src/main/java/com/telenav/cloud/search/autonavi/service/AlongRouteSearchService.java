package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.RequestKeyConstants;
import com.telenav.cloud.search.autonavi.entity.request.RequestValueConstants;
import com.telenav.cloud.search.autonavi.entity.response.AutonaviResponse;
import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by zfshi on 7/24/2015.
 */
public class AlongRouteSearchService extends SearchService<AutonaviResponse> {

    private static Logger logger = Logger.getLogger(AlongRouteSearchService.class);

    private static Object obj = new Object();

    private static AlongRouteSearchService instance;

    public static AlongRouteSearchService getInstance() {
        synchronized (obj) {
            if (instance == null) {
                instance = new AlongRouteSearchService();
            }
        }
        return instance;
    }

    private AlongRouteSearchService() {
        super();
        this.urlPrefix = TelenavConfiguration.getInstance().getAlongRouteUrlPrefix();
    }

    @Override
    protected Map<String, Object> generateQueryParameters(AutonaviSearchRequest request) throws AuthenticationBuildException {
        Map<String, Object> params = super.generateCommonParameters(request);

        if (request.getCategoryList() != null && request.getCategoryList().size() > 0) {
            params.put(RequestKeyConstants.CATEGORY, request.generateCategoryQueryString());
        }
        if (request.getGeoLine() != null && request.getGeoLine().size() > 0) {
            params.put(RequestKeyConstants.GEO_LINE, request.generateGeoLineString());
        }
        return params;
    }

    @Override
    protected String getAuthenticationString(AutonaviSearchRequest request) throws AuthenticationBuildException {
        StringBuilder authentication = new StringBuilder();

        if (request.getCategoryList() != null && request.getCategoryList().size() > 0) {
            authentication.append(request.generateCategoryQueryString());
        }
        if (request.getGeoLine() != null && request.getGeoLine().size() > 0) {
            authentication.append(request.generateGeoLineString());
        }

        authentication.append(RequestValueConstants.AUTHENCATION_SEPERATOR);
        authentication.append(TelenavConfiguration.getInstance().getCustomerKey());
        logger.info("Authentication string is : " + authentication);
        return authentication.toString();
    }

}
