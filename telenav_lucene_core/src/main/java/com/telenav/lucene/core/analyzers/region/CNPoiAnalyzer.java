package com.telenav.lucene.core.analyzers.region;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.langs.ChineseEntityAnalyzer;
import com.telenav.lucene.core.analyzers.langs.CommonAnalyzer;
import com.telenav.lucene.core.analyzers.langs.EnglishEntityAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;

import java.util.HashMap;
import java.util.Map;

public class CNPoiAnalyzer extends ComponentQueryIndexAnalyzer{
	private static CNPoiAnalyzer instance = new CNPoiAnalyzer();
	public static CNPoiAnalyzer getInstance(){
		return instance;
	}
	
	@Override
	Analyzer createAnalyzer(AnalyzerUseType type){
		Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
		analyzerMap.put("NAME_WORDGRAM_ENG", EnglishEntityAnalyzer.getInstance().getAnalyzer(type).getPoiNameAnalyzer());
		analyzerMap.put("NAME_WORDGRAM_CHI", ChineseEntityAnalyzer.getInstance().getAnalyzer(type).getPoiNameAnalyzer());
		
		analyzerMap.put("NAME_TRIGRAM", CommonAnalyzer.getTriGramAnalyzer());
		analyzerMap.put("METAPHONE", CommonAnalyzer.getPhoneticAnalyzer());
		
		analyzerMap.put("NAME_EXACT", CommonAnalyzer.getAsciiFoldingNormalizeAnalyzer());
		analyzerMap.put("POPULAR_NAME_EXACT", CommonAnalyzer.getAsciiFoldingNormalizeAnalyzer());
		analyzerMap.put("POPULAR_NAME_EXACT_OFFICIAL", CommonAnalyzer.getAsciiFoldingNormalizeAnalyzer());
		
		analyzerMap.put("NAME_WORDGRAM_EXACT", CommonAnalyzer.getStandardTokenizeWithASCIIFoldingAnalyzer());
		analyzerMap.put("NAME_WORDGRAM_MULTILANG", CommonAnalyzer.getStandardTokenizeWithASCIIFoldingAnalyzer());
		analyzerMap.put("POPULAR_NAME_WORDGRAM_EXACT", CommonAnalyzer.getStandardTokenizeWithASCIIFoldingAnalyzer());
		
		analyzerMap.put("STREET", CommonAnalyzer.getChinesePoiStreetAnalyzer());
		analyzerMap.put("CITY", CommonAnalyzer.getChinesePoiCityAnalyzer());
		analyzerMap.put("STATE", CommonAnalyzer.getChinesePoiCityAnalyzer());
		analyzerMap.put("COUNTY", CommonAnalyzer.getChinesePoiCityAnalyzer());
		analyzerMap.put("BUILD_UP", CommonAnalyzer.getChinesePoiCityAnalyzer());

		PerFieldAnalyzerWrapper wrapper = new PerFieldAnalyzerWrapper(new CommonAnalyzer.LowercaseAnalyzer(), analyzerMap);
		
		return wrapper;
	}
}
