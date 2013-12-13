package com.lfm.model.parsers;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//TODO: change to enum, each parser is an new instance ...
public enum ArticleParsers implements ArticleParser {
    SIMPLE_ARTICLE_PARSER {
        @Override
        public Map<String, Integer> parseTextFromPath(BufferedReader bufferedReader) throws IOException {
            Map<String, Integer> words = new HashMap<>();
            String line = bufferedReader.readLine();
            while (line != null) {

                for (String elem : line.split("\\W+")) {
                    elem = elem.toLowerCase();
                    Integer count = words.get(elem);
                    if (count == null) {
                        count = 0;
                    }
                    words.put(elem, ++count);
                }
                line = bufferedReader.readLine();
            }
            return words;
        }
    },
    SINGLE_WORD_AMBIGUITY_PARSER {
        @Override
        public Map<String, Integer> parseTextFromPath(BufferedReader reader) throws IOException {

            return null;
        }
    }
}
