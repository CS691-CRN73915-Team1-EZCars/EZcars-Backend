package com.rental.ezcars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.rental.ezcars.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 Optional<User> findByUsername(String username);
	    Optional<User> findByEmail(String email);
    
}
