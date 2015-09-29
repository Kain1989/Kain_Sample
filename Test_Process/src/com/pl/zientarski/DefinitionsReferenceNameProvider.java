package com.pl.zientarski;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;

public class DefinitionsReferenceNameProvider extends DefaultReferenceNameProvider {
    @Override
    protected String getPrefix() {
        return "#/definitions/";
    }

    @Override
    public String typeReferenceName(Class<?> clazz, List<Type> genericTypeArguments) {
        try {
            final StringBuilder ref = new StringBuilder();
            if (Utils.isPrimitiveTypeWrapper(clazz)) {
                ref.append(Utils.unwrap(clazz).getSimpleName());
            } else {
                ref.append(clazz.getSimpleName());
            }
            appendGenericType(ref, genericTypeArguments);

            return getPrefix() + URLEncoder.encode(ref.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new MappingException(e);
        }
    }
}
