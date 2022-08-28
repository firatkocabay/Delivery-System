package com.poc.deliverysystem.service.impl;

import com.poc.deliverysystem.exception.DateParseException;
import com.poc.deliverysystem.exception.SearchIllegalArgumentException;
import com.poc.deliverysystem.model.dto.DeliveryDetailResponseDto;
import com.poc.deliverysystem.model.dto.DeliveryRequestDto;
import com.poc.deliverysystem.model.dto.DeliveryResponseDto;
import com.poc.deliverysystem.model.entity.Delivery;
import com.poc.deliverysystem.model.enums.DeliveryStatus;
import com.poc.deliverysystem.repository.DeliveryRepository;
import com.poc.deliverysystem.service.CompanyService;
import com.poc.deliverysystem.service.DeliveryService;
import com.poc.deliverysystem.specification.GenericSpecification;
import com.poc.deliverysystem.specification.SearchCriteria;
import com.poc.deliverysystem.specification.SearchOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DeliveryRepository deliveryRepository;
    private final CompanyService companyService;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, CompanyService companyService) {
        this.deliveryRepository = deliveryRepository;
        this.companyService = companyService;
    }

    @Override
    public List<DeliveryDetailResponseDto> getDeliveriesByCriteria(Map<String, String> queryCriteria) {
        List<DeliveryDetailResponseDto> deliveryResponseDtoList = new ArrayList<>();
        GenericSpecification<Delivery> genericSpecification = new GenericSpecification<>();
        Map<String,Integer> pageAndLimit = new HashMap<>();
        try {
            queryCriteria.forEach((key, value) -> {
                switch (key) {
                    case "page":
                    case "limit":
                        pageAndLimit.put(key, Integer.valueOf(value));
                        break;
                    case "senderWarehouseAddress":
                    case "deliveryAddress":
                        genericSpecification.add(new SearchCriteria(key, value, SearchOperation.MATCH));
                        break;
                    case "from":
                        genericSpecification.add(new SearchCriteria("creationDate", getDateByValue(value), SearchOperation.GREATER_THAN_EQUAL));
                        break;
                    case "to":
                        genericSpecification.add(new SearchCriteria("creationDate", getDateByValue(value), SearchOperation.LESS_THAN_EQUAL));
                        break;
                    case "creationDate":
                    case "updateDate":
                        genericSpecification.add(new SearchCriteria(key, getDateByValue(value), SearchOperation.EQUAL));
                        break;
                    default:
                        genericSpecification.add(new SearchCriteria(key, value, SearchOperation.EQUAL));
                }
            });

            if (pageAndLimit.get("page") != null || pageAndLimit.get("limit") != null) {
                Page<Delivery> deliveryList;
                if (pageAndLimit.get("limit") == null || pageAndLimit.get("limit") < 1) {
                    PageRequest pageRequest = PageRequest.of(pageAndLimit.get("page"), 10);
                    deliveryList = deliveryRepository.findAll(genericSpecification, pageRequest);
                } else {
                    PageRequest pageRequest = PageRequest.of(0, pageAndLimit.get("limit"));
                    deliveryList = deliveryRepository.findAll(genericSpecification, pageRequest);
                }
                createResponseDto(deliveryResponseDtoList, deliveryList);
            } else {
                List<Delivery> deliveryList = deliveryRepository.findAll(genericSpecification);
                createResponseDto(deliveryResponseDtoList, deliveryList);
            }

        } catch (IllegalArgumentException | InvalidDataAccessApiUsageException e) {
            throw new SearchIllegalArgumentException("Exception message is: " + e.getMessage() +
                    " ### NOTE: Please search only these filter keys: deliveryId," +
                    " senderCompany, senderWarehouseAddress, deliveryAddress, requesterInformation," +
                    " status, creationDate, updateDate, from, to ###");
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Date Time Parse : " + e.getMessage());
        }

        return deliveryResponseDtoList;
    }

    private static void createResponseDto(List<DeliveryDetailResponseDto> deliveryResponseDtoList, Page<Delivery> deliveryList) {
        deliveryList.forEach(delivery -> {
            DeliveryDetailResponseDto responseDto = new DeliveryDetailResponseDto();
            responseDto.setDeliveryId(delivery.getDeliveryId());
            responseDto.setSenderCompanyId(delivery.getSenderCompanyId());
            responseDto.setOrderId(delivery.getOrderId());
            responseDto.setSenderWarehouseAddress(delivery.getSenderWarehouseAddress());
            responseDto.setDeliveryAddress(delivery.getDeliveryAddress());
            responseDto.setRequesterInformation(delivery.getRequesterInformation());
            responseDto.setStatus(delivery.getStatus());
            responseDto.setCreationDate(delivery.getCreationDate());
            responseDto.setUpdateDate(delivery.getUpdateDate());
            deliveryResponseDtoList.add(responseDto);
        });
    }

    private static void createResponseDto(List<DeliveryDetailResponseDto> deliveryResponseDtoList, List<Delivery> deliveryList) {
        deliveryList.forEach(delivery -> {
            DeliveryDetailResponseDto responseDto = new DeliveryDetailResponseDto();
            responseDto.setDeliveryId(delivery.getDeliveryId());
            responseDto.setSenderCompanyId(delivery.getSenderCompanyId());
            responseDto.setOrderId(delivery.getOrderId());
            responseDto.setSenderWarehouseAddress(delivery.getSenderWarehouseAddress());
            responseDto.setDeliveryAddress(delivery.getDeliveryAddress());
            responseDto.setRequesterInformation(delivery.getRequesterInformation());
            responseDto.setStatus(delivery.getStatus());
            responseDto.setCreationDate(delivery.getCreationDate());
            responseDto.setUpdateDate(delivery.getUpdateDate());
            deliveryResponseDtoList.add(responseDto);
        });
    }

    @Override
    public DeliveryResponseDto createNewDelivery(DeliveryRequestDto deliveryRequestDto) {
        DeliveryResponseDto responseDto = new DeliveryResponseDto();
        companyService.findCompany(deliveryRequestDto.getCompanyId());
        Delivery delivery = createDeliveryEntity(deliveryRequestDto);
        responseDto.setDeliveryId(delivery.getDeliveryId());
        return responseDto;
    }

    private Date getDateByValue(String value) {
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            throw new DateParseException("Date parse exception: " + value);
        }
    }

    private Delivery createDeliveryEntity(DeliveryRequestDto deliveryRequestDto) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryAddress(deliveryRequestDto.getDeliveryAddress());
        delivery.setSenderWarehouseAddress(deliveryRequestDto.getSenderWarehouseAddress());
        delivery.setSenderCompanyId(deliveryRequestDto.getCompanyId());
        delivery.setOrderId(deliveryRequestDto.getOrderId());
        delivery.setStatus(DeliveryStatus.NEW);
        delivery.setRequesterInformation(deliveryRequestDto.getRequesterInformation());
        delivery.setCreationDate(new Date());
        delivery.setUpdateDate(new Date());
        return deliveryRepository.save(delivery);
    }

}
