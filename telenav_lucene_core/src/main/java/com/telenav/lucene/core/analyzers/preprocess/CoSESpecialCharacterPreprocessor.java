package com.telenav.lucene.core.analyzers.preprocess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class CoSESpecialCharacterPreprocessor extends SpecialCharactersPreprocessor {

    private static String[] COSE_SPECIAL_CHARACTERS = { "-", "-", "'s", "'s", "'" };
    private static Pattern[] COSE_REGEX_PATTERN = { PATTERN_HIPHEN, PATTERN_HIPHEN, PATTERN_APOSTROPHE_S, PATTERN_APOSTROPHE_S, PATTERN_APOSTROPHE };
    private static String[] COSE_REPLACE_CHARACTER = { SPACE, EMPTY, EMPTY, "s", EMPTY };

    @Override
    public Collection<String> preprocess(String query) {
        Set<String> tokens = new HashSet<String>();
        boolean replaced = false;

        for (int i = 0; i < COSE_SPECIAL_CHARACTERS.length; i++) {
            if (query.indexOf(COSE_SPECIAL_CHARACTERS[i]) >= 0) {
                String replacedStr = (COSE_REGEX_PATTERN[i].matcher(query).replaceAll(COSE_REPLACE_CHARACTER[i])).toLowerCase();
                String replacedSpecialCharacters = PATTERN_SPECIAL_CHARACTERS.matcher(replacedStr).replaceAll(SPACE);
                tokens.add(replacedSpecialCharacters.trim());
                replaced = true;
            }
        }
        if (!replaced) {
            tokens.add(PATTERN_SPECIAL_CHARACTERS.matcher(query).replaceAll(SPACE).trim());
        }

        return tokens;
    }
}
