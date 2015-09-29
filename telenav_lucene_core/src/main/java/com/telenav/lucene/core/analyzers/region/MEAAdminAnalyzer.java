package com.telenav.lucene.core.analyzers.region;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.langs.*;
import org.apache.lucene.analysis.Analyzer;

import java.util.HashMap;
import java.util.Map;

public class MEAAdminAnalyzer extends ComponentQueryIndexAnalyzer {

    private static MEAAdminAnalyzer instance = new MEAAdminAnalyzer();

    public static MEAAdminAnalyzer getInstance() {
        return instance;
    }

    Analyzer createAnalyzer(AnalyzerUseType type) {
        Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
        analyzerMap.putAll(MEACommonAnalyzer.createMEACommonAnalyzerMap(type));

        EntityAnalyzer englishEntityAnalyzer = EnglishEntityAnalyzer.getInstance().getAnalyzer(type);
        Analyzer engAdminBodyAnalyzer = englishEntityAnalyzer.getAdminBodyAnalyzer();
        analyzerMap.put("city_body_eng", engAdminBodyAnalyzer);
        analyzerMap.put("county_body_eng", engAdminBodyAnalyzer);
        analyzerMap.put("locality_body_eng", engAdminBodyAnalyzer);

        EntityAnalyzer frenchEntityAnalyzer = FrenchEntityAnalyzer.getInstance().getAnalyzer(type);
        Analyzer freAdminBodyAnalyzer = frenchEntityAnalyzer.getAdminBodyAnalyzer();
        analyzerMap.put("city_body_fre", freAdminBodyAnalyzer);
        analyzerMap.put("county_body_fre", freAdminBodyAnalyzer);
        analyzerMap.put("locality_body_fre", freAdminBodyAnalyzer);
        
        EntityAnalyzer arabicEntityAnalyzer = ArabicEntityAnalyzer.getInstance().getAnalyzer(type);
        Analyzer arabicAdminBodyAnalyzer = arabicEntityAnalyzer.getAdminBodyAnalyzer();
        analyzerMap.put("city_body_ara", arabicAdminBodyAnalyzer);
        analyzerMap.put("county_body_ara", arabicAdminBodyAnalyzer);
        analyzerMap.put("locality_body_ara", arabicAdminBodyAnalyzer);
        
        ExtendPerFieldAnalyzerWrapper wrapper = new ExtendPerFieldAnalyzerWrapper(new CommonAnalyzer.LowercaseAnalyzer(), analyzerMap);

        return wrapper;
    }
}
