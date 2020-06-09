package com.dawidp.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawidp.ecommerce.entity.Order;
import com.dawidp.ecommerce.entity.OrderLine;
import com.dawidp.ecommerce.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	public Order findOrderById(Long orderId) {
		return orderRepository.findOrderById(orderId);

	}

	public List<Order> findOrdersByUserId(Long userId) {
		return orderRepository.findOrdersByUserId(userId);
	}

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	public void deleteOrder(Long orderId) {
		orderRepository.deleteById(orderId);
		
	}

}
