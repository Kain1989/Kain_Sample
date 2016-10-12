package com.pl.zientarski.serialization;

import com.pl.zientarski.JsonSchema4;
import org.json.JSONObject;
import com.pl.zientarski.MapperContext;
import com.pl.zientarski.MappingException;
import com.pl.zientarski.PropertyDescription;

import java.lang.reflect.ParameterizedType;

public class GenericObjectSerializer extends ObjectSerializer {

    private final ParameterizedType type;

    public GenericObjectSerializer(final PropertyDescription propertyDescription, final MapperContext mapperContext) {
        super(propertyDescription, mapperContext);
        type = (ParameterizedType) propertyDescription.getType();
    }

    @Override
    public JSONObject toJsonObject() {
        final JSONObject result = new JSONObject();
        if (type.getActualTypeArguments().length != 1) {
            if (mapperContext.isStrict()) {
                throw new MappingException("Could not serialize type:" + type);
            } else {
                result.put("type", JsonSchema4.TYPE_ANY);
            }
        } else {
            result.put("$ref", getReference());
        }
        return result;
    }

    @Override
    protected String getReference() {
        mapperContext.addDependency(type);
        return mapperContext.getTypeReference(type);
    }
}
