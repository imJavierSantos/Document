package com.da.repository;

import com.da.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findUserEntityByEmail(String email);

    @Query(value = "select email from document_analyzer.user u where created_date <= cast(:toDate AS date) and email not in (select user_email from document_analyzer.document d where created_date <= cast(:fromDate AS date) and created_date >= cast(:toDate AS date))", nativeQuery = true)
    List<String> findEmails(@Param("fromDate")String fromDate, @Param("toDate")String toDate);

}
