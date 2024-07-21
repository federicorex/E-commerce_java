package com.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entities.Product;
import com.ecommerce.mappers.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	private static final String LOGGER_GET_ALL_USERS = "Fetching all products";
	private static final String LOGGER_GET_USER_BY_ID = "Fetching the product with productId: {}";
	private static final String LOGGER_GET_USER_BY_ID_FAIL = "Fail, product not found";
	private static final String LOGGER_ADD_USER_START = "Adding {}...";
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
	public List<ProductDTO> getAllProducts() {
		logger.info(LOGGER_GET_ALL_USERS);
		List<ProductDTO> productDTOList = this.productDAORepository.findAll()
				.stream().map(product -> ProductMapper.fromProductToProductDTO(product))
				.collect(Collectors.toList());
		
		return productDTOList;
	}

	@Override
	public ProductDTO getProductById(Long productId) {		
		Optional<Product> tempProduct = this.productDAORepository.findById(productId);
		
		if(tempProduct.isPresent()) {
			ProductDTO productDTO = ProductMapper.fromProductToProductDTO(tempProduct.get());
			
			logger.info(LOGGER_GET_USER_BY_ID, productId);
			
			return productDTO;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, productId);
			
			throw new NoSuchElementException("Product with productId: " + productId + "not found...");
		}
	}

	@Override
	@Transactional
	public ProductDTO addProduct(ProductDTO productDTO) {
		Product product = ProductMapper.fromProductDTOToProduct(productDTO);
		
		logger.info(LOGGER_ADD_USER_START, productDTO.toString());
		this.productDAORepository.save(product);
		logger.info(LOGGER_ADD_USER_SUCCESS, product.getId());
		
		return productDTO;
	}

	@Override
	@Transactional
	public ProductDTO updateProduct(ProductDTO productDTO) {
		logger.info(LOGGER_UPDATE_USER_START, productDTO.getId());
		
		Optional<Product> tempProduct = this.productDAORepository.findById(productDTO.getId());

		if(tempProduct.isPresent()) {	
			Product product = ProductMapper.fromProductDTOToProduct(productDTO);
			
			this.productDAORepository.save(product);
			logger.info(LOGGER_UPDATE_USER_SUCCESS, product.getId());
			
			return productDTO;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, productDTO.getId());
			
			throw new NoSuchElementException("Product with productId: " + productDTO.getId() + "not found...");
		}
	}

	@Override
	@Transactional
	public String deleteProduct(Long productId) {
		logger.info(LOGGER_DELETE_USER_START, productId);
		
		Optional<Product> tempProduct = this.productDAORepository.findById(productId);
		
		if(tempProduct.isPresent()) {
			ProductDTO productDTO = ProductMapper.fromProductToProductDTO(tempProduct.get());
			String deletionMessage = productDTO.toStringProductDeleted();
			
			this.productDAORepository.deleteById(productId);
			productDTO = null;
			logger.info(LOGGER_DELETE_USER_SUCCESS, productId);
			
			return deletionMessage;
		} else {
			logger.warn(LOGGER_GET_USER_BY_ID_FAIL, productId);
			
			throw new NoSuchElementException("Product with productId: " + productId + "not found...");
		}
	}
}
