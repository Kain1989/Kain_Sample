package com.generator;

import com.pl.zientarski.SchemaMapper;
import com.telenav.cloud.search.autonavi.entity.response.Ats;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by zfshi on 9/17/2015.
 */
public class JsonSchemaGenerator {

    public static void main(String[] args) throws IOException {
        SchemaMapper schemaMapper = new SchemaMapper();
        JSONObject jsonObject = schemaMapper.toJsonSchema4(Ats.class, true);
        System.out.println(jsonObject.toString(4));
    }
}
