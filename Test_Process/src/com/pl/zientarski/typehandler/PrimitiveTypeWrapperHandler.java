package com.pl.zientarski.typehandler;

import com.pl.zientarski.Utils;
import org.json.JSONObject;
import com.pl.zientarski.MapperContext;
import com.pl.zientarski.TypeDescription;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class PrimitiveTypeWrapperHandler extends PrimitiveTypeHandler {

    @Override
    public boolean accepts(final Type type) {
        return !(type instanceof ParameterizedType) && Utils.isPrimitiveTypeWrapper((Class<?>) type);
    }

    @Override
    public JSONObject process(final TypeDescription typeDescription, final MapperContext mapperContext) {
        final Class<?> clazz = (Class<?>) typeDescription.getType();
        return primitiveTypeSchema(Utils.unwrap(clazz));
    }
}
