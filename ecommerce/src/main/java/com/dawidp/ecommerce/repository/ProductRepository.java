package com.dawidp.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawidp.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findProductById(Long id);

}
