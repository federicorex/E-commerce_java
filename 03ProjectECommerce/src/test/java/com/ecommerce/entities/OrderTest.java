package com.ecommerce.entities;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

	private static Order order;

	@BeforeAll
	static void setUp() {
		order = new Order();
	}
	
	@Test
	void testConstructor() {
		User user = new User();
		Product product = new Product();
		Order newOrder = new Order(user, product, null);
		LocalDate date = LocalDate.of(2024, 12, 1);
		
		newOrder.setDeliveryDate(date);
		assertEquals(user, newOrder.getUser());
		assertEquals(product, newOrder.getProduct());
		assertEquals(date, newOrder.getDeliveryDate());
	}
	
	@Test
	void testGetId() {
		order.setId(6L);
		assertEquals(6L, order.getId().longValue());
	}
	
	@Test
	void testGetUser() {
		User user = new User();
		
		order.setUser(user);
		assertEquals(user, order.getUser());
	}
	
	@Test
	void testGetProduct() {
		Product product = new Product();
		
		order.setProduct(product);
		assertEquals(product, order.getProduct());
	}
	
	@Test
	void testGetDeliveryDate() {
		LocalDate date = LocalDate.of(2024, 12, 1);
		
		order.setDeliveryDate(date);
		assertEquals(date, order.getDeliveryDate());
	}
	
}
