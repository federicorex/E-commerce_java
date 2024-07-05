package com.ecommerce.rest;

import java.util.List;
import java.util.NoSuchElementException;

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

import com.ecommerce.entities.Order;
import com.ecommerce.services.OrderService;

@RestController
@RequestMapping("api")
public class OrderREST {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("orders")
	public ResponseEntity<List<Order>> getAllOrdersREST() {
		return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
	}
	
	@GetMapping("orders/{id}")
	public ResponseEntity<Order> getOrderByIdREST(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		}
	}
	
	@PostMapping("orders")
	public ResponseEntity<Void> addOrderREST(@PathVariable("id") Long orderId, @PathVariable("id") Long productId) {
		orderService.addOrder(orderId, productId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("orders")
	public ResponseEntity<Void> updateOrderREST(@RequestBody Order order) {
		try {
			orderService.updateOrder(order);
			return new ResponseEntity<>(HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);									
		}
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
