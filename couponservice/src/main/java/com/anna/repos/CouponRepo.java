package com.anna.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anna.model.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Long> {
	
	Coupon findByCode(String code);
}
