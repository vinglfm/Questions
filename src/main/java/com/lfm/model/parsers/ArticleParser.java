package com.lfm.model.parsers;

import com.lfm.model.Article;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface ArticleParser {
    Article parseText(BufferedReader reader) throws IOException;
}
