package com.ecommerce.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
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
import com.ecommerce.entities.Order;
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
        Order order = new Order();
        List<Order> orderList = new LinkedList<>();
        orderList.add(order);

        when(orderService.getAllOrders()).thenReturn(orderList);

        assertEquals(HttpStatus.OK, orderREST.getAllOrdersREST().getStatusCode());
    }
    
    @Test
    void testGetOrderByIdEmptyOrderREST() {
        Long orderId = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(orderService.getOrderById(orderId)).thenThrow(nsee);
        
        assertEquals(HttpStatus.NOT_FOUND, orderREST.getOrderByIdREST(orderId).getStatusCode());
    }
    
    @Test
    void testGetOrderByIdREST() {
        Long orderId = 6L;
        Order order = new Order();
        
        when(orderService.getOrderById(orderId)).thenReturn(order);
        
        assertEquals(HttpStatus.OK, orderREST.getOrderByIdREST(orderId).getStatusCode());
    }
    
    @Test
	void testAddOrderUserOrProductEmptyREST() {
		Long userId = 6L;
		Long productId = 6L;
		NoSuchElementException nsee = new NoSuchElementException("error");
		
		doThrow(nsee).when(orderService).addOrder(userId, productId);
		
		assertEquals(HttpStatus.NOT_FOUND, orderREST.addOrderREST(userId, productId).getStatusCode());
	}
	
	@Test
	void testAddOrderREST() {
		Long userId = 6L;
		Long productId = 6L;
		
		doNothing().when(orderService).addOrder(userId, productId);
		
		assertEquals(HttpStatus.CREATED, orderREST.addOrderREST(userId, productId).getStatusCode());
	}
    
    @Test
    void testUpdateOrderREST() {
        OrderDTO orderDTO = new OrderDTO();

        assertEquals(HttpStatus.OK, orderREST.updateOrderREST(orderDTO).getStatusCode());
    }
    
    @Test
    void testDeleteOrderEmptyOrderREST() {
        Long orderId = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        doThrow(nsee).when(orderService).deleteOrder(orderId);
        
        assertEquals(HttpStatus.NOT_FOUND, orderREST.deleteOrderREST(orderId).getStatusCode());
    }
        
    @Test
    void testDeleteOrderREST() {
        Long orderId = 6L;
        
        doNothing().when(orderService).deleteOrder(orderId);
        
        assertEquals(HttpStatus.NO_CONTENT, orderREST.deleteOrderREST(orderId).getStatusCode());
    }
}
