package com.ecommerce.mapper;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entities.Order;

public class OrderMapper {
	
	public static OrderDTO fromOrderToOrderDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		
		orderDTO.setId(order.getId());
		orderDTO.setUser(order.getUser());
		orderDTO.setProduct(order.getProduct());
		orderDTO.setDeliveryDate(order.getDeliveryDate());
		
		return orderDTO;
	}
	
	public static Order fromOrderDTOToOrder(OrderDTO orderDTO) {
		Order order = new Order();

		order.setId(orderDTO.getId());
		order.setUser(orderDTO.getUser());
		order.setProduct(orderDTO.getProduct());
		order.setDeliveryDate(orderDTO.getDeliveryDate());
		
		return order;
	}
}
