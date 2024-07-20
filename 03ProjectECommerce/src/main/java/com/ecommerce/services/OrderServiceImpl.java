package com.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dal.OrderDAORepository;
import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	private static final String LOGGER_GET_ALL_ORDERS = "Fetching all orders";
	private static final String LOGGER_GET_ORDER_BY_ID = "Fetching the order with orderId: {}";
	private static final String LOGGER_GET_ORDER_BY_ID_FAIL = "Fail, order not found";
	private static final String LOGGER_ADD_ORDER_START = "Adding order with userId: {} and productId: {}...";
	private static final String LOGGER_ADD_ORDER_SUCCESS = "Success, order with orderId: {}, userId: {} and productId: {} added";
	private static final String LOGGER_ADD_ORDER_FAIL = "Fail, order not added: user or product not found";
	private static final String LOGGER_UPDATE_ORDER_START = "Updating order with orderId: {}...";
	private static final String LOGGER_UPDATE_ORDER_SUCCESS = "Success, order with orderId: {} updated";
	private static final String LOGGER_DELETE_ORDER_START = "Deleting order with orderId: {}...";
	private static final String LOGGER_DELETE_ORDER_SUCCESS = "Success, order with orderId: {} deleted";
	
	private UserDAORepository userDAORepository;
    private ProductDAORepository productDAORepository;
    private OrderDAORepository orderDAORepository;

    public OrderServiceImpl(UserDAORepository userDAORepository, ProductDAORepository productDAORepository, OrderDAORepository orderDAORepository) {
        this.userDAORepository = userDAORepository;
        this.productDAORepository = productDAORepository;
        this.orderDAORepository = orderDAORepository;
    }

	@Override
	public List<Order> getAllOrders() {
		logger.info(LOGGER_GET_ALL_ORDERS);
		
		return this.orderDAORepository.findAll();
	}

	@Override
	public Order getOrderById(Long orderId) {		
		Optional<Order> tempOrder = this.orderDAORepository.findById(orderId);
		
		if(tempOrder.isPresent()) {
			Order order = tempOrder.get();
			
			logger.info(LOGGER_GET_ORDER_BY_ID, orderId);
			
			return order;
		} else {
			logger.warn(LOGGER_GET_ORDER_BY_ID_FAIL, orderId);
			
			throw new NoSuchElementException("Order with orderId: " + orderId + "not found...");
		}
	}

	@Override
	@Transactional
	public Order addOrder(Long userId, Long productId) {
		logger.info(LOGGER_ADD_ORDER_START, userId, productId);
		
		Optional<User> tempUser = this.userDAORepository.findById(userId);
		Optional <Product> tempProduct = this.productDAORepository.findById(productId);
		
		if(tempUser.isPresent() && tempProduct.isPresent()) {
			User user = tempUser.get();
			Product product = tempProduct.get();
			Order order = new Order(user, product);
			
			this.orderDAORepository.save(order);
			logger.info(LOGGER_ADD_ORDER_SUCCESS, order.getId(), userId, productId);
			
			return order;
		} else {
			logger.warn(LOGGER_ADD_ORDER_FAIL);
			
			throw new NoSuchElementException("User with id: " + userId + " or product with id: " + productId + " not found...");
		}
	}

	@Override
	@Transactional
	public Order updateOrder(Order order) {
		logger.info(LOGGER_UPDATE_ORDER_START, order.getId());
		
		Optional<Order> tempOrder = this.orderDAORepository.findById(order.getId());

		if(tempOrder.isPresent()) {	
			this.orderDAORepository.save(order);
			logger.info(LOGGER_UPDATE_ORDER_SUCCESS, order.getId());
			
			return order;
		} else {
			logger.warn(LOGGER_GET_ORDER_BY_ID_FAIL, order.getId());
			
			throw new NoSuchElementException("Order with orderId: " + order.getId() + "not found...");
		}
	}

	@Override
	@Transactional
	public Order deleteOrder(Long orderId) {
		logger.info(LOGGER_DELETE_ORDER_START, orderId);
		
		Optional<Order> tempOrder = this.orderDAORepository.findById(orderId);
		
		if(tempOrder.isPresent()) {	
			Order order = tempOrder.get();
			
			this.orderDAORepository.deleteById(orderId);
			logger.info(LOGGER_DELETE_ORDER_SUCCESS, orderId);
			
			return order;
		} else {
			logger.warn(LOGGER_GET_ORDER_BY_ID_FAIL, orderId);
			
			throw new NoSuchElementException("Order with orderId: " + orderId + "not found...");
		}
	}
}
