package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.poi_generate.AutonaviResponse;
import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.request.RequestKeyConstants;
import com.telenav.cloud.search.autonavi.request.RequestValueConstants;
import com.telenav.cloud.search.autonavi.test.WebConstKeys;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

public class SuggestionSearchService extends SearchService<AutonaviResponse> {

    private static Logger logger = Logger.getLogger(SuggestionSearchService.class);

    @Override
    protected Map<String, Object> generateQueryParameters(AutonaviSearchRequest request) throws AuthenticationBuildException {
        Map<String, Object> params = super.generateCommonParameters(request);
        if (StringUtils.isNotBlank(request.getCity())) {
            params.put(RequestKeyConstants.CITY, request.getCity());
        }
        if (StringUtils.isNotBlank(request.getWords())) {
            params.put(RequestKeyConstants.WORDS, request.getWords());
        }
        params.put(RequestKeyConstants.ADCODE, request.getAdcode());
        return params;
    }

    @Override
    protected String getAuthenticationString(AutonaviSearchRequest request) throws AuthenticationBuildException {
        StringBuilder authentication = new StringBuilder();

        authentication.append(request.getCity());
        authentication.append(request.getWords());

        authentication.append(RequestValueConstants.AUTHENCATION_SEPERATOR);
        authentication.append(WebConstKeys.Customer_Key_Telenav);
        logger.info("Authentication string is : " + authentication);
        return authentication.toString();
    }
}
