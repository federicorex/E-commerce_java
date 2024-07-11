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

import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entities.Product;
import com.ecommerce.services.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductRESTTest {

    @InjectMocks
    ProductREST productREST;
    
    @Mock
    ProductService productService;
    
    @Mock
    ProductDAORepository productDAORepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testGetAllProductsREST() {
        Product product = new Product();
        List<Product> productList = new LinkedList<>();
        productList.add(product);

        when(productService.getAllProducts()).thenReturn(productList);

        assertEquals(HttpStatus.OK, productREST.getAllProductsREST().getStatusCode());
    }
    
    @Test
    void testGetProductByIdEmptyProductREST() {
        Long productId = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(productService.getProductById(productId)).thenThrow(nsee);
        
        assertEquals(HttpStatus.NOT_FOUND, productREST.getProductByIdREST(productId).getStatusCode());
    }
    
    @Test
    void testGetProductByIdREST() {
        Long productId = 6L;
        Product product = new Product();
        
        when(productService.getProductById(productId)).thenReturn(product);
        
        assertEquals(HttpStatus.OK, productREST.getProductByIdREST(productId).getStatusCode());
    }
    
    @Test
    void testAddProductREST() {
        ProductDTO productDTO = new ProductDTO();

        assertEquals(HttpStatus.CREATED, productREST.addProductREST(productDTO).getStatusCode());
    }
    
    @Test
    void testUpdateProductREST() {
        ProductDTO productDTO = new ProductDTO();

        assertEquals(HttpStatus.OK, productREST.updateProductREST(productDTO).getStatusCode());
    }
    
    @Test
    void testDeleteProductEmptyProductREST() {
        Long productId = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        doThrow(nsee).when(productService).deleteProduct(productId);
        
        assertEquals(HttpStatus.NOT_FOUND, productREST.deleteProductREST(productId).getStatusCode());
    }
        
    @Test
    void testDeleteProductREST() {
        Long productId = 6L;
        
        doNothing().when(productService).deleteProduct(productId);
        
        assertEquals(HttpStatus.NO_CONTENT, productREST.deleteProductREST(productId).getStatusCode());
    }
}
