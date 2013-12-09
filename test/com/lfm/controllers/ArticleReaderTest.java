package com.lfm.controllers;

import junit.framework.TestCase;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.runner.RunWith;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.fail;

/**
 *
 * @author : vinglfm(vinglfm@gmail.com)
 */
@RunWith(JUnitParamsRunner.class)
public class ArticleReaderTest {


    public Object[] getFiles() throws URISyntaxException {
        Path smallArticle = Paths.get(getClass().getResource("/TodoList.txt").toURI());
        Path mediumArticle = Paths.get("TodoList.txt");

        return new Object[]{inputDataForPath(smallArticle), inputDataForPath(mediumArticle)};
    }

    private InputData inputDataForPath(Path path) {
        HashMap<String, Integer> words = new HashMap<String, Integer>();
        Article expectedArticle = new Article(words);
        return new InputData(path, expectedArticle);
    }

    @Test
    @Parameters(method = "getFiles")
    public void shouldCreateArticleReaderWithOnValidParameters(InputData inputData) throws Exception {
        ArticleReader articleReader = new ArticleReader(inputData.pathToFile);
        TestCase.assertEquals(inputData.expectedArticle, articleReader.getArticle());

    }

    @Test
    public void shouldThrowIllegalArgumentExceptionOnCreatingArticleReaderWithNullParameter() throws Exception {
        Path nullPath = null;
        try {
            ArticleReader articleReader = new ArticleReader(nullPath);
            fail();
        } catch (IllegalArgumentException exception) {
            //IllegalArgumentException should be thrown.
        }
    }

    private static class InputData {
        private Path pathToFile;
        private Article expectedArticle;

        private InputData(Path path, Article article) {
            pathToFile = path;
            expectedArticle = article;
        }
    }

}
