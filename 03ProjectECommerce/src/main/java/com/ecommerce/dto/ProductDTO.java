package com.ecommerce.dto;

import java.util.List;

import com.ecommerce.entities.Order;

public class ProductDTO {

	private Long id;
	private String name;
	private String brand;
	private String type;
	private int quantityInStock;
	private boolean secondHand;
	private List<Order> orders;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getQuantityInStock() {
		return quantityInStock;
	}
	
	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
	
	public boolean isSecondHand() {
		return secondHand;
	}
	
	public void setSecondHand(boolean secondHand) {
		this.secondHand = secondHand;
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}