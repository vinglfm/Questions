package com.lfm.model.parsers;


import com.lfm.model.Article;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Works only for EN locale at the moment?
 */
public enum ArticleParsers implements ArticleParser {
    SIMPLE_ARTICLE_PARSER {
        @Override
        public Article parseText(BufferedReader bufferedReader) throws IOException {
            Map<String, Integer> unigram = new HashMap<>();
            Map<String, Integer> bigram = new HashMap<>();
            Map<String, Integer> trigram = new HashMap<>();
//            BreakIterator breakIterator = BreakIterator.getSentenceInstance(Locale.ENGLISH);
            String line = bufferedReader.readLine();
            String previousWord = null;
            while (line != null) {
//                breakIterator.setText(line);
//                int start = breakIterator.first();
//                for(int end = breakIterator.next(); end != BreakIterator.DONE; start = end,
//                        end = breakIterator.next()) {
//                    line = line;//line.substring(start, end);
                unigramParsing(unigram, line);
                previousWord = bigramParsing(bigram, line, previousWord);
                trigramParsing(trigram, line);
//                }
                line = bufferedReader.readLine();
            }
            return new Article(unigram, bigram, trigram);
        }

        private void unigramParsing(Map<String, Integer> unigram, String text) {
            String wordRegex = "[^\\w'-]+";
            for (String elem : text.split(wordRegex)) {
                countWord(unigram, elem);
            }
        }

        private String bigramParsing(Map<String, Integer> bigram, String text, String previousWord) {
            String regexp = "[^\\w'-]+";//"([?\\W|\\p{Punct}]+)";
            Pattern pattern = Pattern.compile(regexp);

            Matcher matcher = pattern.matcher(text);
            int begIndex = 0;
            String begWord = previousWord;
            String collocation;
            String word;
            while (matcher.find()) {
                word = text.substring(begIndex, matcher.start());
                if (word.length() > 1) {
                    if (begWord != null) {
                        collocation = new StringBuilder(begWord).append(" ").append(word).toString();
                        countWord(bigram, collocation);
                    }
                    begWord = " ".equals(matcher.group()) ? word : null;
                } else {
                    begWord = null;
                }
                begIndex = matcher.end();
            }
            if (begIndex != text.length()) {
                word = text.substring(begIndex);
                if (begWord != null) {
                    collocation = new StringBuilder(begWord).append(" ").append(word).toString();
                    countWord(bigram, collocation);
                }
                begWord = word;
            }
            return begWord;
        }

        private void trigramParsing(Map<String, Integer> bigram, String text) {

        }


        private void countWord(Map<String, Integer> map, String collocation) {
            collocation = collocation.toLowerCase();
            Integer count = map.get(collocation);
            if (count == null) {
                count = 0;
            }
            map.put(collocation, ++count);
        }
    },
    SINGLE_WORD_AMBIGUITY_PARSER {
        @Override
        public Article parseText(BufferedReader reader) throws IOException {

            return null;
        }
    };
    private static final String STOP_WORDS_PATH = "src/main/java/resources/stopwords.txt";

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

    private static Set<String> stopWords;
}
