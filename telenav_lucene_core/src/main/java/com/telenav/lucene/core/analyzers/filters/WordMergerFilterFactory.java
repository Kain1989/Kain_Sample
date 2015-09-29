/**
 *
 * Copyright 2015 TeleNav, Inc. All rights reserved.
 * WordMergerFilterFactory.java
 *
 */
package com.telenav.lucene.core.analyzers.filters;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;


/**
 *@author changzhengj (changzhengj@telenav.com)
 *@date Feb 11, 2015
 */
public class WordMergerFilterFactory extends TokenFilterFactory {
    public WordMergerFilterFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public TokenStream create(TokenStream ts) {
        return new WordMergerFilter(ts);
    }    
}
