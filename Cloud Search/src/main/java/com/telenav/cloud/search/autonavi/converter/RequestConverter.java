package com.telenav.cloud.search.autonavi.converter;

import com.telenav.cloud.search.autonavi.request.AutonaviSearchRequest;
import com.telenav.entity.service.model.v4.EntitySearchRequest;
import org.apache.log4j.Logger;

/**
 * Created by zfshi on 7/2/2015.
 */
public class RequestConverter {

    private static Logger logger = Logger.getLogger(RequestConverter.class);

    public static AutonaviSearchRequest convertEntityRequest2Autonavi(EntitySearchRequest entityRequest) {
        AutonaviSearchRequest autonaviRequest = new AutonaviSearchRequest();


        return autonaviRequest;
    }
}
