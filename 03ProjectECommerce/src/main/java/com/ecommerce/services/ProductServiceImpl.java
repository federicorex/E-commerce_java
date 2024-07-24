package com.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.constants.GlobalConstants;
import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entities.Product;
import com.ecommerce.mappers.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	private ProductDAORepository productDAORepository;

    public ProductServiceImpl(ProductDAORepository productDAORepository) {
        this.productDAORepository = productDAORepository;
    }

    @Override
	public List<ProductDTO> getAllProducts() {
		logger.info(GlobalConstants.LOGGER_GET_ALL_PRODUCTS);
		List<ProductDTO> productDTOList = this.productDAORepository.findAll()
				.stream().map(product -> ProductMapper.fromProductToProductDTO(product))
				.collect(Collectors.toList());
		
		return productDTOList;
	}

	@Override
	public ProductDTO getProductById(Long productId) {
		if(productId == null) {
			throw new NullPointerException("The productId must be not null");
		}
		
		Optional<Product> tempProduct = this.productDAORepository.findById(productId);
		
		if(tempProduct.isPresent()) {
			ProductDTO productDTO = ProductMapper.fromProductToProductDTO(tempProduct.get());
			
			logger.info(GlobalConstants.LOGGER_GET_PRODUCT_BY_ID, productId);
			
			return productDTO;
		} else {
			logger.error(GlobalConstants.LOGGER_GET_PRODUCT_BY_ID_FAIL, productId);
			
			throw new NoSuchElementException("Product with productId: " + productId + "not found...");
		}
	}

	@Override
	@Transactional
	public ProductDTO addProduct(ProductDTO productDTO) {
		if(productDTO == null) {
			throw new NullPointerException("The product must be not null");
		}
		
		Product product = ProductMapper.fromProductDTOToProduct(productDTO);
		
		logger.info(GlobalConstants.LOGGER_ADD_PRODUCT_START, productDTO.toString());
		this.productDAORepository.save(product);
		logger.info(GlobalConstants.LOGGER_ADD_PRODUCT_SUCCESS, product.getId());
		
		return productDTO;
	}

	@Override
	@Transactional
	public ProductDTO updateProduct(ProductDTO productDTO) {
		if(productDTO == null) {
			throw new NullPointerException("The product must be not null");
		}
		
		logger.info(GlobalConstants.LOGGER_UPDATE_PRODUCT_START, productDTO.getId());
		
		Optional<Product> tempProduct = this.productDAORepository.findById(productDTO.getId());

		if(tempProduct.isPresent()) {	
			Product product = ProductMapper.fromProductDTOToProduct(productDTO);
			
			this.productDAORepository.save(product);
			logger.info(GlobalConstants.LOGGER_UPDATE_PRODUCT_SUCCESS, product.getId());
			
			return productDTO;
		} else {
			logger.error(GlobalConstants.LOGGER_GET_PRODUCT_BY_ID_FAIL, productDTO.getId());
			
			throw new NoSuchElementException("Product with productId: " + productDTO.getId() + "not found...");
		}
	}

	@Override
	@Transactional
	public String deleteProduct(Long productId) {
		if(productId == null) {
			throw new NullPointerException("The productId must be not null");
		}
		
		logger.info(GlobalConstants.LOGGER_DELETE_PRODUCT_START, productId);
		
		Optional<Product> tempProduct = this.productDAORepository.findById(productId);
		
		if(tempProduct.isPresent()) {
			ProductDTO productDTO = ProductMapper.fromProductToProductDTO(tempProduct.get());
			String deletionMessage = productDTO.toStringProductDeleted();
			
			this.productDAORepository.deleteById(productId);
			productDTO = null;
			logger.info(GlobalConstants.LOGGER_DELETE_PRODUCT_SUCCESS, productId);
			
			return deletionMessage;
		} else {
			logger.error(GlobalConstants.LOGGER_GET_PRODUCT_BY_ID_FAIL, productId);
			
			throw new NoSuchElementException("Product with productId: " + productId + "not found...");
		}
	}
}
