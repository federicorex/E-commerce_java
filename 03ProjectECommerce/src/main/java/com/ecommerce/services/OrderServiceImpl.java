package com.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dal.OrderDAORepository;
import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.dto.OrderDTO;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.Product;
import com.ecommerce.entities.User;
import com.ecommerce.mappers.OrderMapper;

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
	public List<OrderDTO> getAllOrders() {
		logger.info(LOGGER_GET_ALL_ORDERS);
		List<OrderDTO> orderDTOList = this.orderDAORepository.findAll()
				.stream().map(order -> OrderMapper.fromOrderToOrderDTO(order))
				.collect(Collectors.toList());
		
		return orderDTOList;
	}

	@Override
	public OrderDTO getOrderById(Long orderId) {		
		Optional<Order> tempOrder = this.orderDAORepository.findById(orderId);
		
		if(tempOrder.isPresent()) {
			OrderDTO orderDTO = OrderMapper.fromOrderToOrderDTO(tempOrder.get());
			
			logger.info(LOGGER_GET_ORDER_BY_ID, orderId);
			
			return orderDTO;
		} else {
			logger.warn(LOGGER_GET_ORDER_BY_ID_FAIL, orderId);
			
			throw new NoSuchElementException("Order with orderId: " + orderId + "not found...");
		}
	}
	
	@Override
	@Transactional
	public OrderDTO addOrder(Long userId, Long productId) {
		logger.info(LOGGER_ADD_ORDER_START, userId, productId);
		
		Optional<User> tempUser = this.userDAORepository.findById(userId);
		Optional <Product> tempProduct = this.productDAORepository.findById(productId);
		
		if(tempUser.isPresent() && tempProduct.isPresent()) {
			User user = tempUser.get();
			Product product = tempProduct.get();
			Order order = new Order(user, product);
			OrderDTO orderDTO = OrderMapper.fromOrderToOrderDTO(order);
			
			this.orderDAORepository.save(order);
			logger.info(LOGGER_ADD_ORDER_SUCCESS, order.getId(), userId, productId);
			
			return orderDTO;
		} else {
			logger.warn(LOGGER_ADD_ORDER_FAIL);
			
			throw new NoSuchElementException("User with id: " + userId + " or product with id: " + productId + " not found...");
		}
	}

	@Override
	@Transactional
	public OrderDTO updateOrder(OrderDTO orderDTO) {
		logger.info(LOGGER_UPDATE_ORDER_START, orderDTO.getId());
		
		Optional<Order> tempOrder = this.orderDAORepository.findById(orderDTO.getId());

		if(tempOrder.isPresent()) {	
			Order order = OrderMapper.fromOrderDTOToOrder(orderDTO);
			
			this.orderDAORepository.save(order);
			logger.info(LOGGER_UPDATE_ORDER_SUCCESS, order.getId());
			
			return orderDTO;
		} else {
			logger.warn(LOGGER_GET_ORDER_BY_ID_FAIL, orderDTO.getId());
			
			throw new NoSuchElementException("Order with orderId: " + orderDTO.getId() + "not found...");
		}
	}

	@Override
	@Transactional
	public String deleteOrder(Long orderId) {
		logger.info(LOGGER_DELETE_ORDER_START, orderId);
		
		Optional<Order> tempOrder = this.orderDAORepository.findById(orderId);
		
		if(tempOrder.isPresent()) {
			OrderDTO orderDTO = OrderMapper.fromOrderToOrderDTO(tempOrder.get());
			String deletionMessage = orderDTO.toStringOrderDeleted();
			
			this.orderDAORepository.deleteById(orderId);
			orderDTO = null;
			logger.info(LOGGER_DELETE_ORDER_SUCCESS, orderId);
			
			return deletionMessage;
		} else {
			logger.warn(LOGGER_GET_ORDER_BY_ID_FAIL, orderId);
			
			throw new NoSuchElementException("Order with orderId: " + orderId + "not found...");
		}
	}
}
