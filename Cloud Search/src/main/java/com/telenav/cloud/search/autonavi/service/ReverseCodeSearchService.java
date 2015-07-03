package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.poi_generate.AutonaviResponse;
import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.request.RequestKeyConstants;
import com.telenav.cloud.search.autonavi.request.RequestValueConstants;
import com.telenav.cloud.search.autonavi.test.WebConstKeys;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by zfshi on 7/2/2015.
 */
public class ReverseCodeSearchService extends SearchService<AutonaviResponse> {

    private static Logger logger = Logger.getLogger(ReverseCodeSearchService.class);

    @Override
    protected Map<String, Object> generateQueryParameters(AutonaviSearchRequest request) throws AuthenticationBuildException {
        Map<String, Object> params = super.generateCommonParameters(request);

        params.put(RequestKeyConstants.LONGITUDE, request.getLongitude());
        params.put(RequestKeyConstants.LATITUDE, request.getLatitude());
        params.put(RequestKeyConstants.SIGN, getSignatureString(request));
        return params;
    }

    @Override
    protected String getAuthenticationString(AutonaviSearchRequest request) throws AuthenticationBuildException {
        StringBuilder authentication = new StringBuilder();

        authentication.append(request.getLongitude());
        authentication.append(request.getLatitude());

        authentication.append(RequestValueConstants.AUTHENCATION_SEPERATOR);
        authentication.append(WebConstKeys.Customer_Key_Telenav);
        logger.info("Authentication string is : " + authentication);
        return authentication.toString();
    }
}
