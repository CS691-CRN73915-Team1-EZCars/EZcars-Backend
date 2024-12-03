package com.rental.ezcars.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.rental.ezcars.entity.Booking;
import com.rental.ezcars.entity.Vehicle;
import com.rental.ezcars.exception.EmailSendException;
import com.rental.ezcars.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private VehicleServiceImpl vehicleService;
    
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private RatingLinkService ratingLinkService;

    @Value("$(EzCars)") 
    private String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail); 
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        
        try {
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Failed to send email to: " + to);
            e.printStackTrace();
        }
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
        
        this.sendEmail(userMailId, subject, body.toString());
    }
    
    @Override
    public void sendCancellationEmail(Booking booking) throws EmailSendException {
        Optional<Vehicle> bookedVehicle = vehicleService.getVehicleById(booking.getVehicleId());
        String subject = "Booking Cancellation Confirmation - EZCars #" + booking.getId();
        
        StringBuilder body = new StringBuilder();
        body.append("We're sorry to see you go. Your booking has been successfully cancelled.\n")
            .append("Booking ID: ").append(booking.getId()).append("\n")
            .append("Cancellation Date: ").append(LocalDate.now()).append("\n")
            .append("Original Pick Up Date: ").append(booking.getPickUpDate()).append("\n")
            .append("Pick Up Location: ").append(booking.getPickupLocation()).append("\n");

        if (bookedVehicle.isPresent()) {
            Vehicle vehicle = bookedVehicle.get();
            body.append("Cancelled Vehicle Details:\n")
                .append("  Make: ").append(vehicle.getMake()).append("\n")
                .append("  Model: ").append(vehicle.getModel()).append("\n")
                .append("  Year: ").append(vehicle.getYear()).append("\n");
        } 

        body.append("\nYour money will be refunded to your original payment method within 5-7 business days.\n\n");
        body.append("We hope to serve you again in the future. If you have any questions about this cancellation, please don't hesitate to contact our customer service.");

        String userMailId = userService.getUserById(booking.getUserId()).getEmail();
        
        this.sendEmail(userMailId, subject, body.toString());
    }
    
    @Override
    public void sendReminderEmail(Booking booking) throws EmailSendException {
        Optional<Vehicle> bookedVehicle = vehicleService.getVehicleById(booking.getVehicleId());
        String subject = "Reminder: Your EZCars Booking #" + booking.getId();
        
        StringBuilder body = new StringBuilder();
        body.append("This is a reminder for your upcoming booking with EZCars. Your booking details:\n")
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

        body.append("If you need to make any changes to your booking, please contact our customer service.\n\n")
            .append("We look forward to serving you soon. Thank you for choosing EZCars!");

        String userMailId = userService.getUserById(booking.getUserId()).getEmail();
        
        this.sendEmail(userMailId, subject, body.toString());
    }
    
    @Override
    public void sendRatingEmail(Booking booking) throws EmailSendException {
        String subject = "How was your EZCars experience? - Booking #" + booking.getId();
        
        StringBuilder body = new StringBuilder();
        body.append("Thank you for choosing EZCars for your recent rental. We hope you had a great experience!\n\n")
            .append("We value your feedback and would appreciate if you could take a moment to rate your experience.\n\n")
            .append("Please click on the link below to submit your review:\n")
            .append(ratingLinkService.generateFeedbackLink(booking.getId())).append("\n\n")
            .append("Your feedback helps us improve our services and provide better experiences for all our customers.\n\n")
            .append("Thank you for your time and we look forward to serving you again soon!");

        String userMailId = userService.getUserById(booking.getUserId()).getEmail();
        
        try {
            this.sendEmail(userMailId, subject, body.toString());
        } catch (Exception e) {
            throw new EmailSendException("Failed to send rating email for booking " + booking.getId(), e);
        }
    }}
