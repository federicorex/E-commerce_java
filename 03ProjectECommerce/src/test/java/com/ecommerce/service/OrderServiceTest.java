package com.ecommerce.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import com.ecommerce.dto.OrderDTO;
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
        List<OrderDTO> orderDTOList = new ArrayList<>();

        assertNotNull(orderServiceImpl.getAllOrders());
        assertTrue(orderDTOList.getClass().equals(orderServiceImpl.getAllOrders().getClass()));
    }

    @Test
    void testGetOrderByIdEmptyOrder() {
        Long orderId = 6L;
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.getOrderById(orderId));
    }
    
    @Test
    void testGetOrderById() {
        Long orderId = 6L;
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.of(order));

        assertDoesNotThrow(() -> orderServiceImpl.getOrderById(orderId));
        assertNotNull(orderServiceImpl.getOrderById(orderId));
        assertTrue(orderDTO.getClass().equals(orderServiceImpl.getOrderById(orderId).getClass()));
    }
    
    @Test
    void testAddOrderEmptyUserAndProduct() {
    	Long userId = 5L;
    	Long productId = 6L;

        when(userDAORepository.findById(userId)).thenReturn(Optional.empty());
        when(productDAORepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.addOrder(userId, productId));
    }
    
    @Test
    void testAddOrderEmptyProduct() {
    	Long userId = 5L;
    	Long productId = 6L;
    	User user = new User();

        when(userDAORepository.findById(userId)).thenReturn(Optional.of(user));
        when(productDAORepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.addOrder(userId, productId));
    }
    
    @Test
    void testAddOrderEmptyUser() {
    	Long userId = 5L;
    	Long productId = 6L;
    	Product product = new Product();

        when(userDAORepository.findById(userId)).thenReturn(Optional.empty());
        when(productDAORepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.addOrder(userId, productId));
    }
    
    @Test
    void testAddOrder() {
    	Long userId = 5L;
    	Long productId = 6L;
    	User user = new User();
    	Product product = new Product();

        when(userDAORepository.findById(userId)).thenReturn(Optional.of(user));
        when(productDAORepository.findById(productId)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> orderServiceImpl.addOrder(userId, productId));
    }
    
    @Test
    void testUpdateEmptyOrder() {
        OrderDTO orderDTO = new OrderDTO();
        
        when(orderDAORepository.findById(null)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.updateOrder(orderDTO));
    }
    
    @Test
    void testUpdateOrder() {
        OrderDTO orderDTO = new OrderDTO();
        Order order = new Order();
        Long orderDTOId = 6L;
        orderDTO.setId(orderDTOId);

        when(orderDAORepository.findById(orderDTO.getId())).thenReturn(Optional.of(order));
        
        assertDoesNotThrow(() -> orderServiceImpl.updateOrder(orderDTO));
        assertNotNull(orderServiceImpl.updateOrder(orderDTO));
        assertTrue(orderDTO.getClass().equals(orderServiceImpl.updateOrder(orderDTO).getClass()));
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
        String messagge = "deletion";
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.of(order));
        doNothing().when(orderDAORepository).deleteById(orderId);
        
        assertDoesNotThrow(() -> orderServiceImpl.deleteOrder(orderId));
        assertTrue(messagge.getClass().equals(orderServiceImpl.deleteOrder(orderId).getClass()));
    }
}
