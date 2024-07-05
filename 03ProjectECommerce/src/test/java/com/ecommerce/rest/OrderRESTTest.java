package com.ecommerce.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.services.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderRESTTest {

	@InjectMocks
	OrderServiceImpl orderServiceImpl;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
}
