package com.dawidp.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne
	@JsonBackReference
	private Order order;
	@Min(0)
	private int quantity;

	@Transient
	public double calculateLine(){
		return product.getPrice()*quantity;
	}
}
