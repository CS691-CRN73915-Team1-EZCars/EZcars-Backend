package com.rental.ezcars.service;

import com.rental.ezcars.entity.Booking;
import com.rental.ezcars.entity.Booking.BookingStatus;
import com.rental.ezcars.exception.EmailSendException;
import com.rental.ezcars.exception.UserException;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    Page<Booking> getAllBookings(Pageable pageable);
    Booking getBookingById(Long bookingId);
    Booking createBooking(Booking booking);
    Booking updateBooking(Long bookingId, Booking bookingDetails);
    void deleteBooking(Long bookingId);
  
    Page<Booking> getAllBookingsByUserId(Long userId, Booking.BookingStatus status, Integer year, Integer month, String sortDirection,int page, int size);
    
    List<Long> getBookingIdsByUserId(Long userId);
    Booking updateBookingStatus(Long bookingId, Booking.BookingStatus status);
}