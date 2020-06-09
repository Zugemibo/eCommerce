package com.dawidp.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawidp.ecommerce.entity.AuthRequest;
import com.dawidp.ecommerce.util.JwtUtil;

@RestController
@RequestMapping("")
public class WelcomeController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	AuthenticationManager authenticationManager;

	@GetMapping("/")
	public String welcome() {
		return "Welcome user";
	}

	@PostMapping("/authenticate")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception ex) {
			throw new Exception("Invalid username or password.");
		}
		return jwtUtil.generateToken(authRequest.getUsername());
	}

}
