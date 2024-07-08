package com.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private static final String LOGGER_GET_ORDER_BY_ID = "Fetching the order with id: {}";
	private static final String LOGGER_GET_ORDER_BY_ID_FAIL = "Fail, order not found";
	private static final String LOGGER_ADD_ORDER_START = "Adding the order with userId: {} and productId: {}...";
	private static final String LOGGER_ADD_ORDER_SUCCESS = "Success, order added";
	private static final String LOGGER_ADD_ORDER_FAIL = "Fail, order not added: user or product not found";
	private static final String LOGGER_UPDATE_ORDER_START = "Updating order with id: {}...";
	private static final String LOGGER_UPDATE_ORDER_SUCCESS = "Success, order with id: {} updated";
	private static final String LOGGER_DELETE_ORDER_START = "Deleting order with id: {}...";
	private static final String LOGGER_DELETE_ORDER_SUCCESS = "Success, order with id: {} deleted";
	
	@Autowired
	private UserDAORepository userDAORepository;
	@Autowired
    private ProductDAORepository productDAORepository;
	@Autowired
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
	public Order getOrderById(Long id) {
		Optional<Order> tempOrder = orderDAORepository.findById(id);
		
		if(tempOrder.isPresent()) {
			Order order = tempOrder.get();
			
			logger.info(LOGGER_GET_ORDER_BY_ID, id);
			return order;
		} else {
			logger.warn(LOGGER_GET_ORDER_BY_ID_FAIL, id);
			throw new NoSuchElementException("Order with id: " + id + "not found...");
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
			Order order = new Order(user, product, null);
			
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
			throw new NoSuchElementException("Order with id: " + order.getId() + "not found...");
		}
	}

	@Override
	@Transactional
	public void deleteOrder(Long id) {
		logger.info(LOGGER_DELETE_ORDER_START, id);
		
		Optional<Order> tempOrder = orderDAORepository.findById(id);
		
		if(tempOrder.isPresent()) {
			orderDAORepository.deleteById(id);
			logger.info(LOGGER_DELETE_ORDER_SUCCESS, id);
		} else {
			logger.warn(LOGGER_GET_ORDER_BY_ID_FAIL, id);
			throw new NoSuchElementException("Order with id: " + id + "not found...");
		}
	};
	
}
