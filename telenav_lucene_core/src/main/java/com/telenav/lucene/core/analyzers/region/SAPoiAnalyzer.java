package com.telenav.lucene.core.analyzers.region;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.langs.CommonAnalyzer;
import com.telenav.lucene.core.analyzers.langs.EnglishEntityAnalyzer;
import com.telenav.lucene.core.analyzers.langs.PortugueseEntityAnalyzer;
import com.telenav.lucene.core.analyzers.langs.SpanishEntityAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;

import java.util.HashMap;
import java.util.Map;

public class SAPoiAnalyzer extends ComponentQueryIndexAnalyzer {

    private static SAPoiAnalyzer instance = new SAPoiAnalyzer();

    public static SAPoiAnalyzer getInstance() {
        return instance;
    }

    @Override
    Analyzer createAnalyzer(AnalyzerUseType type) {
        Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
        analyzerMap.put("NAME_WORDGRAM_ENG", EnglishEntityAnalyzer.getInstance().getAnalyzer(type).getPoiNameAnalyzer());
        analyzerMap.put("NAME_WORDGRAM_POR", PortugueseEntityAnalyzer.getInstance().getAnalyzer(type).getPoiNameAnalyzer());
        analyzerMap.put("NAME_WORDGRAM_SPA", SpanishEntityAnalyzer.getInstance().getAnalyzer(type).getPoiNameAnalyzer());

        analyzerMap.put("NAME_TRIGRAM", CommonAnalyzer.getTriGramAnalyzer());
        analyzerMap.put("METAPHONE", CommonAnalyzer.getPhoneticAnalyzer());

        analyzerMap.put("NAME_EXACT", CommonAnalyzer.getAsciiFoldingNormalizeAnalyzer());
        analyzerMap.put("POPULAR_NAME_EXACT", CommonAnalyzer.getAsciiFoldingNormalizeAnalyzer());
        analyzerMap.put("POPULAR_NAME_EXACT_OFFICIAL", CommonAnalyzer.getAsciiFoldingNormalizeAnalyzer());

        analyzerMap.put("NAME_WORDGRAM_EXACT", CommonAnalyzer.getStandardTokenizeWithASCIIFoldingAnalyzer());
        analyzerMap.put("NAME_WORDGRAM_MULTILANG", CommonAnalyzer.getStandardTokenizeWithASCIIFoldingAnalyzer());
        analyzerMap.put("POPULAR_NAME_WORDGRAM_EXACT", CommonAnalyzer.getStandardTokenizeWithASCIIFoldingAnalyzer());

        analyzerMap.put("STREET", CommonAnalyzer.getStandardTokenizeWithASCIIFoldingAnalyzer());
        analyzerMap.put("CITY", CommonAnalyzer.getStandardTokenizeWithASCIIFoldingAnalyzer());
        analyzerMap.put("STATE", CommonAnalyzer.getStandardTokenizeWithASCIIFoldingAnalyzer());
        analyzerMap.put("BUILD_UP", CommonAnalyzer.getStandardTokenizeWithASCIIFoldingAnalyzer());

        PerFieldAnalyzerWrapper wrapper = new PerFieldAnalyzerWrapper(new CommonAnalyzer.LowercaseAnalyzer(), analyzerMap);

        return wrapper;
    }
}
