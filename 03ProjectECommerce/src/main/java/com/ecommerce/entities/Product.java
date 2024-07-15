package com.ecommerce.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 45)
	private String name;
	
	@Column(name = "brand", nullable = false, length = 45)
	private String brand;
	
	@Column(name = "type", nullable = false, length = 45)
	private String type;
	
	@Column(name = "quantityInStock", nullable = false)
	private int quantityInStock;
	
	@Column(name = "secondHand", nullable = false)
	private boolean secondHand;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Order> orders;
	
	public Product() {
		
	}
	
	public Product(String name, String brand, String type, int quantityInStock, boolean secondHand, List<Order> orders) {
		super();
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
	
	public int getQuantityInStock() {
		return quantityInStock;
	}
	
	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
	
	public boolean getSecondHand() {
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

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", brand=" + brand + ", type=" + type + ", quantityInStock="
				+ quantityInStock + ", secondHand=" + secondHand + ", orders=" + orders + "]";
	}
}
