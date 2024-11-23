package com.rental.ezcars.service;

import com.rental.ezcars.entity.Booking;
import com.rental.ezcars.exception.EmailSendException;
import com.rental.ezcars.exception.UserException;

public interface EmailService {
	
	void sendEmail(String to, String subject, String body);
	
    void sendConfirmationEmail(Booking booking) throws EmailSendException, UserException;
    
    void sendCancellationEmail(Booking booking) throws EmailSendException;
    
    void sendReminderEmail(Booking booking) throws EmailSendException, UserException;

}
