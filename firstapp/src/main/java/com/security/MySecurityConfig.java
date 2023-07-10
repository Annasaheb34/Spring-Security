package com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class MySecurityConfig {
//	@Bean
//	UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//		UserDetails user = User.withUsername("Annasaheb")
//				.password(passwordEncoder().encode("Anna@123"))
//				.authorities("read").build();
//		userDetailsService.createUser(user);
//		return userDetailsService;
//	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.formLogin();
		http.httpBasic();
		http.authorizeHttpRequests().requestMatchers("/hello").authenticated();
//		.anyRequest().denyAll();
		http.addFilterBefore(new MySecurityFiter(), BasicAuthenticationFilter.class);
		return http.build();
	}

}
