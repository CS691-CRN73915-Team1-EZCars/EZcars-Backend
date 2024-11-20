package com.rental.ezcars.service;

import java.util.List;
import java.util.Optional;

import com.rental.ezcars.entity.Payment;

public interface PaymentService {
    Payment savePayment(Payment payment);
    Optional<Payment> getPaymentById(Long paymentId);
    Optional<Payment> getPaymentByBookingId(Long bookingId);
    List<Payment> getAllPayments();
    Optional<Payment>  updatePayment(Long paymentId, Payment payment);
    void deletePayment(Long paymentId);
}
