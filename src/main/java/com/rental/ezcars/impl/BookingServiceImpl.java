package com.rental.ezcars.impl;

import com.rental.ezcars.entity.Booking;
import com.rental.ezcars.entity.Vehicle;
import com.rental.ezcars.entity.Booking.BookingStatus;
import com.rental.ezcars.exception.EmailSendException;
import com.rental.ezcars.exception.ResourceNotFoundException;
import com.rental.ezcars.repository.BookingRepository;
import com.rental.ezcars.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private VehicleServiceImpl vehicleService;
    
    @Autowired
    private EmailServiceImpl emailService;
    
    @Autowired
    private UserServiceImpl userService;
    
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
    	Booking savedBooking = bookingRepository.save(booking);
    	   taskExecutor.execute(() -> {
    	        try {
    	            sendConfirmationEmail(savedBooking);
    	            try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
    	            updateBookingStatus(savedBooking.getId(), BookingStatus.CONFIRMED);
    	        } catch (EmailSendException e) {
			e.printStackTrace();
		}
    	   });

    	    return savedBooking;
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
    public List<Booking> getAllBookingsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for user id: " + userId);
        }
        return bookings;
    }
    
    @Override
    public void sendConfirmationEmail(Booking booking) throws EmailSendException {
        Optional<Vehicle> bookedVehicle = vehicleService.getVehicleById(booking.getVehicleId());
        String subject = "Booking Confirmation - EZCars #" + booking.getId();
        
        StringBuilder body = new StringBuilder();
        body.append("Thank you for your booking with EZCars. Your booking details:\n")
            .append("Booking ID: ").append(booking.getId()).append("\n")
            .append("Start Date: ").append(booking.getPickUpDate()).append("\n")
            .append("Pick Up Location: ").append(booking.getPickupLocation()).append("\n");

        double totalPrice = 0.0;
        if (bookedVehicle.isPresent()) {
            Vehicle vehicle = bookedVehicle.get();
            body.append("Vehicle Details:\n")
                .append("  Make: ").append(vehicle.getMake()).append("\n")
                .append("  Model: ").append(vehicle.getModel()).append("\n")
                .append("  Year: ").append(vehicle.getYear()).append("\n\n");
            
            // Calculate total price
            totalPrice = vehicle.getPrice() * booking.getDuration();
            body.append("Booking Duration: ").append(booking.getDuration()).append(" days\n")
                .append("Total Rental Amount: $").append(String.format("%.2f", totalPrice)).append("\n\n");
        } 

        body.append("\nThank you for choosing EZCars! We appreciate your business and look forward to serving you again.");

        String userMailId = userService.getUserById(booking.getUserId()).getEmail();
        
        emailService.sendEmail(userMailId, subject, body.toString());
    }
    
    @Override
    public void sendReminderEmail(Booking booking) throws EmailSendException {
        String subject = "Reminder: Your EZCars Booking #" + booking.getId();
        String body = "This is a reminder for your upcoming booking with EZCars:\n" +
                "Booking ID: " + booking.getId() + "\n" +
                "Start Date: " + booking.getPickUpDate() + "\n" +
                "Pick Up Location: " + booking.getPickupLocation();

        emailService.sendEmail(userMailId, subject, body);
    }
    
    
    public void updateBookingStatus(Long bookingId, BookingStatus status) {
        bookingRepository.findById(bookingId).ifPresent(booking -> {
            booking.setStatus(status);
            bookingRepository.save(booking);
        });
    }
}