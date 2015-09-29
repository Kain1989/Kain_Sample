package com.telenav.lucene.core.datatype;

import com.telenav.address.common.datatype.ACLanguage;
import com.telenav.address.common.datatype.Separator;
import org.apache.commons.lang.StringUtils;

/**
 * @author: rapu
 * @date: 2009-8-17
 * @time: 10:56:21 TeleNav Inc.
 */
public class LangName {

    private String langName; // such as KIFER:ENG
    private String origName;
    private ACLanguage lang;

    /**
     * 
     * @param langName
     *            should be unescaped before pass in.
     */
    public LangName(String langName) {
        this.langName = langName;
        int index = StringUtils.lastIndexOf(langName, Separator.Colon);
        if (index >= 0) {
            origName = StringUtils.substring(langName, 0, index);
            lang = ACLanguage.parse(StringUtils.substring(langName, index + 1));
        }

        else {
            origName = langName;
        }
    }

    public String getLangName() {
        return langName;
    }

    public String getOrigName() {
        return origName;
    }

    public ACLanguage getLang() {
        return lang;
    }

    @Override
    public String toString() {
        return langName;
    }

    @Override
    public int hashCode() {
        return langName.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (!(that instanceof LangName)) {
            return false;
        } else {
            return langName.equals(((LangName) that).getLangName());
        }
    }
}
