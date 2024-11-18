package com.rental.ezcars.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rental.ezcars.entity.Booking;
import com.rental.ezcars.entity.Payment;
import com.rental.ezcars.repository.PaymentRepository;
import com.rental.ezcars.service.PaymentService;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }
    
    @Override
    public Optional<Payment> getPaymentByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> updatePayment(Long paymentId, Payment updatedPayment) {
        return paymentRepository.findById(paymentId)
            .map(existingPayment -> {
                existingPayment.setStatus(updatedPayment.getStatus());
                existingPayment.setTimeStamp(updatedPayment.getTimeStamp());
   
                return paymentRepository.save(existingPayment);
            });
    }

    @Override
    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}
