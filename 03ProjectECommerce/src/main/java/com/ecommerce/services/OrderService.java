package com.ecommerce.services;

import java.util.List;

import com.ecommerce.entities.Order;

public interface OrderService {

	List<Order> getAllOrders();
	Order getOrderById(Long id);
	void addOrder(Order order);
	void updateOrder(Order order);
	void deleteOrder(Long id);
}
