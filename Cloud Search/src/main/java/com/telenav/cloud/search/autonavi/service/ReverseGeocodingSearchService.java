package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.response.AutonaviResponse;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.RequestKeyConstants;
import com.telenav.cloud.search.autonavi.entity.request.RequestValueConstants;
import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.test.WebConstKeys;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by zfshi on 7/2/2015.
 */
public class ReverseGeocodingSearchService extends SearchService<AutonaviResponse> {

    private static Logger logger = Logger.getLogger(ReverseGeocodingSearchService.class);

    private static ReverseGeocodingSearchService instance;

    private static Object obj = new Object();

    public static ReverseGeocodingSearchService getInstance() {
        synchronized (obj) {
            if (instance == null) {
                instance = new ReverseGeocodingSearchService();
            }
        }
        return instance;
    }

    private ReverseGeocodingSearchService() {
        super();
        this.urlPrefix = TelenavConfiguration.getInstance().getReverseCodePrefix();
    }

    @Override
    protected Map<String, Object> generateQueryParameters(AutonaviSearchRequest request) throws AuthenticationBuildException {
        Map<String, Object> params = super.generateCommonParameters(request);

        if (request.getLocation() != null) {
            params.put(RequestKeyConstants.LONGITUDE, request.getLocation().getLongitude());
            params.put(RequestKeyConstants.LATITUDE, request.getLocation().getLatitude());
        }
        return params;
    }

    @Override
    protected String getAuthenticationString(AutonaviSearchRequest request) throws AuthenticationBuildException {
        StringBuilder authentication = new StringBuilder();

        if (request.getLocation() != null) {
            authentication.append(request.getLocation().getLongitude());
            authentication.append(request.getLocation().getLatitude());
        }

        authentication.append(RequestValueConstants.AUTHENCATION_SEPERATOR);
        authentication.append(WebConstKeys.Customer_Key_Telenav);
        logger.info("Authentication string is : " + authentication);
        return authentication.toString();
    }
}
