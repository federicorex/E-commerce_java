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
    	
    	assertTrue(userDTOList.getClass().equals(userREST.getAllUsersREST().getBody().getClass()));
        assertEquals(HttpStatus.OK, userREST.getAllUsersREST().getStatusCode());
    }
    
    @Test
    void testGetUserByIdEmptyUserREST() {
        Long userId = null;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(userService.getUserById(userId)).thenThrow(nsee);
        
        assertNull(userREST.getUserByIdREST(userId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, userREST.getUserByIdREST(userId).getStatusCode());
    }
    
    @Test
    void testGetUserByIdREST() {
        Long userId = 6L;
        UserDTO userDTO = new UserDTO();
        
        when(userService.getUserById(userId)).thenReturn(userDTO);
        
        assertTrue(userDTO.getClass().equals(userREST.getUserByIdREST(userId).getBody().getClass()));
        assertEquals(HttpStatus.OK, userREST.getUserByIdREST(userId).getStatusCode());
    }
    
    @Test
    void testAddUserREST() {
        UserDTO userDTO = new UserDTO();
        String messagge = "creation";

        assertTrue(messagge.getClass().equals(userREST.addUserREST(userDTO).getBody().getClass()));
        assertEquals(HttpStatus.CREATED, userREST.addUserREST(userDTO).getStatusCode());
    }
    
    @Test
    void testUpdateUseremptyUserREST() {
        UserDTO userDTO = new UserDTO();
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(userService.updateUser(userDTO)).thenThrow(nsee);

        assertNull(userREST.updateUserREST(userDTO).getBody());
        assertEquals(HttpStatus.NOT_FOUND, userREST.updateUserREST(userDTO).getStatusCode());
    }
    
    @Test
    void testUpdateUserREST() {
        UserDTO userDTO = new UserDTO();
        String messagge = "update";

        assertTrue(messagge.getClass().equals(userREST.updateUserREST(userDTO).getBody().getClass()));
        assertEquals(HttpStatus.OK, userREST.updateUserREST(userDTO).getStatusCode());
    }
    
    @Test
    void testDeleteUserEmptyUserREST() {
        Long userId = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(userService.deleteUser(userId)).thenThrow(nsee);
        
        assertNull(userREST.deleteUserREST(userId).getBody());
        assertEquals(HttpStatus.NOT_FOUND, userREST.deleteUserREST(userId).getStatusCode());
    }
        
    @Test
    void testDeleteUserREST() {
        Long userId = 6L;
        
        assertNull(userREST.deleteUserREST(userId).getBody());
        assertEquals(HttpStatus.NO_CONTENT, userREST.deleteUserREST(userId).getStatusCode());
    }
}
