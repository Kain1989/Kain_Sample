package com.telenav.lucene.core.analyzers.region;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.langs.ArabicEntityAnalyzer;
import com.telenav.lucene.core.analyzers.langs.CommonAnalyzer;
import com.telenav.lucene.core.analyzers.langs.EnglishEntityAnalyzer;
import com.telenav.lucene.core.analyzers.langs.FrenchEntityAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;

import java.util.HashMap;
import java.util.Map;

public class MEAStreetAnalyzer  extends ComponentQueryIndexAnalyzer{
	private static MEAStreetAnalyzer instance = new MEAStreetAnalyzer();
	public static MEAStreetAnalyzer getInstance(){
		return instance;
	}
	
	@Override
	Analyzer createAnalyzer(AnalyzerUseType type){
		Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
		analyzerMap.putAll(MEACommonAnalyzer.createMEACommonAnalyzerMap(type));
		analyzerMap.put("street_eng", EnglishEntityAnalyzer.getInstance().getAnalyzer(type).getStreetAnalyzer());
		analyzerMap.put("street_fre", FrenchEntityAnalyzer.getInstance().getAnalyzer(type).getStreetAnalyzer());
		analyzerMap.put("street_ara", ArabicEntityAnalyzer.getInstance().getAnalyzer(type).getStreetAnalyzer());
		analyzerMap.put("street_gram_exact", CommonAnalyzer.getStandardTokenizeAnalyzer());
		analyzerMap.put("street_geohash", CommonAnalyzer.getWhiteSpaceAnalyzer());
		analyzerMap.put("doornum", CommonAnalyzer.getWhiteSpaceAnalyzer());
		analyzerMap.put("street_exact", CommonAnalyzer.getStreetExactAnalyzer());
		PerFieldAnalyzerWrapper wrapper = new PerFieldAnalyzerWrapper(new CommonAnalyzer.LowercaseAnalyzer(), analyzerMap);
		return wrapper;
	}
}
