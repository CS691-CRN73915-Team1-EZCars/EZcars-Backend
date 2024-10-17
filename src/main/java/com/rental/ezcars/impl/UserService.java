package com.rental.ezcars.impl;

import com.rental.ezcars.entity.User;
import com.rental.ezcars.exception.UserException;
import com.rental.ezcars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jitendra Rawat
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserException("No users found in the system", UserException.UserExceptionType.NO_USERS_FOUND);
        }
        return users;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id, UserException.UserExceptionType.USER_NOT_FOUND));
    }

    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserException("Error creating user: " + e.getMessage(), UserException.UserExceptionType.USER_CREATION_ERROR);
        }
    }

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

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id, UserException.UserExceptionType.USER_NOT_FOUND));

        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new UserException("Error deleting user with id " + id + ": " + e.getMessage(), UserException.UserExceptionType.USER_DELETION_ERROR);
        }
    }
}