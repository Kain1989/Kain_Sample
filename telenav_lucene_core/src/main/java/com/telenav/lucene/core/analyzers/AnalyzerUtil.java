package com.telenav.lucene.core.analyzers;

import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class AnalyzerUtil {

    public static SnowballPorterFilterFactory getSnowballPorterFilterFactory(String languageValue) {
        Map<String, String> args = new HashMap<String, String>();
        args.put("language", languageValue);

        SnowballPorterFilterFactory factory = new SnowballPorterFilterFactory(args);

        try {
            factory.inform(new ClasspathResourceLoader());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return factory;
    }
}
