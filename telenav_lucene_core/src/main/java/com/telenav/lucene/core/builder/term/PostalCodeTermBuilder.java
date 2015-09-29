package com.telenav.lucene.core.builder.term;

import com.telenav.address.common.datatype.ACCountry;
import com.telenav.address.common.datatype.address.PostalCode;
import com.telenav.lucene.core.analyzers.region.UnifiedAnalyzerHelper;
import com.telenav.lucene.core.schema.AddressSchemaRefactor;
import com.telenav.lucene.core.utils.PostalcodeNormalizer;
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
public class PostalCodeTermBuilder extends TermBuilder {

    private static final Logger logger = Logger.getLogger(PostalCodeTermBuilder.class);

    public static List<Term> buildPostalCodeTerms(AddressSchemaRefactor field, PostalCode postalCode, String schemaName){
        List<Term> termList = new ArrayList<Term>();
        List<String> tokens = null;
        try {
            tokens = UnifiedAnalyzerHelper.extractQueryTokens(schemaName, field.getField(), postalCode.getBody());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (tokens != null && !tokens.isEmpty()) {
            for (String token : tokens) {
                termList.add(new Term(field.getField(), token));
            }
        }
        return termList;
    }

    public static Term buildOriginalPostalCodeTerms(AddressSchemaRefactor field, PostalCode postalCode, ACCountry country) {
        String pCode = postalCode.getOriginalName().toLowerCase();
        if(country != null && StringUtils.isNotEmpty(pCode)) {
            List<String> postalCodes = PostalcodeNormalizer.getListOfNormalizedPostalCodes(country, pCode);
            if(CollectionUtils.isNotEmpty(postalCodes) && postalCodes.size() > 0) {
                pCode = postalCodes.get(postalCodes.size() - 1);
            }
        }
        return new Term(field.getField(), pCode);
    }

    public static Term buildPrefixTerm(AddressSchemaRefactor field, PostalCode postalCode) {
        return new Term(field.getField(), postalCode.getPrefixString().toLowerCase());
    }
}
