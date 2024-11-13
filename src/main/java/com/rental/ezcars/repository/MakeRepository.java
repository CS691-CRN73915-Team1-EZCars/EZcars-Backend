package com.rental.ezcars.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.ezcars.entity.Make;

public interface MakeRepository extends JpaRepository<Make, Long> {
}