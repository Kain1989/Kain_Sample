package com.pl.zientarski.serialization;

import com.pl.zientarski.MapperContext;
import com.pl.zientarski.PropertyDescription;

public class PrimitiveSerializer extends ReferenceSerializer {

    public PrimitiveSerializer(final PropertyDescription propertyDescription, final MapperContext mapperContext) {
        super(propertyDescription, mapperContext);
    }

    @Override
    public boolean isPropertyRequired() {
        return true;
    }

    @Override
    protected String getReference() {
        return propertyDescription.getType().getTypeName();
    }
}
