package com.poc.deliverysystem.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "COMPANY_USER")
public class CompanyUser implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "COMPANY_USER_ID", nullable = false, updatable = false)
    private String userId;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String userName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "COMPANY_ID", nullable = false)
    private String companyId;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

}
