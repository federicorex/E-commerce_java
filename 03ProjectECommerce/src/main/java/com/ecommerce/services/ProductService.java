package com.ecommerce.services;

import java.util.List;

import com.ecommerce.entities.Product;

public interface ProductService {

	List<Product> getAllProducts();
	void addProduct();
	void updateProduct();
	void deleteProduct();
}
