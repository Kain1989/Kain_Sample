package com.kain.algorithm.test;


import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2/9/2017.
 */
public class Parent {

    private String astr;

    public String getAstr() {
        return astr;
    }

    public void setAstr(String astr) {
        this.astr = astr;
    }

    public static void main(String args[]) {
        String name = "Parques de n' Guadalupe";
        String words[] = name.split(" ");
        String regex = "\\s(de los|de les|de las|sobre|de sa|de na|de n'|de la|de l'|dels|d'en|des|del|das|'en|en|do|de|d'|al|a)|(de los|de les|de las|sobre|de sa|de na|de n'|de la|de l'|dels|d'en|des|del|das|'en|en|do|de|d'|al|a)\\s";
        Pattern prepositionPattern = Pattern.compile("(de los|de les|de las|sobre|de sa|de na|de n'|de la|de l'|dels|d'en|des|del|das|'en|en|do|de|d'|al|a)");
        if (prepositionPattern == null) {
            System.out.println(name);
        }
        List<String> wordList = new ArrayList<String>(Arrays.asList(words));
        Iterator<String> iterator = wordList.iterator();
        while(iterator.hasNext()) {
            String word = iterator.next();
            Matcher matcher = prepositionPattern.matcher(word);
            if (matcher.matches()) {
                iterator.remove();
            }
        }
        System.out.println(StringUtils.join(wordList, " "));
        System.out.println(name.replaceAll(regex, ""));
        System.out.println("Parques de les Guadalupe".replaceAll(regex, ""));
        System.out.println("Parques de n' Guadalupe".replaceAll(regex, ""));
    }
}
