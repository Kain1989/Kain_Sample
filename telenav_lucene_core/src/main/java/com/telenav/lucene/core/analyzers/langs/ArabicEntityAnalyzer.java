package com.telenav.lucene.core.analyzers.langs;

import com.telenav.commons.datatypes.Language;
import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.analyzers.tokenizers.CosePreprocessTokenizer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ar.ArabicNormalizationFilter;
import org.apache.lucene.analysis.ar.ArabicStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

import java.io.Reader;
import java.util.Arrays;
import java.util.HashSet;

public class ArabicEntityAnalyzer extends QueryIndexAnalyzer{
	
	private static ArabicEntityAnalyzer instance = new ArabicEntityAnalyzer();
	
	public static ArabicEntityAnalyzer getInstance(){
		return instance;
	}
	
	private ArabicEntityAnalyzer() {
		ArabicAdminBodyAnalyzer adminBodyAnalyzer = new ArabicAdminBodyAnalyzer();
		ArabicPoiNameAnalyzer poiNameAnalyzer = new ArabicPoiNameAnalyzer();
		
		ArabicAddressAnalyzer addressIndexAnalyzer = new ArabicAddressAnalyzer(AnalyzerUseType.Index);
		indexAnalyzer = new EntityAnalyzer(addressIndexAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressIndexAnalyzer);

		ArabicAddressAnalyzer addressQueryAnalyzer = new ArabicAddressAnalyzer(AnalyzerUseType.Query);
		queryAnalyzer = new EntityAnalyzer(addressQueryAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressQueryAnalyzer);
	}
	 
	public static class ArabicAddressAnalyzer extends Analyzer {
		AnalyzerUseType useType;
		public ArabicAddressAnalyzer(AnalyzerUseType useType){
			this.useType = useType;
		}
		@Override
		protected TokenStreamComponents createComponents(String fieldName,
				Reader input) {
			 final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
			 TokenStream ts = source;
             ts = new ArabicNormalizationFilter(ts);
			 ts = new ArabicStemFilter(ts);
			 ts = new com.telenav.address.common.analyzer.filter.USBodyPostFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
			 if(AnalyzerUseType.Index == this.useType){
				 ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
			 }else{
				 ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
			 }
			 return new TokenStreamComponents(source, ts);
		}
	}
	
	public static class ArabicAdminBodyAnalyzer extends Analyzer {
		@Override
		protected TokenStreamComponents createComponents(String fieldName,
				Reader input) {
			 final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
			 TokenStream ts = source;
			 ts = new ArabicNormalizationFilter(ts);
			 ts = new ArabicStemFilter(ts);
			 ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
			 return new TokenStreamComponents(source, ts);
		}
	}
	
	public static class ArabicPoiNameAnalyzer extends Analyzer {
		@Override
		protected TokenStreamComponents createComponents(String fieldName,
				Reader input) {
			 final Tokenizer source = new CosePreprocessTokenizer(input, new HashSet<Language>(Arrays.asList(Language.ENGLISH)));
			 TokenStream ts = source;
			 ts = new ArabicNormalizationFilter(ts);
			 ts = new ArabicStemFilter(ts);
			 return new TokenStreamComponents(source, ts);
		}
	}
	
}
