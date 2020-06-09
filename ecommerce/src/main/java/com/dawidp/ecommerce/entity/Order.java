package com.dawidp.ecommerce.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

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
	@NaturalId
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

}
