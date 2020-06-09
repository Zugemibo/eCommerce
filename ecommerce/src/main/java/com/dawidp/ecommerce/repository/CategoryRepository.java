package com.dawidp.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawidp.ecommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findCategoryById(Long id);

	Category findCategoryByName(String name);

}