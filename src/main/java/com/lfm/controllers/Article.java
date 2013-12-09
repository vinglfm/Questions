package com.lfm.controllers;

import java.util.Map;

/**
 *
 * @author : vinglfm(vinglfm@gmail.com)
 */
public class Article {
    private final Map<String, Integer> words;

    protected Article(Map<String, Integer> words) {
        this.words = words;
    }
}
