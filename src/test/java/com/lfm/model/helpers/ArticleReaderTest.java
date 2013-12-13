package com.lfm.model.helpers;

import com.lfm.model.Article;
import com.lfm.model.ArticleReader;
import com.lfm.model.parsers.ArticleParsers;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author : vinglfm(vinglfm@gmail.com)
 */
@RunWith(JUnitParamsRunner.class)
public class ArticleReaderTest {


    public Object[] getFiles() throws URISyntaxException {
        Path smallArticle = Paths.get("src/test/java/resources/simple1.txt");
        Path mediumArticle = Paths.get("src/test/java/resources/empty.txt");

        return new Object[]{inputDataForPath(smallArticle), inputDataForPath(mediumArticle)};
    }

    private InputData inputDataForPath(Path path) {
        Map<String, Integer> words = new HashMap<>();
        Article expectedArticle = new Article(words);
        return new InputData(path, expectedArticle);
    }

    @Test
    @Parameters(method = "getFiles")
    public void shouldCreateArticleReaderWithOnValidParameters(InputData inputData) throws Exception {
        ArticleReader articleReader = new ArticleReader(inputData.pathToFile, ArticleParsers.SIMPLE_ARTICLE_PARSER);
        assertTrue(inputData.expectedArticle.equals(articleReader.getArticle()));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionOnCreatingArticleReaderWithNullParameter() throws Exception {
        Path nullPath = null;
        try {
            ArticleReader articleReader = new ArticleReader(nullPath, ArticleParsers.SIMPLE_ARTICLE_PARSER);
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
