package com.ecommerce.services;

import java.util.List;

import com.ecommerce.dto.OrderDTO;

public interface OrderService {

	/**
	 * CRUD operations
	 * @return
	 */
	
	List<OrderDTO> getAllOrders();
	OrderDTO getOrderById(Long id);
	OrderDTO addOrder(Long userId, Long productId);
	OrderDTO updateOrder(OrderDTO orderDTO);
	String deleteOrder(Long id);
}
