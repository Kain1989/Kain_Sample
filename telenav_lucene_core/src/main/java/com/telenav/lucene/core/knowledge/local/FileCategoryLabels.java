package com.telenav.lucene.core.knowledge.local;

import com.telenav.address.common.datatype.Region;
import com.telenav.lucene.core.datatype.Constants;
import com.telenav.lucene.core.knowledge.CategoryLabels;
import org.apache.commons.io.LineIterator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class FileCategoryLabels implements CategoryLabels {

    private Map<Integer, Map<String, Set<String>>> labelMap = new HashMap<Integer, Map<String, Set<String>>>();
    private Map<Integer, Map<String, Set<String>>> typoLabelMap = new HashMap<Integer, Map<String, Set<String>>>();

    
    public FileCategoryLabels(Region region, String product) {
        InputStream inputStream = FileCategoryLabels.class.getClassLoader().getResourceAsStream(Constants.ONBOARD_SEARCH_RESOURCE_ROOT_PATH + "categoryLabels_"+region.name()+"_"+product.toLowerCase()+".txt");
        LineIterator iter = null;
        try {
            iter = new LineIterator(new InputStreamReader(inputStream, "UTF-8"));
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        while (iter.hasNext()) {
            String line = iter.nextLine().toLowerCase();
            if (line.startsWith("#")) {
                continue;
            }
            
            boolean isTypo = false;
            if(line.startsWith("*")){
                isTypo = true;
                line = line.substring(1);
            }
            
            String[] vals = line.split("%");
            if (vals == null || vals.length < 3) {
                continue;
            }
            
            add(Integer.valueOf(vals[0]), vals[2], vals[1], isTypo);
            System.out.println(line);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(int catId, String language, String aliases) {
        add(catId, language, aliases, false);
    }
    public void add(int catId, String language, String aliases, boolean isTypo) {
        String[] labelStrs = aliases.split("\\|");
        
        language = language.toLowerCase();

        Map<String, Set<String>> existLabelMap;
        
        if(!isTypo){
            existLabelMap = labelMap.get(catId);
            if (existLabelMap == null) {
                existLabelMap = new HashMap<String, Set<String>>();
                labelMap.put(catId, existLabelMap);
            } 
        }else{
            existLabelMap = typoLabelMap.get(catId);
            if (existLabelMap == null) {
                existLabelMap = new HashMap<String, Set<String>>();
                typoLabelMap.put(catId, existLabelMap);
            }
        }
        
        Set<String> labelsInLang = existLabelMap.get(language);
        if(labelsInLang == null){
            labelsInLang = new HashSet<String>();
            existLabelMap.put(language, labelsInLang);
        }
        
        List<String> labelList = new ArrayList<String>();
        for (String labelStr : labelStrs) {
            labelList.add(labelStr.trim());
        }
        labelsInLang.addAll(labelList);
    }

    @Override
    public Map<String, Set<String>> getCatLabels(int catId) {
        return labelMap.get(catId);
    }
    
    public Map<String, Set<String>> getTypoCatLabels(int catId) {
        return typoLabelMap.get(catId);
    }

}
