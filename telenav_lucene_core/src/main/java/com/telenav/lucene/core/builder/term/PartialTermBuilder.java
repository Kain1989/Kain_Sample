package com.telenav.lucene.core.builder.term;

import com.telenav.address.common.datatype.ACLanguage;
import com.telenav.address.common.datatype.NormalizeName;
import com.telenav.lucene.core.schema.AddressSchemaRefactor;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfshi on 8/25/2015.
 */
public class PartialTermBuilder extends TermBuilder {

    public static List<Term> buildTerms(AddressSchemaRefactor field, NormalizeName normalizeName, ACLanguage language, String schemaName) {
        String[] parts = StringUtils.split(normalizeName.getOriginalName());
        BooleanQuery partQuery = new BooleanQuery();
        List<Term> termList = new ArrayList<Term>();
        for (int i = 0; i < parts.length; i++) {
            termList.add(new Term(
                    AddressSchemaRefactor.getFieldName(field, language.getLanguage()),
                    getValueByLanguage(field, parts[i], language.getLanguage(), schemaName)));
        }
        return termList;
    }
}
