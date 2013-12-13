package com.lfm.model;

import java.util.Map;

/**
 *
 * @author : vinglfm(vinglfm@gmail.com)
 */
public class Article {
    private final Map<String, Integer> words;

    public Article(Map<String, Integer> words) {
        this.words = words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (words != null ? !words.equals(article.words) : article.words != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return words != null ? words.hashCode() : 0;
    }
}
