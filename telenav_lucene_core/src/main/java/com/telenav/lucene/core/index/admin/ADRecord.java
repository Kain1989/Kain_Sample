package com.telenav.lucene.core.index.admin;

import com.telenav.address.common.datatype.*;
import com.telenav.lucene.core.datatype.LangName;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: wenjiangc Date: 5/5/14 Time: 11:52 AM Copyright (c) 2013 TeleNav, Inc
 */
public class ADRecord {

    private static Pattern namePattern = Pattern.compile("^%(ON|PC|SO|AN)=(.+?)%LanCode=(\\p{Alpha}{3})");

    public String level;
    public String id;
    public List<LangName> names = new ArrayList<LangName>();
    public ACCountry country;

    public ADRecord(String record) {
        parseTxdLine(record);
    }

    public LangName getPrimaryName() {
        ACLanguage language = country.getLanguages().get(0);

        if ("A1".equalsIgnoreCase(level)) {
        	if (Region.getCurrentRegion() == Region.NA) {
	            // for state, try to find abbreviation
	            for (LangName name : names) {
	                if ((name.getOrigName().length() == 2) && StringUtils.isAsciiPrintable(name.getOrigName())) {
	                    return name;
	                }
	            }
        	}
        }

        for (LangName name : names) {
            if (name.getLang() == language) {
                return name;
            }
        }
        // return default name
        return names.get(0);
    }

    public boolean parseTxdLine(String line) {
        // AD;21520368;;PA;%ON=Sanford%LanCode=ENG<%SN="sam\U+007Cfol%LanCode=SPM%PF=2%ME=2><%SN="sam\U+007Cfor%LanCode=SPM%PF=2%ME=2><%SN="sam\U+007CforD%LanCode=SPM%PF=1%ME=2><%SN=*"s{n\U+007Cf@rd%LanCode=ENU%PF=1%ME=1>;%33=21520368;70260;USA;;;
        String[] splits = StringUtils.splitPreserveAllTokens(line, ";");

        if (splits.length < 8) {
            System.out.println("Invalid AD record : " + line);
            return false;
        }
        try {
            level = splits[3];
            id = splits[1];

            String[] admins = StringUtils.split(splits[4], Separator.VerticalLine);
            for (String admin : admins) {
                Matcher nameMatcher = namePattern.matcher(admin);

                if (nameMatcher.find()) {

                    String strName = nameMatcher.group(2);
                    // clean the escape
                    strName = replaceEscape(strName);

                    String strLanguage = nameMatcher.group(3);

                    if (StringUtils.isEmpty(strName)) {
                        System.out.println("The name is empty !~ " + line);
                    }

                    try {
                        ACLanguage language = ACLanguage.parse(strLanguage);
                        names.add(new LangName(strName + Separator.Colon + language));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            if ((names == null) || (names.size() < 1)) {
                System.out.println("parse name failed : " + line);
                return false;
            }

            this.country = ACCountry.parse(splits[7]);
            return true;

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String replaceEscape(String string) {
        string = StringUtils.replace(string, Delimiter.CommaRep, ",");
        return string;
    }
}
