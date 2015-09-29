package com.telenav.lucene.core.analyzers.preprocess;

import com.telenav.address.common.datatype.Region;
import com.telenav.commons.analyzers.number.Text2NumberAdapter;
import com.telenav.commons.datatypes.Language;

import java.util.*;

public class Text2NumberPreprocessor implements Preprocessor {

    private Set<Language> langs = new HashSet<Language>();

    public Text2NumberPreprocessor(Language lang) {
        langs.add(lang);
    }

    public Text2NumberPreprocessor() {
        langs.add(Language.getRegionOfficialLanguage(Region.getCurrentRegion()));
    }

    @Override
    public Collection<String> preprocess(String query) {
        List<String> queries = new ArrayList<String>();
        queries.add(Text2NumberAdapter.translate(query, langs));
        return queries;
    }

}
