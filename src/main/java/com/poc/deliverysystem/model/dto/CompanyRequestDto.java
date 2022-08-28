package com.poc.deliverysystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyRequestDto {

    private String companyName;
    private String companyAddress;
    private String companyWarehouseAddress;

}
