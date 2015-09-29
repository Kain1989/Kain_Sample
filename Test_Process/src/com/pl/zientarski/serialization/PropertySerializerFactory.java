package com.pl.zientarski.serialization;

import com.pl.zientarski.Utils;
import com.pl.zientarski.MapperContext;
import com.pl.zientarski.PropertyDescription;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;

public interface PropertySerializerFactory {
    public static PropertySerializer get(final PropertyDescription description, final MapperContext mapperContext) {
        final Type type = description.getType();
        return getForType(type, description, mapperContext);
    }

    public static PropertySerializer getForType(final Type type, final PropertyDescription description, final MapperContext mapperContext) {
        if (type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            if (Collection.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                return new CollectionSerializer(description, parameterizedType.getActualTypeArguments()[0], mapperContext);
            }
            return new GenericObjectSerializer(description, mapperContext);
        } else if (type instanceof TypeVariable) {
            final TypeVariable<?> typeVariable = (TypeVariable<?>) type;
            final Type genericType = mapperContext.getGenericTypeByName(typeVariable.getTypeName());
            return getForType(genericType, description, mapperContext);
        } else {
            final Class<?> clazz = (Class<?>) type;
            if (Utils.isArrayType(clazz)) {
                return new CollectionSerializer(description, clazz.getComponentType(), mapperContext);
            }
            if (Collection.class.isAssignableFrom(clazz)) {
                return new CollectionSerializer(description, Object.class, mapperContext);
            }
            if (Utils.isDirectlyMappedToJsonSchemaType(clazz)) {
                return new DirectTypeSerializer(description, mapperContext);
            }
            if (Utils.isPrimitiveType(clazz)) {
                return new PrimitiveSerializer(description, mapperContext);
            }
            if (Utils.isEnumType(clazz)) {
                return new ObjectSerializer(description, mapperContext);
            }
        }

        return new ObjectSerializer(description, mapperContext);
    }
}
