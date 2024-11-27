package com.rental.ezcars.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.ezcars.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByBookingId(Long bookingId);
	Page<Payment> findByBookingIdIn(List<Long> bookingIds, Pageable pageable);
	
}