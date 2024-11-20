package com.rental.ezcars.dto;

import com.rental.ezcars.entity.Booking;

public class BookingStatusRequest {
    private Booking.BookingStatus status;

    // Getter and setter
    public Booking.BookingStatus getStatus() {
        return status;
    }

    public void setStatus(Booking.BookingStatus status) {
        this.status = status;
    }
}
