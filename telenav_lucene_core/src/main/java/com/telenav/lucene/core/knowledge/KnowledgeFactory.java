package com.telenav.lucene.core.knowledge;


import com.telenav.lucene.core.knowledge.local.FileSynonyms;

public class KnowledgeFactory {

    private static KnowledgeFactory instance = new KnowledgeFactory();

    public static KnowledgeFactory getInstance() {
        return instance;
    }
    private Synonyms synonyms;
    
    private KnowledgeFactory() {
        synonyms = new FileSynonyms();
    }

    public Synonyms getSynonyms() {
        return synonyms;
    }    
}
