package com.rental.ezcars.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.ezcars.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
