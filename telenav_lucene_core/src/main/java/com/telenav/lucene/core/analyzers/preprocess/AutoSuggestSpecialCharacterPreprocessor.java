package com.telenav.lucene.core.analyzers.preprocess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class AutoSuggestSpecialCharacterPreprocessor extends SpecialCharactersPreprocessor {

    private static String[] AUTOSUGGEST_SPECIAL_CHARACTERS = { "'s", "&", "'", "-", "-", ".", ".", "\"", "_" };
    private static Pattern[] AUTOSUGGEST_REGEX_PATTERN = { PATTERN_APOSTROPHE_S, PATTERN_AMP, PATTERN_APOSTROPHE, PATTERN_HIPHEN, PATTERN_HIPHEN,
            PATTERN_FOR_REPLACE_DOT_WITH_NOSPACE, PATTERN_FOR_REPLACE_DOT_WITH_SPACE, PATTERN_DOUBLEAPOSTROPHE, PATTERN_UNDERSCORE };

    private static String[] AUTOSUGGEST_REPLACE_CHARACTERS = { EMPTY, EMPTY, EMPTY, SPACE, EMPTY, EMPTY, SPACE, SPACE, SPACE };

    @Override
    public List<String> preprocess(String query) {
        Set<String> tokens = new HashSet<String>();

        for (int i = 0; i < AUTOSUGGEST_SPECIAL_CHARACTERS.length; i++) {
            if (query.indexOf(AUTOSUGGEST_SPECIAL_CHARACTERS[i]) >= 0) {
                tokens.add((AUTOSUGGEST_REGEX_PATTERN[i].matcher(query).replaceAll(AUTOSUGGEST_REPLACE_CHARACTERS[i])).toLowerCase());
            }
        }
        // Now replace all of them at once
        tokens.addAll(super.preprocess(query));
        return new ArrayList<String>(tokens);
    }
}
