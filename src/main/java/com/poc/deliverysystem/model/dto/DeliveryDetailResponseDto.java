package com.poc.deliverysystem.model.dto;

import com.poc.deliverysystem.model.enums.DeliveryStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryDetailResponseDto {

    private String deliveryId;
    private String senderCompanyName;
    private String orderId;
    private String senderWarehouseAddress;
    private String deliveryAddress;
    private String requesterInformation;
    private DeliveryStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

}
