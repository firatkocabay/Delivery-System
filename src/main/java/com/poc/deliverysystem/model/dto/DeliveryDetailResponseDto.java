package com.poc.deliverysystem.model.dto;

import com.poc.deliverysystem.model.enums.DeliveryStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DeliveryDetailResponseDto {

    private String deliveryId;
    private String senderCompanyId;
    private String orderId;
    private String senderWarehouseAddress;
    private String deliveryAddress;
    private String requesterInformation;
    private DeliveryStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

}
