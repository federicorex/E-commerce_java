package com.ecommerce.rest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
import com.ecommerce.mappers.ProductMapper;
import com.ecommerce.services.ProductService;

@RestController
@RequestMapping("api")
public class ProductREST {

	private ProductService productService;
	
	public ProductREST(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("products")
	public ResponseEntity<List<ProductDTO>> getAllProductsREST() {
		List<ProductDTO> productDTOList = this.productService.getAllProducts()
				.stream().map(product -> ProductMapper.fromProductToProductDTO(product))
				.collect(Collectors.toList());
		return new ResponseEntity<>(productDTOList, HttpStatus.OK);
	}
	
	@GetMapping("products/{productId}")
	public ResponseEntity<ProductDTO> getProductByIdREST(@PathVariable("productId") Long productId) {
		try {
			ProductDTO productDTO = ProductMapper.fromProductToProductDTO(this.productService.getProductById(productId));
			return new ResponseEntity<>(productDTO, HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		}
	}
	
	@PostMapping("products")
	public ResponseEntity<String> addProductREST(@RequestBody ProductDTO productDTO) {
		this.productService.addProduct(ProductMapper.fromProductDTOToProduct(productDTO));
		return new ResponseEntity<>(productDTO.toStringProductCreatedOrUpdated(), HttpStatus.CREATED);
	}
	
	@PutMapping("products")
	public ResponseEntity<String> updateProductREST(@RequestBody ProductDTO productDTO) {
		try {
			this.productService.updateProduct(ProductMapper.fromProductDTOToProduct(productDTO));
			return new ResponseEntity<>(productDTO.toStringProductCreatedOrUpdated(), HttpStatus.OK);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);								
		}
	}
	
	@DeleteMapping("products/{productId}")
	public ResponseEntity<Void> deleteProductREST(@PathVariable("productId") Long productId) {
		try {
			this.productService.deleteProduct(productId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
	}
}
