package com.telenav.lucene.core.analyzers.langs;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.filters.PuncRemoverFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.de.GermanLightStemFilter;
import org.apache.lucene.analysis.de.GermanMinimalStemFilter;
import org.apache.lucene.analysis.de.GermanNormalizationFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GermanEntityAnalyzer extends QueryIndexAnalyzer {

    public static final CharArraySet DEFAULT_PLACE_STOPWORDS = CharArraySet.unmodifiableSet(new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList("gmbh",
            "und", "kg", "co", "haftungsbeschränkt", "ug", "am", "eg", "für", "der", "straße", "mbh", "gbr", "im", "die"), true));

    
    private static GermanEntityAnalyzer instance = new GermanEntityAnalyzer();

    public static GermanEntityAnalyzer getInstance() {
        return instance;
    }

    private GermanEntityAnalyzer() {
        GermanAdminBodyAnalyzer adminBodyAnalyzer = new GermanAdminBodyAnalyzer();
        GermanPoiNameAnalyzer poiNameAnalyzer = new GermanPoiNameAnalyzer();

        GermanAddressAnalyzer addressIndexAnalyzer = new GermanAddressAnalyzer(AnalyzerUseType.Index);
        indexAnalyzer = new EntityAnalyzer(addressIndexAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressIndexAnalyzer);

        GermanAddressAnalyzer addressQueryAnalyzer = new GermanAddressAnalyzer(AnalyzerUseType.Query);
        queryAnalyzer = new EntityAnalyzer(addressQueryAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressQueryAnalyzer);
    }

    public static class GermanAddressAnalyzer extends Analyzer {

        AnalyzerUseType useType;

        public GermanAddressAnalyzer(AnalyzerUseType useType) {
            this.useType = useType;
        }

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new WhitespaceTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new GermanNormalizationFilter(ts);
            ts = new ASCIIFoldingFilter(ts);// TODO double check why here twice in schema
            ts = new GermanLightStemFilter(ts);
            if (AnalyzerUseType.Index == this.useType) {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            } else {
                ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            }
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class GermanAdminBodyAnalyzer extends Analyzer {

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
            ts = new GermanNormalizationFilter(ts);
            ts = new ASCIIFoldingFilter(ts);
            ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
            ts = new PuncRemoverFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }

    public static class GermanPoiNameAnalyzer extends Analyzer {

        AnalyzerUseType useType;

        @Override
        protected TokenStreamComponents createComponents(String fieldName, Reader input) {
            final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
            TokenStream ts = source;
            ts = new LowerCaseFilter(Version.LUCENE_45, ts);
			Map<String, String> args = new HashMap<String, String>();
			args.put("expand", "true");
			args.put("ignoreCase", "true");
			args.put("luceneMatchVersion", Version.LUCENE_45.name());
			args.put("synonyms", "solr/conf/lang/place_synonyms_de.txt");
			SynonymFilterFactory synonymFilterFactory = new SynonymFilterFactory(
					args);
			try {
				synonymFilterFactory.inform(new ClasspathResourceLoader());
				ts = synonymFilterFactory.create(ts);
			} catch (IOException e) {
				
			}
            
			ts = new StopFilter(Version.LUCENE_45, ts, DEFAULT_PLACE_STOPWORDS);
            ts = new GermanNormalizationFilter(ts);
            ts = new GermanMinimalStemFilter(ts);
            ts = new ASCIIFoldingFilter(ts);
            return new TokenStreamComponents(source, ts);
        }
    }
}
