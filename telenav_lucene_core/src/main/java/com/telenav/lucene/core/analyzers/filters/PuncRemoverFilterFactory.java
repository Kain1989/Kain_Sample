/**
 *
 * Copyright 2015 TeleNav, Inc. All rights reserved.
 * PuncRemoverFilterFactory.java
 *
 */
package com.telenav.lucene.core.analyzers.filters;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;


/**
 *@author changzhengj (changzhengj@telenav.com)
 *@date Jan 13, 2015
 */
public class PuncRemoverFilterFactory extends TokenFilterFactory{

    public PuncRemoverFilterFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public TokenStream create(TokenStream input) {
        return new PuncRemoverFilter(input);
    }

}
