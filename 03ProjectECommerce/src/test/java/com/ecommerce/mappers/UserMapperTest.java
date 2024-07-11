package com.ecommerce.mappers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.User;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserMapper.class)
public class UserMapperTest {
	
	@BeforeAll
	static void setup() {
		UserMapper userMapper = new UserMapper();
	}
	
	@Test
	void testFromUserToUserDTO() {
		User user = new User();
		LocalDate localDate = LocalDate.of(1990, 8, 10);
		List<Order> orderList = new LinkedList<>();
		
		user.setId(6L);
		user.setName("John");
		user.setSurname("Doe");
		user.setDateOfBirth(localDate);
		user.setAddress("123, Manhattan");
		user.setEmail("john@example.com");
		user.setOrders(orderList);
		
		assertEquals(6L, user.getId());
		assertEquals("John", user.getName());
		assertEquals("Doe", user.getSurname());
		assertEquals(localDate, user.getDateOfBirth());
		assertEquals("123, Manhattan", user.getAddress());
		assertEquals("john@example.com", user.getEmail());
		assertEquals(orderList, user.getOrders());
		assertDoesNotThrow(() -> UserMapper.fromUserToUserDTO(user));
	}
	
	@Test
	void testFromUserDTOToUser() {
		UserDTO userDTO = new UserDTO();
		LocalDate localDate = LocalDate.of(1990, 8, 10);
		List<Order> orderList = new LinkedList<>();
		
		userDTO.setId(6L);
		userDTO.setName("John");
		userDTO.setSurname("Doe");
		userDTO.setPassword("12345");
		userDTO.setDateOfBirth(localDate);
		userDTO.setAddress("123, Manhattan");
		userDTO.setEmail("john@example.com");
		userDTO.setOrders(orderList);
		
		assertEquals(6L, userDTO.getId());
		assertEquals("John", userDTO.getName());
		assertEquals("Doe", userDTO.getSurname());
		assertEquals("12345", userDTO.getPassword());
		assertEquals(localDate, userDTO.getDateOfBirth());
		assertEquals("123, Manhattan", userDTO.getAddress());
		assertEquals("john@example.com", userDTO.getEmail());
		assertEquals(orderList, userDTO.getOrders());
		assertDoesNotThrow(() -> UserMapper.fromUserDTOToUser(userDTO));
	}
}
