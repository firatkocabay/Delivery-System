package com.poc.deliverysystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryRequestDto {

    private String orderId;
    private String requesterInformation;
    private String senderWarehouseAddress;
    private String deliveryAddress;

}
