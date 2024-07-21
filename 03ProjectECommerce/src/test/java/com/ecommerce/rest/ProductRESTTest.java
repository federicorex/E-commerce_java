package com.ecommerce.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
    	List<ProductDTO> productDTOList = new ArrayList<>();
    	
    	when(productService.getAllProducts()).thenReturn(productDTOList);    	
    	
    	assertTrue(productDTOList.getClass().equals(productREST.getAllProductsREST().getBody().getClass()));
        assertEquals(HttpStatus.OK, productREST.getAllProductsREST().getStatusCode());
    }
    
    @Test
    void testGetProductByIdEmptyProductREST() {
        Long productId = null;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(productService.getProductById(productId)).thenThrow(nsee);
        
        assertNull(productREST.getProductByIdREST(productId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, productREST.getProductByIdREST(productId).getStatusCode());
    }
    
    @Test
    void testGetProductByIdREST() {
        Long productId = 6L;
        ProductDTO productDTO = new ProductDTO();
        
        when(productService.getProductById(productId)).thenReturn(productDTO);
        
        assertTrue(productDTO.getClass().equals(productREST.getProductByIdREST(productId).getBody().getClass()));
        assertEquals(HttpStatus.OK, productREST.getProductByIdREST(productId).getStatusCode());
    }
    
    @Test
    void testAddProductREST() {
        ProductDTO productDTO = new ProductDTO();
        String messagge = "creation";

        assertTrue(messagge.getClass().equals(productREST.addProductREST(productDTO).getBody().getClass()));
        assertEquals(HttpStatus.CREATED, productREST.addProductREST(productDTO).getStatusCode());
    }
    
    @Test
    void testUpdateProductemptyProductREST() {
        ProductDTO productDTO = new ProductDTO();
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(productService.updateProduct(productDTO)).thenThrow(nsee);

        assertNull(productREST.updateProductREST(productDTO).getBody());
        assertEquals(HttpStatus.NOT_FOUND, productREST.updateProductREST(productDTO).getStatusCode());
    }
    
    @Test
    void testUpdateProductREST() {
        ProductDTO productDTO = new ProductDTO();
        String messagge = "update";

        assertTrue(messagge.getClass().equals(productREST.updateProductREST(productDTO).getBody().getClass()));
        assertEquals(HttpStatus.OK, productREST.updateProductREST(productDTO).getStatusCode());
    }
    
    @Test
    void testDeleteProductEmptyProductREST() {
        Long productId = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(productService.deleteProduct(productId)).thenThrow(nsee);
        
        assertNull(productREST.deleteProductREST(productId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, productREST.deleteProductREST(productId).getStatusCode());
    }
        
    @Test
    void testDeleteProductREST() {
        Long productId = 6L;
        
        assertNull(productREST.deleteProductREST(productId).getBody());
        assertEquals(HttpStatus.NO_CONTENT, productREST.deleteProductREST(productId).getStatusCode());
    }
}
