package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.dal.OrderDAORepository;
import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;
import com.ecommerce.services.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @Mock
    private OrderDAORepository orderDAORepository;
    
    @Mock
    private UserDAORepository userDAORepository;
    
    @Mock
    private ProductDAORepository productDAORepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllOrders() {
        Order order = new Order();
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);

        when(orderDAORepository.findAll()).thenReturn(orderList);
        List<Order> result = orderServiceImpl.getAllOrders();

        assertEquals(orderList, result);
    }

    @Test
    void testGetOrderByIdEmptyOrder() {
        Long orderId = 6L;
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.getOrderById(orderId));
    }
    
    @Test
    void testGetOrderByIdExistingOrder() {
        Long orderId = 6L;
        Order order = new Order();
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.of(order));

        assertEquals(order, orderServiceImpl.getOrderById(orderId));
    }
    
    @Test
    void testAddOrderEmptyUserAndProduct() {
    	Long id = 6L;

        when(userDAORepository.findById(id)).thenReturn(Optional.empty());
        when(productDAORepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.addOrder(id, id));
    }
    
    @Test
    void testAddOrderEmptyProduct() {
    	Long id = 6L;
    	User user = new User();

        when(userDAORepository.findById(id)).thenReturn(Optional.of(user));
        when(productDAORepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.addOrder(id, id));
    }
    
    @Test
    void testAddOrderEmptyUser() {
    	Long id = 6L;
    	Product product = new Product();

        when(productDAORepository.findById(id)).thenReturn(Optional.of(product));
        when(userDAORepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.addOrder(id, id));
    }
    
    @Test
    void testAddOrder() {
    	Long id = 6L;
    	User user = new User();
    	Product product = new Product();

        when(userDAORepository.findById(id)).thenReturn(Optional.of(user));
        when(productDAORepository.findById(id)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> orderServiceImpl.addOrder(id, id));
    }
    
    @Test
    void testUpdateEmptyOrder() {
        Order order = new Order();
        Long orderId = 6L;
        order.setId(orderId);

        when(orderDAORepository.findById(order.getId())).thenReturn(Optional.empty());
        
        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.updateOrder(order));
    }
    
    @Test
    void testUpdateOrder() {
        Order order = new Order();
        Long orderId = 6L;
        order.setId(orderId);

        when(orderDAORepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderDAORepository.save(order)).thenReturn(null);
        
        assertDoesNotThrow(() -> orderServiceImpl.updateOrder(order));
    }
    
    @Test
    void testDeleteOrderEmptyOrder() {
        Long orderId = 6L;
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.deleteOrder(orderId));
    }
    
    @Test
    void testDeleteOrder() {
    	Long orderId = 6L;
        Order order = new Order();
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.of(order));
        assertEquals(order, orderServiceImpl.getOrderById(orderId));
        doNothing().when(orderDAORepository).deleteById(orderId);

        assertDoesNotThrow(() -> orderServiceImpl.deleteOrder(orderId));
    }
    
}
