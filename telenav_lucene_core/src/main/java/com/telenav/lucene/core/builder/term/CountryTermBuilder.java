package com.telenav.lucene.core.builder.term;

import com.telenav.address.common.datatype.ACCountry;
import com.telenav.lucene.core.schema.AddressSchemaRefactor;
import org.apache.lucene.index.Term;

/**
 * Created by zfshi on 8/19/2015.
 */
public class CountryTermBuilder extends TermBuilder {

    public static Term buildTerm(AddressSchemaRefactor field, ACCountry country) {
        return new Term(field.getField(), country.getIso2Name().toLowerCase());
    }
}
