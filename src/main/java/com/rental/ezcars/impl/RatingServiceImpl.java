package com.rental.ezcars.impl;

import com.rental.ezcars.entity.Rating;
import com.rental.ezcars.repository.RatingRepository;
import com.rental.ezcars.service.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public List<Rating> getAllRatingsByVehicleId(Long vehicleId) {
        return ratingRepository.findByVehicleId(vehicleId);
    }

    @Override
    public Rating updateRating(Long id, Rating ratingDetails) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            Rating existingRating = rating.get();
            existingRating.setRating(ratingDetails.getRating());
            existingRating.setReview(ratingDetails.getReview());
            return ratingRepository.save(existingRating);
        }
        return null;
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}