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
	private static final String LOGGER_ADD_ORDER_START = "Adding order with orderId: {}...";
	private static final String LOGGER_ADD_ORDER_SUCCESS = "Success, order with orderId: {} added";
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
		return orderDAORepository.findAll();
	}

	@Override
	public Order getOrderById(Long orderId) {		
		Optional<Order> tempOrder = orderDAORepository.findById(orderId);
		
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
	public void addOrder(Long userId, Long productId) {
		logger.info(LOGGER_ADD_ORDER_START, userId, productId);
		
		Optional<User> tempUser = userDAORepository.findById(userId);
		Optional <Product> tempProduct = productDAORepository.findById(productId);
		
		if(tempUser.isPresent() && tempProduct.isPresent()) {
			User user = tempUser.get();
			Product product = tempProduct.get();
			Order order = new Order(user, product);
			
			orderDAORepository.save(order);
			logger.info(LOGGER_ADD_ORDER_SUCCESS);
		} else {
			logger.warn(LOGGER_ADD_ORDER_FAIL);
			throw new NoSuchElementException("User with id: " + userId + " or product with id: " + productId + " not found...");
		}
	}

	@Override
	@Transactional
	public void updateOrder(Order order) {
		logger.info(LOGGER_UPDATE_ORDER_START, order.getId());
		
		Optional<Order> tempOrder = orderDAORepository.findById(order.getId());

		if(tempOrder.isPresent()) {	
			orderDAORepository.save(order);
			logger.info(LOGGER_UPDATE_ORDER_SUCCESS, order.getId());
		} else {
			logger.warn(LOGGER_GET_ORDER_BY_ID_FAIL, order.getId());
			throw new NoSuchElementException("Order with orderId: " + order.getId() + "not found...");
		}
	}

	@Override
	@Transactional
	public void deleteOrder(Long orderId) {
		logger.info(LOGGER_DELETE_ORDER_START, orderId);
		
		Optional<Order> tempOrder = orderDAORepository.findById(orderId);
		
		if(tempOrder.isPresent()) {	
			orderDAORepository.deleteById(orderId);
			logger.info(LOGGER_DELETE_ORDER_SUCCESS, orderId);
		} else {
			logger.warn(LOGGER_GET_ORDER_BY_ID_FAIL, orderId);
			throw new NoSuchElementException("Order with orderId: " + orderId + "not found...");
		}
	}
	
}
