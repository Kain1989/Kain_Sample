package com.telenav.lucene.core.analyzers;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.Reader;

public class ChineseExactAnalyzer extends Analyzer {

    private static class CharFilter extends TokenFilter {
        private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

        public CharFilter(TokenStream in) {
            super(in);
        }

        @Override
        public final boolean incrementToken() throws IOException {
            if (input.incrementToken()) {
                char[] buffer = termAtt.buffer();
                int len = termAtt.length();
                termAtt.setEmpty();
                for (int i = 0; i < len; i++) {
                    if (accept(buffer[i])) {
                        termAtt.append(buffer[i]);
                    }
                }

                return true;
            } else
                return false;
        }

        private boolean accept(char ch) {
            return ch != ' ';
        }
    }
    
    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        final Tokenizer source = new KeywordTokenizer(reader);
        TokenStream ts = source;
        ts = new LowerCaseFilter(Version.LUCENE_45, ts);
        ts = new CharFilter(ts);
        ts = new TrimFilter(Version.LUCENE_45, ts);

        return new TokenStreamComponents(source, ts);
    }
}