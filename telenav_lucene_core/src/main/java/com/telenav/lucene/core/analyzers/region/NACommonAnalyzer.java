package com.telenav.lucene.core.analyzers.region;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.langs.*;
import org.apache.lucene.analysis.Analyzer;

import java.util.HashMap;
import java.util.Map;

public class NACommonAnalyzer {
	public static Map<String, Analyzer> createNACommonAnalyzerMap(AnalyzerUseType type){
		Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
		EntityAnalyzer englishEntityAnalyzer = EnglishEntityAnalyzer.getInstance().getAnalyzer(type);
		
		Analyzer engAdminAnalyzer = englishEntityAnalyzer.getAdminAnalyzer();
		analyzerMap.put("city_eng", engAdminAnalyzer);
		analyzerMap.put("county_eng", engAdminAnalyzer);
		analyzerMap.put("locality_eng", engAdminAnalyzer);
		
		EntityAnalyzer frenchEntityAnalyzer = FrenchEntityAnalyzer.getInstance().getAnalyzer(type);
		
		Analyzer freAdminAnalyzer = frenchEntityAnalyzer.getAdminAnalyzer();
		analyzerMap.put("city_fre", freAdminAnalyzer);
		analyzerMap.put("county_fre", freAdminAnalyzer);
		analyzerMap.put("locality_fre", freAdminAnalyzer);
		
		EntityAnalyzer spanishEntityAnalyzer = SpanishEntityAnalyzer.getInstance().getAnalyzer(type);
		
		Analyzer spaAdminAnalyzer = spanishEntityAnalyzer.getAdminAnalyzer();
		analyzerMap.put("city_spa", spaAdminAnalyzer);
		analyzerMap.put("county_spa", spaAdminAnalyzer);
		analyzerMap.put("locality_spa", spaAdminAnalyzer);
		
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
