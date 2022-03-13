package com.da.service;

import com.da.dto.rs.DocumentRS;
import com.da.dto.rs.FrecuencyRS;
import com.da.dto.rs.UserRS;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;

public interface DocumentService {

    void uploadDocument(MultipartFile document, String userEmail) throws Exception;

    List<DocumentRS> getAllDocumentsByUser(String userEmail);

    LinkedHashMap<String, String> getFrecuentWords(Integer totalWords, String userEmail, String documentname);

    UserRS getUsersNotUpload(String fromDate, String toDate);

}
