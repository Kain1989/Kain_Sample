package com.telenav.search.index.test;//package com.telenav.entity.bindings.v4.util;

import java.lang.reflect.Type;
import java.util.Collection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonConverter {

    public static String toJson(Object obj) {

        String json = "";
        if (obj != null) {
            Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Collection.class, new V4JsonCollectionAdapter()).disableHtmlEscaping().create();
            json = gson.toJson(obj);
        }
        return json;
    }

    public static String toPrettyJson(Object obj) {

        String json = "";
        if (obj != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeHierarchyAdapter(Collection.class, new V4JsonCollectionAdapter())
                    .disableHtmlEscaping().create();
            json = gson.toJson(obj);
        }
        return json;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {

        if (json != null) {
            Gson gson = new Gson();
            return gson.fromJson(json, classOfT);
        }
        return null;
    }
//    public static <T> T fromJson2(String json, Type typeOfT) {
//
//        if (json != null) {
//            Gson gson = new Gson();
//            return gson.fromJson(json, typeOfT);
//        }
//        return null;
//    }

}

class V4JsonCollectionAdapter implements JsonSerializer<Collection<?>> {

    @Override
    public JsonElement serialize(Collection<?> src, Type typeOfSrc, JsonSerializationContext context) {
        if ((src == null) || src.isEmpty()) {
            return null;
        }

        JsonArray array = new JsonArray();

        for (Object child : src) {
            JsonElement element = context.serialize(child);
            array.add(element);
        }

        return array;
    }
}
