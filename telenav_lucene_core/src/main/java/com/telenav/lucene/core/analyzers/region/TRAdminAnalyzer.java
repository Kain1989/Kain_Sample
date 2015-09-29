package com.telenav.lucene.core.analyzers.region;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.langs.*;
import org.apache.lucene.analysis.Analyzer;

import java.util.HashMap;
import java.util.Map;

public class TRAdminAnalyzer extends ComponentQueryIndexAnalyzer {

    private static TRAdminAnalyzer instance = new TRAdminAnalyzer();

    public static TRAdminAnalyzer getInstance() {
        return instance;
    }

    Analyzer createAnalyzer(AnalyzerUseType type) {
        Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
        analyzerMap.putAll(TRCommonAnalyzer.createTRCommonAnalyzerMap(type));

        EntityAnalyzer englishEntityAnalyzer = EnglishEntityAnalyzer.getInstance().getAnalyzer(type);
        Analyzer engAdminBodyAnalyzer = englishEntityAnalyzer.getAdminBodyAnalyzer();
        analyzerMap.put("city_body_eng", engAdminBodyAnalyzer);
        analyzerMap.put("county_body_eng", engAdminBodyAnalyzer);
        analyzerMap.put("locality_body_eng", engAdminBodyAnalyzer);

        EntityAnalyzer russianEntityAnalyzer = RussianEntityAnalyzer.getInstance().getAnalyzer(type);
        Analyzer russianAdminBodyAnalyzer = russianEntityAnalyzer.getAdminBodyAnalyzer();
        analyzerMap.put("city_body_rus", russianAdminBodyAnalyzer);
        analyzerMap.put("county_body_rus", russianAdminBodyAnalyzer);
        analyzerMap.put("locality_body_rus", russianAdminBodyAnalyzer);
        
        EntityAnalyzer turkishEntityAnalyzer = TurkishEntityAnalyzer.getInstance().getAnalyzer(type);
        Analyzer turkishAdminBodyAnalyzer = turkishEntityAnalyzer.getAdminBodyAnalyzer();
        analyzerMap.put("city_body_tur", turkishAdminBodyAnalyzer);
        analyzerMap.put("county_body_tur", turkishAdminBodyAnalyzer);
        analyzerMap.put("locality_body_tur", turkishAdminBodyAnalyzer);
        
        ExtendPerFieldAnalyzerWrapper wrapper = new ExtendPerFieldAnalyzerWrapper(new CommonAnalyzer.LowercaseAnalyzer(), analyzerMap);

        return wrapper;
    }
}
