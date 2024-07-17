package com.ecommerce.dto;

import java.time.LocalDate;
import java.util.List;

import com.ecommerce.entities.Order;

public class UserDTO {

	private Long id;
	private String name;
	private String surname;
	private String password;
	private LocalDate dateOfBirth;
	private String address;
	private String email;
	private List<Order> orders;
	
	public UserDTO() {
		
	}

	public UserDTO(String name, String surname, String password, LocalDate dateOfBirth, String address, String email,
			List<Order> orders) {
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.email = email;
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
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String toStringUserCreatedOrUpdated() {
		return "The user with id=" + id + ", name=" + name + ", surname=" + surname + ", dateOfBirth=" + dateOfBirth
				+ ", address=" + address + ", email=" + email + ", orders=" + orders + " is created or updated successfully";
	}
}
