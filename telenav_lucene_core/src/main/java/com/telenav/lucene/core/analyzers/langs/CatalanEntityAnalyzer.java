package com.telenav.lucene.core.analyzers.langs;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.AnalyzerUtil;
import com.telenav.lucene.core.analyzers.filters.PuncRemoverFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.ElisionFilter;
import org.apache.lucene.util.Version;

import java.io.Reader;
import java.util.Arrays;

public class CatalanEntityAnalyzer extends QueryIndexAnalyzer {

    public static final CharArraySet DEFAULT_ARTICLES = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList("d", "l", "m", "n",
            "s", "t"), true));

    private static CatalanEntityAnalyzer instance = new CatalanEntityAnalyzer();

    public static CatalanEntityAnalyzer getInstance() {
        return instance;
    }

    private CatalanEntityAnalyzer() {
        CatalanAdminBodyAnalyzer adminBodyAnalyzer = new CatalanAdminBodyAnalyzer();
        CatalanPoiNameAnalyzer poiNameAnalyzer = new CatalanPoiNameAnalyzer();

        CatalanAdminAnalyzer adminIndexAnalyzer = new CatalanAdminAnalyzer(AnalyzerUseType.Index);
        CatalanAddressAnalyzer addressIndexAnalyzer = new CatalanAddressAnalyzer(AnalyzerUseType.Index);
        indexAnalyzer = new EntityAnalyzer(adminIndexAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressIndexAnalyzer);

        CatalanAdminAnalyzer adminQueryAnalyzer = new CatalanAdminAnalyzer(AnalyzerUseType.Query);
        CatalanAddressAnalyzer addressQueryAnalyzer = new CatalanAddressAnalyzer(AnalyzerUseType.Query);
        queryAnalyzer = new EntityAnalyzer(adminQueryAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressQueryAnalyzer);
    }

    public static class CatalanAddressAnalyzer extends Analyzer {

        AnalyzerUseType useType;

        public CatalanAddressAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new WhitespaceTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new ASCIIFoldingFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class CatalanAdminAnalyzer extends Analyzer {

        AnalyzerUseType useType;

        public CatalanAdminAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new ElisionFilter(ts, DEFAULT_ARTICLES);
            ts = AnalyzerUtil.getSnowballPorterFilterFactory("Catalan").create(ts);
            ts = new ASCIIFoldingFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class CatalanAdminBodyAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new ElisionFilter(ts, DEFAULT_ARTICLES);
            ts = AnalyzerUtil.getSnowballPorterFilterFactory("Catalan").create(ts);
            ts = new ASCIIFoldingFilter(ts);
            ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            ts = new PuncRemoverFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class CatalanPoiNameAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new ElisionFilter(ts, DEFAULT_ARTICLES);
            ts = AnalyzerUtil.getSnowballPorterFilterFactory("Catalan").create(ts);
            return new TokenStreamComponents(source, ts);
        }
    }
}
