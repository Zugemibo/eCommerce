package com.dawidp.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dawidp.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	Order findOrderById(Long orderId);

	List<Order> findOrdersByUserId(Long userId);

}
