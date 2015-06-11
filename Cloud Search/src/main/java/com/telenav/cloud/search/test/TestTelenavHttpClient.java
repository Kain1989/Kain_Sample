package com.telenav.cloud.search.test;

import com.telenav.cloud.search.utils.http.TelenavHttpClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by zfshi on 6/4/2015.
 */
public class TestTelenavHttpClient {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"spring/appContext.xml"});
        TelenavHttpClient client = (TelenavHttpClient) context.getBean("telenavHttpClient");
        client.testRun();
    }
}
