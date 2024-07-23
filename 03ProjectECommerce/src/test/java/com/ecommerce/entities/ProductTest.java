package com.ecommerce.entities;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductTest {

	private static Product product;

	@BeforeAll
	static void setUp() {
		product = new Product();
	}
	
	@Test
	void testConstructor() {
		Order order = new Order(new User(), new Product(), null);
		List<Order> orderList = new LinkedList<>();
		orderList.add(order);
		Product newProduct = new Product("F2 Pro", "Poco", "Smartphone", 6, false, orderList);
		
		assertEquals("F2 Pro", newProduct.getName());
		assertEquals("Poco", newProduct.getBrand());
		assertEquals("Smartphone", newProduct.getType());
		assertEquals(Integer.valueOf(6), newProduct.getQuantityInStock());
		assertEquals(Boolean.FALSE, newProduct.getSecondHand());
		assertEquals(orderList, newProduct.getOrders());
	}
	
	@Test
	void testGetId() {
		product.setId(6L);
		
		assertEquals(6L, product.getId().longValue());
	}
	
	@Test
	void testGetName() {
		product.setName("F2 Pro");
		
		assertEquals("F2 Pro", product.getName());
	}
	
	@Test
	void testGetBrand() {
		product.setBrand("Poco");
		
		assertEquals("Poco", product.getBrand());
	}
	
	@Test
	void testGetType() {
		product.setType("Smartphone");
		
		assertEquals("Smartphone", product.getType());
	}
	
	@Test
	void testGetQuantityInStock() {
		product.setQuantityInStock(Integer.valueOf(6));
		
		assertEquals(Integer.valueOf(6), product.getQuantityInStock());
	}
	
	@Test
	void testGetSecondHand() {
		product.setSecondHand(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, product.getSecondHand());
	}
	
	@Test
	void testGetOrders() {
		Order order = new Order(new User(), new Product(), null);
		List<Order> orderList = new LinkedList<>();
		orderList.add(order);
		product.setOrders(orderList);
		
		assertEquals(orderList, product.getOrders());
	}
}
