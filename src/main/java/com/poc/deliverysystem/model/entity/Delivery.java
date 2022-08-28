package com.poc.deliverysystem.model.entity;

import com.poc.deliverysystem.model.enums.DeliveryStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DELIVERY")
public class Delivery implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "DELIVERY_ID", nullable = false, updatable = false)
    private String deliveryId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "SENDER_COMPANY", joinColumns = @JoinColumn(name = "DELIVERY_INFORMATION_ID"), inverseJoinColumns = @JoinColumn(name = "COMPANY_ID"))
    private Company senderCompany;

    @Column(name = "ORDER_ID", nullable = false)
    private String orderId;

    @Column(name = "SENDER_WAREHOUSE_ADDRESS", nullable = false)
    private String senderWarehouseAddress;

    @Column(name = "DELIVERY_ADDRESS", nullable = false)
    private String deliveryAddress;

    @Column(name = "REQUESTER_INFORMATION", nullable = false)
    private String requesterInformation;

    @Column(name = "STATUS", nullable = false)
    private DeliveryStatus status;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

}
