package com.lfm.model.helpers;


import java.util.HashMap;
import java.util.Map;

public enum  AmbiguityHelper {
    INSTANCE;
    private final Map<String, String> wordMapping;

    AmbiguityHelper() {
        //TODO: read from properties file all ambiguities
        wordMapping = new HashMap<>();
        wordMapping.put("isn't","is not");
        wordMapping.put("its","it is");
        wordMapping.put("it's","it is");
    }


    public String resolveAmbiguity(String forString) {
        return wordMapping.get(forString);
    }
}
