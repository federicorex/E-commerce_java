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

import java.time.LocalDate;
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

import com.ecommerce.customexceptions.LessThanEighteenYearsOldException;
import com.ecommerce.dal.UserDAORepository;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.entities.User;
import com.ecommerce.mappers.UserMapper;
import com.ecommerce.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserDAORepository userDAORepository;
    
    private MockedStatic<UserMapper> userMapperStatic;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userMapperStatic = Mockito.mockStatic(UserMapper.class);
    }
    
    @AfterEach
    void tearDown() {
    	userMapperStatic.close();
    }

    @Test
    void testGetAllUsers() {
        User user = new User();
        List<User> userList = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.add(user);

        when(userDAORepository.findAll()).thenReturn(userList);

        assertNotNull(userServiceImpl.getAllUsers());
        assertTrue(userDTOList.getClass().equals(userServiceImpl.getAllUsers().getClass()));
        
        verify(userDAORepository, times(2)).findAll();
    }
    
    @Test
    void testGetUserByIdNullId() {
        var exception = assertThrows(NullPointerException.class, () -> userServiceImpl.getUserById(null));
        assertEquals("The userId must be not null", exception.getMessage());
    }

    @Test
    void testGetUserByIdEmptyUser() {
        Long userId = 6L;
        
        when(userDAORepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userServiceImpl.getUserById(userId));
        
        verify(userDAORepository, times(1)).findById(userId);
    }
    
    @Test
    void testGetUserById() {
        Long userId = 6L;
        User user = new User();
        UserDTO userDTO = new UserDTO();
        
        when(userDAORepository.findById(userId)).thenReturn(Optional.of(user));
        userMapperStatic.when(() -> UserMapper.fromUserToUserDTO(Optional.of(user).get())).thenReturn(userDTO);

        assertDoesNotThrow(() -> userServiceImpl.getUserById(userId));
        assertEquals(userDTO, userServiceImpl.getUserById(userId));
        
        verify(userDAORepository, times(2)).findById(userId);
    }
    
    @Test
    void testAddUserNullUser() {
    	var exception = assertThrows(NullPointerException.class, () -> userServiceImpl.addUser(null));
    	assertEquals("The user must be not null", exception.getMessage());
    }
    
    @Test
    void testAddUserLessThan18YearsOld() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        userDTO.setDateOfBirth(LocalDate.of(2010, 5, 10));
        
        userMapperStatic.when(() -> UserMapper.fromUserDTOToUser(userDTO)).thenReturn(user);

        assertThrows(LessThanEighteenYearsOldException.class, () -> userServiceImpl.addUser(userDTO));
    }
    
    @Test
    void testAddUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        userDTO.setDateOfBirth(LocalDate.of(2000, 5, 10));
        
        userMapperStatic.when(() -> UserMapper.fromUserDTOToUser(userDTO)).thenReturn(user);
        when(userDAORepository.save(user)).thenReturn(user);
        
        assertEquals(userDTO, userServiceImpl.addUser(userDTO));
        
        verify(userDAORepository, times(1)).save(user);
    }
    
    @Test
    void testUpdateUserNullUser() {
    	var exception = assertThrows(NullPointerException.class, () -> userServiceImpl.updateUser(null));
        assertEquals("The user must be not null", exception.getMessage());
    }
    
    @Test
    void testUpdateUserEmptyUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        
        when(userDAORepository.findById(userDTO.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userServiceImpl.updateUser(userDTO));
        
        verify(userDAORepository, times(1)).findById(1L);
    }
    
    @Test
    void testUpdateUser() {
        UserDTO userDTO = new UserDTO();
        User user = new User();
        Long userDTOId = 6L;
        userDTO.setId(userDTOId);

        when(userDAORepository.findById(userDTO.getId())).thenReturn(Optional.of(user));
        userMapperStatic.when(() -> UserMapper.fromUserDTOToUser(userDTO)).thenReturn(user);
        when(userDAORepository.save(user)).thenReturn(user);
        
        assertEquals(userDTO, userServiceImpl.updateUser(userDTO));
        
        verify(userDAORepository, times(1)).findById(userDTO.getId());
        verify(userDAORepository, times(1)).save(user);
    }
    
    @Test
    void testDeleteUserNullId() {
    	var exception = assertThrows(NullPointerException.class, () -> userServiceImpl.deleteUser(null));
        assertEquals("The userId must be not null", exception.getMessage());
    }
    
    @Test
    void testDeleteUserEmptyUser() {
        Long userId = 6L;
        
        when(userDAORepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userServiceImpl.deleteUser(userId));
        
        verify(userDAORepository, times(1)).findById(userId);
    }
    
    @Test
    void testDeleteUser() {
    	Long userId = 6L;
        User user = new User();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        String messagge = "The user with id:" + 6 + ", name:" + null + ", surname:" + null + ", dateOfBirth:" + null
				+ ", address:" + null + ", email:" + null + ", orders:" + null + " is deleted successfully.";
        
        when(userDAORepository.findById(userId)).thenReturn(Optional.of(user));
        userMapperStatic.when(() -> UserMapper.fromUserToUserDTO(Optional.of(user).get())).thenReturn(userDTO);
        doNothing().when(userDAORepository).deleteById(userId);
        
        assertEquals(messagge, userServiceImpl.deleteUser(userId));
        
        verify(userDAORepository, times(1)).findById(userId);
        verify(userDAORepository, times(1)).deleteById(userId);
    }
}
