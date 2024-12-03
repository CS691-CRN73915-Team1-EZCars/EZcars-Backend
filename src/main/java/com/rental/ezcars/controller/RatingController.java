package com.rental.ezcars.controller;

import com.rental.ezcars.dto.RatingDTO;
import com.rental.ezcars.entity.Rating;
import com.rental.ezcars.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating, @RequestParam String token) {
        Rating newRating = ratingService.createRating(rating, token);
        return new ResponseEntity<>(newRating, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long id) {
        return ratingService.getRatingById(id)
                .map(rating -> new ResponseEntity<>(rating, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<Page<RatingDTO>> getAllRatingsByVehicleId(
        @PathVariable Long vehicleId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "ratingId") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDirection
    ) {
  
        if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
            return ResponseEntity.badRequest().body(null);
        }

        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        try {
            Page<RatingDTO> ratings = ratingService.getAllRatingsByVehicleId(vehicleId, pageable);
            return ResponseEntity.ok(ratings); 
        } catch (Exception e) {
            System.err.println("Error fetching ratings: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return 500 Internal Server Error
        }
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