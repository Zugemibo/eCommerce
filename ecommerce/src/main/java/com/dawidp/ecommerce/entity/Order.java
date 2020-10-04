package com.dawidp.ecommerce.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "order_number")
	private String orderNumber;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@NotNull
	private User user;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	@OrderBy("id")
	private List<OrderLine> lines;
	@CreationTimestamp
	private LocalDateTime added;
	private Status status;
	@Min(0)
	private double sum;
	
	public Order addLine(OrderLine line) {
		lines.add(line);
		line.setOrder(this);
		return this;
	}
	
	public Order removeLine(OrderLine line) {
		lines.remove(line);
		line.setOrder(null);
		return this;
	}
	
	public Order removeLines() {
		lines.clear();
		return this;
	}

	@Transient
	public double calculateOrder(){
		for(OrderLine line:lines){
			sum=sum+line.calculateLine();
		}
		return sum;
	}

}
