package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.RequestKeyConstants;
import com.telenav.cloud.search.autonavi.entity.request.RequestValueConstants;
import com.telenav.cloud.search.autonavi.entity.response.AutonaviResponse;
import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by zfshi on 7/28/2015.
 */
public class DeepInfoSearchService extends SearchService<AutonaviResponse> {

    private static Logger logger = Logger.getLogger(DeepInfoSearchService.class);

    private static Object obj = new Object();

    private static DeepInfoSearchService instance;

    public static DeepInfoSearchService getInstance() {
        synchronized (obj) {
            if (instance == null) {
                instance = new DeepInfoSearchService();
            }
        }
        return instance;
    }

    private DeepInfoSearchService() {
        super();
        this.urlPrefix = TelenavConfiguration.getInstance().getDeepInfoUrlPrefix();
    }

    @Override
    protected Map<String, Object> generateQueryParameters(AutonaviSearchRequest request) throws AuthenticationBuildException {
        Map<String, Object> params = super.generateCommonParameters(request);

        params.put(RequestKeyConstants.ID, request.getId());
        return params;
    }

    @Override
    protected String getAuthenticationString(AutonaviSearchRequest request) throws AuthenticationBuildException {
        StringBuilder authentication = new StringBuilder();

//        authentication.append(RequestValueConstants.TELENAV);
        if (StringUtils.isNotBlank(request.getId())) {
            authentication.append(request.getId());
        }

        authentication.append(RequestValueConstants.AUTHENCATION_SEPERATOR);
        authentication.append(TelenavConfiguration.getInstance().getCustomerKey());
        logger.info("Authentication string is : " + authentication);
        return authentication.toString();
    }
}
