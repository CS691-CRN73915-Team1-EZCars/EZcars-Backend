package com.rental.ezcars.impl;

import com.rental.ezcars.dto.RatingDTO;
import com.rental.ezcars.entity.Rating;
import com.rental.ezcars.entity.User;
import com.rental.ezcars.exception.UserException;
import com.rental.ezcars.repository.RatingRepository;
import com.rental.ezcars.service.RatingService;
import com.rental.ezcars.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    
    @Autowired
    private UserService userService;

    public Rating createRating(Rating rating) {
        try {
            if (rating == null) {
                throw new IllegalArgumentException("Rating cannot be null");
            }
            if (rating.getUserId() != null) {
                User user = userService.getUserById(rating.getUserId());
                if (user == null) {
                    throw new UserException("User not found with id: " + rating.getUserId(), UserException.UserExceptionType.USER_NOT_FOUND);
                }
            }
            return ratingRepository.save(rating);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating rating: " + e.getMessage(), e);
        }
    }



    @Override
    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public List<RatingDTO> getAllRatingsByVehicleId(Long vehicleId) {
        List<Rating> ratings = ratingRepository.findByVehicleId(vehicleId);
        List<RatingDTO> ratingDTOs = new ArrayList<>();

        for (Rating rating : ratings) {
            RatingDTO dto = convertToDTO(rating);
           
            if (rating.getUserId() != null) {
                User user = userService.getUserById(rating.getUserId()); 
                if (user != null) {
                    dto.setUserName(user.getUsername());
                }
            }
            
            ratingDTOs.add(dto);
        }

        return ratingDTOs;
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
    
    
    private RatingDTO convertToDTO(Rating rating) {
        RatingDTO dto = new RatingDTO();
        
        dto.setRatingId(rating.getRatingId());
        dto.setVehicleId(rating.getVehicleId());
        dto.setUserId(rating.getUserId());
        dto.setRating(rating.getRating());
        dto.setReview(rating.getReview());
        dto.setCreatedAt(rating.getCreatedAt()); 
        dto.setUpdatedAt(rating.getUpdatedAt()); 

        return dto;
    }
   
}