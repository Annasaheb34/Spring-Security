package com.anna.controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.anna.model.Role;
import com.anna.model.User;
import com.anna.repos.UserRepo;
import com.anna.security.SecurityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/showReg")
	public String showRegistrationPage() {
		return "registerUser";
	}

	@PostMapping("/registerUser")
	public String register(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		HashSet<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setId(2L);
		roles.add(role);
		user.setRoles(roles);
		userRepo.save(user);
		return "login";
	}

	@GetMapping("/")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String login(String email, String password, HttpServletRequest request, HttpServletResponse response) {
		boolean loginResponse = securityService.login(email, password, request, response);
		if (loginResponse) {
			return "index";
		}
		return "login";
	}
}
