package com.ecommerce.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.services.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductRESTTest {

	@InjectMocks
	ProductServiceImpl productServiceImpl;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
}
