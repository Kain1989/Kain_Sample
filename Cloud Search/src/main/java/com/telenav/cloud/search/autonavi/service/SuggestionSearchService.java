package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.response.AutonaviResponse;
import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.RequestKeyConstants;
import com.telenav.cloud.search.autonavi.entity.request.RequestValueConstants;
import com.telenav.cloud.search.autonavi.test.WebConstKeys;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

public class SuggestionSearchService extends SearchService<AutonaviResponse> {

    private static Logger logger = Logger.getLogger(SuggestionSearchService.class);

    private static Object obj = new Object();

    private static SuggestionSearchService instance;

    public static SuggestionSearchService getInstance() {
        synchronized (obj) {
            if (instance == null) {
                instance = new SuggestionSearchService();
            }
        }
        return instance;
    }

    private SuggestionSearchService() {
        super();
        this.urlPrefix = TelenavConfiguration.getInstance().getSuggestUrlPrefix();
    }

    @Override
    protected Map<String, Object> generateQueryParameters(AutonaviSearchRequest request) throws AuthenticationBuildException {
        Map<String, Object> params = super.generateCommonParameters(request);
        if (StringUtils.isNotBlank(request.getCity())) {
            params.put(RequestKeyConstants.CITY, request.getCity());
        }
        if (StringUtils.isNotBlank(request.getWords())) {
            params.put(RequestKeyConstants.WORDS, request.getWords());
        }
        if (request.getAdcode() != null) {
            params.put(RequestKeyConstants.ADCODE, request.getAdcode());
        }
        return params;
    }

    @Override
    protected String getAuthenticationString(AutonaviSearchRequest request) throws AuthenticationBuildException {
        StringBuilder authentication = new StringBuilder();

        if (StringUtils.isNotBlank(request.getCity())) {
            authentication.append(request.getCity());
        }
        if (StringUtils.isNotBlank(request.getWords())) {
            authentication.append(request.getWords());
        }

        authentication.append(RequestValueConstants.AUTHENCATION_SEPERATOR);
        authentication.append(WebConstKeys.Customer_Key_Telenav);
        logger.info("Authentication string is : " + authentication);
        return authentication.toString();
    }
}
