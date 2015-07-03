package com.telenav.cloud.search.autonavi.utils.json;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

/**
 * Created by zfshi on 6/4/2015.
 */
public class JsonUtils {

    private static Logger logger = Logger.getLogger(JsonUtils.class);

    private static Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        T t = null;
        try {
            t = gson.fromJson(json, classOfT);
        } catch (Exception e) {
            logger.error("Parsing json failed.", e);
        }
        return t;
    }

    public static  <T> String toJson(T t) {
        return gson.toJson(t);
    }
}
