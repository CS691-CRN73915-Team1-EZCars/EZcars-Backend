package com.rental.ezcars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.ezcars.entity.CustomerSupport;

public interface CustomerSupportRepository extends JpaRepository<CustomerSupport, Long> {
    List<CustomerSupport> findByCustomerId(Long customerId);
}