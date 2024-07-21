package com.ecommerce.services;

import java.util.List;

import com.ecommerce.dto.UserDTO;

public interface UserService {

	/**
	 * CRUD operations
	 * @return
	 */
	
	List<UserDTO> getAllUsers();
	UserDTO getUserById(Long id);
	UserDTO addUser(UserDTO userDTO);
	UserDTO updateUser(UserDTO userDTO);
	String deleteUser(Long id);
}
