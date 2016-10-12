package com.pl.zientarski.typehandler;

import org.json.JSONObject;
import com.pl.zientarski.MapperContext;
import com.pl.zientarski.TypeDescription;

import java.lang.reflect.Type;

public interface TypeHandler {
    boolean accepts(Type type);

    JSONObject process(TypeDescription typeDescription, MapperContext mapperContext);
}
