package com.ecommerce.mappers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OrderMapper.class)
public class OrderMapperTest {
	
	@BeforeAll
	static void setup() {
		OrderMapper orderMapper = new OrderMapper();
	}

	@Test
	void testFromOrderToOrderDTO() {
		Order order = new Order();
		User user = new User();
		Product product = new Product();
		LocalDate localDate = LocalDate.of(2024, 8, 10);
		
		order.setId(6L);
		order.setUser(user);
		order.setProduct(product);
		order.setDeliveryDate(localDate);
		
		assertEquals(6L, order.getId());
		assertEquals(user, order.getUser());
		assertEquals(product, order.getProduct());
		assertEquals(localDate, order.getDeliveryDate());
		assertDoesNotThrow(() -> OrderMapper.fromOrderToOrderDTO(order));
	}
	
	@Test
	void testFromOrderDTOToOrder() {
		OrderDTO orderDTO = new OrderDTO();
		User user = new User();
		Product product = new Product();
		LocalDate localDate = LocalDate.of(2024, 8, 10);
		
		orderDTO.setId(6L);
		orderDTO.setUser(user);
		orderDTO.setProduct(product);
		orderDTO.setDeliveryDate(localDate);
		
		assertEquals(6L, orderDTO.getId());
		assertEquals(user, orderDTO.getUser());
		assertEquals(product, orderDTO.getProduct());
		assertEquals(localDate, orderDTO.getDeliveryDate());
		assertDoesNotThrow(() -> OrderMapper.fromOrderDTOToOrder(orderDTO));
	}
}
