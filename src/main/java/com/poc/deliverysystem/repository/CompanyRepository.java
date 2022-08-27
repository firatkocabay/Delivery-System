package com.poc.deliverysystem.repository;

import com.poc.deliverysystem.model.entity.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, String> {

}
