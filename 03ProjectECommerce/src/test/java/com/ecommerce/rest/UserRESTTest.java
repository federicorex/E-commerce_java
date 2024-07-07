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
		
		assertEquals(userList, userREST.getAllUsersREST().getBody());
		assertEquals(HttpStatus.OK, userREST.getAllUsersREST().getStatusCode());
	}
	
	@Test
	void testGetUserByIdEmptyUserREST() {
		Long id = 6L;
		NoSuchElementException nsee = new NoSuchElementException("error");
		
		when(userServiceImpl.getUserById(id)).thenThrow(nsee);
		
		assertEquals(HttpStatus.NOT_FOUND, userREST.getUserByIdREST(id).getStatusCode());
	}
	
	@Test
	void testGetUserByIdREST() {
		Long id = 6L;
		User user = new User();
		
		when(userServiceImpl.getUserById(id)).thenReturn(user);
		
		assertEquals(user, userREST.getUserByIdREST(id).getBody());
		assertEquals(HttpStatus.OK, userREST.getUserByIdREST(id).getStatusCode());
	}
	
	@Test
	void testAddUserREST() {
		User user = new User();
		
		doNothing().when(userServiceImpl).addUser(user);
		
		assertEquals(HttpStatus.CREATED, userREST.addUserREST(user).getStatusCode());
	}
	
	@Test
	void testUpdateUserEmptyUserREST() {
		User user = new User();
		NoSuchElementException nsee = new NoSuchElementException("error");
		
		doThrow(nsee).when(userServiceImpl).updateUser(user);
		
		assertEquals(HttpStatus.NOT_FOUND, userREST.updateUserREST(user).getStatusCode());
	}
	
	@Test
	void testUpdateUserREST() {
		User user = new User();
		
		doNothing().when(userServiceImpl).updateUser(user);
		
		assertEquals(HttpStatus.OK, userREST.updateUserREST(user).getStatusCode());
	}
	
	@Test
	void testDeleteUserEmptyUserREST() {
		Long id = 6L;
		NoSuchElementException nsee = new NoSuchElementException("error");
		
		doThrow(nsee).when(userServiceImpl).deleteUser(id);
		
		assertEquals(HttpStatus.NOT_FOUND, userREST.deleteUserREST(id).getStatusCode());
	}
		
	@Test
	void testDeleteUserREST() {
		Long id = 6L;
		
		doNothing().when(userServiceImpl).deleteUser(id);
		
		assertEquals(HttpStatus.NO_CONTENT, userREST.deleteUserREST(id).getStatusCode());
	}
	
}
