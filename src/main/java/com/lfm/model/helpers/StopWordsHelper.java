package com.lfm.model.helpers;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum  StopWordsHelper {
    INSTANCE;
    private static final String STOP_WORDS_PATH = "src/main/java/resources/stopwords.txt";
    private static Set<String> stopWords;
    private static final int LOWER_WORD_LENGTH_BOUND = 1;

    static {
        Set<String> words = new HashSet<>();
        Path pathToStopWords = Paths.get(STOP_WORDS_PATH);
        try {
            BufferedReader br = Files.newBufferedReader(pathToStopWords, Charset.defaultCharset());
            String word;
            while ((word = br.readLine()) != null) {
                words.add(word);
            }
            stopWords = Collections.unmodifiableSet(words);
        } catch (IOException e) {
            //TODO: use some apache4 logger ?
            stopWords = Collections.emptySet();
        }
    }

    public boolean isStopWord(String collocation) {
        return stopWords.contains(collocation) || !(collocation.length() > LOWER_WORD_LENGTH_BOUND);
    }

}
