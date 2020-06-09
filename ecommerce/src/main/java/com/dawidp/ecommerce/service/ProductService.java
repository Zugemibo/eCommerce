package com.dawidp.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawidp.ecommerce.entity.Category;
import com.dawidp.ecommerce.entity.Product;
import com.dawidp.ecommerce.repository.CategoryRepository;
import com.dawidp.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Product> showAll() {
		return productRepository.findAll();
	}

	public Product saveOrUpdate(Product product) {
		if(product.getCategory() == null) {
			product.setCategory(categoryRepository.findCategoryByName("Uncategorized"));
		}
		return productRepository.save(product);
	}

	public Product findById(Long id) {
		return productRepository.findProductById(id);
	}

	public void delete(Long id) {
		productRepository.deleteById(id);
	}

}
