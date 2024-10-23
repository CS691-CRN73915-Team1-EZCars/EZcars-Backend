package com.rental.ezcars.controller;

import com.rental.ezcars.dto.VehicleSearchCriteria;
import com.rental.ezcars.entity.Vehicle;
import com.rental.ezcars.exception.VehicleNotFoundException;
import com.rental.ezcars.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

   @Autowired
   private VehicleService vehicleService;

   @GetMapping
   public ResponseEntity<Page<Vehicle>> getAllVehicles(
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size) {
       Page<Vehicle> vehicles = vehicleService.getAllVehicles(PageRequest.of(page, size));
       return ResponseEntity.ok(vehicles);
   }
   
   @GetMapping("/search")
   public ResponseEntity<Page<Vehicle>> searchVehicles(
           @RequestParam(required = false) String searchText,
           @RequestParam(required = false) String make,
           @RequestParam(required = false) String model,

           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size) {
       
       VehicleSearchCriteria criteria = new VehicleSearchCriteria();
       criteria.setSearchText(searchText);
       criteria.setMake(make);
       criteria.setModel(model);

       Page<Vehicle> vehicles = vehicleService.searchVehicles(criteria, PageRequest.of(page, size));
       return ResponseEntity.ok(vehicles);
   }

   @GetMapping("/{vehicleId}")
   public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long vehicleId) {
       Vehicle vehicle = vehicleService.getVehicleById(vehicleId)
               .orElseThrow(() -> new VehicleNotFoundException(vehicleId));
       return ResponseEntity.ok(vehicle);
   }

   @PostMapping
   public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
       Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
       return ResponseEntity.ok(savedVehicle);
   }

   @PutMapping("/{vehicleId}")
   public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long vehicleId, 
                                                @RequestBody Vehicle vehicleDetails) {
       Vehicle updatedVehicle = vehicleService.updateVehicle(vehicleId, vehicleDetails);
       return ResponseEntity.ok(updatedVehicle);
   }

   @DeleteMapping("/{vehicleId}")
   public ResponseEntity<?> deleteVehicle(@PathVariable Long vehicleId) {
       vehicleService.deleteVehicle(vehicleId);
       return ResponseEntity.ok().build();
   }
}