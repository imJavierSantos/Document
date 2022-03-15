package com.da.repository;

import com.da.entity.DocumentEntity;
import com.da.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, String> {

    List<DocumentEntity> getAllDocumentsByUser(UserEntity user);

    DocumentEntity getDocumentByUserAndName(UserEntity user, String documentDate);

}
