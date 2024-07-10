package com.ecommerce.rest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.mapper.OrderMapper;
import com.ecommerce.services.OrderService;

@RestController
@RequestMapping("api")
public class OrderREST {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("orders")
	public ResponseEntity<List<OrderDTO>> getAllOrdersREST() {
		List<OrderDTO> orderDTOList = orderService.getAllOrders()
				.stream().map(order -> OrderMapper.fromOrderToOrderDTO(order))
				.collect(Collectors.toList());
		return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
	}
	
	@GetMapping("orders/{id}")
	public ResponseEntity<OrderDTO> getOrderByIdREST(@PathVariable("id") Long id) {
		try {
			OrderDTO orderDTO = OrderMapper.fromOrderToOrderDTO(orderService.getOrderById(id));
			return new ResponseEntity<>(orderDTO, HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		}
	}
	
	@PostMapping("orders")
	public ResponseEntity<Void> addOrderREST(@PathVariable("id") Long orderId, @PathVariable("id") Long productId) {
		try {
			orderService.addOrder(orderId, productId);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);									
		}
	}
	
	@PutMapping("orders")
	public ResponseEntity<Void> updateOrderREST(@RequestBody OrderDTO orderDTO) {
		orderService.updateOrder(OrderMapper.fromOrderDTOToOrder(orderDTO));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("orders")
	public ResponseEntity<Void> deleteOrderREST(@PathVariable("id") Long id) {
		try {
			orderService.deleteOrder(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);									
		}
	}
}
