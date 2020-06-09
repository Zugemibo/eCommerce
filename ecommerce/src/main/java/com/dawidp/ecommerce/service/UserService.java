package com.dawidp.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dawidp.ecommerce.entity.User;
import com.dawidp.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (user.getRole() == null) {
			user.setRole("user");
		}
		return userRepository.save(user);

	}

	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}

	public User findByUserId(Long userId) {
		User user = userRepository.findUserById(userId);
		return user;
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

}
