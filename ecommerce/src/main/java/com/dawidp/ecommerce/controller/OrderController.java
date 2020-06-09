package com.dawidp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawidp.ecommerce.entity.Order;
import com.dawidp.ecommerce.entity.User;
import com.dawidp.ecommerce.service.OrderService;
import com.dawidp.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
		Order order = orderService.findOrderById(orderId);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		User currentUser = userService.findByUsername(username);

		if (currentUser.getId() != order.getUser().getId()) {
			throw new AccessDeniedException("Access Denied");
		}

		return new ResponseEntity<Order>(order, HttpStatus.OK);
	}

}
