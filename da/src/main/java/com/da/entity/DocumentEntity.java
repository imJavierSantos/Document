package com.da.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "document", schema = "document_analyzer")
@Data
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    private String name;

    @Column(name = "word_count")
    private Integer wordCount;

    private byte[] data;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name="user_email")
    private UserEntity user;

}
