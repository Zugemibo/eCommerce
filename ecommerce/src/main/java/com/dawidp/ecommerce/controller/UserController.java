package com.dawidp.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.dawidp.ecommerce.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
		order.setStatus(Status.ACTIVE);
		orderService.save(order);
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
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
	@PutMapping("/{userId}/orders/{orderId}/{orderLineId}/dec")
	public OrderLine decreaseQuantityByOne(@PathVariable Long userId, @PathVariable Long orderId, @PathVariable Long orderLineId) {
		checkAccess(userId);
		OrderLine line = lineService.findLineById(orderLineId);
		line.setQuantity(line.getQuantity()-1);
		lineService.save(line);
		return line;
	}
    @PutMapping("/{userId}/orders/{orderId}/{orderLineId}/inc")
    public OrderLine increaseQuantityByOne(@PathVariable Long userId, @PathVariable Long orderId, @PathVariable Long orderLineId) {
        checkAccess(userId);
        OrderLine line = lineService.findLineById(orderLineId);
		line.setQuantity(line.getQuantity()+1);
		lineService.save(line);
        return line;
    }
	@DeleteMapping("/{userId}/orders/{orderId}/{orderLineId}")
	public Order deleteLine(@PathVariable Long userId, @PathVariable Long orderId, @PathVariable Long orderLineId) {
		checkAccess(userId);
		Order order = orderService.findOrderById(orderId);
		OrderLine line = lineService.findLineById(orderLineId);
		order.removeLine(line);
		orderService.save(order);
		return order;
	}

	@DeleteMapping("/{userId}/orders/{orderId}")
	public String orderCancel(@PathVariable Long userId, @PathVariable Long orderId) {
		checkAccess(userId);
		orderService.deleteOrder(orderId);
		return "Order with id: " + orderId + " has been canceled.";
	}
	@PostMapping("/{userId}/orders/{orderId}/checkout")
    public String orderCheckout(@PathVariable Long userId, @PathVariable Long orderId){
	    checkAccess(userId);
	    return orderService.checkout(orderId);
    }
	
}
