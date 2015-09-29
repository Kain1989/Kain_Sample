package com.telenav.lucene.core.analyzers.preprocess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class SpecialCharactersPreprocessor implements Preprocessor {

    protected static final Pattern PATTERN_SPECIAL_CHARACTERS = Pattern
            .compile("\\^|%|#|@|!|<|>|\\+|=|`|_|~|#|\'|\\*|\\||\\)|\\(|\\}|\\{|\\[|\\||^|\"|\\]|\\?|,|;|:|\\.|\\$|\\/");

    protected static final String EMPTY = "";
    protected static final String SPACE = " ";
    protected static final String REGEX_FOR_REPLACE_DOT_WITH_NOSPACE = "\\.";
    protected static final String REGEX_FOR_REPLACE_DOT_WITH_SPACE = "( *\\.+ *)";
    protected static final Pattern PATTERN_AMP = Pattern.compile("&");
    protected static final Pattern PATTERN_APOSTROPHE_S = Pattern.compile("'s");
    protected static final Pattern PATTERN_APOSTROPHE = Pattern.compile("'");
    protected static final Pattern PATTERN_DOUBLEAPOSTROPHE = Pattern.compile("\"");
    protected static final Pattern PATTERN_HIPHEN = Pattern.compile("-");
    protected static final Pattern PATTERN_UNDERSCORE = Pattern.compile("_");
    protected static final Pattern PATTERN_FOR_REPLACE_DOT_WITH_NOSPACE = Pattern.compile(REGEX_FOR_REPLACE_DOT_WITH_NOSPACE);
    protected static final Pattern PATTERN_FOR_REPLACE_DOT_WITH_SPACE = Pattern.compile(REGEX_FOR_REPLACE_DOT_WITH_SPACE);
    private static String[] GENERIC_SPECIAL_CHARACTERS = { "'s", "&", "'", "-", "-", ".", ".", "\"", "_" };
    private static Pattern[] GENERIC_REGEX_PATTERN = { PATTERN_APOSTROPHE_S, PATTERN_AMP, PATTERN_APOSTROPHE, PATTERN_HIPHEN, PATTERN_HIPHEN,
            PATTERN_FOR_REPLACE_DOT_WITH_NOSPACE, PATTERN_FOR_REPLACE_DOT_WITH_SPACE, PATTERN_DOUBLEAPOSTROPHE, PATTERN_UNDERSCORE };

    private static String[] GENERIC_REPLACE_CHARACTERS = { EMPTY, EMPTY, EMPTY, SPACE, EMPTY, EMPTY, SPACE, SPACE, SPACE };

    @Override
    public Collection<String> preprocess(String query) {

        int[] specialCharIndexes = new int[query.length()];
        int j = 0;
        for (int i = 0; i < GENERIC_SPECIAL_CHARACTERS.length; i++) {
            if (query.indexOf(GENERIC_SPECIAL_CHARACTERS[i]) >= 0) {
                specialCharIndexes[j++] = i;
            }
        }
        String tmp = query;
        for (int i : specialCharIndexes) {
            tmp = GENERIC_REGEX_PATTERN[i].matcher(tmp).replaceAll(GENERIC_REPLACE_CHARACTERS[i]);
        }
        List<String> list = new ArrayList<String>();
        list.add(tmp.toLowerCase());
        return (list);
    }

}
