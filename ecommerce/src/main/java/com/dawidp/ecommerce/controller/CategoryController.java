package com.dawidp.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawidp.ecommerce.entity.Category;
import com.dawidp.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("")
	public ResponseEntity<List<Category>> findAll() {
		List<Category> categories = categoryService.findAll();
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);

	}

	@GetMapping("/{name}")
	public ResponseEntity<Category> findByCategoryName(@PathVariable String name) {
		Category category = categoryService.findCategoryByName(name);
		return new ResponseEntity<Category>(category, HttpStatus.FOUND);
	}

	@PostMapping("")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		categoryService.save(category);
		return new ResponseEntity<Category>(category, HttpStatus.CREATED);
	}

	/*@GetMapping("/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category category = categoryService.findCategoryById(id);
		return new ResponseEntity<Category>(category, HttpStatus.FOUND);

	}
	*/

	@DeleteMapping("/{id}")
	public ResponseEntity deleteCategory(@PathVariable Long id) {
		categoryService.deleteById(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);

	}

}
