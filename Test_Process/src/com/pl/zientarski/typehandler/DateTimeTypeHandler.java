package com.pl.zientarski.typehandler;

import com.pl.zientarski.JsonSchema4;
import com.pl.zientarski.MapperContext;
import com.pl.zientarski.TypeDescription;
import com.pl.zientarski.Utils;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DateTimeTypeHandler implements TypeHandler {

    @Override
    public boolean accepts(final Type type) {
        return !(type instanceof ParameterizedType) && Utils.isDateTime((Class<?>) type);
    }

    @Override
    public JSONObject process(final TypeDescription typeDescription, final MapperContext mapperContext) {
        final JSONObject result = new JSONObject();
        result.put("type", JsonSchema4.TYPE_STRING);
        result.put("format", mapperContext.getDateTimeFormat());
        result.put("$schema", JsonSchema4.SCHEMA_REFERENCE);
        return result;
    }
}
