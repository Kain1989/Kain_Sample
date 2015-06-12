package com.telenav.cloud.search.service;

import com.telenav.cloud.search.entity.CloudResult;
import com.telenav.cloud.search.test.WebConstKeys;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public class InputSuggestion extends SearchService<CloudResult> {

    private static Logger logger = Logger.getLogger(InputSuggestion.class);

    public String City = new String("");
    public String Words = new String("");
    public boolean adcode = false;

    @Override
    protected String getAuthenticationString() {
        return City + Words + "@" + WebConstKeys.Customer_Key_Telenav;
    }

    @Override
    protected Map<String, Object> generateQueryParameters() {

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        if (Words.isEmpty())
            return null;

        params.put("from", "iPhone_5.1");
        params.put("output", "json");
        params.put("channel", WebConstKeys.Customer_Channel_Telenav);

        if (!City.isEmpty()) {
            params.put("city", City);
        }

        if (!Words.isEmpty()) {
            params.put("words", Words);
        }

        if (adcode) {
            params.put("adcode", "true");
        } else {
            params.put("adcode", "false");
        }

        params.put("sign", getSignatureString());

        return params;
    }

}
