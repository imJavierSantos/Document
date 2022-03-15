package com.da.service.impl;

import com.da.service.DocumentProcessorService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DocumentProcessorServiceImpl implements DocumentProcessorService {

    @Override
    public Integer wordCount(byte[] documentData) {
        return cleanDocument(documentData).split("\\s+").length;
    }

    @Override
    public LinkedHashMap<String, String> frecuentWords(byte[] documentData, Integer totalWords) {
        String document = cleanDocument(documentData);
        document = removeUnnecesaryWordsFromDocument(document);
        Map<String, Integer> wordsCounted = countWordsFromDocument(document);
        return shortCountedWords(wordsCounted, totalWords);
    }

    private static String cleanDocument(byte[] documentData) {
        return new String(documentData).replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    private static String removeUnnecesaryWordsFromDocument(String document) {
        document = " ".concat(document).concat(" ");
        return document.replaceAll(" the ", " ").replaceAll(" me ", " ").replaceAll(" you ", " ")
                .replaceAll(" i ", " ").replaceAll(" of ", " ").replaceAll(" and ", " ")
                .replaceAll(" a ", " ").replaceAll(" we ", " ");
    }

    private static Map<String, Integer> countWordsFromDocument(String document){
        String[] words = document.split("\\s+");
        int wrc=1;
        Map<String, Integer> result = new HashMap<>();
        for(int i=0;i<words.length;i++){
            for(int j=i+1;j<words.length;j++){
                if(words[i].equals(words[j])){
                    wrc=wrc+1;
                    words[j]="0";
                }
            }
            if(!Objects.equals(words[i], "0")) {
                result.put(words[i], wrc);
            }
            wrc=1;
        }
        result.remove("");
        return result;
    }

    private static LinkedHashMap<String, String> shortCountedWords(Map<String, Integer> countedWords, Integer totalWords){
        LinkedHashMap<String, String> sortedMap = new LinkedHashMap<>();
        countedWords.entrySet()
                .stream().limit(totalWords)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), String.valueOf(x.getValue())));
        return sortedMap;
    }

}
