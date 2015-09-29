package com.telenav.lucene.core.analyzers.langs;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.filters.PuncRemoverFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.it.ItalianLightStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.ElisionFilter;
import org.apache.lucene.util.Version;

import java.io.Reader;
import java.util.Arrays;

public class ItalianEntityAnalyzer extends QueryIndexAnalyzer {

    public static final CharArraySet DEFAULT_ARTICLES = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList("c", "l", "all",
            "dall", "dell", "nell", "sull", "coll", "pell", "gl", "agl", "dagl", "degl", "negl", "sugl", "un", "m", "t", "s", "v", "d"), true));
    
    public static final CharArraySet DEFAULT_STOPWORDS = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList("di", "srl", "e",
            "la", "il", "del", "a", "dei"), true));

    private static ItalianEntityAnalyzer instance = new ItalianEntityAnalyzer();

    public static ItalianEntityAnalyzer getInstance() {
        return instance;
    }

    private ItalianEntityAnalyzer() {
        ItalianAdminBodyAnalyzer adminBodyAnalyzer = new ItalianAdminBodyAnalyzer();
        ItalianPoiNameAnalyzer poiNameAnalyzer = new ItalianPoiNameAnalyzer();

        ItalianAddressAnalyzer addressIndexAnalyzer = new ItalianAddressAnalyzer(AnalyzerUseType.Index);
        ItalianAdminAnalyzer adminIndexAnalyzer = new ItalianAdminAnalyzer(AnalyzerUseType.Index);
        indexAnalyzer = new EntityAnalyzer(adminIndexAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressIndexAnalyzer);

        ItalianAddressAnalyzer addressQueryAnalyzer = new ItalianAddressAnalyzer(AnalyzerUseType.Query);
        ItalianAdminAnalyzer adminQueryAnalyzer = new ItalianAdminAnalyzer(AnalyzerUseType.Query);
        queryAnalyzer = new EntityAnalyzer(adminQueryAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressQueryAnalyzer);
    }

    public static class ItalianAddressAnalyzer extends Analyzer {

        AnalyzerUseType useType;

        public ItalianAddressAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new WhitespaceTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ElisionFilter(ts, DEFAULT_ARTICLES);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new ASCIIFoldingFilter(ts);
            ts = new ItalianLightStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }
    
    public static class ItalianAdminAnalyzer extends Analyzer {
        
        AnalyzerUseType useType;
        
        public ItalianAdminAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }
        
        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ElisionFilter(ts, DEFAULT_ARTICLES);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new ASCIIFoldingFilter(ts);
            ts = new ItalianLightStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class ItalianAdminBodyAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ElisionFilter(ts, DEFAULT_ARTICLES);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new ASCIIFoldingFilter(ts);
            ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            ts = new PuncRemoverFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class ItalianPoiNameAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new ElisionFilter(ts, DEFAULT_ARTICLES);
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new StopFilter(Version.LUCENE_45, ts, DEFAULT_STOPWORDS);
            ts = new ItalianLightStemFilter(ts);
            ts = new ASCIIFoldingFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }
}
