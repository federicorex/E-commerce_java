package com.ecommerce.services;

import java.util.List;

import com.ecommerce.dto.ProductDTO;

public interface ProductService {
	
	/**
	 * CRUD operations
	 * @return
	 */
	
	List<ProductDTO> getAllProducts();
	ProductDTO getProductById(Long id);
	ProductDTO addProduct(ProductDTO productDTO);
	ProductDTO updateProduct(ProductDTO productDTO);
	String deleteProduct(Long id);
}
