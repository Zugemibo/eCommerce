package com.dawidp.ecommerce.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import com.dawidp.ecommerce.util.DataView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(DataView.ProductAddView.class)
	private Long id;
	@JsonView(DataView.ProductAddView.class)
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@NotNull
	@JsonView(DataView.ProductAddView.class)
	private String name;
	@NotNull
	@JsonView(DataView.ProductAddView.class)
	private String description;
	@JsonView(DataView.ProductAddView.class)
	private double price;
	@CreationTimestamp
	@JsonView(DataView.ProductAddView.class)
	private LocalDateTime added;
	

}
