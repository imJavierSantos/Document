package com.da.service.impl;

import com.da.entity.UserEntity;
import com.da.service.DocumentProcessorService;
import org.springframework.util.StringUtils;
import com.da.dto.rs.DocumentRS;
import com.da.dto.rs.UserRS;
import com.da.entity.DocumentEntity;
import com.da.repository.DocumentRepository;
import com.da.repository.UserRepository;
import com.da.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentProcessorService documentProcessorService;


    @Override
    public void uploadDocument(MultipartFile document, String userEmail) throws Exception {
        DocumentEntity documentEntity = new DocumentEntity();
        try {
            documentEntity.setName(StringUtils.cleanPath(Objects.requireNonNull(document.getOriginalFilename())));
            documentEntity.setData(document.getBytes());
            documentEntity.setWordCount(documentProcessorService.wordCount(document.getBytes()));
            Optional<UserEntity> userEntity = userRepository.findUserEntityByEmail(userEmail);
            documentEntity.setUser(userEntity.orElseThrow(() -> new RuntimeException("No user found")));
            documentEntity.setCreatedDate(new Date());
            documentRepository.save(documentEntity);
        } catch (Exception e) {
            throw new Exception("There was an error processing the document", e);
        }
    }

    @Override
    public List<DocumentRS> getAllDocumentsByUser(String userEmail) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByEmail(userEmail);
        List<DocumentEntity> documents = documentRepository.getAllDocumentsByUser(userEntity.orElseThrow(() -> new RuntimeException("No user found")));
        return getDocumetsFromEntity(documents);
    }

    private List<DocumentRS> getDocumetsFromEntity(List<DocumentEntity> documents) {
        List<DocumentRS> documentRSList = new ArrayList<>();
        for (DocumentEntity documentEntity: documents) {
            DocumentRS documentRS = new DocumentRS();
            documentRS.setName(documentEntity.getName());
            documentRS.setCreationDate(String.valueOf(documentEntity.getCreatedDate()));
            documentRSList.add(documentRS);
        }
        return documentRSList;
    }

    @Override
    public LinkedHashMap<String, String> getFrecuentWords(Integer totalWords, String userEmail, String documentName) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByEmail(userEmail);
        DocumentEntity documentEntity = documentRepository.getDocumentByUserAndName(userEntity.orElseThrow(() -> new RuntimeException("No user found")), documentName);
        return documentProcessorService.frecuentWords(documentEntity.getData(), totalWords);
    }

    @Override
    public UserRS getUsersNotUpload(String fromDate, String toDate) {
        List<String> emails = userRepository.findEmails(toDate, fromDate);
        UserRS userRS = new UserRS();
        userRS.setUserEmail(emails);
        userRS.setNumber(emails.size());
        return userRS;
    }
}
