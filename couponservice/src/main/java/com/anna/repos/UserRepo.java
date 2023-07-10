package com.anna.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anna.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
