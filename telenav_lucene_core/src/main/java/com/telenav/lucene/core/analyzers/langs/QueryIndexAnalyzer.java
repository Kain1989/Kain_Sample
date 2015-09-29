package com.telenav.lucene.core.analyzers.langs;


import com.telenav.lucene.core.analyzers.AnalyzerUseType;

public abstract class QueryIndexAnalyzer {
	
	EntityAnalyzer indexAnalyzer;
	EntityAnalyzer queryAnalyzer;
	
	public EntityAnalyzer getAnalyzer(AnalyzerUseType type){
		if(AnalyzerUseType.Index == type){
			return this.indexAnalyzer;
		}else{
			return this.queryAnalyzer;
		}
	}
}
