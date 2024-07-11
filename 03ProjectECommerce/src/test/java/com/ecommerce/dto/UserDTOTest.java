package com.ecommerce.dto;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.entities.Order;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;

@ExtendWith(MockitoExtension.class)
public class UserDTOTest {

	@InjectMocks
	private UserDTO userDTO;
	
	@BeforeEach
	void setUp() {
		userDTO = new UserDTO();
	}
	
	@Test
	void testConstructor() {
		LocalDate date = LocalDate.of(2000, 1, 1);
		Order order = new Order(new User(), new Product(), null);
		List<Order> orderList = new LinkedList<>();
		orderList.add(order);
		UserDTO newUserDTO = new UserDTO("John", "Black", "12345", date, "Baker street", "example@gmail.com", orderList);
		
		assertEquals("John", newUserDTO.getName());
		assertEquals("Black", newUserDTO.getSurname());
		assertEquals("12345", newUserDTO.getPassword());
		assertEquals(date, newUserDTO.getDateOfBirth());
		assertEquals("Baker street", newUserDTO.getAddress());
		assertEquals("example@gmail.com", newUserDTO.getEmail());
		assertEquals(orderList, newUserDTO.getOrders());
	}
	
	@Test
	void testGetId() {
		userDTO.setId(6L);
		
		assertEquals(6L, userDTO.getId().longValue());
	}
	
	@Test
	void testGetName() {
		userDTO.setName("John");
		
		assertEquals("John", userDTO.getName());
	}
	
	@Test
	void testGetSurname() {
		userDTO.setSurname("Black");
		
		assertEquals("Black", userDTO.getSurname());
	}
	
	@Test
	void testGetPassword() {
		userDTO.setPassword("12345");
		
		assertEquals("12345", userDTO.getPassword());
	}
	
	@Test
	void testGetDateOfBirth() {
		LocalDate date = LocalDate.of(2000, 1, 1);
		userDTO.setDateOfBirth(date);
		
		assertEquals(date, userDTO.getDateOfBirth());
	}
	
	@Test
	void testGetAddress() {
		userDTO.setAddress("Baker street");
		
		assertEquals("Baker street", userDTO.getAddress());
	}
	
	@Test
	void testGetEmail() {
		userDTO.setEmail("example@gmail.com");

		assertEquals("example@gmail.com", userDTO.getEmail());
	}
	
	@Test
	void testGetOrders() {
		Order order = new Order(new User(), new Product(), null);
		List<Order> orderList = new LinkedList<>();
		orderList.add(order);
		userDTO.setOrders(orderList);
		
		assertEquals(orderList, userDTO.getOrders());
	}
}
