package com.rental.ezcars.impl;

import com.rental.ezcars.entity.Booking;

import com.rental.ezcars.exception.EmailSendException;
import com.rental.ezcars.exception.ResourceNotFoundException;
import com.rental.ezcars.repository.BookingRepository;
import com.rental.ezcars.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private EmailServiceImpl emailService;
    
    @Autowired
    private AsyncTaskExecutor taskExecutor;
    
    String userMailId;

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
        return bookingRepository.findById(bookingId)
                .map(booking -> {
                    if (bookingDetails.getVehicleId() != null) {
                        booking.setVehicleId(bookingDetails.getVehicleId());
                    }
                    if (bookingDetails.getPickUpDate() != null) {
                        booking.setPickUpDate(bookingDetails.getPickUpDate());
                    }
                    if (bookingDetails.getDuration() != null) {
                        booking.setDuration(bookingDetails.getDuration());
                    }
                    if (bookingDetails.getPickupLocation() != null) {
                        booking.setPickupLocation(bookingDetails.getPickupLocation());
                    }
                    if (bookingDetails.getDropoffLocation() != null) {
                        booking.setDropoffLocation(bookingDetails.getDropoffLocation());
                    }
                    if (bookingDetails.getStatus() != null) {
                        booking.setStatus(bookingDetails.getStatus());
                    }
                    return bookingRepository.save(booking);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        bookingRepository.delete(booking);
    }

    @Override
    public Page<Booking> getAllBookingsByUserId(Long userId, Booking.BookingStatus status, Integer year, Integer month, String sortDirection, int page, int size) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "pickUpDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        
        return bookingRepository.findAllByUserIdWithFilters(userId, status, year, month, pageable);
    }
     
    @Override
    public Booking updateBookingStatus(Long bookingId, Booking.BookingStatus status) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        
        LocalDate today = LocalDate.now(); 

        if (status.equals(Booking.BookingStatus.CANCELLED)) {
            // Check if the pickup date is more than one day away
            if (booking.getPickUpDate().isAfter(today.plusDays(1))) {
                bookingRepository.save(booking);
            } else {
                // Throw an IllegalStateException if cancellation is not allowed
                throw new IllegalStateException("You cannot cancel this booking at this moment. Cancellations are only allowed one day prior to the pickup date.");
            }
        } else if (status.equals(Booking.BookingStatus.CONFIRMED)) {
            bookingRepository.save(booking);
        } else {
            throw new IllegalArgumentException("Invalid booking status.");
        }

        // Execute email sending in a separate thread
        taskExecutor.execute(() -> {
            try {
                if (status.equals(Booking.BookingStatus.CONFIRMED)) {
                    emailService.sendConfirmationEmail(booking);
                } else if (status.equals(Booking.BookingStatus.CANCELLED)) {
                    emailService.sendCancellationEmail(booking);
                }
            } catch (EmailSendException e) {
                e.printStackTrace();
            }
        });

        return booking;
    }
    
    
   @Scheduled(cron = "0 0 10 * * ?") // Runs every day at 10:00 AM
    public void sendReminderEmailsForUpcomingBookings() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        System.out.println(tomorrow.toString());
        int pageSize = 500;
        int pageNumber = 0;
        Page<Booking> bookingPage;

        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            try {
                bookingPage = getAllBookings(pageable);
            } catch (ResourceNotFoundException e) {
                // No more bookings found, exit the loop
                break;
            }

            for (Booking booking : bookingPage.getContent()) {
                if (booking.getPickUpDate().isEqual(tomorrow)) {
                    try {
                    	emailService.sendReminderEmail(booking);
          
                    } catch (EmailSendException e) {
                    }
                }
            }

            pageNumber++;
        } while (bookingPage.hasNext());
    }
}