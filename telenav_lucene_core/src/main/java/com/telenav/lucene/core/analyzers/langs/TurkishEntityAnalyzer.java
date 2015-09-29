package com.telenav.lucene.core.analyzers.langs;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.AnalyzerUtil;
import com.telenav.lucene.core.analyzers.filters.PuncRemoverFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tr.TurkishLowerCaseFilter;
import org.apache.lucene.util.Version;

import java.io.Reader;

public class TurkishEntityAnalyzer extends QueryIndexAnalyzer {

    private static TurkishEntityAnalyzer instance = new TurkishEntityAnalyzer();

    public static TurkishEntityAnalyzer getInstance() {
        return instance;
    }

    private TurkishEntityAnalyzer() {
        TurkishAdminBodyAnalyzer adminBodyAnalyzer = new TurkishAdminBodyAnalyzer();
        TurkishPoiNameAnalyzer poiNameAnalyzer = new TurkishPoiNameAnalyzer();

        TurkishAddressAnalyzer addressIndexAnalyzer = new TurkishAddressAnalyzer(AnalyzerUseType.Index);
        TurkishAdminAnalyzer adminIndexAnalyzer = new TurkishAdminAnalyzer(AnalyzerUseType.Index);
        indexAnalyzer = new EntityAnalyzer(adminIndexAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressIndexAnalyzer);

        TurkishAddressAnalyzer addressQueryAnalyzer = new TurkishAddressAnalyzer(AnalyzerUseType.Query);
        TurkishAdminAnalyzer adminQueryAnalyzer = new TurkishAdminAnalyzer(AnalyzerUseType.Query);
        queryAnalyzer = new EntityAnalyzer(adminQueryAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressQueryAnalyzer);
    }

    public static class TurkishAddressAnalyzer extends Analyzer {

        AnalyzerUseType useType;

        public TurkishAddressAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new WhitespaceTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new TurkishLowerCaseFilter(ts);
            ts = AnalyzerUtil.getSnowballPorterFilterFactory("Turkish").create(ts);
            ts = new ASCIIFoldingFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }
    public static class TurkishAdminAnalyzer extends Analyzer {
        
        AnalyzerUseType useType;
        
        public TurkishAdminAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }
        
        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new TurkishLowerCaseFilter(ts);
            ts = AnalyzerUtil.getSnowballPorterFilterFactory("Turkish").create(ts);
            ts = new ASCIIFoldingFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class TurkishAdminBodyAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new TurkishLowerCaseFilter(ts);
            ts = AnalyzerUtil.getSnowballPorterFilterFactory("Turkish").create(ts);
            ts = new ASCIIFoldingFilter(ts);
            ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            ts = new PuncRemoverFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class TurkishPoiNameAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new TurkishLowerCaseFilter(ts);
            ts = AnalyzerUtil.getSnowballPorterFilterFactory("Turkish").create(ts);
            return new TokenStreamComponents(source, ts);
        }
    }
}
