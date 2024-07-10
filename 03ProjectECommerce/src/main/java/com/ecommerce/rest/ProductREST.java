package com.ecommerce.rest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.services.ProductService;

@RestController
@RequestMapping("api")
public class ProductREST {

	@Autowired
	private ProductService productService;
	
	@GetMapping("products")
	public ResponseEntity<List<ProductDTO>> getAllProductsREST() {
		List<ProductDTO> productDTOList = productService.getAllProducts()
				.stream().map(product -> ProductMapper.fromProductToProductDTO(product))
				.collect(Collectors.toList());
		return new ResponseEntity<>(productDTOList, HttpStatus.OK);
	}
	
	@GetMapping("products/{id}")
	public ResponseEntity<ProductDTO> getProductByIdREST(@PathVariable("id") Long id) {
		try {
			ProductDTO productDTO = ProductMapper.fromProductToProductDTO(productService.getProductById(id));
			return new ResponseEntity<>(productDTO, HttpStatus.OK);			
		} catch(NoSuchElementException noSuchElementException) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);						
		}
	}
	
	@PostMapping("products")
	public ResponseEntity<Void> addProductREST(@RequestBody ProductDTO productDTO) {
		productService.addProduct(ProductMapper.fromProductDTOToProduct(productDTO));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("products")
	public ResponseEntity<Void> updateProductREST(@RequestBody ProductDTO productDTO) {
		productService.updateProduct(ProductMapper.fromProductDTOToProduct(productDTO));
		return new ResponseEntity<>(HttpStatus.OK);
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
