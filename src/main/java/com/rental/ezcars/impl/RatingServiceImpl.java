package com.rental.ezcars.impl;

import com.rental.ezcars.dto.RatingDTO;
import com.rental.ezcars.entity.Booking;
import com.rental.ezcars.entity.Rating;
import com.rental.ezcars.entity.User;
import com.rental.ezcars.exception.UserException;
import com.rental.ezcars.repository.RatingRepository;
import com.rental.ezcars.service.BookingService;
import com.rental.ezcars.service.RatingService;
import com.rental.ezcars.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
	
    @Autowired
    @Lazy
    private BookingService bookingService;

    @Autowired
    private RatingRepository ratingRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RatingLinkService ratingLinkService;
    


    public Rating createRating(Rating rating, String token) {
        if (rating.getRating() == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }

        if (!ratingLinkService.validateAndInvalidateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        User user = userService.getUserById(rating.getUserId());
        if (user == null) {
            throw new UserException("User not found with id: " + rating.getUserId(), UserException.UserExceptionType.USER_NOT_FOUND);
        }
        
       Booking booking = bookingService.getBookingById(ratingLinkService.getBookingIdFromToken(token));
        
       rating.setVehicleId(booking.getVehicleId());
        return ratingRepository.save(rating);
    }



    @Override
    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public Page<RatingDTO> getAllRatingsByVehicleId(Long vehicleId, Pageable pageable) {
        Page<Rating> ratingsPage = ratingRepository.findByVehicleId(vehicleId, pageable);
        
        return ratingsPage.map(rating -> {
            RatingDTO dto = convertToDTO(rating);
            
            if (rating.getUserId() != null) {
                try {
                    User user = userService.getUserById(rating.getUserId());
                    if (user != null) {
                        dto.setUserName(user.getUsername());
                    } else {
                        dto.setUserName("Unknown User"); 
                    }
                } catch (Exception e) {
                    System.err.println("Error fetching user for rating ID " + rating.getRatingId() + ": " + e.getMessage());
                    dto.setUserName("Error fetching username"); 
                }
            } else {
                dto.setUserName("Anonymous"); 
            }
            
            return dto;
        });
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