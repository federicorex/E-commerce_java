package com.ecommerce.rest;

import java.util.List;
import java.util.NoSuchElementException;

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
import com.ecommerce.services.OrderService;

@RestController
@RequestMapping("api")
public class OrderREST {

	private OrderService orderService;
	
	public OrderREST(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("orders")
	public ResponseEntity<List<OrderDTO>> getAllOrdersREST() {
		return new ResponseEntity<>(this.orderService.getAllOrders(), HttpStatus.OK);
	}
	
	@GetMapping("orders/{orderId}")
	public ResponseEntity<OrderDTO> getOrderByIdREST(@PathVariable("orderId") Long orderId) {
		try {
			return new ResponseEntity<>(this.orderService.getOrderById(orderId), HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		}
	}
	
	@PostMapping("orders/users/{userId}/products/{productId}")
	public ResponseEntity<String> addOrderREST(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) {
		try {
			OrderDTO orderDTO = this.orderService.addOrder(userId, productId);
			return new ResponseEntity<>(orderDTO.toStringOrderCreatedOrUpdated(), HttpStatus.CREATED);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);									
		}
	}
	
	@PutMapping("orders")
	public ResponseEntity<String> updateOrderREST(@RequestBody OrderDTO orderDTO) {
		try {
			this.orderService.updateOrder(orderDTO);
			
			return new ResponseEntity<>(orderDTO.toStringOrderCreatedOrUpdated(), HttpStatus.OK);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);								
		}
	}
	
	@DeleteMapping("orders/{orderId}")
	public ResponseEntity<Void> deleteOrderREST(@PathVariable("orderId") Long orderId) {
		try {
			this.orderService.deleteOrder(orderId);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
	}
}
