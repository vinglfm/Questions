package com.lfm.model;

import com.lfm.model.parsers.ArticleParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author : vinglfm(vinglfm@gmail.com)
 */
public class ArticleReader {

    private Article article;

    private ArticleParser articleParser;

    /**
     * @param pathToArticle path to article file
     * @throws IllegalArgumentException if path to article or parser instance is null
     * @throws IOException if file wasn't found
     */
    public ArticleReader(Path pathToArticle, ArticleParser articleParser) throws IllegalArgumentException,
            IOException {
        if(pathToArticle == null || articleParser == null) {
            throw new IllegalArgumentException(/*TODO: write explanation*/);
        }
        this.articleParser = articleParser;
        createArticle(pathToArticle);
    }

    private void createArticle(Path pathToArticle) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(pathToArticle, Charset.defaultCharset());
        article = articleParser.parseText(bufferedReader);
    }


    public Article getArticle() {
        return article;
    }
}
