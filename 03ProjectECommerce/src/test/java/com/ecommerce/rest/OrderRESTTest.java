package com.ecommerce.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
    	
    	assertTrue(orderDTOList.getClass().equals(orderREST.getAllOrdersREST().getBody().getClass()));
        assertEquals(HttpStatus.OK, orderREST.getAllOrdersREST().getStatusCode());
    }
    
    @Test
    void testGetOrderByIdEmptyOrderREST() {
        Long orderId = null;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(orderService.getOrderById(orderId)).thenThrow(nsee);
        
        assertNull(orderREST.getOrderByIdREST(orderId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, orderREST.getOrderByIdREST(orderId).getStatusCode());
    }
    
    @Test
    void testGetOrderByIdREST() {
        Long orderId = 6L;
        OrderDTO orderDTO = new OrderDTO();
        
        when(orderService.getOrderById(orderId)).thenReturn(orderDTO);
        
        assertTrue(orderDTO.getClass().equals(orderREST.getOrderByIdREST(orderId).getBody().getClass()));
        assertEquals(HttpStatus.OK, orderREST.getOrderByIdREST(orderId).getStatusCode());
    }
    
    @Test
	void testAddOrderUserOrProductEmptyREST() {
		Long userId = 6L;
		Long productId = 6L;
		NoSuchElementException nsee = new NoSuchElementException("error");
		
		when(orderService.addOrder(userId, productId)).thenThrow(nsee);
		
		assertEquals(HttpStatus.NOT_FOUND, orderREST.addOrderREST(userId, productId).getStatusCode());
	}
	
	@Test
	void testAddOrderREST() {
		Long userId = 6L;
		Long productId = 6L;
		OrderDTO orderDTO = new OrderDTO();
		
		when(orderService.addOrder(userId, productId)).thenReturn(orderDTO);
		
		assertEquals(HttpStatus.CREATED, orderREST.addOrderREST(userId, productId).getStatusCode());
	}
    
    @Test
    void testUpdateOrderemptyOrderREST() {
        OrderDTO orderDTO = new OrderDTO();
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(orderService.updateOrder(orderDTO)).thenThrow(nsee);

        assertNull(orderREST.updateOrderREST(orderDTO).getBody());
        assertEquals(HttpStatus.NOT_FOUND, orderREST.updateOrderREST(orderDTO).getStatusCode());
    }
    
    @Test
    void testUpdateOrderREST() {
        OrderDTO orderDTO = new OrderDTO();
        String messagge = "update";

        assertTrue(messagge.getClass().equals(orderREST.updateOrderREST(orderDTO).getBody().getClass()));
        assertEquals(HttpStatus.OK, orderREST.updateOrderREST(orderDTO).getStatusCode());
    }
    
    @Test
    void testDeleteOrderEmptyOrderREST() {
        Long orderId = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(orderService.deleteOrder(orderId)).thenThrow(nsee);
        
        assertNull(orderREST.deleteOrderREST(orderId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, orderREST.deleteOrderREST(orderId).getStatusCode());
    }
        
    @Test
    void testDeleteOrderREST() {
        Long orderId = 6L;
        
        assertNull(orderREST.deleteOrderREST(orderId).getBody());
        assertEquals(HttpStatus.NO_CONTENT, orderREST.deleteOrderREST(orderId).getStatusCode());
    }
}
