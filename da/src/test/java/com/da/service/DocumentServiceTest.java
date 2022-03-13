package com.da.service;

import com.da.dto.rs.DocumentRS;
import com.da.service.impl.DocumentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class DocumentServiceTest {


    @Autowired
    private DocumentService documentService = new DocumentServiceImpl();

    @Test
    public void testGetAllDocumentsByUser(){
        List<DocumentRS> response = documentService.getAllDocumentsByUser("123");
        response.get(0);
    }


}
