package com.anna.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anna.model.Role;
import com.anna.model.User;

public interface RoleRepo extends JpaRepository<Role, Long> {
	
}
