package com.telenav.lucene.core.analyzers;

import com.telenav.lucene.core.analyzers.filters.ChineseCityBodyFilter;
import com.telenav.lucene.core.analyzers.filters.PuncRemoverFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cn.smart.SentenceTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.util.Version;

import java.io.Reader;

/**
 * @author: rapu
 * @date: 2010-8-12
 * @time: 11:14:58
 * TeleNav Inc.
 */
public class ChineseCityAnalyzer extends Analyzer
{
    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        final Tokenizer source = new SentenceTokenizer(reader);
        TokenStream ts = new LowerCaseFilter(Version.LUCENE_45, source);
        ts = new ChineseCityBodyFilter(ts);
        ts = new PuncRemoverFilter(ts);
        return new TokenStreamComponents(source, ts);
    }
}
