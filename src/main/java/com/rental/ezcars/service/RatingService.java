package com.rental.ezcars.service;

import com.rental.ezcars.dto.RatingDTO;
import com.rental.ezcars.entity.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    Rating createRating(Rating rating);

    Optional<Rating> getRatingById(Long id);

    List<RatingDTO> getAllRatingsByVehicleId(Long vehicleId);

    Rating updateRating(Long id, Rating ratingDetails);

    void deleteRating(Long id);
}