package com.rental.ezcars.exception;

public class VehicleNotFoundException extends RuntimeException {
   public VehicleNotFoundException(Long vehicleId) {
       super("Vehicle not found with id: " + vehicleId);
   }
}