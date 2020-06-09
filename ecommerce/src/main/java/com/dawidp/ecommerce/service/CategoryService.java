package com.dawidp.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawidp.ecommerce.entity.Category;
import com.dawidp.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public Category save(Category category) {
		return categoryRepository.save(category);
		
	}

	public Category findCategoryById(Long id) {
		return categoryRepository.findCategoryById(id);
	}

	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
		
	}

	public Category findCategoryByName(String name) {
		return categoryRepository.findCategoryByName(name);
	}

}
