package com.ecommerce.rest;

import java.util.List;
import java.util.NoSuchElementException;

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

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class ProductREST {

	private ProductService productService;
	
	public ProductREST(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("products")
	public ResponseEntity<List<ProductDTO>> getAllProductsREST() {
		return new ResponseEntity<>(this.productService.getAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping("products/{productId}")
	public ResponseEntity<ProductDTO> getProductByIdREST(@PathVariable("productId") Long productId) {
		try {
			return new ResponseEntity<>(this.productService.getProductById(productId), HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		} catch(NullPointerException nullPointerException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("products")
	public ResponseEntity<String> addProductREST(@Valid @RequestBody ProductDTO productDTO) {
		try {
			this.productService.addProduct(productDTO);
			
			return new ResponseEntity<>(productDTO.toStringProductCreatedOrUpdated(), HttpStatus.CREATED);
		} catch(NullPointerException nullPointerException) {
			return new ResponseEntity<>(nullPointerException.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("products")
	public ResponseEntity<String> updateProductREST(@Valid @RequestBody ProductDTO productDTO) {
		try {
			this.productService.updateProduct(productDTO);
			
			return new ResponseEntity<>(productDTO.toStringProductCreatedOrUpdated(), HttpStatus.OK);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);								
		} catch(NullPointerException nullPointerException) {
			return new ResponseEntity<>(nullPointerException.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("products/{productId}")
	public ResponseEntity<Void> deleteProductREST(@PathVariable("productId") Long productId) {
		try {
			this.productService.deleteProduct(productId);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		} catch(NullPointerException nullPointerException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
