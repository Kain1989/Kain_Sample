package com.telenav.lucene.core.builder.term;

import com.telenav.address.common.datatype.NormalizeName;
import com.telenav.lucene.core.analyzers.region.UnifiedAnalyzerHelper;
import com.telenav.lucene.core.schema.AddressSchemaRefactor;
import com.telenav.unifiedsearch.common.TokenUtility;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfshi on 8/17/2015.
 */
public class AdminTermBuilder extends TermBuilder {

    private static final Logger logger = Logger.getLogger(AdminTermBuilder.class);

    public static Term buildExactTerm(AddressSchemaRefactor field, NormalizeName name, String schemaName) {
        try {
            List<String> tokens = TokenUtility.getTokens(field.getField(), name.getBody(), UnifiedAnalyzerHelper.getQueryAnalyzer(schemaName));
            if (tokens.size() > 0) {
                return new Term(AddressSchemaRefactor.STR_INDEXED_STATE_EXACT.getField(), tokens.get(0));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static List<Term> buildExactTerms(AddressSchemaRefactor field, NormalizeName normalizeName, String schemaName) {
        List<String> tokens = null;
        try {
            tokens = UnifiedAnalyzerHelper.extractQueryTokens(schemaName, field.getField(), normalizeName.getBody());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        List<Term> termList = new ArrayList<Term>();
        if (tokens != null && !tokens.isEmpty()) {
            for (String token : tokens) {
                termList.add(new Term(field.getField(), token));
            }
        }
        return termList;
    }

}
