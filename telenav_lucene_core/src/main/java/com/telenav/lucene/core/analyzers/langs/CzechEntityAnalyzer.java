package com.telenav.lucene.core.analyzers.langs;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.filters.PuncRemoverFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.cz.CzechStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

import java.io.Reader;

public class CzechEntityAnalyzer extends QueryIndexAnalyzer {

    private static CzechEntityAnalyzer instance = new CzechEntityAnalyzer();

    public static CzechEntityAnalyzer getInstance() {
        return instance;
    }

    private CzechEntityAnalyzer() {
        CzechAdminBodyAnalyzer adminBodyAnalyzer = new CzechAdminBodyAnalyzer();
        CzechPoiNameAnalyzer poiNameAnalyzer = new CzechPoiNameAnalyzer();

        CzechAddressAnalyzer addressIndexAnalyzer = new CzechAddressAnalyzer(AnalyzerUseType.Index);
        CzechAdminAnalyzer adminIndexAnalyzer = new CzechAdminAnalyzer(AnalyzerUseType.Index);
        indexAnalyzer = new EntityAnalyzer(adminIndexAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressIndexAnalyzer);

        CzechAddressAnalyzer addressQueryAnalyzer = new CzechAddressAnalyzer(AnalyzerUseType.Query);
        CzechAdminAnalyzer adminQueryAnalyzer = new CzechAdminAnalyzer(AnalyzerUseType.Query);
        queryAnalyzer = new EntityAnalyzer(adminQueryAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressQueryAnalyzer);
    }

    public static class CzechAddressAnalyzer extends Analyzer {

        AnalyzerUseType useType;

        public CzechAddressAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new WhitespaceTokenizer(Version.LUCENE_45, input);//TODO Street and city not same
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new CzechStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }
    public static class CzechAdminAnalyzer extends Analyzer {
        
        AnalyzerUseType useType;
        
        public CzechAdminAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }
        
        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);//TODO Street and city not same
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new CzechStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class CzechAdminBodyAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new CzechStemFilter(ts);
            ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            ts = new PuncRemoverFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class CzechPoiNameAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new CzechStemFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }
}
