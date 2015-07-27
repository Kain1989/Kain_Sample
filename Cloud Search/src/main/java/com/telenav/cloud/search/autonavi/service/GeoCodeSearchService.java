package com.telenav.cloud.search.autonavi.service;

import com.telenav.cloud.search.autonavi.entity.poi_generate.AutonaviResponse;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.RequestKeyConstants;
import com.telenav.cloud.search.autonavi.entity.request.RequestValueConstants;
import com.telenav.cloud.search.autonavi.exception.AuthenticationBuildException;
import com.telenav.cloud.search.autonavi.test.WebConstKeys;
import com.telenav.cloud.search.autonavi.utils.config.TelenavConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by zfshi on 7/24/2015.
 */
public class GeoCodeSearchService extends SearchService<AutonaviResponse> {

    private static Logger logger = Logger.getLogger(GeoCodeSearchService.class);

    private static Object obj = new Object();

    private static GeoCodeSearchService instance;

    public static GeoCodeSearchService getInstance() {
        synchronized (obj) {
            if (instance == null) {
                instance = new GeoCodeSearchService();
            }
        }
        return instance;
    }

    private GeoCodeSearchService() {
        super();
        this.urlPrefix = TelenavConfiguration.getInstance().getGeoCodeUrlPrefix();
    }

    @Override
    protected Map<String, Object> generateQueryParameters(AutonaviSearchRequest request) throws AuthenticationBuildException {
        Map<String, Object> params = super.generateCommonParameters(request);

        if (StringUtils.isNotBlank(request.getAddress())) {
            params.put(RequestKeyConstants.ADDRESS, request.getAddress());
        } else {
            throw new AuthenticationBuildException("Could not build authentication string without address when search address");
        }
        if (request.getOneRow() != null) {
            params.put(RequestKeyConstants.ONE_ROW, request.getOneRow());
        }
        return params;
    }

    @Override
    protected String getAuthenticationString(AutonaviSearchRequest request) throws AuthenticationBuildException {
        StringBuilder authentication = new StringBuilder();

        if (StringUtils.isEmpty(request.getAddress())) {
            throw new AuthenticationBuildException("Could not build authentication string without address when search address");
        }
        authentication.append(request.getAddress());

        authentication.append(RequestValueConstants.AUTHENCATION_SEPERATOR);
        authentication.append(WebConstKeys.Customer_Key_Telenav);
        logger.info("Authentication string is : " + authentication);
        return authentication.toString();
    }
}
