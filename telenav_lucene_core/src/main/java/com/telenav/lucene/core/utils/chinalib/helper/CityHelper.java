package com.telenav.lucene.core.utils.chinalib.helper;


import com.telenav.lucene.core.utils.chinalib.district.District;

/**
 * @author kcui
 *
 */
public class CityHelper
{
    public static String getBodyFromCity(String city)
    {
    	District fullNameDistrict = ChinaDistrictHelper.getByFullName(city);
    	if(fullNameDistrict == null)
    		return city;
    	else
    		return fullNameDistrict.getName();
    }
}
