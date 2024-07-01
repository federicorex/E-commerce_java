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
	void addProduct(Product product);
	void updateProduct(Product product);
	void deleteProduct(Long id);
}
