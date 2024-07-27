package com.ecommerce.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    	
    	assertEquals(productDTOList, productREST.getAllProductsREST().getBody());
        assertEquals(HttpStatus.OK, productREST.getAllProductsREST().getStatusCode());
        
        verify(productService, times(2)).getAllProducts();
    }
    
    @Test
    void testGetProductByIdRESTNullProductId() {
        NullPointerException npe = new NullPointerException();
        
        when(productService.getProductById(null)).thenThrow(npe);
        
        assertNull(productREST.getProductByIdREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, productREST.getProductByIdREST(null).getStatusCode());
        
        verify(productService, times(2)).getProductById(null);
    }
    
    @Test
    void testGetProductByIdRESTEmptyProduct() {
        Long productId = 1L;
        NoSuchElementException nsee = new NoSuchElementException();
        
        when(productService.getProductById(productId)).thenThrow(nsee);
        
        assertNull(productREST.getProductByIdREST(productId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, productREST.getProductByIdREST(productId).getStatusCode());
        
        verify(productService, times(2)).getProductById(productId);
    }
    
    @Test
    void testGetProductByIdREST() {
        Long productId = 6L;
        ProductDTO productDTO = new ProductDTO();
        
        when(productService.getProductById(productId)).thenReturn(productDTO);
        
        assertEquals(productDTO, productREST.getProductByIdREST(productId).getBody());
        assertEquals(HttpStatus.OK, productREST.getProductByIdREST(productId).getStatusCode());
        
        verify(productService, times(2)).getProductById(productId);
    }
    
    @Test
    void testAddProductRESTNullProduct() {
        NullPointerException npe = new NullPointerException("The product must be not null");
        
        when(productService.addProduct(null)).thenThrow(npe);
        
        assertEquals("The product must be not null", productREST.addProductREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, productREST.addProductREST(null).getStatusCode());
        
        verify(productService, times(2)).addProduct(null);
    }
    
    @Test
    void testAddProductREST() {
        ProductDTO productDTO = new ProductDTO();
        String messagge = "The product with id:" + null + ", name:" + null + ", brand:" + null + ", type:" + null + ", quantityInStock:" + null + ", secondHand:" + null + ", orders:" + null + " is created or updated successfully.";
        
        when(productService.addProduct(productDTO)).thenReturn(productDTO);

		assertEquals(messagge, productREST.addProductREST(productDTO).getBody());
        assertEquals(HttpStatus.CREATED, productREST.addProductREST(productDTO).getStatusCode());
        
        verify(productService, times(2)).addProduct(productDTO);
    }
    
    @Test
    void testUpdateProductRESTNullProduct() {
        NullPointerException npe = new NullPointerException("The product must be not null");
        
        when(productService.updateProduct(null)).thenThrow(npe);
        
        assertEquals("The product must be not null", productREST.updateProductREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, productREST.updateProductREST(null).getStatusCode());
        
        verify(productService, times(2)).updateProduct(null);
    }
    
    @Test
    void testUpdateProductRESTEmptyProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        NoSuchElementException nsee = new NoSuchElementException("Product with productId: " + productDTO.getId() + "not found...");
        
        when(productService.updateProduct(productDTO)).thenThrow(nsee);

        assertEquals("Product with productId: " + productDTO.getId() + "not found...", productREST.updateProductREST(productDTO).getBody());
        assertEquals(HttpStatus.NOT_FOUND, productREST.updateProductREST(productDTO).getStatusCode());
        
        verify(productService, times(2)).updateProduct(productDTO);
    }
    
    @Test
    void testUpdateProductREST() {
        ProductDTO productDTO = new ProductDTO();
        String messagge = "The product with id:" + null + ", name:" + null + ", brand:" + null + ", type:" + null + ", quantityInStock:" + null + ", secondHand:" + null + ", orders:" + null + " is created or updated successfully.";
        
        when(productService.updateProduct(productDTO)).thenReturn(productDTO);

		assertEquals(messagge, productREST.updateProductREST(productDTO).getBody());
        assertEquals(HttpStatus.OK, productREST.updateProductREST(productDTO).getStatusCode());
        
        verify(productService, times(2)).updateProduct(productDTO);
    }
    
    @Test
    void testDeleteProductRESTNullProductId() {
        NullPointerException npe = new NullPointerException();
        
        when(productService.deleteProduct(null)).thenThrow(npe);
        
        assertNull(productREST.deleteProductREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, productREST.deleteProductREST(null).getStatusCode());
        
        verify(productService, times(2)).deleteProduct(null);
    }
    
    @Test
    void testDeleteProductRESTEmptyProduct() {
        Long productId = 1L;
        NoSuchElementException nsee = new NoSuchElementException();
        
        when(productService.deleteProduct(productId)).thenThrow(nsee);
        
        assertNull(productREST.deleteProductREST(productId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, productREST.deleteProductREST(productId).getStatusCode());
        
        verify(productService, times(2)).deleteProduct(productId);
    }
        
    @Test
    void testDeleteProductREST() {
        Long productId = 6L;
        String message = "The product with id:" + productId + ", name:" + null + ", brand:" + null + ", type:" + null + ", quantityInStock:" + null + ", secondHand:" + null + ", orders:" + null + " is deleted successfully.";
        
        when(productService.deleteProduct(productId)).thenReturn(message);
        
        assertNull(productREST.deleteProductREST(productId).getBody());
        assertEquals(HttpStatus.NO_CONTENT, productREST.deleteProductREST(productId).getStatusCode());
        
        verify(productService, times(2)).deleteProduct(productId);
    }
}
