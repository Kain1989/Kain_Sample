package com.telenav.cloud.search.autonavi.converter;

import com.telenav.cloud.search.autonavi.exception.InvalidArgumentsException;
import com.telenav.cloud.search.autonavi.model.type.QueryType;
import com.telenav.cloud.search.autonavi.entity.request.AutonaviSearchRequest;
import com.telenav.cloud.search.autonavi.entity.request.Point;
import com.telenav.entity.service.model.v4.EntitySearchRequest;
import com.telenav.entity.service.model.v4.Intent;
import org.apache.log4j.Logger;

/**
 * Created by zfshi on 7/2/2015.
 */
public class SearchRequestConverter {

    private static Logger logger = Logger.getLogger(SearchRequestConverter.class);

    public static AutonaviSearchRequest convert2AutonaviSearchRequest(EntitySearchRequest entityRequest) {
        AutonaviSearchRequest autonaviRequest = new AutonaviSearchRequest();
        autonaviRequest.setLanguage(entityRequest.getLocale());
        autonaviRequest.setKeywords(entityRequest.getQuery());
        if (entityRequest.getLimit() > 0) {
            autonaviRequest.setPageNumber(new Long(entityRequest.getLimit()));
        }
        if (entityRequest.getCategories() != null && entityRequest.getCategories().size() > 0) {
            autonaviRequest.setCategoryList(entityRequest.getCategories());
        }
        if (entityRequest.getLocation() != null) {
            autonaviRequest.setLocation(new Point(entityRequest.getLocation().getLongitude(), entityRequest.getLocation().getLatitude()));
        }


        return autonaviRequest;
    }

    private static void convertQueryType(AutonaviSearchRequest autonaviRequest, EntitySearchRequest entityRequest) throws InvalidArgumentsException {

        if (isCategorySearch(entityRequest)) {
            if (entityRequest.getCategories() == null || entityRequest.getCategories().size() <= 0) {
                throw new InvalidArgumentsException("Search for category must have categories within the request");
            }
            autonaviRequest.setQueryType(QueryType.KEYWORDS);
        }
    }

    private static boolean isCategorySearch(EntitySearchRequest request) {
        return ((request.getCategories() != null) && (request.getCategories().size() > 0));
    }

    private static boolean isAddressSearch(EntitySearchRequest entityRequest) {
        return Intent.address.equals(entityRequest.getIntent());
    }

    private static boolean isAdminAirportSearch(EntitySearchRequest entityRequest) {
        return Intent.admin_airport.equals(entityRequest.getIntent());
    }

    private static boolean isPlaceSearch(EntitySearchRequest entityRequest) {
        return Intent.place.equals(entityRequest.getIntent());
    }

    private static boolean isAlongRouteSearch(EntitySearchRequest entityRequest) {
        return (entityRequest.getRoutes() != null) && !entityRequest.getRoutes().isEmpty();
    }

    private static boolean isRecommendationSearch(EntitySearchRequest entityRequest) {
        return Intent.recommendation.equals(entityRequest.getIntent());
    }
}
