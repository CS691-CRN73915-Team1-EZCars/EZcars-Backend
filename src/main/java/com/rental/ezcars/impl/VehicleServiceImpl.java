package com.rental.ezcars.impl;

import com.rental.ezcars.dto.VehicleSearchCriteria;
import com.rental.ezcars.entity.Vehicle;
import com.rental.ezcars.exception.VehicleNotFoundException;
import com.rental.ezcars.repository.VehicleRepository;
import com.rental.ezcars.service.VehicleService;
import com.rental.ezcars.specification.VehicleSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Page<Vehicle> getAllVehicles(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }
    
    @Override
        public Page<Vehicle> searchVehicles(VehicleSearchCriteria criteria, Pageable pageable) {
            if (criteria.getSearchText() != null && !criteria.getSearchText().isEmpty()) {
                return vehicleRepository.fullTextSearch(criteria.getSearchText(), pageable);
            } else {
                return vehicleRepository.findAll(VehicleSpecification.searchVehicles(criteria), pageable);
            }
        }


    @Override
    public Optional<Vehicle> getVehicleById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(Long vehicleId, Vehicle vehicleDetails) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        // Update fields
        vehicle.setMake(vehicleDetails.getMake());
        vehicle.setModel(vehicleDetails.getModel());
        vehicle.setYear(vehicleDetails.getYear());
        vehicle.setTransmission(vehicleDetails.getTransmission());
        vehicle.setFuelType(vehicleDetails.getFuelType());
        vehicle.setPrice(vehicleDetails.getPrice());
        vehicle.setMileage(vehicleDetails.getMileage());
        vehicle.setImageUrl(vehicleDetails.getImageUrl());
        vehicle.setDetails(vehicleDetails.getDetails());
        vehicle.setStatus(vehicleDetails.getStatus());

        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));
        vehicleRepository.delete(vehicle);
    }
}