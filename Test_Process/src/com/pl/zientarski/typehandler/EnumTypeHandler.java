package com.pl.zientarski.typehandler;

import com.pl.zientarski.JsonSchema4;
import com.pl.zientarski.MapperContext;
import com.pl.zientarski.TypeDescription;
import com.pl.zientarski.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class EnumTypeHandler implements TypeHandler {

    @Override
    public boolean accepts(final Type type) {
        return !(type instanceof ParameterizedType) && Utils.isEnumType((Class<?>) type);
    }

    @Override
    public JSONObject process(final TypeDescription typeDescription, final MapperContext mapperContext) {
        final Class<?> clazz = (Class<?>) typeDescription.getType();
        final JSONArray values = new JSONArray();
        Arrays.asList(clazz.getEnumConstants()).stream().forEach(value -> values.put(value.toString()));

        final JSONObject result = new JSONObject();
        result.put("enum", values);
        result.put("$schema", JsonSchema4.SCHEMA_REFERENCE);

        return result;
    }
}
