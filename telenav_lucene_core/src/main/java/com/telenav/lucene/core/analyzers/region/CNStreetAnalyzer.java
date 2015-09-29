package com.telenav.lucene.core.analyzers.region;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.langs.ChineseEntityAnalyzer;
import com.telenav.lucene.core.analyzers.langs.CommonAnalyzer;
import com.telenav.lucene.core.analyzers.langs.EnglishEntityAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;

import java.util.HashMap;
import java.util.Map;

public class CNStreetAnalyzer  extends ComponentQueryIndexAnalyzer{
    
	private static CNStreetAnalyzer instance = new CNStreetAnalyzer();
	public static CNStreetAnalyzer getInstance(){
		return instance;
	}
	
	@Override
	Analyzer createAnalyzer(AnalyzerUseType type){
		Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
		analyzerMap.putAll(CNCommonAnalyzer.createCNCommonAnalyzerMap(type));
		analyzerMap.put("street_eng", EnglishEntityAnalyzer.getInstance().getAnalyzer(type).getStreetAnalyzer());
		analyzerMap.put("street_chi", ChineseEntityAnalyzer.getInstance().getAnalyzer(type).getStreetAnalyzer());
		analyzerMap.put("street_geohash", CommonAnalyzer.getWhiteSpaceAnalyzer());
		analyzerMap.put("street_exact", CommonAnalyzer.getStreetExactAnalyzer());
		analyzerMap.put("street_gram_exact", CommonAnalyzer.getStandardTokenizeAnalyzer());
		analyzerMap.put("doornum", CommonAnalyzer.getWhiteSpaceAnalyzer());
		PerFieldAnalyzerWrapper wrapper = new PerFieldAnalyzerWrapper(new CommonAnalyzer.LowercaseAnalyzer(), analyzerMap);
		return wrapper;
	}
}
