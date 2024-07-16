package com.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.entities.Product;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	private static final String LOGGER_GET_ALL_USERS = "Fetching all products";
	private static final String LOGGER_GET_USER_BY_ID = "Fetching the product with productId: {}";
	private static final String LOGGER_GET_USER_BY_ID_FAIL = "Fail, product not found";
	private static final String LOGGER_ADD_USER_START = "Adding product with productId: {}...";
	private static final String LOGGER_ADD_USER_SUCCESS = "Success, product with productId: {} added";
	private static final String LOGGER_UPDATE_USER_START = "Updating product with productId: {}...";
	private static final String LOGGER_UPDATE_USER_SUCCESS = "Success, product with productId: {} updated";
	private static final String LOGGER_DELETE_USER_START = "Deleting product with productId: {}...";
	private static final String LOGGER_DELETE_USER_SUCCESS = "Success, product with productId: {} deleted";
	
	private ProductDAORepository productDAORepository;

    public ProductServiceImpl(ProductDAORepository productDAORepository) {
        this.productDAORepository = productDAORepository;
    }

	@Override
	public List<Product> getAllProducts() {
		logger.info(LOGGER_GET_ALL_USERS);
		
		return productDAORepository.findAll();
	}

	@Override
	public Product getProductById(Long productId) {		
		Optional<Product> tempProduct = productDAORepository.findById(productId);
		
		if(tempProduct.isPresent()) {
			Product product = tempProduct.get();
			
			logger.info(LOGGER_GET_USER_BY_ID, productId);
			
			return product;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, productId);
			
			throw new NoSuchElementException("Product with productId: " + productId + "not found...");
		}
	}

	@Override
	@Transactional
	public Product addProduct(Product product) {
		logger.info(LOGGER_ADD_USER_START, product.getId());
		productDAORepository.save(product);
		logger.info(LOGGER_ADD_USER_SUCCESS, product.getId());
		
		return product;
	}

	@Override
	@Transactional
	public Product updateProduct(Product product) {
		logger.info(LOGGER_UPDATE_USER_START, product.getId());
		
		Optional<Product> tempProduct = productDAORepository.findById(product.getId());

		if(tempProduct.isPresent()) {	
			productDAORepository.save(product);
			logger.info(LOGGER_UPDATE_USER_SUCCESS, product.getId());
			
			return product;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, product.getId());
			
			throw new NoSuchElementException("Product with productId: " + product.getId() + "not found...");
		}
	}

	@Override
	@Transactional
	public Product deleteProduct(Long productId) {
		logger.info(LOGGER_DELETE_USER_START, productId);
		
		Optional<Product> tempProduct = productDAORepository.findById(productId);
		
		if(tempProduct.isPresent()) {
			Product product = tempProduct.get();
			
			productDAORepository.deleteById(productId);
			logger.info(LOGGER_DELETE_USER_SUCCESS, productId);
			
			return product;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, productId);
			
			throw new NoSuchElementException("Product with productId: " + productId + "not found...");
		}
	}
}
