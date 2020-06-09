package com.dawidp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawidp.ecommerce.entity.User;
import com.dawidp.ecommerce.service.UserService;

@RestController
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserService userService;

	@PostMapping("")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		userService.createUser(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

}
