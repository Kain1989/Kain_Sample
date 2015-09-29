package com.telenav.lucene.core.analyzers.langs;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.filters.PuncRemoverFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.no.NorwegianLightStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

import java.io.Reader;

public class NorwegianEntityAnalyzer extends QueryIndexAnalyzer {

    private static NorwegianEntityAnalyzer instance = new NorwegianEntityAnalyzer();

    public static NorwegianEntityAnalyzer getInstance() {
        return instance;
    }

    private NorwegianEntityAnalyzer() {
        NorwegianAdminBodyAnalyzer adminBodyAnalyzer = new NorwegianAdminBodyAnalyzer();
        NorwegianPoiNameAnalyzer poiNameAnalyzer = new NorwegianPoiNameAnalyzer();

        NorwegianAddressAnalyzer addressIndexAnalyzer = new NorwegianAddressAnalyzer(AnalyzerUseType.Index);
        NorwegianAdminAnalyzer adminIndexAnalyzer = new NorwegianAdminAnalyzer(AnalyzerUseType.Index);
        indexAnalyzer = new EntityAnalyzer(adminIndexAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressIndexAnalyzer);

        NorwegianAddressAnalyzer addressQueryAnalyzer = new NorwegianAddressAnalyzer(AnalyzerUseType.Query);
        NorwegianAdminAnalyzer adminQueryAnalyzer = new NorwegianAdminAnalyzer(AnalyzerUseType.Query);
        queryAnalyzer = new EntityAnalyzer(adminQueryAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressQueryAnalyzer);
    }

    public static class NorwegianAddressAnalyzer extends Analyzer {

        AnalyzerUseType useType;

        public NorwegianAddressAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new WhitespaceTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new NorwegianLightStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }
    public static class NorwegianAdminAnalyzer extends Analyzer {
        
        AnalyzerUseType useType;
        
        public NorwegianAdminAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }
        
        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new NorwegianLightStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class NorwegianAdminBodyAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new NorwegianLightStemFilter(ts);
            ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            ts = new PuncRemoverFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class NorwegianPoiNameAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new NorwegianLightStemFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }
}
