package com.da.service;

import com.da.dto.rs.DocumentRS;
import com.da.entity.DocumentEntity;
import com.da.entity.UserEntity;
import com.da.repository.DocumentRepository;
import com.da.repository.UserRepository;
import com.da.service.impl.DocumentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

public class DocumentServiceTest {

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DocumentProcessorService documentProcessorService;

    private final Date date = new Date();

    @BeforeEach
    public void setup(){
        this.documentService = new DocumentServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadDocument() throws Exception {

        byte[] content = null;
        MultipartFile file = new MockMultipartFile("document1",
                "document1", "text/plain", content);
        Integer words = 10;

        Mockito.when(this.documentProcessorService.wordCount(null)).thenReturn(words);
        Mockito.when(this.userRepository.findUserEntityByEmail("123")).thenReturn(Optional.of(new UserEntity()));

        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setName("document1");
        documentEntity.setData(null);
        documentEntity.setWordCount(words);
        documentEntity.setUser(new UserEntity());
        documentEntity.setCreatedDate(date);

        this.documentService.uploadDocument(file, "123");


    }

    @Test
    public void testUploadDocumentUserNotFound(){
        byte[] content = null;
        MultipartFile file = new MockMultipartFile("document1",
                "document1", "text/plain", content);
        Integer words = 10;
        Mockito.when(this.documentProcessorService.wordCount(null)).thenReturn(words);
        Mockito.when(this.userRepository.findUserEntityByEmail("123")).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> {
            this.documentService.uploadDocument(file, "123");
        });
        assertEquals("There was an error processing the document", exception.getMessage());
        assertEquals("No user found", exception.getCause().getMessage());
    }

    @Test
    public void testGetAllDocumentsByUser(){

        Mockito.when(this.userRepository.findUserEntityByEmail("123")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            List<DocumentRS> response = this.documentService.getAllDocumentsByUser("123");
        });

        assertEquals("No user found", exception.getMessage());

    }

    @Test
    public void testGetAllDocumentsByUserNotFound(){
        this.setupGetAllDocumentsByUser();
        List<DocumentRS> response = this.documentService.getAllDocumentsByUser("123");
        this.checkGetAllDocumentsByUserResult(response);
    }

    @Test
    public void testGetFrecuentWords(){
        LinkedHashMap<String, String> frecuentWords = new LinkedHashMap<>();
        frecuentWords.put("word1", "3");

        DocumentEntity documentEntity = this.setDocumentEntity("document1");
        UserEntity userEntity = this.setUserEntity();

        Mockito.when(this.userRepository.findUserEntityByEmail("123")).thenReturn(Optional.of(userEntity));
        Mockito.when(this.documentRepository.getDocumentByUserAndName(userEntity, "document1")).thenReturn(documentEntity);
        Mockito.when(this.documentProcessorService.frecuentWords(documentEntity.getData(), 3)).thenReturn(frecuentWords);

        LinkedHashMap<String, String> response = this.documentService.getFrecuentWords(3,"123", "document1");

        verify(this.documentRepository).getDocumentByUserAndName(userEntity, "document1");
        verify(this.documentProcessorService).frecuentWords(documentEntity.getData(), 3);

        Assertions.assertEquals("3", response.get("word1"));
    }

    @Test
    public void testGetFrecuentWordsUserNotFound(){
        Mockito.when(this.userRepository.findUserEntityByEmail("123")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            LinkedHashMap<String, String> response = this.documentService.getFrecuentWords(3,"123", "document1");
        });
        assertEquals("No user found", exception.getMessage());
    }

    private void setupGetAllDocumentsByUser(){
        UserEntity userEntity = this.setUserEntity();

        Mockito.when(this.userRepository.findUserEntityByEmail("123")).thenReturn(Optional.of(userEntity));

        List<DocumentEntity> documents = new ArrayList<>();
        DocumentEntity documentEntity1 = this.setDocumentEntity("document1");
        documents.add(documentEntity1);
        DocumentEntity documentEntity2 = this.setDocumentEntity("document2");
        documents.add(documentEntity2);

        Mockito.when(this.documentRepository.getAllDocumentsByUser(userEntity)).thenReturn(documents);
    }

    private void checkGetAllDocumentsByUserResult(List<DocumentRS> response){
        verify(this.userRepository).findUserEntityByEmail("123");
        verify(this.documentRepository).getAllDocumentsByUser(this.setUserEntity());

        Assertions.assertEquals("document1", response.get(0).getName());
        Assertions.assertEquals(String.valueOf(this.date), response.get(0).getCreationDate());
        Assertions.assertEquals("document2", response.get(1).getName());
        Assertions.assertEquals(String.valueOf(this.date), response.get(0).getCreationDate());
    }

    private DocumentEntity setDocumentEntity(String name){
        byte[] content = null;
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setName(name);
        documentEntity.setData(content);
        documentEntity.setCreatedDate(this.date);
        return documentEntity;
    }

    private UserEntity setUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("123");
        return userEntity;
    }

}
