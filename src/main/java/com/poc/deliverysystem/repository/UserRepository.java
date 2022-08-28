package com.poc.deliverysystem.repository;

import com.poc.deliverysystem.model.entity.CompanyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<CompanyUser, String> {

    Optional<CompanyUser> findByUserNameAndPassword(String userName, String password);

}
