package com.rental.ezcars.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.ezcars.entity.Model;

public interface ModelRepository extends JpaRepository<Model, Long> {
}