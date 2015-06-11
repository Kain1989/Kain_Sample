package com.telenav.cloud.search.service;

import com.telenav.cloud.search.entity.poi.PoiResult;
import com.telenav.cloud.search.test.WebConstKeys;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

public class InputSuggestion extends SearchService<PoiResult> {

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

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"spring/appContext.xml"});


        InputSuggestion suggestion = (InputSuggestion) context.getBean("inputSuggestion");
        suggestion.Words = "东方明珠";
        PoiResult result = suggestion.search();
        System.out.println("Request = " + result.getAts().getTip_list());

        suggestion.Words = "仙霞";
        suggestion.City = "上海";
        System.out.println("Request = " + suggestion.search().getAts().getTip_list());

    }
}
