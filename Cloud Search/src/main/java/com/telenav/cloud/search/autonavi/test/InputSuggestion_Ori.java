package com.telenav.cloud.search.test;

import com.telenav.cloud.search.autonavi.test.WebConstKeys;
import com.telenav.cloud.search.autonavi.utils.coder.MD5Encoder;
import com.telenav.cloud.search.autonavi.utils.http.HttpRequest;

public class InputSuggestion_Ori {

	public String City = new String("");
	public String Words = new String("");
	public boolean adcode = false;

	private String getAuthenticationString()
	{
		return City + Words + "@" + WebConstKeys.Customer_Key_Telenav;
	}

	private String getSignatureString()
	{
		String str = getAuthenticationString();
		return MD5Encoder.getMD5(str.getBytes());
	}
	
	private String getSearchQuestString()
	{
		if (Words.isEmpty())
			return null;
		
		String request = new String("from=iPhone_5.1&output=json");
		request += "&channel=" + WebConstKeys.Customer_Channel_Telenav;
		if (!City.isEmpty())
			request += "&city=" + City;
		if (!Words.isEmpty())
			request += "&words=" + Words;
				
		request += "&adcode=";
		if (adcode)
			request += "true";
		else
			request += "false";
		
		request += "&sign=" + getSignatureString();
		
		return request;
	}
	
	public String search()
	{
		String sr = HttpRequest.sendGet(WebConstKeys.Web_Suggest_URL, getSearchQuestString());
        System.out.println(sr);
        return sr;
	}

	 public static void main(String[] args) 
	 {
		 InputSuggestion_Ori test = new InputSuggestion_Ori();
		 test.Words = "东方明珠";
		 System.out.println("Request = " + test.getSearchQuestString());
		 test.search();
		 
		 test.Words = "仙霞";
		 test.City = "上海";
		 System.out.println("Request = " + test.getSearchQuestString());
		 test.search(); 
		 
	 }
}
