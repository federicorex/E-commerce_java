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

import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.entities.User;
import com.ecommerce.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserDAORepository userDAORepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user = new User();
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userDAORepository.findAll()).thenReturn(userList);
        List<User> result = userServiceImpl.getAllUsers();

        assertEquals(userList, result);
    }

    @Test
    void testGetUserByIdEmptyUser() {
        Long id = 6L;
        
        when(userDAORepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userServiceImpl.getUserById(id));
    }
    
    @Test
    void testGetUserByIdExistingUser() {
        Long id = 6L;
        User user = new User();
        
        when(userDAORepository.findById(id)).thenReturn(Optional.of(user));

        assertEquals(user, userServiceImpl.getUserById(id));
    }
    
    @Test
    void testAddUser() {
        User user = new User();

        when(userDAORepository.save(user)).thenReturn(null);

        assertDoesNotThrow(() -> userServiceImpl.addUser(user));
    }
    
    @Test
    void testUpdateEmptyUser() {
        User user = new User();
        user.setId(6L);

        when(userDAORepository.findById(user.getId())).thenReturn(Optional.empty());
        
        assertThrows(NoSuchElementException.class, () -> userServiceImpl.updateUser(user));
    }
    
    @Test
    void testUpdateUser() {
        User user = new User();
        user.setId(6L);

        when(userDAORepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userDAORepository.save(user)).thenReturn(user);
        
        assertDoesNotThrow(() -> userServiceImpl.updateUser(user));
    }
    
    @Test
    void testDeleteUserEmptyUser() {
        Long id = 6L;
        
        when(userDAORepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userServiceImpl.deleteUser(id));
    }
    
    @Test
    void testDeleteUser() {
    	Long id = 6L;
        User user = new User();
        
        when(userDAORepository.findById(id)).thenReturn(Optional.of(user));
        assertEquals(user, userServiceImpl.getUserById(id));
        doNothing().when(userDAORepository).deleteById(id);

        assertDoesNotThrow(() -> userServiceImpl.deleteUser(id));
    }
    
}
