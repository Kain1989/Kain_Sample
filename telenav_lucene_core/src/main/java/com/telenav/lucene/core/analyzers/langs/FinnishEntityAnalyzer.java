package com.telenav.lucene.core.analyzers.langs;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.filters.PuncRemoverFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.fi.FinnishLightStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

import java.io.Reader;

public class FinnishEntityAnalyzer extends QueryIndexAnalyzer {

    private static FinnishEntityAnalyzer instance = new FinnishEntityAnalyzer();

    public static FinnishEntityAnalyzer getInstance() {
        return instance;
    }

    private FinnishEntityAnalyzer() {
        FinnishAdminBodyAnalyzer adminBodyAnalyzer = new FinnishAdminBodyAnalyzer();
        FinnishPoiNameAnalyzer poiNameAnalyzer = new FinnishPoiNameAnalyzer();

        FinnishAddressAnalyzer addressIndexAnalyzer = new FinnishAddressAnalyzer(AnalyzerUseType.Index);
        FinnishAdminAnalyzer adminIndexAnalyzer = new FinnishAdminAnalyzer(AnalyzerUseType.Index);
        indexAnalyzer = new EntityAnalyzer(adminIndexAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressIndexAnalyzer);

        FinnishAddressAnalyzer addressQueryAnalyzer = new FinnishAddressAnalyzer(AnalyzerUseType.Query);
        FinnishAdminAnalyzer adminQueryAnalyzer = new FinnishAdminAnalyzer(AnalyzerUseType.Query);
        queryAnalyzer = new EntityAnalyzer(adminQueryAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressQueryAnalyzer);
    }

    public static class FinnishAddressAnalyzer extends Analyzer {

        AnalyzerUseType useType;

        public FinnishAddressAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new WhitespaceTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new FinnishLightStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }
    public static class FinnishAdminAnalyzer extends Analyzer {
        
        AnalyzerUseType useType;
        
        public FinnishAdminAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }
        
        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new FinnishLightStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class FinnishAdminBodyAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new FinnishLightStemFilter(ts);
            ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            ts = new PuncRemoverFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class FinnishPoiNameAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new FinnishLightStemFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }
}
