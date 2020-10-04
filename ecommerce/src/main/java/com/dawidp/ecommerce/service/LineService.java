package com.dawidp.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawidp.ecommerce.entity.OrderLine;
import com.dawidp.ecommerce.repository.LineRepository;
import com.dawidp.ecommerce.repository.ProductRepository;

@Service
public class LineService {
	
	@Autowired
	private LineRepository lineRepository;
	
	@Autowired
	private ProductRepository productRepository;

	public OrderLine save(OrderLine line) {
		line.setProduct(productRepository.findProductById(line.getProduct().getId()));
		if(line.getQuantity() > 0){
			return lineRepository.save(line);

		}else{
			lineRepository.delete(line);
			return null;
		}
	}

	public List<OrderLine> findAll() {
		return lineRepository.findAll();
	}

    public OrderLine findLineById(Long orderLineId) {
		OrderLine line = lineRepository.findLineById(orderLineId);
		return line;
    }

	public void deleteLine(Long orderLineId) {

	}
}
