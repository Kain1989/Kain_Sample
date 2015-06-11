package com.telenav.cloud.search.service;

import com.telenav.cloud.search.entity.suggestion.InputSuggestionResult;
import com.telenav.cloud.search.test.WebConstKeys;
import com.telenav.cloud.search.utils.coder.MD5Encoder;
import com.telenav.cloud.search.utils.http.TelenavHttpClient;
import com.telenav.cloud.search.utils.http.TelenavHttpResponse;
import com.telenav.cloud.search.utils.json.JsonUtils;
import com.telenav.cloud.search.utils.uri.TelenavUriBuilder;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputSuggestion {

    private static Logger logger = Logger.getLogger(InputSuggestion.class);

    private TelenavHttpClient httpClient;

    private String urlPrefix;

    public void setHttpClient(TelenavHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public String City = new String("");
    public String Words = new String("");
    public boolean adcode = false;

    private String getAuthenticationString() {
        return City + Words + "@" + WebConstKeys.Customer_Key_Telenav;
    }

    private String getSignatureString() {
        String str = getAuthenticationString();
        return MD5Encoder.getMD5(str.getBytes());
    }

    private Map<String, Object> generateQueryParameters() {

        Map<String, Object> params = new HashMap<String, Object>();
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
        }
        else {
            params.put("adcode", "false");
        }

        params.put("sign", getSignatureString());

        return params;
    }

    public InputSuggestionResult search() {
        InputSuggestionResult result = null;
        TelenavHttpResponse response = null;
        Map<String, Object> params = this.generateQueryParameters();
        try {
            response = httpClient.doGet(TelenavUriBuilder.build(urlPrefix, params).toString());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        if (response.getStatus() == HttpStatus.SC_OK) {
            System.out.println("Search success : " + response.getBody());
            result = JsonUtils.fromJson(response.getBody(), InputSuggestionResult.class);
            return result;
        } else {
            return result;
        }

    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"spring/appContext.xml"});


        InputSuggestion suggestion = (InputSuggestion) context.getBean("inputSuggestion");
        suggestion.Words = "东方明珠";
        System.out.println("Request = " + suggestion.search().getAts().getTip_list());

        suggestion.Words = "仙霞";
        suggestion.City = "上海";
        System.out.println("Request = " + suggestion.search().getAts().getTip_list());

    }
}
