package com.rental.ezcars.impl;

import com.rental.ezcars.entity.Booking;
import com.rental.ezcars.exception.ResourceNotFoundException;
import com.rental.ezcars.repository.BookingRepository;
import com.rental.ezcars.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Page<Booking> getAllBookings(Pageable pageable) {
        Page<Booking> bookings = bookingRepository.findAll(pageable);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found");
        }
        return bookings;
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long bookingId, Booking bookingDetails) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        // Update fields
        booking.setUserId(bookingDetails.getUserId());
        booking.setVehicleId(bookingDetails.getVehicleId());
        booking.setStartTime(bookingDetails.getStartTime());
        booking.setEndTime(bookingDetails.getEndTime());
        // Add any other fields that need to be updated

        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        bookingRepository.delete(booking);
    }

    @Override
    public List<Booking> getAllBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for user id: " + userId);
        }
        return bookings;
    }
}