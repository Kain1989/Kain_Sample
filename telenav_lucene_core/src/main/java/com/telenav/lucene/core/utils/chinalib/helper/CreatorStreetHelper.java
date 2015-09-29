package com.telenav.lucene.core.utils.chinalib.helper;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: shbzeng
 * @date: 10-12-29
 * @time: 18:58
 * TeleNav Inc.
 */
public class CreatorStreetHelper
{
    private static final int MIN_STREET_LENGTH = 2;
    private static final List<String> DIRECTION_EXCLUDES = Arrays.asList("??","??","??","??","??","??", "??", "???","??","???");
    private static final List<String> STREET_TYPES = Arrays.asList("??", "??", "??", "?", "?", "?",  "?");
    private static final List<String> END_DIRECTION = Arrays.asList("?","?", "?","?","?");
    private static Pattern[] patterns = new Pattern[]{
            Pattern.compile("[????]?(.{2,}?)(?:[????]?)?[??]((?:?)+)"),
            Pattern.compile("[????]?(.{2,}?)(?:?[????????????0-9])?[??????????????????]*[??????????]*?((?:?|?|?|?|??|??|??)+)")
    };
    private static Pattern[] patterns2 = new Pattern[]{
            Pattern.compile("[????](?![???????])(.{2,}?(?:?|?|?|?|??|??|??))")
    };

    public static String removeStartDirection1(String street)
    {
        String result = street;
        for (Pattern pattern : patterns2)
        {
            Matcher matcher = pattern.matcher(street);
            if (matcher.matches())
            {
                result = matcher.group(1);
                break;
            }
        }
        return result;
    }

    /**
     * street body helper.
     *
     * @param street street name
     * @return street body
     * @author shbzeng
     */
    public static String removeStartDirection(String street)
    {
        street = StringUtils.trimToEmpty(street);
        if (street.length() <= MIN_STREET_LENGTH)
            return street;
        String result = street;
        String streetCopy = street;
        int startLength = -1;
        int i = 0;
        for (i = 0; i < DIRECTION_EXCLUDES.size(); i++)
        {
            String s = DIRECTION_EXCLUDES.get(i);
            if (street.startsWith(s))
            {
                startLength = s.length();
                streetCopy = StringUtils.repeat("$", startLength) + StringUtils.substringAfter(street, s);
                break;
            }
        }
        for (Pattern pattern : patterns2)
        {
            Matcher matcher = pattern.matcher(streetCopy);
            if (matcher.matches())
            {
                result = matcher.group(1);
                break;
            }
        }

        if (startLength > 0)
            result = DIRECTION_EXCLUDES.get(i) + result.substring(startLength);
        return result;
    }
    public static String removeEndDirection(String street)
    {
        street = StringUtils.trimToEmpty(street);
        if (street.length() <= MIN_STREET_LENGTH)
            return street;
        String streetPrefix = street.substring(0, street.length() - 1);
        String streetSuffix = street.substring(street.length() - 1, street.length());
        if(END_DIRECTION.contains(streetSuffix))
            return streetPrefix;
        else
            return street;
    }
    public static String getStreetBody(String street)
    {
        street = StringUtils.trimToEmpty(street);
        if (street.length() <= MIN_STREET_LENGTH)
            return street;
        String result = street;
        String streetCopy = street;
        int startLength = -1;
        int i = 0;
        for (i = 0; i < DIRECTION_EXCLUDES.size(); i++)
        {
            String s = DIRECTION_EXCLUDES.get(i);
            if (street.startsWith(s))
            {
                startLength = s.length();
                streetCopy = StringUtils.repeat("$", startLength) + StringUtils.substringAfter(street, s);
                break;
            }
        }
        for (Pattern pattern : patterns)
        {
            Matcher matcher = pattern.matcher(streetCopy);
            if (matcher.matches())
            {
                result = matcher.group(1);
                break;
            }
        }

        if (startLength > 0)
            result = DIRECTION_EXCLUDES.get(i) + result.substring(startLength);
        return result;
    }

    public static String getStreetType(String street)
    {
        if (StringUtils.isNotEmpty(street))
        {
            for (String type : STREET_TYPES)
            {
                if (street.endsWith(type))
                    return type;
            }
        }

        return StringUtils.EMPTY;
    }

    public static void testRemoveDirection()
    {
          String[] streets = new String[]{
                "????|???",
                "????|????",
                "?????|?????",
          };

        for (String street : streets)
        {
            String[] parts = StringUtils.split(street, "|");

            if (StringUtils.equals(parts[1], CreatorStreetHelper.removeStartDirection(parts[0])))
                System.out.println(parts[0] + ":"/* + parts[1] + ":"*/ + CreatorStreetHelper.removeStartDirection(parts[0]));
            else
                System.err.println(parts[0] + ":"/* + parts[1] + ":"*/ + CreatorStreetHelper.removeStartDirection(parts[0]));
        }
    }
    public static void testStreetBody()
    {
        String[] streets = new String[]{
                "?????|??",
                "???|??",
                "????|??",
                "????|???",
                "?????|???",
                "????|??",
                "????|??",
                "???|??",
                "?????|????",
                "??????|??",
                "G110|G110",
                "G110??|G110",
                "???|??",
                "???|??",
                "???123?|???123?",
                "?????|????",
                "?????|??",
                "??????|????",
                "???|??",
                "????|??",
                "?????|??",
                "???????|??",
                "?????|??",
                "????|??",
                "??????|???",
                "?????|???",
                "???|??",
                "???|??",
                "??|??",
                "???|???",
                "?2???|?2?",
                "??????|??",
                "????|??",
                "????|??",
                "??????|???",
                "????|??",
                "???????|???",
                "?????|???",
                "??|??",
                "??????|???",
                "??????|???",
                "??????|???",
                "324??|324",
                "??????|????",
                "???|??",
                "?????|??",
                "115??|115",
                "??????|??",
                "????|??",
                "????|??"
        };

        for (String street : streets)
        {
            String[] parts = StringUtils.split(street, "|");

            if (StringUtils.equals(parts[1], CreatorStreetHelper.getStreetBody(parts[0])))
                System.out.println(parts[0] + ":"/* + parts[1] + ":"*/ + CreatorStreetHelper.getStreetBody(parts[0]));
            else
                System.err.println(parts[0] + ":"/* + parts[1] + ":"*/ + CreatorStreetHelper.getStreetBody(parts[0]));
        }
    }


    public static void main(String[] args)
    {
        testStreetBody();
    }

}

