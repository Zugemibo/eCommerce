package com.dawidp.ecommerce;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dawidp.ecommerce.entity.User;
import com.dawidp.ecommerce.repository.UserRepository;

@SpringBootApplication
public class EcommerceApplication {

	@Autowired
	private UserRepository repository;

	@PostConstruct
	public void initUsers() {
		List<User> users = Stream.of(
				new User(1L, "username", "$2y$04$Kjm1oxwsxIq.BqdeNglBdey6AAr8i5iTB6v1w.OFQJx1sI9mRPzOW", "dawid", "piatek", "polska", "krakow", "dawid@gmail.com", "user"))
				.collect(Collectors.toList());
		repository.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
