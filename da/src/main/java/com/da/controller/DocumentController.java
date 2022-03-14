package com.da.controller;

import com.da.dto.rs.DocumentRS;
import com.da.dto.rs.UserRS;
import com.da.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("document")
public class DocumentController {


    @Autowired
    private DocumentService documentService;

    @PostMapping
    public void uploadDocument(@RequestParam("document") MultipartFile document, @RequestParam("userEmail") String userEmail) throws Exception {
        documentService.uploadDocument(document, userEmail);
    }

    @GetMapping("/documents")
    public List<DocumentRS> getAllDocumentsByUser(@RequestParam("userEmail") String userEmail){
        return documentService.getAllDocumentsByUser(userEmail);
    }

    @GetMapping("/frecuency")
    public LinkedHashMap<String, String> getFrecuentWords(@RequestParam("totalWords") Integer totalWords, @RequestParam("userEmail") String userEmail, @RequestParam("documentName") String documentName){
        return documentService.getFrecuentWords(totalWords, userEmail, documentName);
    }

    @GetMapping("/users")
    public UserRS getUsersNotUpload(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate){
        return documentService.getUsersNotUpload(fromDate, toDate);
    }

}
