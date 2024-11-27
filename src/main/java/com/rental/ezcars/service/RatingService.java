package com.rental.ezcars.service;

import com.rental.ezcars.dto.RatingDTO;
import com.rental.ezcars.entity.Rating;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatingService {

    Rating createRating(Rating rating);

    Optional<Rating> getRatingById(Long id);

    Page<RatingDTO> getAllRatingsByVehicleId(Long vehicleId, Pageable pageable);

    Rating updateRating(Long id, Rating ratingDetails);

    void deleteRating(Long id);
}