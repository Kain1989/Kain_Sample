package com.telenav.lucene.core.builder.term;

import com.telenav.address.common.datatype.ACLanguage;
import com.telenav.address.common.datatype.NormalizeName;
import com.telenav.address.common.datatype.Region;
import com.telenav.lucene.core.analyzers.region.UnifiedAnalyzerHelper;
import com.telenav.lucene.core.schema.AddressSchemaRefactor;
import com.telenav.unifiedsearch.common.TokenUtility;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfshi on 8/18/2015.
 */
public class TermBuilder {

    private static Logger logger = Logger.getLogger(TermBuilder.class);

    public static Term buildTerm(AddressSchemaRefactor field, NormalizeName normalizeName) {
        return new Term(field.getField(), normalizeName.getBody());
    }

    public static List<Term> buildTerms(AddressSchemaRefactor field, NormalizeName normalizeName, String schemaName) {
        List<Term> termList = new ArrayList<Term>();
        if (normalizeName == null) {
            return termList;
        }
        List<String> exactTokens = null;
        try {
                exactTokens = UnifiedAnalyzerHelper.extractQueryTokens(schemaName, AddressSchemaRefactor.getFieldName_Exact(field), normalizeName.toString());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (exactTokens != null && !exactTokens.isEmpty()) {
            for (String token : exactTokens) {
                Term term = new Term(AddressSchemaRefactor.getFieldName_Exact(field), token);
                termList.add(term);
            }
        }
        return termList;
    }

    public static List<Term> buildExactTerms(AddressSchemaRefactor field, NormalizeName normalizeName, String schemaName) {
        List<Term> termList = new ArrayList<Term>();
        if (normalizeName == null) {
            return termList;
        }
        List<String> exactTokens = null;
        try {
            if (!Region.is(Region.CN)) {
                exactTokens = UnifiedAnalyzerHelper.extractQueryTokens(schemaName, AddressSchemaRefactor.getFieldName_Exact(field), normalizeName.toString());
            } else {
                exactTokens = UnifiedAnalyzerHelper.extractQueryTokens(schemaName, AddressSchemaRefactor.getFieldName_Exact(field), normalizeName.getOriginalName());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (exactTokens != null && !exactTokens.isEmpty()) {
            for (String token : exactTokens) {
                Term term = new Term(AddressSchemaRefactor.getFieldName_Exact(field), token);
                termList.add(term);
            }
        }
        return termList;
    }

    public static List<Term> buildExactLanguageTerms(AddressSchemaRefactor field, NormalizeName standardName, ACLanguage language, String schemaName) {
        List<Term> termList = new ArrayList<Term>();
            List<String> tokens = null;
            try {
                tokens = UnifiedAnalyzerHelper.extractQueryTokens(schemaName, AddressSchemaRefactor.getFieldName(field, language.getLanguage()), standardName.getBody());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            if (tokens != null && !tokens.isEmpty()) {
                for (String token : tokens) {
                    Term term = new Term(AddressSchemaRefactor.getFieldName(field, language.getLanguage()), token);
                    termList.add(term);
                }
            }
        return termList;
    }

    protected static String getValueByLanguage(AddressSchemaRefactor field, String value, String language, String schemaName) {
        String cleanValue = StringUtils.EMPTY;
        List<String> tokens = null;
        try {
            tokens = TokenUtility.getTokens(AddressSchemaRefactor.getFieldName(field, language), value, UnifiedAnalyzerHelper.getQueryAnalyzer(schemaName));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (CollectionUtils.isNotEmpty(tokens) && tokens.size() > 0) {
            cleanValue = tokens.get(0);
        }
        return cleanValue;
    }
}
