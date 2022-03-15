package com.da.service;

import com.da.service.impl.DocumentProcessorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.LinkedHashMap;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;

public class DocumentProcessorServiceTest {

    @InjectMocks
    private DocumentProcessorServiceImpl documentProcessorService;

    byte[] documentData = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");

    @BeforeEach
    public void setup(){
        this.documentProcessorService = new DocumentProcessorServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWordCount() {
        Integer response = documentProcessorService.wordCount(this.documentData);
        Assertions.assertEquals(2, response);
    }

    @Test
    public void testFrecuentWords() {
        LinkedHashMap<String, String> response = documentProcessorService.frecuentWords(this.documentData, 3);
        Assertions.assertEquals("1", response.get("o"));
    }


}
