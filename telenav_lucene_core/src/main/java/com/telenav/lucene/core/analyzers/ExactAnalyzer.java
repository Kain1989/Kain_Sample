package com.telenav.lucene.core.analyzers;

import com.telenav.address.common.datatype.Region;
import com.telenav.lucene.core.analyzers.filters.NonAlphaNumberFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.util.Version;

import java.io.Reader;

//import com.telenav.onboard.commons.analyzers.filters.NonAlphaNumberFilter;

public class ExactAnalyzer extends Analyzer {

    private boolean withStemming = true;

    public ExactAnalyzer() {
        this.withStemming = true;
    }

    public ExactAnalyzer(boolean withSteming) {
        this.withStemming = withSteming;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        final Tokenizer source = new KeywordTokenizer(reader);
        TokenStream ts = source;
        ts = new ASCIIFoldingFilter(ts);
        ts = new LowerCaseFilter(Version.LUCENE_45, ts);
        // the chain index schema need be configured per region to avoid the following workaround
        if (!Region.is(Region.CN))
            ts = new NonAlphaNumberFilter(ts);
        ts = new TrimFilter(Version.LUCENE_45, ts);
        if (withStemming) {
            ts = new KStemFilter(ts);
        }
        return new TokenStreamComponents(source, ts);
    }
}