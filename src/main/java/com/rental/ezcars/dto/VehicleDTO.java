package com.rental.ezcars.dto;

import com.rental.ezcars.entity.Vehicle.VehicleStatus;

public class VehicleDTO {

    private Long vehicleId;
    private String make;
    private String model;
    private int year;
    private String transmission;
    private String fuelType;
    private double price;
    private int mileage;
    private String imageUrl;
    private String details;
    private VehicleStatus status;
    private double rating; 

    // Default constructor
    public VehicleDTO() {}

    // Constructor with all fields
    public VehicleDTO(Long vehicleId, String make, String model, int year, String transmission,
                      String fuelType, double price, int mileage, String imageUrl,
                      String details, VehicleStatus status, double rating) {
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.price = price;
        this.mileage = mileage;
        this.imageUrl = imageUrl;
        this.details = details;
        this.status = status;
        this.rating = rating;
    }

    // Getters and setters for all fields
    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}