package com.telenav.lucene.core.utils.chinalib.district;

public enum DistrictLevel {
	
	StateLevel,
	CountyLevel,
	CityLevel,
	Unknown;
	
	public static boolean higherThan(DistrictLevel high,DistrictLevel low)
	{
		return high.ordinal() < low.ordinal();
	}

}
