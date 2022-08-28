package com.poc.deliverysystem.model.dto;

import lombok.Data;

@Data
public class DeliveryRequestDto {

    private String orderId;
    private String requesterInformation;
    private String senderWarehouseAddress;
    private String deliveryAddress;

}
