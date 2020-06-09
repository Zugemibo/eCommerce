package com.dawidp.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.dawidp.ecommerce.entity.Product;
import com.dawidp.ecommerce.service.ProductService;
import com.dawidp.ecommerce.util.DataView;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@JsonView(DataView.ProductAddView.class)
	@GetMapping("")
	public ResponseEntity<List<Product>> showAll(){
		List<Product> products = productService.showAll();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	@JsonView(DataView.ProductAddView.class)
	@PostMapping("")
	public ResponseEntity<Product> add(@RequestBody @Valid Product product){
		productService.saveOrUpdate(product);
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id){
		Product product = productService.findById(id);
		return new ResponseEntity<Product>(product, HttpStatus.FOUND);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity deleteById(@PathVariable Long id) {
		productService.delete(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
