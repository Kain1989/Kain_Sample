package com.telenav.lucene.core.analyzers.filters;

import com.telenav.address.common.datatype.Separator;
import com.telenav.lucene.core.utils.chinalib.helper.CityHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author: rapu
 * @date: 2010-8-12
 * @time: 11:25:49
 * TeleNav Inc.
 */
public class ChineseCityBodyFilter extends TokenFilter
{
    private CharTermAttribute termAttr = addAttribute(CharTermAttribute.class);
    private LinkedList<String> tokens = new LinkedList<String>(); 

    public ChineseCityBodyFilter(TokenStream input)
    {
        super(input);
    }
    
    @Override
    public final boolean incrementToken() throws IOException
    {
    	if (!tokens.isEmpty()) {
    		termAttr.setEmpty().append(tokens.removeFirst());
    		return true;
    	}
    	
    	if (input.incrementToken()) {
    		getTokensAndText();
    		return incrementToken();
    	} else
    		return false;
    }


    private void getTokensAndText()
    {
        try
        {
        	Set<String> tokenSet = new TreeSet<String>();
        	CharTermAttribute termAttribute = input.getAttribute(CharTermAttribute.class);
        	String fullName = StringUtils.substringBeforeLast(termAttribute.toString(), Separator.Colon);
        	tokenSet.add(fullName);
            String body = CityHelper.getBodyFromCity(fullName);
            tokenSet.add(body);
            tokens.addAll(tokenSet);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
