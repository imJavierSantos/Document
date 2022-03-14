package com.da.service;

import java.util.LinkedHashMap;

public interface DocumentProcessorService {

    Integer wordCount(byte[] documentData);

    LinkedHashMap<String, String> frecuentWords(byte[] documentData, Integer totalWords);

}
