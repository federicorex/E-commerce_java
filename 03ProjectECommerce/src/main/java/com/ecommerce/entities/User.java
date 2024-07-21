package com.ecommerce.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 45)
	private String name;
	
	@Column(name = "surname", nullable = false, length = 45)
	private String surname;
	
	@JsonIgnore
	@Column(name = "password", nullable = false, length = 45)
	private String password;
	
	@Column(name = "dateOfBirth", nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(name = "address", nullable = false, length = 50)
	private String address;
	
	@Column(name = "email", nullable = false, length = 45)
	private String email;
	
	@JsonManagedReference(value = "order-user")
//	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	
	public User() {
		
	}
	
	public User(String name, String surname, String password, LocalDate dateOfBirth, String address, String email,
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
}
