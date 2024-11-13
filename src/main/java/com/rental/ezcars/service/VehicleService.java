package com.rental.ezcars.service;

import com.rental.ezcars.dto.MakeModelDTO;
import com.rental.ezcars.dto.VehicleSearchCriteria;
import com.rental.ezcars.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehicleService {
   Page<Vehicle> getAllVehicles(Pageable pageable);
   
   Optional<Vehicle> getVehicleById(Long vehicleId);
   
   Vehicle saveVehicle(Vehicle vehicle);
   
   Vehicle updateVehicle(Long vehicleId, Vehicle vehicleDetails);
   
   void deleteVehicle(Long vehicleId);
   
   
   Page<Vehicle> searchVehicles(VehicleSearchCriteria criteria, Pageable pageable);
   
   MakeModelDTO getMakesAndModels();
}