package com.poc.deliverysystem.model.dto;

import com.poc.deliverysystem.model.enums.DeliveryStatus;
import lombok.Data;

import java.util.Date;

@Data
public class DeliveryDetailResponseDto {

    private String deliveryId;
    private String senderCompanyId;
    private String senderWarehouseAddress;
    private String deliveryAddress;
    private String requesterInformation;
    private DeliveryStatus status;
    private Date creationDate;
    private Date updateDate;

}
