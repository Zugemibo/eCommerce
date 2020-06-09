package com.dawidp.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawidp.ecommerce.entity.Order;
import com.dawidp.ecommerce.entity.OrderLine;
import com.dawidp.ecommerce.entity.User;
import com.dawidp.ecommerce.service.LineService;
import com.dawidp.ecommerce.service.OrderService;
import com.dawidp.ecommerce.service.ProductService;
import com.dawidp.ecommerce.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private LineService lineService;

	private User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		return userService.findByUsername(username);
	}

	public void checkAccess(Long userId) {
		User currentUser = getCurrentUser();

		if (currentUser.getId() != userId) {
			throw new AccessDeniedException("Access Denied");
		}
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
		checkAccess(userId);
		List<Order> orders = orderService.findOrdersByUserId(userId);
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}

	@PostMapping("/{userId}/orders")
	public ResponseEntity<Order> createOrder(@PathVariable Long userId) {
		checkAccess(userId);

		LocalDateTime time = LocalDateTime.now();

		Order order = new Order();
		order.setUser(userService.findByUserId(userId));
		order.setAdded(time);

		orderService.save(order);

		order.setOrderNumber(time.getYear() + "/" + time.getMonth() + "/" + time.getDayOfMonth() + "/" + order.getId());

		orderService.save(order);
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
		// Do poprawienia
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Long userId) {
		checkAccess(userId);
		User user = userService.findByUserId(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> getAllUsers() {

		List<User> users = userService.findAll();
		return new ResponseEntity<List<User>>(users, HttpStatus.FOUND);

	}

	@PostMapping("/{userId}/orders/{orderId}/")
	public ResponseEntity<Order> addToCart(@PathVariable Long userId, @PathVariable Long orderId,
			@RequestBody OrderLine line) {
		checkAccess(userId);
		Order order = orderService.findOrderById(orderId);
		line = lineService.save(line);
		order.addLine(line);
		orderService.save(order);
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}

	@DeleteMapping("/{userId}/orders/{orderId}")
	public String orderCancel(@PathVariable Long userId, @PathVariable Long orderId) {
		checkAccess(userId);
		orderService.deleteOrder(orderId);
		return "Order with id: " + orderId + " has been canceled.";
	}
	
}
