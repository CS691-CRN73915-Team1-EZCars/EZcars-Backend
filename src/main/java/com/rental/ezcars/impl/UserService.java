package com.rental.ezcars.impl;


import com.rental.ezcars.entity.User;
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
	        return userRepository.findAll();
	    }

	    public User getUserById(Long id) {
	        return userRepository.findById(id).orElse(null);
	    }

	    public User createUser(User user) {
	        return userRepository.save(user);
	    }

	    public User updateUser(Long id, User userDetails) {
	        User user = userRepository.findById(id).orElse(null);
	        if (user != null) {
	            user.setUsername(userDetails.getUsername());
	            user.setEmail(userDetails.getEmail());
	            user.setPhoneNumber(userDetails.getPhoneNumber());
	            user.setRole(userDetails.getRole());
	            user.setSubscriptionStatus(userDetails.getSubscriptionStatus());
	            return userRepository.save(user);
	        }
	        return null;
	    }

	    public void deleteUser(Long id) {
	        userRepository.deleteById(id);
	    }
	}