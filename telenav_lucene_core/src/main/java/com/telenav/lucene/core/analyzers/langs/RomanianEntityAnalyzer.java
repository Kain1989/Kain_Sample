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
import org.apache.lucene.util.Version;

import java.io.Reader;

public class RomanianEntityAnalyzer extends QueryIndexAnalyzer{
	
	private static RomanianEntityAnalyzer instance = new RomanianEntityAnalyzer();
	
	public static RomanianEntityAnalyzer getInstance(){
		return instance;
	}
	
	private RomanianEntityAnalyzer() {
		RomanianAdminBodyAnalyzer adminBodyAnalyzer = new RomanianAdminBodyAnalyzer();
		RomanianPoiNameAnalyzer poiNameAnalyzer = new RomanianPoiNameAnalyzer();
		
		RomanianAddressAnalyzer addressIndexAnalyzer = new RomanianAddressAnalyzer(AnalyzerUseType.Index);
		RomanianAdminAnalyzer adminIndexAnalyzer = new RomanianAdminAnalyzer(AnalyzerUseType.Index);
		indexAnalyzer = new EntityAnalyzer(adminIndexAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressIndexAnalyzer);
		
		RomanianAddressAnalyzer addressQueryAnalyzer = new RomanianAddressAnalyzer(AnalyzerUseType.Query);
		RomanianAdminAnalyzer adminQueryAnalyzer = new RomanianAdminAnalyzer(AnalyzerUseType.Query);
		queryAnalyzer = new EntityAnalyzer(adminQueryAnalyzer, adminBodyAnalyzer, poiNameAnalyzer, addressQueryAnalyzer);
	}
	
	public static class RomanianAddressAnalyzer extends Analyzer {
		AnalyzerUseType useType;
		public RomanianAddressAnalyzer(AnalyzerUseType useType){
			this.useType = useType;
		}
		@Override
		protected TokenStreamComponents createComponents(String fieldName,
				Reader input) {
			 final Tokenizer source = new WhitespaceTokenizer(Version.LUCENE_45, input);
			 TokenStream ts = source;
			 ts = new ASCIIFoldingFilter(ts);
			 ts = new LowerCaseFilter(Version.LUCENE_45, ts);
			 ts = AnalyzerUtil.getSnowballPorterFilterFactory("Romanian").create(ts);
			 if(AnalyzerUseType.Index == this.useType){
				 ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
			 }else{
				 ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
			 }
			 return new TokenStreamComponents(source, ts);
		}
	}
	public static class RomanianAdminAnalyzer extends Analyzer {
	    AnalyzerUseType useType;
	    public RomanianAdminAnalyzer(AnalyzerUseType useType){
	        this.useType = useType;
	    }
	    @Override
	    protected TokenStreamComponents createComponents(String fieldName,
	            Reader input) {
	        final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
	        TokenStream ts = source;
	        ts = new ASCIIFoldingFilter(ts);
	        ts = new LowerCaseFilter(Version.LUCENE_45, ts);
	        ts = AnalyzerUtil.getSnowballPorterFilterFactory("Romanian").create(ts);
	        if(AnalyzerUseType.Index == this.useType){
	            ts = new com.telenav.address.common.analyzer.filter.BodyPackIndexFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
	        }else{
	            ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
	        }
	        return new TokenStreamComponents(source, ts);
	    }
	}
	public static class RomanianAdminBodyAnalyzer extends Analyzer {
		@Override
		protected TokenStreamComponents createComponents(String fieldName,
				Reader input) {
			 final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
			 TokenStream ts = source;
			 ts = new ASCIIFoldingFilter(ts);
			 ts = new LowerCaseFilter(Version.LUCENE_45, ts);
			 ts = AnalyzerUtil.getSnowballPorterFilterFactory("Romanian").create(ts);
			 ts = new com.telenav.address.common.analyzer.filter.BodyPackQueryFilterFactory(CommonAnalyzer.EMPTY_ARGS).create(ts);
			 ts = new PuncRemoverFilter(ts);
			 return new TokenStreamComponents(source, ts);
		}
	}
	
	public static class RomanianPoiNameAnalyzer extends Analyzer {
		@Override
		protected TokenStreamComponents createComponents(String fieldName,
				Reader input) {
			 final Tokenizer source = new StandardTokenizer(Version.LUCENE_45, input);
			 TokenStream ts = source;
			 ts = new LowerCaseFilter(Version.LUCENE_45, ts);
			 ts = AnalyzerUtil.getSnowballPorterFilterFactory("Romanian").create(ts);
			 return new TokenStreamComponents(source, ts);
		}
	}
}
