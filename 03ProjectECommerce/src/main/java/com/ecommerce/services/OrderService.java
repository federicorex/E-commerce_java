package com.ecommerce.services;

import java.util.List;

import com.ecommerce.entities.Order;

public interface OrderService {

	/**
	 * CRUD operations
	 * @return
	 */
	
	List<Order> getAllOrders();
	Order getOrderById(Long id);
	Order addOrder(Long userId, Long productId);
	Order updateOrder(Order order);
	Order deleteOrder(Long id);
}
