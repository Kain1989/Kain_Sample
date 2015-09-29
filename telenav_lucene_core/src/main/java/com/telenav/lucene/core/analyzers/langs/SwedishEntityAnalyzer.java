package com.telenav.lucene.core.analyzers.langs;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.filters.PuncRemoverFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.sv.SwedishLightStemFilter;
import org.apache.lucene.util.Version;

import java.io.Reader;

public class SwedishEntityAnalyzer extends QueryIndexAnalyzer {

    private static SwedishEntityAnalyzer instance = new SwedishEntityAnalyzer();

    public static SwedishEntityAnalyzer getInstance() {
        return instance;
    }

    private SwedishEntityAnalyzer() {
        SwedishAdminBodyAnalyzer adminBodyAnalyzer = new SwedishAdminBodyAnalyzer();
        SwedishPoiNameAnalyzer poiNameAnalyzer = new SwedishPoiNameAnalyzer();

        SwedishAddressAnalyzer addressIndexAnalyzer = new SwedishAddressAnalyzer(AnalyzerUseType.Index);
        SwedishAdminAnalyzer adminIndexAnalyzer = new SwedishAdminAnalyzer(AnalyzerUseType.Index);
        indexAnalyzer = new EntityAnalyzer(adminIndexAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressIndexAnalyzer);

        SwedishAddressAnalyzer addressQueryAnalyzer = new SwedishAddressAnalyzer(AnalyzerUseType.Query);
        SwedishAdminAnalyzer adminQueryAnalyzer = new SwedishAdminAnalyzer(AnalyzerUseType.Query);
        queryAnalyzer = new EntityAnalyzer(adminQueryAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressQueryAnalyzer);
    }

    public static class SwedishAddressAnalyzer extends Analyzer {

        AnalyzerUseType useType;

        public SwedishAddressAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new WhitespaceTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new SwedishLightStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }
    public static class SwedishAdminAnalyzer extends Analyzer {
        
        AnalyzerUseType useType;
        
        public SwedishAdminAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }
        
        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new SwedishLightStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class SwedishAdminBodyAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ASCIIFoldingFilter(ts);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new SwedishLightStemFilter(ts);
            ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            ts = new PuncRemoverFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class SwedishPoiNameAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new SwedishLightStemFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }
}
