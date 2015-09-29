package com.pl.zientarski.serialization;

import com.pl.zientarski.JsonSchema4;
import com.pl.zientarski.Utils;
import org.json.JSONObject;
import com.pl.zientarski.MapperContext;
import com.pl.zientarski.PropertyDescription;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

public class CollectionSerializer extends PropertySerializer {

    private final Type collectedType;

    public CollectionSerializer(final PropertyDescription propertyDescription, final Type collectedType, final MapperContext mapperContext) {
        super(propertyDescription, mapperContext);
        this.collectedType = collectedType;
    }

    @Override
    public JSONObject toJsonObject() {
        return arrayTypeSchema(collectedType);
    }

    protected JSONObject arrayTypeSchema(final Type typeArgument) {
        JSONObject items = new JSONObject();
        final Type itemType = getItemType(typeArgument);
        if (Utils.isParameterizedType(itemType)) {
            items = arrayTypeSchema(Utils.getTypeArgument((ParameterizedType) itemType));
        } else if(Utils.isTypeVariable(itemType)) {
            final Type genericTypeByName = mapperContext.getGenericTypeByName(itemType.getTypeName());
            items.put("$ref", mapperContext.getTypeReference(genericTypeByName));
        } else {

            final Class<?> itemClass = (Class<?>) itemType;

            if (itemClass.equals(boolean.class) || itemClass.equals(Boolean.class)) {
                items.put("type", JsonSchema4.TYPE_BOOLEAN);
            } else if (itemClass.equals(Object.class)) {
                items.put("type", JsonSchema4.TYPE_ANY);
            } else if (itemClass.equals(String.class)) {
                items.put("type", JsonSchema4.TYPE_STRING);
            } else if (itemClass.isArray()) {
                items = arrayTypeSchema(Utils.getTypeArgument(itemClass));
            } else {
                items.put("$ref", mapperContext.getTypeReference(itemClass));
                mapperContext.addDependency(itemType);
            }
        }

        final JSONObject result = new JSONObject();
        result.put("type", JsonSchema4.TYPE_ARRAY);
        result.put("items", items);
        return result;
    }

    private Type getItemType(final Type typeArgument) {
        if (Utils.isWildcardType(typeArgument)) {
            return Utils.getTypeArgument((WildcardType) typeArgument);
        }
        return typeArgument;
    }
}
