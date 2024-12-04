package com.rental.ezcars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.ezcars.entity.Booking;
import com.rental.ezcars.entity.Booking.BookingStatus;
import com.rental.ezcars.service.BookingService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	  @Autowired
	    private BookingService bookingService;
	
	   @GetMapping("/status/{bookingId}")
	    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long bookingId) {
		   BookingStatus status = BookingStatus.COMPLETED;
		   
	        Booking updatedBooking = bookingService.updateBookingStatus(bookingId, status);
	        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
	    }

}
