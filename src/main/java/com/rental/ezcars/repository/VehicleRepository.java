package com.rental.ezcars.repository;

import com.rental.ezcars.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {
    @Query(value = "SELECT v FROM Vehicle v WHERE " +
           "LOWER(v.make) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(v.model) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(v.transmission) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(v.fuelType) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
           "LOWER(v.details) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    Page<Vehicle> fullTextSearch(@Param("searchText") String searchText, Pageable pageable);
}