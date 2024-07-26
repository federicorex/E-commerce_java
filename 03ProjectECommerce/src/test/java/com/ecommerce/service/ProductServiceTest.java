package com.ecommerce.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.dal.ProductDAORepository;
import com.ecommerce.dto.ProductDTO;
import com.ecommerce.entities.Product;
import com.ecommerce.mappers.ProductMapper;
import com.ecommerce.services.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductDAORepository productDAORepository;

    private MockedStatic<ProductMapper> productMapperStatic;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        productMapperStatic = Mockito.mockStatic(ProductMapper.class);
    }
    
    @AfterEach
    void tearDown() {
    	productMapperStatic.close();
    }

    @Test
    void testGetAllProducts() {
        Product product = new Product();
        List<Product> productList = new ArrayList<>();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productList.add(product);

        when(productDAORepository.findAll()).thenReturn(productList);

        assertNotNull(productServiceImpl.getAllProducts());
        assertTrue(productDTOList.getClass().equals(productServiceImpl.getAllProducts().getClass()));
        
        verify(productDAORepository, times(2)).findAll();
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
        
        verify(productDAORepository, times(1)).findById(productId);
    }
    
    @Test
    void testGetProductById() {
        Long productId = 6L;
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        
        when(productDAORepository.findById(productId)).thenReturn(Optional.of(product));
        productMapperStatic.when(() -> ProductMapper.fromProductToProductDTO(Optional.of(product).get())).thenReturn(productDTO);

        assertDoesNotThrow(() -> productServiceImpl.getProductById(productId));
        assertEquals(productDTO, productServiceImpl.getProductById(productId));
        
        verify(productDAORepository, times(2)).findById(productId);
    }
    
    @Test
    void testAddProductNullProduct() {
    	var exception = assertThrows(NullPointerException.class, () -> productServiceImpl.addProduct(null));
    	assertEquals("The product must be not null", exception.getMessage());
    }
    
    @Test
    void testAddProduct() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        
        productMapperStatic.when(() -> ProductMapper.fromProductDTOToProduct(productDTO)).thenReturn(product);
        when(productDAORepository.save(product)).thenReturn(product);
        
        assertEquals(productDTO, productServiceImpl.addProduct(productDTO));
        
        verify(productDAORepository, times(1)).save(product);
    }
    
    @Test
    void testUpdateProductNullProduct() {
    	var exception = assertThrows(NullPointerException.class, () -> productServiceImpl.updateProduct(null));
        assertEquals("The product must be not null", exception.getMessage());
    }
    
    @Test
    void testUpdateProductEmptyProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        
        when(productDAORepository.findById(productDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productServiceImpl.updateProduct(productDTO));
        
        verify(productDAORepository, times(1)).findById(1L);
    }
    
    @Test
    void testUpdateProduct() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        Long productDTOId = 6L;
        productDTO.setId(productDTOId);

        when(productDAORepository.findById(productDTO.getId())).thenReturn(Optional.of(product));
        productMapperStatic.when(() -> ProductMapper.fromProductDTOToProduct(productDTO)).thenReturn(product);
        when(productDAORepository.save(product)).thenReturn(product);
        
        assertEquals(productDTO, productServiceImpl.updateProduct(productDTO));
        
        verify(productDAORepository, times(1)).findById(productDTO.getId());
        verify(productDAORepository, times(1)).save(product);
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
        
        verify(productDAORepository, times(1)).findById(productId);
    }
    
    @Test
    void testDeleteProduct() {
    	Long productId = 6L;
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);
        String messagge = "The product with id:" + 6L + ", name:" + null + ", brand:" + null + ", type:" + null + ", quantityInStock:"
				+ null + ", secondHand:" + null + ", orders:" + null + " is deleted successfully.";
        
        when(productDAORepository.findById(productId)).thenReturn(Optional.of(product));
        productMapperStatic.when(() -> ProductMapper.fromProductToProductDTO(Optional.of(product).get())).thenReturn(productDTO);
        doNothing().when(productDAORepository).deleteById(productId);
        
        assertEquals(messagge, productServiceImpl.deleteProduct(productId));
        
        verify(productDAORepository, times(1)).findById(productId);
        verify(productDAORepository, times(1)).deleteById(productId);
    }
}
