package com.ecommerce.rest;

import static org.junit.Assert.assertEquals;
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

import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.entities.User;
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
        User user = new User();
        List<User> userList = new LinkedList<>();
        userList.add(user);

        when(userService.getAllUsers()).thenReturn(userList);

        assertEquals(HttpStatus.OK, userREST.getAllUsersREST().getStatusCode());
    }
    
    @Test
    void testGetUserByIdEmptyUserREST() {
        Long userId = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        when(userService.getUserById(userId)).thenThrow(nsee);
        
        assertEquals(HttpStatus.NOT_FOUND, userREST.getUserByIdREST(userId).getStatusCode());
    }
    
    @Test
    void testGetUserByIdREST() {
        Long userId = 6L;
        User user = new User();
        
        when(userService.getUserById(userId)).thenReturn(user);
        
        assertEquals(HttpStatus.OK, userREST.getUserByIdREST(userId).getStatusCode());
    }
    
    @Test
    void testAddUserREST() {
        UserDTO userDTO = new UserDTO();

        assertEquals(HttpStatus.CREATED, userREST.addUserREST(userDTO).getStatusCode());
    }
    
    @Test
    void testUpdateUserREST() {
        UserDTO userDTO = new UserDTO();

        assertEquals(HttpStatus.OK, userREST.updateUserREST(userDTO).getStatusCode());
    }
    
    @Test
    void testDeleteUserEmptyUserREST() {
        Long userId = 6L;
        NoSuchElementException nsee = new NoSuchElementException("error");
        
        doThrow(nsee).when(userService).deleteUser(userId);
        
        assertEquals(HttpStatus.NOT_FOUND, userREST.deleteUserREST(userId).getStatusCode());
    }
        
    @Test
    void testDeleteUserREST() {
        Long userId = 6L;
        User user = new User();
        
        when(userService.deleteUser(userId)).thenReturn(user);
        
        assertEquals(HttpStatus.NO_CONTENT, userREST.deleteUserREST(userId).getStatusCode());
    }
}
