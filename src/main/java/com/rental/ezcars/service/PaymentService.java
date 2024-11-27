package com.rental.ezcars.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.ezcars.entity.Payment;

public interface PaymentService {
    Payment savePayment(Payment payment);
    Optional<Payment> getPaymentById(Long paymentId);
    Optional<Payment> getPaymentByBookingId(Long bookingId);
    Page<Payment> getPaymentsByUserId(Long userId, Pageable pageable);
    Optional<Payment>  updatePayment(Long paymentId, Payment payment);
    void deletePayment(Long paymentId);
}
