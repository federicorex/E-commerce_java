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
import com.ecommerce.mappers.OrderMapper;
import com.ecommerce.services.OrderService;

@RestController
@RequestMapping("api")
public class OrderREST {

	private OrderService orderService;
	
	@Autowired
	@GetMapping("orders")
	public ResponseEntity<List<OrderDTO>> getAllOrdersREST() {
		List<OrderDTO> orderDTOList = orderService.getAllOrders()
				.stream().map(order -> OrderMapper.fromOrderToOrderDTO(order))
				.collect(Collectors.toList());
		return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
	}
	
	@Autowired
	@GetMapping("orders/{orderId}")
	public ResponseEntity<OrderDTO> getOrderByIdREST(@PathVariable("orderId") Long orderId) {
		try {
			OrderDTO orderDTO = OrderMapper.fromOrderToOrderDTO(orderService.getOrderById(orderId));
			return new ResponseEntity<>(orderDTO, HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		}
	}
	
	@Autowired
	@PostMapping("orders/users/{userId}/products/{productId}")
	public ResponseEntity<Void> addOrderREST(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) {
		try {
			orderService.addOrder(userId, productId);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);									
		}
	}
	
	@Autowired
	@PutMapping("orders")
	public ResponseEntity<Void> updateOrderREST(@RequestBody OrderDTO orderDTO) {
		try {
			orderService.updateOrder(OrderMapper.fromOrderDTOToOrder(orderDTO));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);								
		}
	}
	
	@Autowired
	@DeleteMapping("orders/{orderId}")
	public ResponseEntity<Void> deleteOrderREST(@PathVariable("orderId") Long orderId) {
		try {
			orderService.deleteOrder(orderId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
	}
}
