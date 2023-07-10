package com.anna.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anna.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
