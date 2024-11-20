package com.rental.ezcars.controller;

import com.rental.ezcars.dto.RatingDTO;
import com.rental.ezcars.entity.Rating;
import com.rental.ezcars.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating newRating = ratingService.createRating(rating);
        return new ResponseEntity<>(newRating, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long id) {
        return ratingService.getRatingById(id)
                .map(rating -> new ResponseEntity<>(rating, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<RatingDTO>> getAllRatingsByVehicleId(@PathVariable Long vehicleId) {
        List<RatingDTO> ratings = ratingService.getAllRatingsByVehicleId(vehicleId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long id, @RequestBody Rating ratingDetails) {
        Rating updatedRating = ratingService.updateRating(id, ratingDetails);
        if (updatedRating != null) {
            return new ResponseEntity<>(updatedRating, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}