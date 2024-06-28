package com.ecommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.dal.OrderDAO;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.User;

@Service
public class OrderServiceImpl implements OrderService {
	
	OrderDAO orderDAORepository;

	@Override
	public List<Order> getAllOrders() {
		return orderDAORepository.findAll();
	}

	@Override
	public Order getOrderById(Long id) {
		return orderDAORepository.findById(id).get();
	}

	@Override
	public void addOrder(Order order) {
//		User user = orderDAORepository.findById(order.getUser().getId()).get();
		
	}

	@Override
	public void updateOrder(Order order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteOrder(Long id) {
		// TODO Auto-generated method stub
		
	};
	
}
