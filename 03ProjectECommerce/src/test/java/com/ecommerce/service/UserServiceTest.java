package com.ecommerce.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.ecommerce.customexceptions.LessThanEighteenYearsOldException;
import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.dto.UserDTO;
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
        List<UserDTO> userDTOList = new ArrayList<>();

        assertNotNull(userServiceImpl.getAllUsers());
        assertTrue(userDTOList.getClass().equals(userServiceImpl.getAllUsers().getClass()));
    }

    @Test
    void testGetUserByIdEmptyUser() {
        Long userId = 6L;
        
        when(userDAORepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userServiceImpl.getUserById(userId));
    }
    
    @Test
    void testGetUserById() {
        Long userId = 6L;
        User user = new User();
        UserDTO userDTO = new UserDTO();
        
        when(userDAORepository.findById(userId)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userServiceImpl.getUserById(userId));
        assertNotNull(userServiceImpl.getUserById(userId));
        assertTrue(userDTO.getClass().equals(userServiceImpl.getUserById(userId).getClass()));
    }
    
    @Test
    void testAddUserLessThan18YearsOld() {
        UserDTO userDTO = new UserDTO();
        userDTO.setDateOfBirth(LocalDate.of(2010, 5, 10));

        assertThrows(LessThanEighteenYearsOldException.class, () -> userServiceImpl.addUser(userDTO));
    }
    
    @Test
    void testAddUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setDateOfBirth(LocalDate.of(2000, 5, 10));
        
        assertNotNull(userServiceImpl.addUser(userDTO));
        assertTrue(userDTO.getClass().equals(userServiceImpl.addUser(userDTO).getClass()));
    }
    
    @Test
    void testUpdateEmptyUser() {
        UserDTO userDTO = new UserDTO();
        
        when(userDAORepository.findById(null)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userServiceImpl.updateUser(userDTO));
    }
    
    @Test
    void testUpdateUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        Long userDTOId = 6L;
        userDTO.setId(userDTOId);

        when(userDAORepository.findById(userDTO.getId())).thenReturn(Optional.of(user));
        
        assertDoesNotThrow(() -> userServiceImpl.updateUser(userDTO));
        assertNotNull(userServiceImpl.updateUser(userDTO));
        assertTrue(userDTO.getClass().equals(userServiceImpl.updateUser(userDTO).getClass()));
    }
    
    @Test
    void testDeleteUserEmptyUser() {
        Long userId = 6L;
        
        when(userDAORepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userServiceImpl.deleteUser(userId));
    }
    
    @Test
    void testDeleteUser() {
    	Long userId = 6L;
        User user = new User();
        String messagge = "deletion";
        
        when(userDAORepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userDAORepository).deleteById(userId);
        
        assertDoesNotThrow(() -> userServiceImpl.deleteUser(userId));
        assertTrue(messagge.getClass().equals(userServiceImpl.deleteUser(userId).getClass()));
    }
}
