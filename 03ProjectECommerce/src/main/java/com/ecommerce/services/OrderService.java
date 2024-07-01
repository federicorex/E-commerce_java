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
	void addOrder(Long userId, Long productId);
	void updateOrder(Order order);
	void deleteOrder(Long id);
}
