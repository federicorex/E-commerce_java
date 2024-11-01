package com.ecommerce.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JsonBackReference(value = "order-user")
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne
	@JsonBackReference(value = "order-product")
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@Column(name = "deliveryDate", nullable = false)
	private LocalDate deliveryDate;
	
	public Order() {
		
	}
	
	public Order(User user, Product product) {
		this.user = user;
		this.product = product;
		this.deliveryDate = LocalDate.now().plusDays(5);
	}
	
	public Order(User user, Product product, LocalDate deliveryDate) {
		this.user = user;
		this.product = product;
		this.deliveryDate = deliveryDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public void setDefaultDeliveryDate() {
		this.deliveryDate = LocalDate.now().plusDays(5);
	}
}
