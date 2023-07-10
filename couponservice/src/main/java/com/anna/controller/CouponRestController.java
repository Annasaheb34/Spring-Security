package com.anna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anna.model.Coupon;
import com.anna.repos.CouponRepo;

@RestController
@RequestMapping("/couponapi")
@CrossOrigin
public class CouponRestController {

	@Autowired
	private CouponRepo repo;

	@PostMapping("/coupons")
	@PreAuthorize("hasRole('ADMIN')")
	public Coupon create(@RequestBody Coupon coupon) {
		return repo.save(coupon);
	}

	@GetMapping("/coupons/{code}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public Coupon getCoupon(@PathVariable String code) {
		return repo.findByCode(code);
	}

	@DeleteMapping("/deleteCoupon/{id}")
	public Coupon deleteCoupon(@PathVariable("id") Long id) {
		Coupon temp_coupon = repo.findById(id).get();
		repo.deleteById(id);
		return temp_coupon;
	}

}
