package com.telenav.lucene.core.analyzers.region;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.langs.*;
import org.apache.lucene.analysis.Analyzer;

import java.util.HashMap;
import java.util.Map;

public class TRCommonAnalyzer {
	public static Map<String, Analyzer> createTRCommonAnalyzerMap(AnalyzerUseType type){
		Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
		
		EntityAnalyzer englishEntityAnalyzer = EnglishEntityAnalyzer.getInstance().getAnalyzer(type);
		Analyzer engAdminAnalyzer = englishEntityAnalyzer.getAdminAnalyzer();
		analyzerMap.put("city_eng", engAdminAnalyzer);
		analyzerMap.put("county_eng", engAdminAnalyzer);
		analyzerMap.put("locality_eng", engAdminAnalyzer);
		
		EntityAnalyzer russianEntityAnalyzer = RussianEntityAnalyzer.getInstance().getAnalyzer(type);
        Analyzer russianAdminAnalyzer = russianEntityAnalyzer.getAdminAnalyzer();
        analyzerMap.put("city_rus", russianAdminAnalyzer);
        analyzerMap.put("county_rus", russianAdminAnalyzer);
        analyzerMap.put("locality_rus", russianAdminAnalyzer);
        
		EntityAnalyzer turkishEntityAnalyzer = TurkishEntityAnalyzer.getInstance().getAnalyzer(type);
        Analyzer turkishAdminAnalyzer = turkishEntityAnalyzer.getAdminAnalyzer();
        analyzerMap.put("city_tur", turkishAdminAnalyzer);
        analyzerMap.put("county_tur", turkishAdminAnalyzer);
        analyzerMap.put("locality_tur", turkishAdminAnalyzer);
        
		Analyzer standardTokenizeAnalyzer = CommonAnalyzer.getStandardTokenizeAnalyzer();
        analyzerMap.put("city_gram_exact", standardTokenizeAnalyzer);
        analyzerMap.put("county_gram_exact", standardTokenizeAnalyzer);
        analyzerMap.put("locality_gram_exact", standardTokenizeAnalyzer);
        analyzerMap.put("state_gram_exact", standardTokenizeAnalyzer);
		
		analyzerMap.put("admin_geohash", CommonAnalyzer.getWhiteSpaceAnalyzer());
		
		Analyzer lowercaseTokenizeAnalyzer = CommonAnalyzer.getLowerCaseAnalyzer();
		analyzerMap.put("fullname", lowercaseTokenizeAnalyzer);
		analyzerMap.put("city_state", lowercaseTokenizeAnalyzer);
		analyzerMap.put("country", lowercaseTokenizeAnalyzer);
		analyzerMap.put("postalcode", lowercaseTokenizeAnalyzer);
		
		Analyzer icuNormalizeAnalyzer = CommonAnalyzer.getICUNormalizeAnalyzer();
		analyzerMap.put("state_exact", icuNormalizeAnalyzer);
		analyzerMap.put("city_exact", icuNormalizeAnalyzer);
		analyzerMap.put("locality_exact", icuNormalizeAnalyzer);
		analyzerMap.put("county_exact", icuNormalizeAnalyzer);
		
		return analyzerMap;
	}
}
