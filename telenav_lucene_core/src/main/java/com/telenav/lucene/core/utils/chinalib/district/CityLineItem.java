package com.telenav.lucene.core.utils.chinalib.district;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CityLineItem
{
	public String state;
	public String county;
	public String city;
	public String stateBody;
	public String countyBody;
	public String cityBody;
	
	public String statePinyin;
	public String countyPinyin;
	public String cityPinyin;
	public String stateBodyPinyin;
	public String countyBodyPinyin;
	public String cityBodyPinyin;
	
	public List<String> stateEquals=new ArrayList<String>();
	public List<String> stateEqualsPY=new ArrayList<String>();
	public List<String> countyEquals=new ArrayList<String>();
	public List<String> countyEqualsPY=new ArrayList<String>();
	public List<String> cityEquals=new ArrayList<String>();
	public List<String> cityEqualsPY=new ArrayList<String>();
	
	
	//??,??,???;???,???,????????
	//[??????,????]
	// |34.99425,106.21188
	//|gan_su,tian_shui,zhang_jia_chuan;gan_su_sheng,tian_shui_shi,zhang_jia_chuan_hui_zu_zi_zhi_xian
	//[zhang_jia_chuan_zi_zhi_xian,zhang_jia_chuan_xian]
	public CityLineItem(String line)
	{
		if(StringUtils.isBlank(line))
			return;

//		if(line.equals("??,??;???,???????[?????,???,???]|34.98334,102.91104|gan_su,gan_nan;gan_su_sheng,gan_nan_zang_zu_zi_zhi_zhou[gan_nan_zi_zhi_zhou,gan_nan_zhou,gan_nan_shi]"))
//			System.out.println("e");

		String[] a=StringUtils.split(line,"|");
		String chLine=a[0],pyLine=a[1];
		//cLine: 	??,??;???,???????[?????,???,???]

		String[] a1=StringUtils.split(chLine,";");

		String[] a10=StringUtils.split(a1[0],",");
		//??,??,  a10[0...4]
		//?????,???,??? formatAlias[1]
		stateBody=a10[0];
		if(a10.length >= 2)
		    countyBody=a10[1];
		if(a10.length==3)
		    cityBody=a10[2];

		String[] a11=StringUtils.split(a1[1],"[]");
		String fnLine=a11[0]; //fnLine: ???,???????
		String aliasLine="";
		if(a11.length>1)
			aliasLine=a11[1];//aliasLine:?????,???,???
		String[] fnLineArr=StringUtils.split(fnLine,",");
		state=fnLineArr[0];
        if(fnLineArr.length >= 2)
            county=fnLineArr[1];
		if(fnLineArr.length==1&&StringUtils.isNotBlank(aliasLine))
        {

            String[] aliasArr=StringUtils.split(aliasLine,",");
            for(String al:aliasArr)
                stateEquals.add(al);
        }
		if(fnLineArr.length==2&&StringUtils.isNotBlank(aliasLine))
		{

			String[] aliasArr=StringUtils.split(aliasLine,",");
			for(String al:aliasArr)
				countyEquals.add(al);
		}
		if(fnLineArr.length==3)
		{
			city=fnLineArr[2];
			if(StringUtils.isNotBlank(aliasLine))
			{
				String[] aliasArr=StringUtils.split(aliasLine,",");
				for(String al:aliasArr)
				{
					cityEquals.add(al);
				}
			}
 
		}
		
		//pyLine:gan_su,gan_nan;gan_su_sheng,gan_nan_zang_zu_zi_zhi_zhou[gan_nan_zi_zhi_zhou,gan_nan_zhou,gan_nan_shi]
		String[] a1py=StringUtils.split(pyLine,";");

		String[] a10py=StringUtils.split(a1py[0],",");
	 
		stateBodyPinyin=a10py[0];
        if(a10py.length >= 2)
            countyBodyPinyin=a10py[1];
		if(a10py.length==3)
		    cityBodyPinyin=a10py[2];
		
		String[] a11py=StringUtils.split(a1py[1],"[]");
		String fnLinepy=a11py[0];  
		String aliasLinepy="";
		if(a11py.length>1)
			aliasLinepy=a11py[1]; 
		String[] fnLineArrpy=StringUtils.split(fnLinepy,",");
		statePinyin=fnLineArrpy[0];
        if(fnLineArrpy.length >= 2)
            countyPinyin=fnLineArrpy[1];
        if(fnLineArrpy.length==1&&StringUtils.isNotBlank(aliasLinepy))
        {

            String[] aliasArrpy=StringUtils.split(aliasLinepy,",");
            for(String alpy:aliasArrpy)
                stateEqualsPY.add(alpy);
        }
		if(fnLineArrpy.length==2&&StringUtils.isNotBlank(aliasLinepy))
		{

			String[] aliasArrpy=StringUtils.split(aliasLinepy,",");
			for(String alpy:aliasArrpy)
				countyEqualsPY.add(alpy);
		}
		if(fnLineArrpy.length==3)
		{
			cityPinyin=fnLineArrpy[2];
			if(StringUtils.isNotBlank(aliasLinepy))
			{
				String[] aliasArrpy=StringUtils.split(aliasLinepy,",");
				for(String alpy:aliasArrpy)
				{
					cityEqualsPY.add(alpy);
				}
			}
 
		}
		
		
	}

    public boolean isState()
    {
        return StringUtils.isBlank(county)&&StringUtils.isBlank(city);
    }

	public boolean isCounty()
	{
		return StringUtils.isNotBlank(county)&&StringUtils.isBlank(city);
	}
	public boolean isCity()
	{
		return StringUtils.isNotBlank(county)&&StringUtils.isNotBlank(city);
	}
}