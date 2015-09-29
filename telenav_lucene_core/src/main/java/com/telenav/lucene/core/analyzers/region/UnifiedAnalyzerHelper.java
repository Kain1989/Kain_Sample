package com.telenav.lucene.core.analyzers.region;

import com.telenav.lucene.core.analyzers.AnalyzerUseType;
import com.telenav.lucene.core.schema.AddressSchemaRefactor;
import com.telenav.unifiedsearch.common.TokenUtility;
import com.telenav.unifiedsearch.core.SchemaManager;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.List;

public class UnifiedAnalyzerHelper {
	public static Analyzer getQueryAnalyzer(String schemaName){
		return getAnalyzer(schemaName, AnalyzerUseType.Query);
	}
	
	public static Analyzer getIndexAnalyzer(String schemaName){
		return getAnalyzer(schemaName, AnalyzerUseType.Index);
	}
	
	private static Analyzer getAnalyzer(String schemaName, AnalyzerUseType type){
        if(schemaName.contains("address.admin.na")){
            return NAAdminAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.admin.eu")){
            return EUAdminAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.admin.sa")){
            return SAAdminAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.admin.anz")){
            return ANZAdminAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.admin.cn")){
            return CNAdminAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.admin.il")){
            return ILAdminAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.admin.af")){
            return AFAdminAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.admin.mea")){
            return MEAAdminAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.na")){
            return NAStreetAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.eu")){
            return EUStreetAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.sa")){
            return SAStreetAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.anz")){
            return ANZStreetAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.cn")){
            return CNStreetAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.il")){
            return ILStreetAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.af")){
            return AFStreetAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("address.mea")){
            return MEAStreetAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("poi.na")){
            return NAPoiAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("poi.eu")){
            return EUPoiAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("poi.sa")){
            return SAPoiAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("poi.anz")){
            return ANZPoiAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("poi.cn")){
            return CNPoiAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("poi.il")){
            return ILPoiAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("poi.af")){
            return AFPoiAnalyzer.getInstance().getAnalyzer(type);
        }else if(schemaName.contains("poi.mea")){
            return MEAPoiAnalyzer.getInstance().getAnalyzer(type);
        }else{
            return SchemaManager.getInstance().getQueryAnalyzer(schemaName);
        }
    }
    
    public static List<String> extractQueryTokens(String schemaName, String fieldName, String query) throws IOException{
        return TokenUtility.getTokens(fieldName, query, getQueryAnalyzer(schemaName));
    }
    
    public static void naRegionWarmUp() throws IOException{
        Analyzer adminAnalyzer = NAAdminAnalyzer.getInstance().getAnalyzer(AnalyzerUseType.Query);
        TokenUtility.getTokens("city_exact", "sunnyvale", adminAnalyzer);
        TokenUtility.getTokens("postalcode", "94086", adminAnalyzer);
        TokenUtility.getTokens("city_eng", "sunnyvale", adminAnalyzer);
        TokenUtility.getTokens("city_body_eng", "sunnyvale", adminAnalyzer);
        TokenUtility.getTokens("admin_geohash", "9q9", adminAnalyzer);
        TokenUtility.getTokens("fre_eng", "sunnyvale", adminAnalyzer);
        TokenUtility.getTokens("fre_body_eng", "sunnyvale", adminAnalyzer);
        
        Analyzer streetAnalyzer = NAStreetAnalyzer.getInstance().getAnalyzer(AnalyzerUseType.Query);
        TokenUtility.getTokens("street_eng", "sunnyvale", streetAnalyzer);
        TokenUtility.getTokens("street_fre", "sunnyvale", streetAnalyzer);
        
        TokenUtility.getTokens("admin_geohash", "9q9", adminAnalyzer);
        TokenUtility.getTokens("NAME_EXACT", "telenav", NAPoiAnalyzer.getInstance().getAnalyzer(AnalyzerUseType.Query));
        TokenUtility.getTokens("NAME_WORDGRAM_EXACT", "telenav", NAPoiAnalyzer.getInstance().getAnalyzer(AnalyzerUseType.Query));
        TokenUtility.getTokens("NAME_WORDGRAM_ENG", "telenav", NAPoiAnalyzer.getInstance().getAnalyzer(AnalyzerUseType.Query));
        TokenUtility.getTokens("NAME_WORDGRAM_FRE", "telenav", NAPoiAnalyzer.getInstance().getAnalyzer(AnalyzerUseType.Query));
        TokenUtility.getTokens("NAME_WORDGRAM_MULTILANG", "telenav", NAPoiAnalyzer.getInstance().getAnalyzer(AnalyzerUseType.Query));
    }
    
    public static void cnRegionWarmUp() throws IOException{
    	Analyzer smartChineseAnalyzer = new SmartChineseAnalyzer(Version.LUCENE_45);
        TokenUtility.getTokens("ALIAS", "?", smartChineseAnalyzer);
        Analyzer adminAnalyzer = UnifiedAnalyzerHelper.getQueryAnalyzer(AddressSchemaRefactor.getAdminSchemaName());
        TokenUtility.getTokens(AddressSchemaRefactor.STR_INDEXED_STATE_EXACT.getField(), "??", adminAnalyzer);
    }
}
