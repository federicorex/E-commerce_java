package com.ecommerce.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ecommerce.entities.User;
import com.ecommerce.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserRESTTest {

	@InjectMocks
	UserREST userREST;
	
	@Mock
	UserServiceImpl userServiceImpl;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testGetAllUsersREST() {
		User user = new User();
		List<User> userList = new LinkedList<User>();
		userList.add(user);

		when(userServiceImpl.getAllUsers()).thenReturn(userList);
		
		assertEquals(userList, userREST.getAllUsersREST());
	}
	
	@Test
	void testGetUserByIdREST() {
		User user = new User();
		
		
		
		
	}
	
	@Test
	void testAddUserREST() {
		
	}
	
	@Test
	void testUpdateUserREST() {
		
	}
	
	@Test
	void testDeleteUserREST() {
		
	}
	
}
