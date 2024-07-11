package com.ecommerce.dto;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;

public class OrderDTOTest {

	private OrderDTO orderDTO;

	@BeforeEach
	void setUp() {
		orderDTO = new OrderDTO();
	}
	
	@Test
	void testConstructorTwoParameters() {
		User user = new User();
		Product product = new Product();
		OrderDTO newOrderDTO = new OrderDTO(user, product);
		
		assertEquals(user, newOrderDTO.getUser());
		assertEquals(product, newOrderDTO.getProduct());
		assertEquals(LocalDate.now().plusDays(5), newOrderDTO.getDeliveryDate());
	}
	
	@Test
	void testConstructorThreeParameters() {
		User user = new User();
		Product product = new Product();
		OrderDTO newOrderDTO = new OrderDTO(user, product, null);
		LocalDate date = LocalDate.of(2024, 12, 1);
		newOrderDTO.setDeliveryDate(date);
		
		assertEquals(user, newOrderDTO.getUser());
		assertEquals(product, newOrderDTO.getProduct());
		assertEquals(date, newOrderDTO.getDeliveryDate());
	}
	
	@Test
	void testGetId() {
		orderDTO.setId(6L);
		
		assertEquals(6L, orderDTO.getId().longValue());
	}
	
	@Test
	void testGetUser() {
		User user = new User();
		orderDTO.setUser(user);
		
		assertEquals(user, orderDTO.getUser());
	}
	
	@Test
	void testGetProduct() {
		Product product = new Product();
		orderDTO.setProduct(product);
		
		assertEquals(product, orderDTO.getProduct());
	}
	
	@Test
	void testGetDeliveryDate() {
		LocalDate date = LocalDate.of(2024, 12, 1);
		orderDTO.setDeliveryDate(date);
		
		assertEquals(date, orderDTO.getDeliveryDate());
	}
	
	@Test
	void testGetdefaultDeliveryDate() {
		orderDTO.setDefaultDeliveryDate();
		
		assertEquals(LocalDate.now().plusDays(5), orderDTO.getDeliveryDate());
	}
}
