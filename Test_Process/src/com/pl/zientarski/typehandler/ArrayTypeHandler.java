package com.pl.zientarski.typehandler;

import com.pl.zientarski.JsonSchema4;
import com.pl.zientarski.Utils;
import org.json.JSONObject;
import com.pl.zientarski.MapperContext;
import com.pl.zientarski.TypeDescription;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ArrayTypeHandler implements TypeHandler {

    @Override
    public boolean accepts(final Type type) {
        return !(type instanceof ParameterizedType) && Utils.isArrayType((Class<?>) type);
    }

    @Override
    public JSONObject process(final TypeDescription typeDescription, final MapperContext mapperContext) {
        final Class<?> clazz = (Class<?>) typeDescription.getType();
        return arrayTypeSchema(Utils.getTypeArgument(clazz), mapperContext);
    }

    public static JSONObject arrayTypeSchema(final Type typeArgument, final MapperContext mapperContext) {
        JSONObject items = new JSONObject();
        if (Utils.isParameterizedType(typeArgument)) {
            items = arrayTypeSchema(Utils.getTypeArgument((ParameterizedType) typeArgument), mapperContext);
        } else {
            final Class<?> itemClass = (Class<?>) typeArgument;
            if (itemClass.equals(boolean.class) || itemClass.equals(Boolean.class)) {
                items.put("type", JsonSchema4.TYPE_BOOLEAN);
            } else if (itemClass.equals(String.class)) {
                items.put("type", JsonSchema4.TYPE_STRING);
            } else if (itemClass.isArray()) {
                items = arrayTypeSchema(Utils.getTypeArgument(itemClass), mapperContext);
            } else {
                items.put("$ref", mapperContext.getTypeReference(itemClass));
                mapperContext.addDependency(typeArgument);
            }
        }

        final JSONObject result = new JSONObject();
        result.put("type", JsonSchema4.TYPE_ARRAY);
        result.put("items", items);
        result.put("$schema", JsonSchema4.SCHEMA_REFERENCE);
        return result;
    }

}
