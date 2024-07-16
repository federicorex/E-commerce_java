package com.ecommerce.services;

import java.util.List;

import com.ecommerce.entities.Product;

public interface ProductService {
	
	/**
	 * CRUD operations
	 * @return
	 */
	
	List<Product> getAllProducts();
	Product getProductById(Long id);
	Product addProduct(Product product);
	Product updateProduct(Product product);
	Product deleteProduct(Long id);
}
