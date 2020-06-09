package com.dawidp.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawidp.ecommerce.entity.OrderLine;
import com.dawidp.ecommerce.service.LineService;
import com.dawidp.ecommerce.util.DataView;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api/orderline")
public class LineController {
	
	@Autowired
	private LineService lineService;
	
	@JsonView(DataView.ProductAddView.class)
	@PostMapping("")
	public ResponseEntity<OrderLine> addToCart(@RequestBody OrderLine line){
		lineService.save(line);
		return new ResponseEntity<OrderLine>(line, HttpStatus.CREATED);
	}
	@GetMapping("/lines")
	public ResponseEntity<List<OrderLine>> getAll(){
		List<OrderLine> orderLines = lineService.findAll();
		return new ResponseEntity<List<OrderLine>>(orderLines, HttpStatus.FOUND);
	}

}
