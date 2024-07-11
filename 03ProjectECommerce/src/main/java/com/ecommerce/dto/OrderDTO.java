package com.ecommerce.dto;

import java.time.LocalDate;

import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;

public class OrderDTO {

	private Long id;
	private User user;
	private Product product;
	private LocalDate deliveryDate;
	
	public OrderDTO() {
		
	}
	
	public OrderDTO(User user, Product product) {
		this.user = user;
		this.product = product;
		this.deliveryDate = LocalDate.now().plusDays(5);
	}

	public OrderDTO(User user, Product product, LocalDate deliveryDate) {
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
