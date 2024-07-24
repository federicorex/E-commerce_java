package com.ecommerce.dto;

import java.util.List;

import com.ecommerce.entities.Order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductDTO {

	private Long id;
	@NotEmpty(message = "The name must not be empty")
	private String name;
	@NotEmpty(message = "The brand must not be empty")
	private String brand;
	@NotEmpty(message = "The type must not be empty")
	private String type;
	@NotNull
	@PositiveOrZero
	private Integer quantityInStock;
	@NotNull
	private Boolean secondHand;
	private List<Order> orders;

	public ProductDTO() {
		
	}

	public ProductDTO(String name, String brand, String type, Integer quantityInStock, boolean secondHand,
			List<Order> orders) {
		this.name = name;
		this.brand = brand;
		this.type = type;
		this.quantityInStock = quantityInStock;
		this.secondHand = secondHand;
		this.orders = orders;
	}

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
	
	public Integer getQuantityInStock() {
		return quantityInStock;
	}
	
	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
	
	public Boolean isSecondHand() {
		return secondHand;
	}
	
	public void setSecondHand(Boolean secondHand) {
		this.secondHand = secondHand;
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "the product with id:" + id + ", name:" + name + ", brand:" + brand + ", type:" + type + ", quantityInStock:"
				+ quantityInStock + ", secondHand:" + secondHand + ", orders:" + orders + " .";
	}

	public String toStringProductCreatedOrUpdated() {
		return "The product with id:" + id + ", name:" + name + ", brand:" + brand + ", type:" + type + ", quantityInStock:"
				+ quantityInStock + ", secondHand:" + secondHand + ", orders:" + orders + " is created or updated successfully.";
	}
	
	public String toStringProductDeleted() {
		return "The product with id:" + id + ", name:" + name + ", brand:" + brand + ", type:" + type + ", quantityInStock:"
				+ quantityInStock + ", secondHand:" + secondHand + ", orders:" + orders + " is deleted successfully.";
	}
}
