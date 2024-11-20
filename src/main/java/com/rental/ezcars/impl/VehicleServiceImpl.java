package com.rental.ezcars.impl;

import com.rental.ezcars.dto.MakeModelDTO;
import com.rental.ezcars.dto.RatingDTO;
import com.rental.ezcars.dto.VehicleDTO;
import com.rental.ezcars.dto.VehicleSearchCriteria;
import com.rental.ezcars.entity.Make;
import com.rental.ezcars.entity.Model;
import com.rental.ezcars.entity.Vehicle;
import com.rental.ezcars.exception.VehicleNotFoundException;
import com.rental.ezcars.repository.MakeRepository;
import com.rental.ezcars.repository.ModelRepository;
import com.rental.ezcars.repository.VehicleRepository;
import com.rental.ezcars.service.RatingService;
import com.rental.ezcars.service.VehicleService;
import com.rental.ezcars.specification.VehicleSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Autowired
    private MakeRepository makeRepository;

    @Autowired
    private ModelRepository modelRepository;
    
    @Autowired
    private RatingService ratingService;

    @Override
    public MakeModelDTO getMakesAndModels() {
        List<String> makes = makeRepository.findAll().stream()
                .map(Make::getName)
                .collect(Collectors.toList());

        List<String> models = modelRepository.findAll().stream()
                .map(Model::getName)
                .collect(Collectors.toList());

        return new MakeModelDTO(makes, models);
    }


    @Override
    public Page<VehicleDTO> getAllVehicles(Pageable pageable) {
        Page<Vehicle> vehiclesPage = vehicleRepository.findAll(pageable);
        
        return vehiclesPage.map(vehicle -> {
            VehicleDTO dto = convertToDTO(vehicle);
            List<RatingDTO> ratings = ratingService.getAllRatingsByVehicleId(vehicle.getVehicleId());
            List<Double> ratingValues = extractRatingValues(ratings);
            dto.setRating(calculateAverageRating(ratingValues));
            return dto;
        });
    }
     
    @Override
    public Page<VehicleDTO> searchVehicles(VehicleSearchCriteria criteria, Pageable pageable) {
        Page<Vehicle> vehiclesPage;
        
        if (criteria.getSearchText() != null && !criteria.getSearchText().isEmpty()) {
            vehiclesPage = vehicleRepository.fullTextSearch(criteria.getSearchText(), pageable);
        } else {
            vehiclesPage = vehicleRepository.findAll(VehicleSpecification.searchVehicles(criteria), pageable);
        }
        
        return vehiclesPage.map(vehicle -> {
            VehicleDTO dto = convertToDTO(vehicle);
            List<RatingDTO> ratings = ratingService.getAllRatingsByVehicleId(vehicle.getVehicleId());
            List<Double> ratingValues = extractRatingValues(ratings);
            dto.setRating(calculateAverageRating(ratingValues));
            return dto;
        });
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
    
    private VehicleDTO convertToDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setVehicleId(vehicle.getVehicleId());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setTransmission(vehicle.getTransmission());
        dto.setFuelType(vehicle.getFuelType());
        dto.setPrice(vehicle.getPrice());
        dto.setMileage(vehicle.getMileage());
        dto.setImageUrl(vehicle.getImageUrl());
        dto.setDetails(vehicle.getDetails());
        dto.setStatus(vehicle.getStatus());
    
        return dto;
    }
    
    private double calculateAverageRating(List<Double> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0; 
        }
        return ratings.stream()
                      .mapToDouble(Double::doubleValue)
                      .average()
                      .orElse(0.0);
    }
    
    private List<Double> extractRatingValues(List<RatingDTO> ratings) {
        return ratings.stream()
                      .map(rating -> rating.getRating().doubleValue())  
                      .collect(Collectors.toList());
    }
}