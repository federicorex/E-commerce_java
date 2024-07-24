package com.ecommerce.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entities.Product;
import com.ecommerce.services.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductDAORepository productDAORepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product product = new Product();
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productDAORepository.findAll()).thenReturn(productList);
        List<ProductDTO> productDTOList = new ArrayList<>();

        assertNotNull(productServiceImpl.getAllProducts());
        assertTrue(productDTOList.getClass().equals(productServiceImpl.getAllProducts().getClass()));
    }
    
    @Test
    void testGetProductByIdNullId() {
    	var exception = assertThrows(NullPointerException.class, () -> productServiceImpl.getProductById(null));
    	assertEquals("The productId must be not null", exception.getMessage());
    }

    @Test
    void testGetProductByIdEmptyProduct() {
        Long productId = 6L;
        
        when(productDAORepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productServiceImpl.getProductById(productId));
    }
    
    @Test
    void testGetProductById() {
        Long productId = 6L;
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        
        when(productDAORepository.findById(productId)).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productServiceImpl.getProductById(productId));
        assertNotNull(productServiceImpl.getProductById(productId));
        assertTrue(productDTO.getClass().equals(productServiceImpl.getProductById(productId).getClass()));
    }
    
    @Test
    void testAddProductNullProduct() {
    	var exception = assertThrows(NullPointerException.class, () -> productServiceImpl.addProduct(null));
    	assertEquals("The product must be not null", exception.getMessage());
    }
    
    @Test
    void testAddProduct() {
        ProductDTO productDTO = new ProductDTO();

        assertNotNull(productServiceImpl.addProduct(productDTO));
        assertTrue(productDTO.getClass().equals(productServiceImpl.addProduct(productDTO).getClass()));
    }
    
    @Test
    void testUpdateProductNullProduct() {
    	var exception = assertThrows(NullPointerException.class, () -> productServiceImpl.updateProduct(null));
    	assertEquals("The product must be not null", exception.getMessage());
    }
    
    @Test
    void testUpdateProductEmptyProduct() {
        ProductDTO productDTO = new ProductDTO();
        
        when(productDAORepository.findById(null)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productServiceImpl.updateProduct(productDTO));
    }
    
    @Test
    void testUpdateProduct() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        Long productDTOId = 6L;
        productDTO.setId(productDTOId);

        when(productDAORepository.findById(productDTO.getId())).thenReturn(Optional.of(product));
        
        assertDoesNotThrow(() -> productServiceImpl.updateProduct(productDTO));
        assertNotNull(productServiceImpl.updateProduct(productDTO));
        assertTrue(productDTO.getClass().equals(productServiceImpl.updateProduct(productDTO).getClass()));
    }
    
    @Test
    void testDeleteProductNullId() {
    	var exception = assertThrows(NullPointerException.class, () -> productServiceImpl.deleteProduct(null));
    	assertEquals("The productId must be not null", exception.getMessage());
    }
    
    @Test
    void testDeleteProductEmptyProduct() {
        Long productId = 6L;
        
        when(productDAORepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productServiceImpl.deleteProduct(productId));
    }
    
    @Test
    void testDeleteProduct() {
    	Long productId = 6L;
        Product product = new Product();
        String messagge = "deletion";
        
        when(productDAORepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productDAORepository).deleteById(productId);
        
        assertDoesNotThrow(() -> productServiceImpl.deleteProduct(productId));
        assertTrue(messagge.getClass().equals(productServiceImpl.deleteProduct(productId).getClass()));
    }
}
