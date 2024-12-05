package com.rental.ezcars.dto;

public class UserStatsDTO {
    private Long userId;
    private String userName;
    private int totalRides;
    private double totalAmountSpent;
    private int completedRides;
    private int cancelledRides;
    private double averageRating;

    // Constructors
    public UserStatsDTO() {}

    public UserStatsDTO(Long userId, String userName, int totalRides, double totalAmountSpent, 
                        int completedRides, int cancelledRides, double averageRating) {
        this.userId = userId;
        this.userName = userName;
        this.totalRides = totalRides;
        this.totalAmountSpent = totalAmountSpent;
        this.completedRides = completedRides;
        this.cancelledRides = cancelledRides;
        this.averageRating = averageRating;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotalRides() {
        return totalRides;
    }

    public void setTotalRides(int totalRides) {
        this.totalRides = totalRides;
    }

    public double getTotalAmountSpent() {
        return totalAmountSpent;
    }

    public void setTotalAmountSpent(double totalAmountSpent) {
        this.totalAmountSpent = totalAmountSpent;
    }

    public int getCompletedRides() {
        return completedRides;
    }

    public void setCompletedRides(int completedRides) {
        this.completedRides = completedRides;
    }

    public int getCancelledRides() {
        return cancelledRides;
    }

    public void setCancelledRides(int cancelledRides) {
        this.cancelledRides = cancelledRides;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}