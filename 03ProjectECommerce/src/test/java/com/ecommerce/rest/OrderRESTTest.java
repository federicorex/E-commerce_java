package com.ecommerce.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.ecommerce.dal.OrderDAORepository;
import com.ecommerce.dto.OrderDTO;
import com.ecommerce.services.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderRESTTest {

    @InjectMocks
    OrderREST orderREST;
    
    @Mock
    OrderService orderService;
    
    @Mock
    OrderDAORepository orderDAORepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testGetAllOrdersREST() {
    	List<OrderDTO> orderDTOList = new ArrayList<>();
    	
    	when(orderService.getAllOrders()).thenReturn(orderDTOList);	
    	
    	assertEquals(orderDTOList, orderREST.getAllOrdersREST().getBody());
        assertEquals(HttpStatus.OK, orderREST.getAllOrdersREST().getStatusCode());
        
        verify(orderService, times(2)).getAllOrders();
    }
    
    @Test
    void testGetOrderByIdRESTNullOrderId() {
        NullPointerException npe = new NullPointerException();
        
        when(orderService.getOrderById(null)).thenThrow(npe);
        
        assertNull(orderREST.getOrderByIdREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, orderREST.getOrderByIdREST(null).getStatusCode());
        
        verify(orderService, times(2)).getOrderById(null);
    }
    
    @Test
    void testGetOrderByIdRESTEmptyOrder() {
        Long orderId = 1L;
        NoSuchElementException nsee = new NoSuchElementException();
        
        when(orderService.getOrderById(orderId)).thenThrow(nsee);
        
        assertNull(orderREST.getOrderByIdREST(orderId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, orderREST.getOrderByIdREST(orderId).getStatusCode());
        
        verify(orderService, times(2)).getOrderById(orderId);
    }
    
    @Test
    void testGetOrderByIdREST() {
        Long orderId = 6L;
        OrderDTO orderDTO = new OrderDTO();
        
        when(orderService.getOrderById(orderId)).thenReturn(orderDTO);
        
        assertEquals(orderDTO, orderREST.getOrderByIdREST(orderId).getBody());
        assertEquals(HttpStatus.OK, orderREST.getOrderByIdREST(orderId).getStatusCode());
        
        verify(orderService, times(2)).getOrderById(orderId);
    }
    
    @Test
    void testAddOrderRESTNullOrder() {
        NullPointerException npe = new NullPointerException("The userId or productId must be not null");
        
        when(orderService.addOrder(null, null)).thenThrow(npe);
        
        assertEquals("The userId or productId must be not null", orderREST.addOrderREST(null, null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, orderREST.addOrderREST(null, null).getStatusCode());
        
        verify(orderService, times(2)).addOrder(null, null);
    }
    
    @Test
	void testAddOrderRESTUserOrProductEmpty() {
		Long userId = 6L;
		Long productId = 6L;
		NoSuchElementException nsee = new NoSuchElementException("User with id: " + userId + " or product with id: " + productId + " not found...");
		
		when(orderService.addOrder(userId, productId)).thenThrow(nsee);
		
		assertEquals("User with id: " + userId + " or product with id: " + productId + " not found...", orderREST.addOrderREST(userId, productId).getBody());
		assertEquals(HttpStatus.NOT_FOUND, orderREST.addOrderREST(userId, productId).getStatusCode());
		
		verify(orderService, times(2)).addOrder(userId, productId);
	}
	
	@Test
	void testAddOrderREST() {
		Long userId = 6L;
		Long productId = 6L;
		OrderDTO orderDTO = new OrderDTO();
		String message = "The order with id:" + null + ", user:" + null + ", product:" + null + ", deliveryDate:" + null + " is created or updated successfully.";
		
		when(orderService.addOrder(userId, productId)).thenReturn(orderDTO);
		
		assertEquals(message, orderREST.addOrderREST(userId, productId).getBody());
		assertEquals(HttpStatus.CREATED, orderREST.addOrderREST(userId, productId).getStatusCode());
		
		verify(orderService, times(2)).addOrder(userId, productId);
	}
    
    @Test
    void testUpdateOrderRESTNullOrder() {
        NullPointerException npe = new NullPointerException("The order must be not null");
        
        when(orderService.updateOrder(null)).thenThrow(npe);
        
        assertEquals("The order must be not null", orderREST.updateOrderREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, orderREST.updateOrderREST(null).getStatusCode());
        
        verify(orderService, times(2)).updateOrder(null);
    }
    
    @Test
    void testUpdateOrderRESTEmptyOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        NoSuchElementException nsee = new NoSuchElementException("Order with orderId: " + orderDTO.getId() + "not found...");
        
        when(orderService.updateOrder(orderDTO)).thenThrow(nsee);

        assertEquals("Order with orderId: " + orderDTO.getId() + "not found...", orderREST.updateOrderREST(orderDTO).getBody());
        assertEquals(HttpStatus.NOT_FOUND, orderREST.updateOrderREST(orderDTO).getStatusCode());
        
        verify(orderService, times(2)).updateOrder(orderDTO);
    }
    
    @Test
    void testUpdateOrderREST() {
        OrderDTO orderDTO = new OrderDTO();
        String messagge = "The order with id:" + null + ", user:" + null + ", product:" + null + ", deliveryDate:" + null + " is created or updated successfully.";

        when(orderService.updateOrder(orderDTO)).thenReturn(orderDTO);
		
        assertEquals(messagge, orderREST.updateOrderREST(orderDTO).getBody());
        assertEquals(HttpStatus.OK, orderREST.updateOrderREST(orderDTO).getStatusCode());
        
        verify(orderService, times(2)).updateOrder(orderDTO);
    }
    
    @Test
    void testDeleteOrderRESTNullOrderId() {
        NullPointerException npe = new NullPointerException();
        
        when(orderService.deleteOrder(null)).thenThrow(npe);
        
        assertNull(orderREST.deleteOrderREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, orderREST.deleteOrderREST(null).getStatusCode());
        
        verify(orderService, times(2)).deleteOrder(null);
    }
    
    @Test
    void testDeleteOrderRESTEmptyOrder() {
        Long orderId = 1L;
        NoSuchElementException nsee = new NoSuchElementException();
        
        when(orderService.deleteOrder(orderId)).thenThrow(nsee);
        
        assertNull(orderREST.deleteOrderREST(orderId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, orderREST.deleteOrderREST(orderId).getStatusCode());
        
        verify(orderService, times(2)).deleteOrder(orderId);
    }
        
    @Test
    void testDeleteOrderREST() {
        Long orderId = 6L;
        String message = "The order with id:" + orderId + ", user:" + null + ", product:" + null + ", deliveryDate:" + null + " is deleted successfully.";
        
        when(orderService.deleteOrder(orderId)).thenReturn(message);
        
        assertNull(orderREST.deleteOrderREST(orderId).getBody());
        assertEquals(HttpStatus.NO_CONTENT, orderREST.deleteOrderREST(orderId).getStatusCode());
        
        verify(orderService, times(2)).deleteOrder(orderId);
    }
}
