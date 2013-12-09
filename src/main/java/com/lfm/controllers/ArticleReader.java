package com.lfm.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author : vinglfm(vinglfm@gmail.com)
 */
public class ArticleReader {

    private Article article;

    /**
     * @param pathToArticle path to article file
     * @throws IllegalArgumentException
     */
    public ArticleReader(Path pathToArticle) throws IllegalArgumentException, IOException {
        if(pathToArticle == null) {
            throw new IllegalArgumentException();
        }

        createArticle(pathToArticle);
    }

    private void createArticle(Path pathToArticle) throws IOException {
        Map<String, Integer> words = new HashMap<>();
        BufferedReader bufferedReader = Files.newBufferedReader(pathToArticle, Charset.defaultCharset());

        String line = bufferedReader.readLine();
        while (line != null) {

            String[] elems = line.split("\\W+");
            for (String elem : elems) {
                Integer count = words.get(elem);
                if(count == null) {
                    count = 0;
                } else {
                    ++count;
                }
                words.put(elem, count);
            }
            line = bufferedReader.readLine();
        }


        article = new Article(words);
    }

    public Article getArticle() {
        return article;
    }
}
