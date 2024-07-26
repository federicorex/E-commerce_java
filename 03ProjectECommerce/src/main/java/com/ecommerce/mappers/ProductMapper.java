package com.ecommerce.mappers;

import org.springframework.stereotype.Component;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entities.Product;

@Component
public class ProductMapper {
	
	public static ProductDTO fromProductToProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setBrand(product.getBrand());
		productDTO.setType(product.getType());
		productDTO.setQuantityInStock(product.getQuantityInStock());
		productDTO.setSecondHand(product.getSecondHand());
		productDTO.setOrders(product.getOrders());
		
		return productDTO;
	}
	
	public static Product fromProductDTOToProduct(ProductDTO productDTO) {
		Product product = new Product();
		
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setBrand(productDTO.getBrand());
		product.setType(productDTO.getType());
		product.setQuantityInStock(productDTO.getQuantityInStock());
		product.setSecondHand(productDTO.isSecondHand());
		product.setOrders(productDTO.getOrders());
		
		return product;
	}
}
