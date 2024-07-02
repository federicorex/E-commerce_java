package com.ecommerce.entities;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserTest {
	
	private static User user;
	
	@BeforeAll
	static void setUp() {
		user = new User();
	}
	
	@Test
	void testConstructor() {
		Date date = new Date(946681200000L);
		Order order = new Order(new User(), new Product());
		List<Order> orderList = new LinkedList<>();
		orderList.add(order);
		User newUser = new User("John", "Black", "12345", date, "Baker street", "example@gmail.com", orderList);
		
		assertEquals("John", newUser.getName());
		assertEquals("Black", newUser.getSurname());
		assertEquals("12345", newUser.getPassword());
		assertEquals(date, newUser.getDateOfBirth());
		assertEquals("Baker street", newUser.getAddress());
		assertEquals("example@gmail.com", newUser.getEmail());
		assertEquals(orderList, newUser.getOrders());
	}
	
	@Test
	void testGetId() {
		user.setId(6L);
		assertEquals(6L, user.getId().longValue());
	}
	
	@Test
	void testGetName() {
		user.setName("John");
		assertEquals("John", user.getName());
	}
	
	@Test
	void testGetSurname() {
		user.setSurname("Black");
		assertEquals("Black", user.getSurname());
	}
	
	@Test
	void testGetPassword() {
		user.setPassword("12345");
		assertEquals("12345", user.getPassword());
	}
	
	@Test
	void testGetDateOfBirth() {
		Date date = new Date(946681200000L);
		user.setDateOfBirth(date);
		assertEquals(date, user.getDateOfBirth());
	}
	
	@Test
	void testGetAddress() {
		user.setAddress("Baker street");
		assertEquals("Baker street", user.getAddress());
	}
	
	@Test
	void testGetEmail() {
		user.setEmail("example@gmail.com");
		assertEquals("example@gmail.com", user.getEmail());
	}
	
	@Test
	void testGetOrders() {
		Order order = new Order(new User(), new Product());
		List<Order> orderList = new LinkedList<>();
		
		orderList.add(order);
		user.setOrders(orderList);
		assertEquals(orderList, user.getOrders());
	}
	
}
