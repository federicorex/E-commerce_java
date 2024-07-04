package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.ecommerce.dal.ProductDAO;
import com.ecommerce.entities.Product;
import com.ecommerce.services.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductDAO productDAORepository;

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
        List<Product> result = productServiceImpl.getAllProducts();

        assertEquals(productList, result);
    }

    @Test
    void testGetProductByIdEmptyProduct() {
        Long id = 6L;
        
        when(productDAORepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productServiceImpl.getProductById(id));
    }
    
    @Test
    void testGetProductByIdExistingProduct() {
        Long id = 6L;
        Product product = new Product();
        
        when(productDAORepository.findById(id)).thenReturn(Optional.of(product));

        assertEquals(product, productServiceImpl.getProductById(id));
    }
    
    @Test
    void testAddProduct() {
        Product product = new Product();

        when(productDAORepository.save(product)).thenReturn(null);

        assertDoesNotThrow(() -> productServiceImpl.addProduct(product));
    }
    
    @Test
    void testUpdateProduct() {
        Product product = new Product();

        when(productDAORepository.save(product)).thenReturn(null);

        assertDoesNotThrow(() -> productServiceImpl.updateProduct(product));
    }
    
    @Test
    void testDeleteProductEmptyProduct() {
        Long id = 6L;
        
        when(productDAORepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productServiceImpl.deleteProduct(id));
    }
    
    @Test
    void testDeleteProduct() {
    	Long id = 6L;
        Product product = new Product();
        
        when(productDAORepository.findById(id)).thenReturn(Optional.of(product));

        assertEquals(product, productServiceImpl.getProductById(id));
        doNothing().when(productDAORepository).deleteById(id);
        assertDoesNotThrow(() -> productServiceImpl.deleteProduct(id));
    }
	
}
