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
        Long id = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(orderService.getOrderById(id)).thenThrow(nsee);
        
        assertEquals(HttpStatus.NOT_FOUND, orderREST.getOrderByIdREST(id).getStatusCode());
    }
    
    @Test
    void testGetOrderByIdREST() {
        Long id = 6L;
        Order order = new Order();
        
        when(orderService.getOrderById(id)).thenReturn(order);
        
        assertEquals(HttpStatus.OK, orderREST.getOrderByIdREST(id).getStatusCode());
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
        Long id = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        doThrow(nsee).when(orderService).deleteOrder(id);
        
        assertEquals(HttpStatus.NOT_FOUND, orderREST.deleteOrderREST(id).getStatusCode());
    }
        
    @Test
    void testDeleteOrderREST() {
        Long id = 6L;
        
        doNothing().when(orderService).deleteOrder(id);
        
        assertEquals(HttpStatus.NO_CONTENT, orderREST.deleteOrderREST(id).getStatusCode());
    }
}
