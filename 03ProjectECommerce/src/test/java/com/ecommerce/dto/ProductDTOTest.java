package com.ecommerce.dto;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecommerce.entities.Order;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;

public class ProductDTOTest {

	private ProductDTO productDTO;

	@BeforeEach
	void setUp() {
		productDTO = new ProductDTO();
	}
	
	@Test
	void testConstructor() {
		Order order = new Order(new User(), new Product(), null);
		List<Order> orderList = new LinkedList<>();
		orderList.add(order);
		ProductDTO newProductDTO = new ProductDTO("F2 Pro", "Poco", "Smartphone", 6, false, orderList);
		
		assertEquals("F2 Pro", newProductDTO.getName());
		assertEquals("Poco", newProductDTO.getBrand());
		assertEquals("Smartphone", newProductDTO.getType());
		assertEquals(Integer.valueOf(6), newProductDTO.getQuantityInStock());
		assertEquals(Boolean.FALSE, newProductDTO.isSecondHand());
		assertEquals(orderList, newProductDTO.getOrders());
	}
	
	@Test
	void testGetId() {
		productDTO.setId(6L);
		
		assertEquals(6L, productDTO.getId().longValue());
	}
	
	@Test
	void testGetName() {
		productDTO.setName("F2 Pro");
		
		assertEquals("F2 Pro", productDTO.getName());
	}
	
	@Test
	void testGetBrand() {
		productDTO.setBrand("Poco");
		
		assertEquals("Poco", productDTO.getBrand());
	}
	
	@Test
	void testGetType() {
		productDTO.setType("Smartphone");
		
		assertEquals("Smartphone", productDTO.getType());
	}
	
	@Test
	void testGetQuantityInStock() {
		productDTO.setQuantityInStock(Integer.valueOf(6));
		
		assertEquals(Integer.valueOf(6), productDTO.getQuantityInStock());
	}
	
	@Test
	void testGetSecondHand() {
		productDTO.setSecondHand(Boolean.FALSE);
		
		assertEquals(Boolean.FALSE, productDTO.isSecondHand());
	}
	
	@Test
	void testGetOrders() {
		Order order = new Order(new User(), new Product(), null);
		List<Order> orderList = new LinkedList<>();
		orderList.add(order);
		productDTO.setOrders(orderList);
		
		assertEquals(orderList, productDTO.getOrders());
	}
}
