package com.pl.zientarski.typehandler;

import com.pl.zientarski.JsonSchema4;
import org.json.JSONObject;
import com.pl.zientarski.MapperContext;
import com.pl.zientarski.MappingException;
import com.pl.zientarski.TypeDescription;

import java.lang.reflect.Type;

public class AnyTypeHandler implements TypeHandler {
    @Override
    public boolean accepts(final Type type) {
        return true;
    }

    @Override
    public JSONObject process(final TypeDescription typeDescription, final MapperContext mapperContext) {
        if (mapperContext.isStrict()) {
            throw new MappingException("Cannot process type:" + typeDescription.getType().getTypeName());
        } else {
            final JSONObject any = new JSONObject();
            any.put("type", JsonSchema4.TYPE_ANY);
            return any;
        }
    }
}
