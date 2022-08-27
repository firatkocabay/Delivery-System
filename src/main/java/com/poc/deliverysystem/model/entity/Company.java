package com.poc.deliverysystem.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "COMPANY")
public class Company implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "COMPANY_ID", nullable = false, updatable = false)
    private String companyId;

    @Column(name = "COMPANY_NAME", nullable = false)
    private String companyName;

    @Column(name = "COMPANY_ADDRESS")
    private String companyAddress;

    @Column(name = "COMPANY_WAREHOUSE_ADDRESS", nullable = false)
    private String companyWarehouseAddresses;

}
