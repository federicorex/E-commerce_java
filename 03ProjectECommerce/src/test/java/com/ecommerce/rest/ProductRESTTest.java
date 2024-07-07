package com.ecommerce.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.ecommerce.entities.Product;
import com.ecommerce.services.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductRESTTest {

	@InjectMocks
	ProductREST productREST;
	
	@Mock
	ProductServiceImpl productServiceImpl;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testGetAllProductsREST() {
		Product product = new Product();
		List<Product> productList = new LinkedList<Product>();
		productList.add(product);

		when(productServiceImpl.getAllProducts()).thenReturn(productList);
		
		assertEquals(productList, productREST.getAllProductsREST().getBody());
		assertEquals(HttpStatus.OK, productREST.getAllProductsREST().getStatusCode());
	}
	
	@Test
	void testGetProductByIdEmptyProductREST() {
		Long id = 6L;
		NoSuchElementException nsee = new NoSuchElementException("error");
		
		when(productServiceImpl.getProductById(id)).thenThrow(nsee);
		
		assertEquals(HttpStatus.NOT_FOUND, productREST.getProductByIdREST(id).getStatusCode());
	}
	
	@Test
	void testGetProductByIdREST() {
		Long id = 6L;
		Product product = new Product();
		
		when(productServiceImpl.getProductById(id)).thenReturn(product);
		
		assertEquals(product, productREST.getProductByIdREST(id).getBody());
		assertEquals(HttpStatus.OK, productREST.getProductByIdREST(id).getStatusCode());
	}
	
	@Test
	void testAddProductREST() {
		Product product = new Product();
		
		doNothing().when(productServiceImpl).addProduct(product);
		
		assertEquals(HttpStatus.CREATED, productREST.addProductREST(product).getStatusCode());
	}
	
	@Test
	void testUpdateProductEmptyProductREST() {
		Product product = new Product();
		NoSuchElementException nsee = new NoSuchElementException("error");
		
		doThrow(nsee).when(productServiceImpl).updateProduct(product);
		
		assertEquals(HttpStatus.NOT_FOUND, productREST.updateProductREST(product).getStatusCode());
	}
	
	@Test
	void testUpdateProductREST() {
		Product product = new Product();
		
		doNothing().when(productServiceImpl).updateProduct(product);
		
		assertEquals(HttpStatus.OK, productREST.updateProductREST(product).getStatusCode());
	}
	
	@Test
	void testDeleteProductEmptyProductREST() {
		Long id = 6L;
		NoSuchElementException nsee = new NoSuchElementException("error");
		
		doThrow(nsee).when(productServiceImpl).deleteProduct(id);
		
		assertEquals(HttpStatus.NOT_FOUND, productREST.deleteProductREST(id).getStatusCode());
	}
		
	@Test
	void testDeleteProductREST() {
		Long id = 6L;
		
		doNothing().when(productServiceImpl).deleteProduct(id);
		
		assertEquals(HttpStatus.NO_CONTENT, productREST.deleteProductREST(id).getStatusCode());
	}
	
}
