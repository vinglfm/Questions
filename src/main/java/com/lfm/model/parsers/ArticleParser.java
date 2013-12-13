package com.lfm.model.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface ArticleParser {
    Map<String, Integer> parseTextFromPath(BufferedReader reader) throws IOException;
}
