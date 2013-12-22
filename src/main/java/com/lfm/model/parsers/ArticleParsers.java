package com.lfm.model.parsers;


import com.lfm.model.Article;
import com.lfm.model.helpers.StopWordsHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Works only for EN locale at the moment?
 */
public enum ArticleParsers implements ArticleParser {

    SIMPLE_ARTICLE_PARSER {
        private final String WORD_SPLITTING_REGEX = "[^\\w'-]+";
        private final Pattern pattern = Pattern.compile(WORD_SPLITTING_REGEX);

        @Override
        public Article parseText(BufferedReader bufferedReader) throws IOException {
            Map<String, Integer> unigram = new HashMap<>();
            Map<String, Integer> bigram = new HashMap<>();
            Map<String, Integer> trigram = new HashMap<>();
//            BreakIterator breakIterator = BreakIterator.getSentenceInstance(Locale.ENGLISH);
            String line = bufferedReader.readLine();
            TrigramCollocation previousWords = new TrigramCollocation();
            String previousWord = null;
            while (line != null) {
//                breakIterator.setText(line);
//                int start = breakIterator.first();
//                for(int end = breakIterator.next(); end != BreakIterator.DONE; start = end,
//                        end = breakIterator.next()) {
//                    line = line;//line.substring(start, end);
                unigramParsing(unigram, line);
                previousWord = bigramParsing(bigram, line, previousWord);
                previousWords = trigramParsing(trigram, line, previousWords);
//                }
                line = bufferedReader.readLine();
            }
            return new Article(unigram, bigram, trigram);
        }

        private void unigramParsing(Map<String, Integer> unigram, String text) {

            String lowerCaseWord;
            for (String word : text.split(WORD_SPLITTING_REGEX)) {
                lowerCaseWord = word.toLowerCase();
                if (!StopWordsHelper.INSTANCE.isStopWord(lowerCaseWord)) {
                    countWord(unigram, lowerCaseWord);
                }
            }
        }

        private String bigramParsing(Map<String, Integer> bigram, String text, String previousWord) {
            Matcher matcher = pattern.matcher(text);
            int begIndex = 0;
            String begWord = previousWord;
            String collocation, word;
            while (matcher.find()) {
                word = text.substring(begIndex, matcher.start()).toLowerCase();
                if (word.length() > 1) {
                    if (!StopWordsHelper.INSTANCE.isStopWord(word)) {
                        if (begWord != null) {
                            collocation = new StringBuilder(begWord).append(" ").append(word).toString();
                            countWord(bigram, collocation);
                        }
                        begWord = " ".equals(matcher.group()) ? word : null;
                    }
                } else {
                    begWord = null;
                }
                begIndex = matcher.end();
            }
            if (begIndex != text.length()) {
                word = text.substring(begIndex).toLowerCase();
                if (!StopWordsHelper.INSTANCE.isStopWord(word)) {
                    if (begWord != null) {
                        collocation = new StringBuilder(begWord).append(" ").append(word).toString();
                        countWord(bigram, collocation);
                    }
                    begWord = word;
                }
            }
            return begWord;
        }

        private TrigramCollocation trigramParsing(Map<String, Integer> trigram, String text, TrigramCollocation previousWords) {
            Matcher matcher = pattern.matcher(text);
            int begIndex = 0;
            TrigramCollocation begWords = previousWords;
            String word;
            while (matcher.find()) {
                word = text.substring(begIndex, matcher.start()).toLowerCase();
                if (word.length() > 1) {
                    if (!StopWordsHelper.INSTANCE.isStopWord(word)) {
                        if (begWords.addWord(word)) {
                            countWord(trigram, begWords.toString());
                            begWords.shiftWords();
                        }

                        if (!" ".equals(matcher.group())) {
                            begWords.clear();
                        }
                    }
                } else {
                    begWords.clear();
                }
                begIndex = matcher.end();
            }
            if (begIndex != text.length()) {
                word = text.substring(begIndex).toLowerCase();
                if (!StopWordsHelper.INSTANCE.isStopWord(word)) {
                    if (begWords.addWord(word)) {
                        countWord(trigram, begWords.toString());
                        begWords.shiftWords();
                    }
                }
            }
            return begWords;
        }

        private void countWord(Map<String, Integer> map, String collocation) {
            if (!StopWordsHelper.INSTANCE.isStopWord(collocation)) {
                Integer count = map.get(collocation);
                if (count == null) {
                    count = 0;
                }
                map.put(collocation, ++count);
            }
        }
    };

    private static class TrigramCollocation {
        private static final int WORD_NUMBER = 3;
        private final String[] wordBuilder = new String[WORD_NUMBER];
        private int wordsCounter = 0;

        private boolean addWord(String word) {
            wordBuilder[wordsCounter++] = word;
            return wordsCounter < WORD_NUMBER ? false : true;
        }

        private void shiftWords() {
            for(int i = 1; i < wordsCounter; ++i) {
                wordBuilder[i - 1] = wordBuilder[i];
            }
            wordBuilder[wordsCounter - 1] = null;
            --wordsCounter;
        }

        private void clear() {
            wordsCounter = 0;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < wordsCounter - 1; ++i) {
                sb.append(wordBuilder[i]);
                sb.append(" ");
            }
            sb.append(wordBuilder[wordsCounter - 1]);
            return sb.toString();
        }
    }
}
