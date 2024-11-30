package com.rental.ezcars.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rental.ezcars.entity.TokenEntity;
import com.rental.ezcars.repository.FeedbackTokens;
import com.rental.ezcars.service.BookingService;

import java.util.UUID;

@Service
public class RatingLinkService {

    @Autowired
    private FeedbackTokens tokenRepository;
    
    @Value("${cors.allowed-origins}")
    private String feedbackBaseUrl;

    public String generateFeedbackLink(Long bookingId) {
        String token = UUID.randomUUID().toString();
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setBookingId(bookingId);
        tokenEntity.setUsed(false);
        tokenRepository.save(tokenEntity);
        return feedbackBaseUrl + "/AddRating/?token=" + token + "&BookingId=" + bookingId;
    }

        public boolean validateAndInvalidateToken(String token) {
            Optional<TokenEntity> tokenEntity = tokenRepository.findByToken(token);
            System.out.print(tokenEntity);
            
            if (tokenEntity.isPresent()) {
                TokenEntity entity = tokenEntity.get();
                if (!entity.isUsed()) {
                    entity.setUsed(true);
                    tokenRepository.save(entity);
                    return true; 
                }
            }           
            return false; 
        }

    
    public Long getBookingIdFromToken(String token) {
        Optional<TokenEntity> tokenEntity = tokenRepository.findByToken(token);
        if (tokenEntity.isPresent()) {
            return tokenEntity.get().getBookingId();
        }
        return null;
    }
}