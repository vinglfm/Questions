package com.lfm.model;

import java.util.Map;

/**
 *
 * @author : vinglfm(vinglfm@gmail.com)
 */
public class Article {
    private final Map<String, Integer> unigram;
    private final Map<String, Integer> bigram;
    private final Map<String, Integer> trigram;

    public Article(Map<String, Integer> words, Map<String, Integer> bigram, Map<String, Integer> trigram) {
        this.unigram = words;
        this.bigram = bigram;
        this.trigram = trigram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (bigram != null ? !bigram.equals(article.bigram) : article.bigram != null) return false;
        if (trigram != null ? !trigram.equals(article.trigram) : article.trigram != null) return false;
        if (unigram != null ? !unigram.equals(article.unigram) : article.unigram != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = unigram != null ? unigram.hashCode() : 0;
        result = 31 * result + (bigram != null ? bigram.hashCode() : 0);
        result = 31 * result + (trigram != null ? trigram.hashCode() : 0);
        return result;
    }
}
