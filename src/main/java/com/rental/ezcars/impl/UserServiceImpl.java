package com.rental.ezcars.impl;

import com.rental.ezcars.entity.User;
import com.rental.ezcars.exception.UserException;
import com.rental.ezcars.repository.UserRepository;
import com.rental.ezcars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jitendra Rawat
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserException("No users found in the system", UserException.UserExceptionType.NO_USERS_FOUND);
        }       
        return users.stream()
                .map(this::sanitizeUser)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id,
                		UserException.UserExceptionType.USER_NOT_FOUND));
        user.setPasswordHash("XXXXXXXX");
        
        return user;
    }

    @Override
    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserException("Error creating user: " + e.getMessage(), UserException.UserExceptionType.USER_CREATION_ERROR);
        }
    }
    
    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id, UserException.UserExceptionType.USER_NOT_FOUND));

        try {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPhoneNumber(userDetails.getPhoneNumber());
            user.setRole(userDetails.getRole());
            user.setSubscriptionStatus(userDetails.getSubscriptionStatus());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserException("Error updating user with id " + id + ": " + e.getMessage(), UserException.UserExceptionType.USER_UPDATE_ERROR);
        }
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id, UserException.UserExceptionType.USER_NOT_FOUND));

        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new UserException("Error deleting user with id " + id + ": " + e.getMessage(), UserException.UserExceptionType.USER_DELETION_ERROR);
        }
    }
    
    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("User not found with username: " + username, UserException.UserExceptionType.USER_NOT_FOUND));

        if (password.equals(user.getPasswordHash())) {
            return sanitizeUser(user);
        } else {
            throw new UserException("Invalid password", UserException.UserExceptionType.INVALID_CREDENTIALS);
        }
    }
     
    private User sanitizeUser(User user) {
        User sanitizedUser = new User();
        sanitizedUser.setUserId(user.getUserId());
        sanitizedUser.setUsername(user.getUsername());
        sanitizedUser.setEmail(user.getEmail());
        sanitizedUser.setPhoneNumber(user.getPhoneNumber());
        sanitizedUser.setRole(user.getRole());
        sanitizedUser.setSubscriptionStatus(user.getSubscriptionStatus());
        sanitizedUser.setCreatedAt(user.getCreatedAt());
        sanitizedUser.setUpdatedAt(user.getUpdatedAt());
        sanitizedUser.setPasswordHash("XXXXXXXX");
        return sanitizedUser;
    }
}