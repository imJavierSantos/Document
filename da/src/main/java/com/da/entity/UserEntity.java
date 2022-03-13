package com.da.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user", schema = "document_analyzer")
@Data
public class UserEntity {

    @Id
    private String email;

    @Column(name = "created_date")
    private Date createdDate;

}
