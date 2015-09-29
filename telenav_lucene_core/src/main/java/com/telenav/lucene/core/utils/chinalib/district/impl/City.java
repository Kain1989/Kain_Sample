package com.telenav.lucene.core.utils.chinalib.district.impl;


import com.telenav.lucene.core.utils.chinalib.district.District;
import com.telenav.lucene.core.utils.chinalib.district.DistrictLevel;


public class City extends District {
	
	public City(String name,String fullname,String name_py,String fullname_py,District superDist)
	{
		super(name,fullname,name_py,fullname_py, DistrictLevel.CityLevel,superDist);
	}
	
}
