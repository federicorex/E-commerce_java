package com.ecommerce.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.dal.OrderDAORepository;
import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;
import com.ecommerce.mappers.OrderMapper;
import com.ecommerce.services.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @Mock
    private ProductDAORepository productDAORepository;
    
    @Mock
    private UserDAORepository userDAORepository;
    
    @Mock
    private OrderDAORepository orderDAORepository;

    private MockedStatic<OrderMapper> orderMapperStatic;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orderMapperStatic = Mockito.mockStatic(OrderMapper.class);
    }
    
    @AfterEach
    void tearDown() {
    	orderMapperStatic.close();
    }

    @Test
    void testGetAllOrders() {
        Order order = new Order();
        List<Order> orderList = new ArrayList<>();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderList.add(order);

        when(orderDAORepository.findAll()).thenReturn(orderList);

        assertNotNull(orderServiceImpl.getAllOrders());
        assertTrue(orderDTOList.getClass().equals(orderServiceImpl.getAllOrders().getClass()));
        
        verify(orderDAORepository, times(2)).findAll();
    }
    
    @Test
    void testGetOrderByIdNullId() {
        var exception = assertThrows(NullPointerException.class, () -> orderServiceImpl.getOrderById(null));
        assertEquals("The orderId must be not null", exception.getMessage());
    }

    @Test
    void testGetOrderByIdEmptyOrder() {
        Long orderId = 6L;
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.getOrderById(orderId));
        
        verify(orderDAORepository, times(1)).findById(orderId);
    }
    
    @Test
    void testGetOrderById() {
        Long orderId = 6L;
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.of(order));
        orderMapperStatic.when(() -> OrderMapper.fromOrderToOrderDTO(Optional.of(order).get())).thenReturn(orderDTO);

        assertDoesNotThrow(() -> orderServiceImpl.getOrderById(orderId));
        assertEquals(orderDTO, orderServiceImpl.getOrderById(orderId));
        
        verify(orderDAORepository, times(2)).findById(orderId);
    }
    
//    @Test
//    void testAddOrderNullOrder() {
//    	var exception = assertThrows(NullPointerException.class, () -> orderServiceImpl.addOrder(null));
//    	assertEquals("The order must be not null", exception.getMessage());
//    }
    
//    @Test
//    void testAddOrder() {
//        OrderDTO orderDTO = new OrderDTO();
//        Order order = new Order();
//        
//        orderMapperStatic.when(() -> OrderMapper.fromOrderDTOToOrder(orderDTO)).thenReturn(order);
//        when(orderDAORepository.save(order)).thenReturn(order);
//        
//        assertEquals(orderDTO, orderServiceImpl.addOrder(orderDTO));
//        
//        verify(orderDAORepository, times(1)).save(order);
//    }
    
    @Test
    void testAddOrderNullUserIdAndProductId() {
    	var exception = assertThrows(NullPointerException.class, () -> orderServiceImpl.addOrder(null, null));
    	assertEquals("The userId or productId must be not null", exception.getMessage());
    }
    
    @Test
    void testAddOrderNullProductId() {
    	var exception = assertThrows(NullPointerException.class, () -> orderServiceImpl.addOrder(5L, null));
    	assertEquals("The userId or productId must be not null", exception.getMessage());
    }
    
    @Test
    void testAddOrderNullUserId() {
    	var exception = assertThrows(NullPointerException.class, () -> orderServiceImpl.addOrder(null, 6L));
    	assertEquals("The userId or productId must be not null", exception.getMessage());
    }
    
    @Test
    void testAddOrderEmptyUserAndProduct() {
    	Long userId = 5L;
    	Long productId = 6L;

        when(userDAORepository.findById(userId)).thenReturn(Optional.empty());
        when(productDAORepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.addOrder(userId, productId));
        
        verify(userDAORepository, times(1)).findById(userId);
        verify(productDAORepository, times(1)).findById(productId);
    }
    
    @Test
    void testAddOrderEmptyProduct() {
    	Long userId = 5L;
    	Long productId = 6L;
    	User user = new User();

        when(userDAORepository.findById(userId)).thenReturn(Optional.of(user));
        when(productDAORepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.addOrder(userId, productId));
        
        verify(userDAORepository, times(1)).findById(userId);
        verify(productDAORepository, times(1)).findById(productId);
    }
    
    @Test
    void testAddOrderEmptyUser() {
    	Long userId = 5L;
    	Long productId = 6L;
    	Product product = new Product();

        when(userDAORepository.findById(userId)).thenReturn(Optional.empty());
        when(productDAORepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.addOrder(userId, productId));
        
        verify(userDAORepository, times(1)).findById(userId);
        verify(productDAORepository, times(1)).findById(productId);
    }
    
    @Test
    void testAddOrder() {
    	Long userId = 5L;
    	Long productId = 6L;
    	User user = new User();
    	Product product = new Product();
    	OrderDTO orderDTO = new OrderDTO();

        when(userDAORepository.findById(userId)).thenReturn(Optional.of(user));
        when(productDAORepository.findById(productId)).thenReturn(Optional.of(product));
        orderMapperStatic.when(() -> OrderMapper.fromOrderToOrderDTO(any(Order.class))).thenReturn(orderDTO);
        when(orderDAORepository.save(any(Order.class))).thenReturn(any(Order.class));

        assertDoesNotThrow(() -> orderServiceImpl.addOrder(userId, productId));
        assertEquals(orderDTO, orderServiceImpl.addOrder(userId, productId));
        
        verify(userDAORepository, times(2)).findById(userId);
        verify(productDAORepository, times(2)).findById(productId);
        verify(orderDAORepository, times(2)).save(any(Order.class));
    }
    
    @Test
    void testUpdateOrderNullOrder() {
    	var exception = assertThrows(NullPointerException.class, () -> orderServiceImpl.updateOrder(null));
        assertEquals("The order must be not null", exception.getMessage());
    }
    
    @Test
    void testUpdateOrderEmptyOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        
        when(orderDAORepository.findById(orderDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.updateOrder(orderDTO));
        
        verify(orderDAORepository, times(1)).findById(1L);
    }
    
    @Test
    void testUpdateOrder() {
        OrderDTO orderDTO = new OrderDTO();
        Order order = new Order();
        Long orderDTOId = 6L;
        orderDTO.setId(orderDTOId);

        when(orderDAORepository.findById(orderDTO.getId())).thenReturn(Optional.of(order));
        orderMapperStatic.when(() -> OrderMapper.fromOrderDTOToOrder(orderDTO)).thenReturn(order);
        when(orderDAORepository.save(order)).thenReturn(order);
        
        assertEquals(orderDTO, orderServiceImpl.updateOrder(orderDTO));
        
        verify(orderDAORepository, times(1)).findById(orderDTO.getId());
        verify(orderDAORepository, times(1)).save(order);
    }
    
    @Test
    void testDeleteOrderNullId() {
    	var exception = assertThrows(NullPointerException.class, () -> orderServiceImpl.deleteOrder(null));
        assertEquals("The orderId must be not null", exception.getMessage());
    }
    
    @Test
    void testDeleteOrderEmptyOrder() {
        Long orderId = 6L;
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderServiceImpl.deleteOrder(orderId));
        
        verify(orderDAORepository, times(1)).findById(orderId);
    }
    
    @Test
    void testDeleteOrder() {
    	Long orderId = 6L;
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);
        String messagge = "The order with id:" + 6L + ", user:" + null + ", product:" + null + ", deliveryDate:" + null + " is deleted successfully.";
        
        when(orderDAORepository.findById(orderId)).thenReturn(Optional.of(order));
        orderMapperStatic.when(() -> OrderMapper.fromOrderToOrderDTO(Optional.of(order).get())).thenReturn(orderDTO);
        doNothing().when(orderDAORepository).deleteById(orderId);
        
        assertEquals(messagge, orderServiceImpl.deleteOrder(orderId));
        
        verify(orderDAORepository, times(1)).findById(orderId);
        verify(orderDAORepository, times(1)).deleteById(orderId);
    }
}
