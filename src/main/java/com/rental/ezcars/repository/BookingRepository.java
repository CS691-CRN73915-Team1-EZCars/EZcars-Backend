package com.rental.ezcars.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rental.ezcars.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    @Query("SELECT b FROM Booking b WHERE b.userId = :userId " +
           "AND (:status IS NULL OR b.status = :status) " +
           "AND (:year IS NULL OR FUNCTION('YEAR', b.pickUpDate) = :year) " +
           "AND (:month IS NULL OR FUNCTION('MONTH', b.pickUpDate) = :month)")
    Page<Booking> findAllByUserIdWithFilters(
        @Param("userId") Long userId,
        @Param("status") Booking.BookingStatus status,  
        @Param("year") Integer year,
        @Param("month") Integer month,
        Pageable pageable
    );
}