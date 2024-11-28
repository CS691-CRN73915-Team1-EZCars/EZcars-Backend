package com.rental.ezcars.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rental.ezcars.entity.TokenEntity;

@Repository
public interface FeedbackTokens extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByToken(String token);
}
