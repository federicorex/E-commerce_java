package com.ecommerce.rest;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entities.Product;
import com.ecommerce.services.ProductService;

@RestController
@RequestMapping("api")
public class ProductREST {

	@Autowired
	private ProductService productService;
	
	@GetMapping("products")
	public ResponseEntity<List<Product>> getAllProductsREST() {
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping("products/{id}")
	public ResponseEntity<Product> getProductByIdREST(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		}
	}
	
	@PostMapping("products")
	public ResponseEntity<Void> addProductREST(@RequestBody Product product) {
		productService.addProduct(product);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("products")
	public ResponseEntity<Void> updateProductREST(@RequestBody Product product) {
		try {
			productService.updateProduct(product);
			return new ResponseEntity<>(HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);									
		}
	}
	
	@DeleteMapping("products")
	public ResponseEntity<Void> deleteProductREST(@PathVariable("id") Long id) {
		try {
			productService.deleteProduct(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);									
		}
	}
	
}
