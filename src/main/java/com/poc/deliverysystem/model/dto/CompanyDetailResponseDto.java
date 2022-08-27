package com.poc.deliverysystem.model.dto;

import lombok.Data;

@Data
public class CompanyDetailResponseDto {

    private String companyId;
    private String companyName;
    private String companyAddress;
    private String companyWarehouseAddress;

}
