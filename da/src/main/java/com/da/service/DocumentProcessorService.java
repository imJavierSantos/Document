package com.da.service;

import com.da.dto.rs.DocumentRS;
import com.da.dto.rs.FrecuencyRS;
import com.da.dto.rs.UserRS;
import com.da.entity.DocumentEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;

public interface DocumentProcessorService {

    Integer wordCount(byte[] documentData);

    LinkedHashMap<String, String> frecuentWords(byte[] documentData, Integer totalWords);

}
