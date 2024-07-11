package com.ecommerce.mappers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.Product;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ProductMapper.class)
public class ProductMapperTest {
	
	@BeforeAll
	static void setup() {
		ProductMapper productMapper = new ProductMapper();
	}

	@Test
	void testFromProductToProductDTO() {
		Product product = new Product();
		List<Order> orderList = new LinkedList<>();
		
		product.setId(6L);
		product.setName("Laptop");
		product.setBrand("Apple");
		product.setType("Electronics");
		product.setQuantityInStock(20);
		product.setSecondHand(true);
		product.setOrders(orderList);
		
		assertEquals(6L, product.getId());
		assertEquals("Laptop", product.getName());
		assertEquals("Apple", product.getBrand());
		assertEquals("Electronics", product.getType());
		assertEquals(20, product.getQuantityInStock());
		assertEquals(true, product.getSecondHand());
		assertEquals(orderList, product.getOrders());
		assertDoesNotThrow(() -> ProductMapper.fromProductToProductDTO(product));
	}
	
	@Test
	void testFromProductDTOToProduct() {
		ProductDTO productDTO = new ProductDTO();
		List<Order> orderList = new LinkedList<>();
		
		productDTO.setId(6L);
		productDTO.setName("Laptop");
		productDTO.setBrand("Apple");
		productDTO.setType("Electronics");
		productDTO.setQuantityInStock(20);
		productDTO.setSecondHand(false);
		productDTO.setOrders(orderList);
		
		assertEquals(6L, productDTO.getId());
		assertEquals("Laptop", productDTO.getName());
		assertEquals("Apple", productDTO.getBrand());
		assertEquals("Electronics", productDTO.getType());
		assertEquals(20, productDTO.getQuantityInStock());
		assertEquals(false, productDTO.isSecondHand());
		assertEquals(orderList, productDTO.getOrders());
		assertDoesNotThrow(() -> ProductMapper.fromProductDTOToProduct(productDTO));
	}
}
