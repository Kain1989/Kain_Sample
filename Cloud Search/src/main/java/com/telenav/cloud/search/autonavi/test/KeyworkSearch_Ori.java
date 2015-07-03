package com.telenav.cloud.search.autonavi.test;

import com.telenav.cloud.search.autonavi.utils.coder.MD5Encoder;
import com.telenav.cloud.search.autonavi.utils.http.HttpRequest;

/**
 * Created by zfshi on 6/5/2015.
 */
public class KeyworkSearch_Ori {

    public String Query_type = new String("TQUERY");
    public String Data_type = new String("POI+ROAD+ROADINTER");
    //public String Data_type = new String("POI");
    public String City = new String("");
    public String Keywords = new String("");
    public String Category = new String("");

    public double lat;
    public double lon;
    public long	Page_size = 20;
    public long Page_number = 1;
    public boolean addr_poi_merge = false;

    private String getAuthenticationString()
    {
        //id, longitude, latitude, keywords, category, center, geobj + @ + telenav key
        return lon + lat + Keywords + Category + "@" + WebConstKeys.Customer_Key_Telenav;
    }

    private String getSignatureString()
    {
        String str = getAuthenticationString();
        return MD5Encoder.getMD5(str.getBytes());
    }

    private String getSearchQuestString()
    {
        if (Keywords.isEmpty() && Category.isEmpty())
            return null;

        String request = new String("from=iPhone_5.1&output=json");
        request += "&channel=" + WebConstKeys.Customer_Channel_Telenav;
        request += "&query_type=" + Query_type;
        request += "&data_type=" + Data_type;
        if (!City.isEmpty())
            request += "&city=" + City;
        if (!Keywords.isEmpty())
            request += "&keywords=" + Keywords;
        if (!Category.isEmpty())
            request += "&category=" + Category;
        request +="longitude=" + lon;
        request +="latitude=" + lat;

        request += "&pagesize=" + Page_size;
        request += "&pagenum=" + Page_number;

        request += "&addr_poi_merge=";
        if (addr_poi_merge)
            request += "true";
        else
            request += "false";

        request += "&sign=" + getSignatureString();

        return request;
    }

    public String search()
    {
        String sr = HttpRequest.sendGet(WebConstKeys.Web_POI_Search_URL, getSearchQuestString());
        System.out.println(sr);
        return sr;
    }

    public static void main(String[] args)
    {
        KeyworkSearch_Ori test = new KeyworkSearch_Ori();
        test.Keywords = "东方明珠";
        test.lat = 31.294162;
        test.lon = 121.16845;
        System.out.println("Request = " + test.getSearchQuestString());
        test.search();

//        KeyworkSearch_Ori test1 = new KeyworkSearch_Ori();
//        test1.Keywords = "仙霞";
//        test1.City = "上海";
//        System.out.println("Request = " + test1.getSearchQuestString());
//        test1.search();
//
//        KeyworkSearch_Ori test2 = new KeyworkSearch_Ori();
//        test2.Keywords = "上海仙霞";
//        System.out.println("Request = " + test2.getSearchQuestString());
//        test2.search();
//
//        KeyworkSearch_Ori test3 = new KeyworkSearch_Ori();
//        test3.Keywords = "dfmz";
//        test3.City = "上海";
//        System.out.println("Request = " + test3.getSearchQuestString());
//        test3.search();

    }
}
