package com.rental.ezcars.service;

import com.rental.ezcars.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    Page<Booking> getAllBookings(Pageable pageable);
    Booking getBookingById(Long bookingId);
    Booking createBooking(Booking booking);
    Booking updateBooking(Long bookingId, Booking bookingDetails);
    void deleteBooking(Long bookingId);
    List<Booking> getAllBookingsByUserId(Long userId);
}