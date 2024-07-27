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

import com.ecommerce.customexceptions.LessThanEighteenYearsOldException;
import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserRESTTest {

    @InjectMocks
    UserREST userREST;
    
    @Mock
    UserService userService;
    
    @Mock
    UserDAORepository userDAORepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testGetAllUsersREST() {
    	List<UserDTO> userDTOList = new ArrayList<>();
    	
    	when(userService.getAllUsers()).thenReturn(userDTOList);	
    	
    	assertEquals(userDTOList, userREST.getAllUsersREST().getBody());
        assertEquals(HttpStatus.OK, userREST.getAllUsersREST().getStatusCode());
        
        verify(userService, times(2)).getAllUsers();
    }
    
    @Test
    void testGetUserByIdRESTNullUserId() {
        NullPointerException npe = new NullPointerException();
        
        when(userService.getUserById(null)).thenThrow(npe);
        
        assertNull(userREST.getUserByIdREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, userREST.getUserByIdREST(null).getStatusCode());
        
        verify(userService, times(2)).getUserById(null);
    }
    
    @Test
    void testGetUserByIdRESTEmptyUser() {
        Long userId = 1L;
        NoSuchElementException nsee = new NoSuchElementException();
        
        when(userService.getUserById(userId)).thenThrow(nsee);
        
        assertNull(userREST.getUserByIdREST(userId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, userREST.getUserByIdREST(userId).getStatusCode());
        
        verify(userService, times(2)).getUserById(userId);
    }
    
    @Test
    void testGetUserByIdREST() {
        Long userId = 6L;
        UserDTO userDTO = new UserDTO();
        
        when(userService.getUserById(userId)).thenReturn(userDTO);
        
        assertEquals(userDTO, userREST.getUserByIdREST(userId).getBody());
        assertEquals(HttpStatus.OK, userREST.getUserByIdREST(userId).getStatusCode());
        
        verify(userService, times(2)).getUserById(userId);
    }
    
    @Test
    void testAddUserRESTNullUser() {
        NullPointerException npe = new NullPointerException("The user must be not null");
        
        when(userService.addUser(null)).thenThrow(npe);
        
        assertEquals("The user must be not null", userREST.addUserREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, userREST.addUserREST(null).getStatusCode());
        
        verify(userService, times(2)).addUser(null);
    }
    
    @Test
    void testAddUserRESTLessThan18YearsOld() {
    	UserDTO userDTO = new UserDTO();
    	LessThanEighteenYearsOldException lteyoe = new LessThanEighteenYearsOldException("You must be at least 18 years old");
    	
    	when(userService.addUser(userDTO)).thenThrow(lteyoe);
    	
    	assertEquals("You must be at least 18 years old", userREST.addUserREST(userDTO).getBody());
    	assertEquals(HttpStatus.BAD_REQUEST, userREST.addUserREST(userDTO).getStatusCode());
    	
    	verify(userService, times(2)).addUser(userDTO);
    }
    
    @Test
    void testAddUserREST() {
        UserDTO userDTO = new UserDTO();
        String messagge = "The user with id:" + null + ", name:" + null + ", surname:" + null + ", dateOfBirth:" + null + ", address:" + null + ", email:" + null + ", orders:" + null + " is created or updated successfully.";

        when(userService.addUser(userDTO)).thenReturn(userDTO);
		
        assertEquals(messagge, userREST.addUserREST(userDTO).getBody());
        assertEquals(HttpStatus.CREATED, userREST.addUserREST(userDTO).getStatusCode());
        
        verify(userService, times(2)).addUser(userDTO);
    }
    
    @Test
    void testUpdateUserRESTNullUser() {
        NullPointerException npe = new NullPointerException("The user must be not null");
        
        when(userService.updateUser(null)).thenThrow(npe);
        
        assertEquals("The user must be not null", userREST.updateUserREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, userREST.updateUserREST(null).getStatusCode());
        
        verify(userService, times(2)).updateUser(null);
    }
    
    @Test
    void testUpdateUserRESTEmptyUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        NoSuchElementException nsee = new NoSuchElementException("User with userId: " + userDTO.getId() + "not found...");
        
        when(userService.updateUser(userDTO)).thenThrow(nsee);

        assertEquals("User with userId: " + userDTO.getId() + "not found...", userREST.updateUserREST(userDTO).getBody());
        assertEquals(HttpStatus.NOT_FOUND, userREST.updateUserREST(userDTO).getStatusCode());
        
        verify(userService, times(2)).updateUser(userDTO);
    }
    
    @Test
    void testUpdateUserREST() {
        UserDTO userDTO = new UserDTO();
        String messagge = "The user with id:" + null + ", name:" + null + ", surname:" + null + ", dateOfBirth:" + null + ", address:" + null + ", email:" + null + ", orders:" + null + " is created or updated successfully.";
        
        when(userService.updateUser(userDTO)).thenReturn(userDTO);

		assertEquals(messagge, userREST.updateUserREST(userDTO).getBody());
        assertEquals(HttpStatus.OK, userREST.updateUserREST(userDTO).getStatusCode());
        
        verify(userService, times(2)).updateUser(userDTO);
    }
    
    @Test
    void testDeleteUserRESTNullUserId() {
        NullPointerException npe = new NullPointerException();
        
        when(userService.deleteUser(null)).thenThrow(npe);
        
        assertNull(userREST.deleteUserREST(null).getBody());
        assertEquals(HttpStatus.BAD_REQUEST, userREST.deleteUserREST(null).getStatusCode());
        
        verify(userService, times(2)).deleteUser(null);
    }
    
    @Test
    void testDeleteUserRESTEmptyUser() {
        Long userId = 1L;
        NoSuchElementException nsee = new NoSuchElementException();
        
        when(userService.deleteUser(userId)).thenThrow(nsee);
        
        assertNull(userREST.deleteUserREST(userId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, userREST.deleteUserREST(userId).getStatusCode());
        
        verify(userService, times(2)).deleteUser(userId);
    }
        
    @Test
    void testDeleteUserREST() {
        Long userId = 6L;
        String message = "The user with id:" + userId + ", name:" + null + ", surname:" + null + ", dateOfBirth:" + null + ", address:" + null + ", email:" + null + ", orders:" + null + " is deleted successfully.";
        
        when(userService.deleteUser(userId)).thenReturn(message);
        
        assertNull(userREST.deleteUserREST(userId).getBody());
        assertEquals(HttpStatus.NO_CONTENT, userREST.deleteUserREST(userId).getStatusCode());
        
        verify(userService, times(2)).deleteUser(userId);
    }
}
